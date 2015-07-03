package bread_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bread_and_aces.game.model.controller.DistributedControllerForRemoteHandling;
import bread_and_aces.game.model.oracle.actions.Message;
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
	public void receiveAction(String fromPlayer, Message message) {
		DevPrinter.println();
		
		distributedControllerForRemoteHandling.setActionOnReceive(fromPlayer, message);
		pinger.ping();
	}

	@Override
	public void receiveActionAndDeal(String fromPlayer, Message message, GameUpdater gameUpdater) throws RemoteException {
		DevPrinter.println();
		
		distributedControllerForRemoteHandling.setActionOnReceive(fromPlayer, message, gameUpdater);
		pinger.ping();
	}
	
	@Override
	public void receiveWinnerEndGame(String fromPlayer, Message message) throws RemoteException {
		DevPrinter.println();
		
		distributedControllerForRemoteHandling.setActionOnReceive(fromPlayer, message);
	}

	@Override
	public void removeCrashedPeerService(String crashedPeer) throws RemoteException {
		DevPrinter.println();
		crashHandler.removeLocallyFromEverywhere(crashedPeer);
	}
}
