package bread_and_aces.utils.keepers;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.players.player.PlayerRegistrationId;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult.RegistrationStatus;
import bread_and_aces.registration.model.NodeConnectionInfos;
import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import bread_and_aces.services.rmi.game.utils.ServiceUtils;
import bread_and_aces.utils.DevPrinter;

@Singleton
public class KeepersUtilDelegate implements KeepersUtilDelegateForServable, KeepersUtilDelegateForClientable {

	private final RegistrarPlayersKeeper playersKeeper;
	private final GameServicesKeeper gameServiceKeeper;
//	private final Printer printer;

	@Inject
	public KeepersUtilDelegate(RegistrarPlayersKeeper playersKeeper,
			GameServicesKeeper gameServiceKeeper
			/*,Printer printer*/) {
		this.playersKeeper = playersKeeper;
		this.gameServiceKeeper = gameServiceKeeper;
//		this.printer = printer;
	}

	/*
	 * servable zone - start
	 */
	@Override
	public boolean contains(String playerId) {
		if (playersKeeper.contains(playerId)) { // && gameServiceKeeper.contains(playerId)) {
			return true;
		}
		
		return false;
	}

	@Override
	public void registerPlayer(String playerId) {
		long now = System.currentTimeMillis();

		PlayerRegistrationId playerRegistrationId = new PlayerRegistrationId(playerId, now);
		Player player = new Player(playerId, now);

		playersKeeper.addPlayer(playerRegistrationId, player);
	}

	
	@Override
	public void registerServable(String playerId) {
		registerPlayer(playerId);
		playersKeeper.setMyName(playerId);
	}

	@Override
	public RegistrationResult registerClientable(
			NodeConnectionInfos nodeConnectionInfos, String playerId) {
		try {
			GameService gameService = ServiceUtils.lookup(
					nodeConnectionInfos.getAddress(),
					nodeConnectionInfos.getPort() );

         // here we register gameservice in keeper, since binding was fine
			gameServiceKeeper.addService(playerId, gameService);

			// here we register node and player, since binding was fine
			registerPlayer(playerId);

			return new RegistrationResult(RegistrationStatus.OK);
		} catch (MalformedURLException e) {
			DevPrinter.println(e.getMessage());
			return new RegistrationResult(RegistrationStatus.ERROR);
		} catch (RemoteException | NotBoundException e) {
			DevPrinter.println("Player " + playerId+ " not registered: no more responding");
			return new RegistrationResult(RegistrationStatus.ERROR);
		}
	}
	/*
	 * servable zone - end
	 */

	/*
	 * clientable zone - start
	 */
	/**
	 * @param nodesConnectionInfosMap
	 * @param playersMap
	 * @return
	 */
	@Override
	public List<String> synchronizeNodesPlayersGameservicesLocallyAsClientable(
			List<NodeConnectionInfos> nodesConnectionInfos,
			Map<PlayerRegistrationId, Player> playersMap) {
		playersKeeper.addPlayers(playersMap);

		List<String> crashedDuringSync = new ArrayList<>();

		nodesConnectionInfos.iterator().forEachRemaining(
				nodeConnectionInfos -> {
					String id = nodeConnectionInfos.getNodeId();
					try {
						GameService gameService = ServiceUtils.lookup(
								nodeConnectionInfos.getAddress(),
								nodeConnectionInfos.getPort());
						gameServiceKeeper.addService(id, gameService);
					} catch (MalformedURLException e) {
					} catch (RemoteException | NotBoundException e) {
						if (!crashedDuringSync.contains(id)) {
							crashedDuringSync.add(id);
							playersKeeper.remove(id);
						}
					}
				});
		return crashedDuringSync;
	}
	/*
	 * clientable zone - end
	 */
}
