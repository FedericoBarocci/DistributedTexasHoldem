package breads_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.model.controller.DistributedController;
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
	public void receiveAction(String fromPlayer, Action action) {
		distributedController.setRemoteAction(fromPlayer, action);
	}

	@Override
	public void receiveActionAndDeal(String fromPlayer, Action action, GameUpdater gameUpdater) {
		distributedController.updateRemoteAction(fromPlayer, action, gameUpdater);
	}

	@Override
	public void receiveWinnerEndGame(String fromPlayer, Action action) {
		distributedController.setRemoteAction(fromPlayer, action);
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
	public void removePlayersAndService(Collection<String> crashedPeers) throws RemoteException {
		// TODO remove gameService from GameServiceKeeper and player from playersKeeper 
//		distributedController.RIMUOVI_PLAYERS
		crashedPeers.forEach(c->{
			crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(c);
		});
	}
}
