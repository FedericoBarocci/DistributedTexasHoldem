package breads_and_aces.rmi.game.init.servable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Scanner;

import javax.inject.Inject;

import breads_and_aces.node.model.ConnectionInfo;
import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.init.servable.registrar.registrars.GameRegistrarInit;
import breads_and_aces.rmi.game.init.servable.registrar.registrars.GameRegistrarStarted;
import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.game.model.Players;
import breads_and_aces.rmi.services.rmi.game._init.PlayersSynchronizar;
import breads_and_aces.rmi.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.InputUtils;

public class GameInitializerServableUsingRMIAndShellInput extends AbstractGameInitializerServable {
	
	@Inject
	public GameInitializerServableUsingRMIAndShellInput(GameRegistrarInit gameRegistrarStateInit, Game game, Players players) {
		super(gameRegistrarStateInit, game, players);
	}

	// TODO real version will use GUI
	@Override
	protected void waitForPlayersAndStartGame() {
		System.out.println("Acting as initializer: waiting for players");
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while (!next.equals(START_GAME)) {
			next = scanner.next();
		}
		gameRegistrar = new GameRegistrarStarted(); // maintain this line in gui version
		System.out.print("Game can start! ");
	}

	// using rmi
	@Override
	protected void sendNodesConnectionInfosToNode(String targetnodeId, ConnectionInfo targetnodeConnectionInfo, Map<String, Player> playersInfos, Players players) {
//		String nodeId = targetPlayer.getId();
//		ConnectionInfo connectionInfo = targetPlayer.getConnectionInfo();
		try {
System.out.print(targetnodeId+" ");
			PlayersSynchronizar gameServerFromRemoteNode = ServiceUtils.lookup(targetnodeConnectionInfo.getAddress(), targetnodeConnectionInfo.getPort());
			gameServerFromRemoteNode.synchronizeAllPlayersFromInitiliazer(playersInfos);
		} catch (MalformedURLException e) {
		} catch (RemoteException|NotBoundException e) {
			// remote host not responding, so remove
			players.getPlayersInfos().remove(targetnodeId);
		}
	}
}
