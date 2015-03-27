package breads_and_aces.game.init.registrar.utils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult.Cause;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.GameServicesRegistry;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class RegistriesUtils {

	private final NodesConnectionInfosRegistry nodesConnectionInfosRegistry;
	private final PlayersRegistry playersRegistry;
	private final GameServicesRegistry gameServiceRegistry;
	private final Printer printer;

	@Inject
	public RegistriesUtils(NodesConnectionInfosRegistry nodesConnectionInfosRegistry, PlayersRegistry playersRegistry, GameServicesRegistry gameServiceRegistry, Printer printer) {
		this.nodesConnectionInfosRegistry = nodesConnectionInfosRegistry;
		this.playersRegistry = playersRegistry;
		this.gameServiceRegistry = gameServiceRegistry;
		this.printer = printer;
	}
	
	public boolean contains(String playerId) {
		if (playersRegistry.contains(playerId) && nodesConnectionInfosRegistry.contains(playerId) && gameServiceRegistry.contains(playerId))
			return true;
		return false;
	}

	public RegistrationResult addNodePlayerGameService(NodeConnectionInfos nodeConnectionInfos, String playerId) {
//		List<String> crashed = new ArrayList<>(); 
		try {
			GameService gameService = ServiceUtils.lookup(nodeConnectionInfos.getAddress(), nodeConnectionInfos.getPort());
			gameServiceRegistry.addService(playerId, gameService);
			
			// here we register player and node
			long now = System.currentTimeMillis();
			nodeConnectionInfos.setRegisterTime(now);
			nodesConnectionInfosRegistry.addNodeInfo(nodeConnectionInfos);
			
			PlayerRegistrationId playerRegistrationId = new PlayerRegistrationId(playerId, now);
			Player player = new Player(playerId);
			
			playersRegistry.addPlayer(playerRegistrationId,player);
			
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

	public void removeNodePlayerGameService(String id) {
		gameServiceRegistry.removeService(id);
		nodesConnectionInfosRegistry.removeNode(id);
		playersRegistry.remove(id);
	}
	
	
}
