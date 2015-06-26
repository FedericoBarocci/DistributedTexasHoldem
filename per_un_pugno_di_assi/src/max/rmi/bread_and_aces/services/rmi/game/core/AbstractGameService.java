package bread_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bread_and_aces.game.model.controller.DistributedControllerForRemoteHandling;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.updater.GameUpdater;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

public abstract class AbstractGameService extends UnicastRemoteObject implements GameService {

	private static final long serialVersionUID = 7999272435762156455L;

	
	private final CrashHandler crashHandler;
	private final String nodeId;
	protected final DistributedControllerForRemoteHandling distributedControllerForRemoteHandling;
	protected final Pinger pinger;
	
	public AbstractGameService(String nodeId,
			DistributedControllerForRemoteHandling distributedControllerForRemoteHandling,
			Pinger pinger,
			CrashHandler crashHandler) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.distributedControllerForRemoteHandling = distributedControllerForRemoteHandling;
		this.pinger = pinger;
		this.crashHandler = crashHandler;
	}
	
	@Override
	public String getId() {
		return nodeId;
	}

	@Override
	public void echo(String playerId, String string) throws RemoteException {
		DevPrinter.println(playerId + " says : " + string);
	}
	
	/**
	 * do nothing. if it is dead, knocking client will throw a RMI RemoteException
	 */
	@Override
	public void isAlive() throws RemoteException {}

	@Override
	public void receiveAction(String fromPlayer, ActionKeeper actionKeeper) {
		distributedControllerForRemoteHandling.setActionOnReceive(fromPlayer, actionKeeper);
		pinger.ping();
	}

	@Override
	public void receiveActionAndDeal(String fromPlayer, ActionKeeper actionKeeper, GameUpdater gameUpdater) throws RemoteException {
		distributedControllerForRemoteHandling.setActionOnReceive(fromPlayer, actionKeeper, gameUpdater);
		pinger.ping();
	}
	
	/*@Override
	public void ping() {
		if (beeperHandle!=null) {
			beeperHandle.cancel(true);
		}
				
		beeperHandle = scheduler.scheduleAtFixedRate(checkLeaderIsAlive, TIMEOUT, TIMEOUT , TimeUnit.SECONDS);
	}*/

	@Override
	public void receiveWinnerEndGame(String fromPlayer, ActionKeeper actionKeeper) throws RemoteException {
		distributedControllerForRemoteHandling.setActionOnReceive(fromPlayer, actionKeeper);
	}

	/*
	 * @Override public void receiveCheck(String fromPlayer, String toPlayer) {
	 * try { controllerLogic.check(fromPlayer, toPlayer); } catch
	 * (DealEventException e) {
	 * DevPrinter.println("receiveCheck should not be here"); } }
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
		DevPrinter.println( /*new Throwable()*/ );
		crashHandler.removeLocallyFromEverywhere(crashedPeer);
//		distributedController.removePlayer(crashedPeer);
	}
}
