package breads_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import bread_and_aces.utils.DevPrinter;
import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.utils.crashhandler.CrashHandler;

public abstract class AbstractGameService extends UnicastRemoteObject implements
		GameService {

	private static final long serialVersionUID = 7999272435762156455L;

	private final DistributedController distributedController;

	private final CrashHandler crashHandler;

	public AbstractGameService(
			DistributedController distributedController,
			CrashHandler crashHandler) throws RemoteException {
		super();
		
//		this.nodeId = nodeId;
//		this.playersKeeper = playersKeeper;
		this.distributedController = distributedController;
		this.crashHandler = crashHandler;
	}

	@Override
	public void echo(String playerId, String string) throws RemoteException {
		System.out.println(playerId + " says : " + string);
	}

	@Override
	public void receiveBucket() throws RemoteException {
		distributedController.handleToken();
	}

	@Override
	public void receiveAction(String fromPlayer, Action action)/* throws RemoteException*/ {
		distributedController.setActionOnReceive(fromPlayer, action);
	}

	@Override
	public void receiveActionAndDeal(String fromPlayer, Action action, GameUpdater gameUpdater) throws RemoteException {
		distributedController.setActionOnReceive(fromPlayer, action, gameUpdater);
	}

	@Override
	public void receiveWinnerEndGame(String fromPlayer, Action action) throws RemoteException {
		distributedController.setActionOnReceive(fromPlayer, action);
	}

	/*
	 * @Override public void receiveCheck(String fromPlayer, String toPlayer) {
	 * try { controllerLogic.check(fromPlayer, toPlayer); } catch
	 * (DealEventException e) {
	 * System.out.println("receiveCheck should not be here"); } }
	 */

	/*
	 * @Override public void sayIHaveToken(String from, String id) {
	 * playersKeeper.getPlayer(id).receiveBucket(from);
	 * 
	 * GameView gameView = gameViewInitializer.get();
	 * gameView.setViewToken(playersKeeper.getPlayer(id).getName()); }
	 */
	
	@Override
	public void removeService(Collection<String> crashedPeers) throws RemoteException {
		new DevPrinter(new Throwable()).println("");
		crashedPeers.forEach(c->{
			crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(c);
			distributedController.removePlayer(c);
		});
	}
	@Override
	public void removeService(String crashedPeer) throws RemoteException {
		new DevPrinter(new Throwable()).println("");
		crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(crashedPeer);
		distributedController.removePlayer(crashedPeer);
	}
}
