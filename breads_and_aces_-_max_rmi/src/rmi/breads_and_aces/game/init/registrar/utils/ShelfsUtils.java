package breads_and_aces.game.init.registrar.utils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult.Cause;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.node.NodesConnectionInfosShelf;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.GameServicesShelf;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class ShelfsUtils {

	private final NodesConnectionInfosShelf nodesConnectionInfosShelf;
	private final PlayersShelf playersShelf;
	private final GameServicesShelf gameServiceShelf;
//	private final CrashHandler crashHandler;
	
	private final Printer printer;

	@Inject
	public ShelfsUtils(NodesConnectionInfosShelf nodesConnectionInfosShelf, PlayersShelf playersShelf, GameServicesShelf gameServiceShelf, /*CrashHandler crashHandler,*/ Printer printer) {
		this.nodesConnectionInfosShelf = nodesConnectionInfosShelf;
		this.playersShelf = playersShelf;
		this.gameServiceShelf = gameServiceShelf;
//		this.crashHandler = crashHandler;
		this.printer = printer;
	}
	
	public boolean contains(String playerId) {
		if (playersShelf.contains(playerId) && nodesConnectionInfosShelf.contains(playerId) && gameServiceShelf.contains(playerId))
			return true;
		return false;
	}

	public RegistrationResult registerNodePlayerGameServiceAsServable(NodeConnectionInfos nodeConnectionInfos, String playerId) {
//		List<String> crashed = new ArrayList<>(); 
		try {
			GameService gameService = ServiceUtils.lookup(nodeConnectionInfos.getAddress(), nodeConnectionInfos.getPort());
			gameServiceShelf.addService(playerId, gameService);
			
			// here we register player and node
			long now = System.currentTimeMillis();
			nodeConnectionInfos.setRegisterTime(now);
			nodesConnectionInfosShelf.addNodeInfo(nodeConnectionInfos);
			
			PlayerRegistrationId playerRegistrationId = new PlayerRegistrationId(playerId, now);
			Player player = new Player(playerId);
			
			playersShelf.addPlayer(playerRegistrationId,player);
			
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
	public List<String> synchronizeNodesPlayersGameservicesLocallyAsClientable(Map<String, NodeConnectionInfos> nodesConnectionInfosMap, Map<PlayerRegistrationId, Player> playersMap) {
		nodesConnectionInfosShelf.setNodesConnectionInfos(nodesConnectionInfosMap);
		playersShelf.setPlayers(playersMap);
		
		List<String> crashedDuringSync = new ArrayList<>();

		nodesConnectionInfosMap.entrySet().stream().forEach(entry->{
//		Iterator<Entry<String, NodeConnectionInfos>> iterator = nodesConnectionInfosMap.entrySet().iterator();
//		while (iterator.hasNext()) {
//			Entry<String, NodeConnectionInfos> entry = iterator.next();
			String id = entry.getKey();
			NodeConnectionInfos nodeConnectionInfos = entry.getValue();
			try {
				GameService gameService = ServiceUtils.lookup(nodeConnectionInfos.getAddress(), nodeConnectionInfos.getPort());
				gameServiceShelf.addService(id, gameService);
			} catch (MalformedURLException e) { 
			} catch (RemoteException|NotBoundException e) {
				if (!crashedDuringSync.contains(id))
					crashedDuringSync.add(id);
			}
		}
		);
//		crashHandler.handleRemovingLocally(crashedDuringSync);
		return crashedDuringSync;
	}

	public void removeNodePlayerGameService(String id) {
		gameServiceShelf.removeService(id);
		nodesConnectionInfosShelf.removeNode(id);
		playersShelf.remove(id);
	}
	
	
}
