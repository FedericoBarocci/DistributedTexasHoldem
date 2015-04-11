package breads_and_aces.utils.keepers;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerFactory;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult.Cause;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class KeepersUtils {

	private final PlayersKeeper playersKeeper;
	private final GameServicesKeeper gameServiceKeeper;
//	private final CrashHandler crashHandler;
	
	private final Printer printer;
	private final PlayerFactory playerFactory;

	@Inject
	public KeepersUtils(
			PlayersKeeper playersKeeper, 
			GameServicesKeeper gameServiceKeeper, 
			PlayerFactory playerFactory, 
			Printer printer) {
		this.playersKeeper = playersKeeper;
		this.gameServiceKeeper = gameServiceKeeper;
		this.playerFactory = playerFactory;
		this.printer = printer;
	}
	
	public boolean contains(String playerId) {
		if (playersKeeper.contains(playerId) && gameServiceKeeper.contains(playerId))
			return true;
		return false;
	}
	
	public void registerPlayer(/*NodeConnectionInfos nodeConnectionInfos,*/ String playerId) {
		long now = System.currentTimeMillis();
//		nodeConnectionInfos.setRegisterTime(now);
		
		PlayerRegistrationId playerRegistrationId = new PlayerRegistrationId(playerId, now);
		Player player = playerFactory.create(playerId);
		
		playersKeeper.addPlayer(playerRegistrationId,player);
	}

	public RegistrationResult registerNodePlayerGameServiceAsServable(NodeConnectionInfos nodeConnectionInfos, String playerId) {
//		List<String> crashed = new ArrayList<>(); 
		try {
			GameService gameService = ServiceUtils.lookup(nodeConnectionInfos.getAddress(), nodeConnectionInfos.getPort());
			gameServiceKeeper.addService(playerId, gameService);
			
			// here we register node and player, because gameservice was fine
			registerPlayer(/*nodeConnectionInfos,*/ playerId);
			
			return new RegistrationResult(true, Cause.OK);
		} catch (MalformedURLException e) {
			printer.print(e.getMessage());
			return new RegistrationResult(false, Cause.ERROR);
		} catch (RemoteException | NotBoundException e) {
//			crashed.add(playerId); // TODO really needed ?
			printer.print("Player "+playerId+" not registered: no more responding");
			return new RegistrationResult(false, Cause.ERROR);
		}
	}
	
	
	/**
	 * @param nodesConnectionInfosMap
	 * @param playersMap
	 * @return 
	 */
	public List<String> synchronizeNodesPlayersGameservicesLocallyAsClientable(List<NodeConnectionInfos> nodesConnectionInfos, Map<PlayerRegistrationId, Player> playersMap) {
//		nodesConnectionInfosShelf.setNodesConnectionInfos(nodesConnectionInfosMap);
		playersKeeper.setPlayers(playersMap);
		
		List<String> crashedDuringSync = new ArrayList<>();

		nodesConnectionInfos.iterator().forEachRemaining(nodeConnectionInfos->{
//			String id = nci.getKey();
			String id = nodeConnectionInfos.getNodeId();
//			NodeConnectionInfos nodeConnectionInfos = nci.getValue();
			try {
				GameService gameService = ServiceUtils.lookup(nodeConnectionInfos.getAddress(), nodeConnectionInfos.getPort());
				gameServiceKeeper.addService(id, gameService);
//				playersMap.keySet().stream().parallel().filter(pri->{return pri.getId().equals(id);}).findFirst().ifPresent(pri->
//					{ playersShelf.addPlayer(pri, playersMap.get(pri) );} );
			} catch (MalformedURLException e) { 
			} catch (RemoteException|NotBoundException e) {
				if (!crashedDuringSync.contains(id)) {
					crashedDuringSync.add(id);
					
					// TODO to be improved: could be better not to add playersmap to shelfs, 
					// instead add per-playerid after game service is bound? (the row 101 commented above  
					playersKeeper.remove(id);
				}
			}
		}
		);
//		crashHandler.handleRemovingLocally(crashedDuringSync);
		return crashedDuringSync;
	}

	public void removePlayerGameService(String id) {
		gameServiceKeeper.removeService(id);
//		nodesConnectionInfosShelf.removeNode(id);
		playersKeeper.remove(id);
	}
}
