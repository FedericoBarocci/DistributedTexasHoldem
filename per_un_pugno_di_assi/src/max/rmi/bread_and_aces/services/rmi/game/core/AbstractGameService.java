package bread_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bread_and_aces.game.model.controller.DistributedController;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.updater.GameUpdater;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

public abstract class AbstractGameService extends UnicastRemoteObject implements
		GameService {

	private static final long serialVersionUID = 7999272435762156455L;

	private final DistributedController distributedController;

	private final CrashHandler crashHandler;

	protected final String nodeId;

	public AbstractGameService(String nodeId,
			DistributedController distributedController,
			CrashHandler crashHandler) throws RemoteException {
		super();
		
		this.nodeId = nodeId;
//		this.playersKeeper = playersKeeper;
		this.distributedController = distributedController;
		this.crashHandler = crashHandler;
	}
	
	@Override
	public String getId() {
		return nodeId;
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
	public void receiveAction(String fromPlayer, ActionKeeper actionKeeper)/* throws RemoteException*/ {
		distributedController.setActionOnReceive(fromPlayer, actionKeeper);
	}

	@Override
	public void receiveActionAndDeal(String fromPlayer, ActionKeeper actionKeeper, GameUpdater gameUpdater) throws RemoteException {
		distributedController.setActionOnReceive(fromPlayer, actionKeeper, gameUpdater);
	}

	@Override
	public void receiveWinnerEndGame(String fromPlayer, ActionKeeper actionKeeper) throws RemoteException {
		distributedController.setActionOnReceive(fromPlayer, actionKeeper);
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
	
	/*@Override
	public void removeService(Collection<String> crashedPeers) throws RemoteException {
		DevPrinter.println(new Throwable());
		crashedPeers.forEach(c->{
			crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(c);
			distributedController.removePlayer(c);
		});
	}*/
	@Override
	public void removeCrashedPeerService(String crashedPeer) throws RemoteException {
		DevPrinter.println(new Throwable());
		crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(crashedPeer);
//		distributedController.removePlayer(crashedPeer);
	}
}
