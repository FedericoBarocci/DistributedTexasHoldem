package breads_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.updater.GameUpdater;

public abstract class AbstractGameService extends UnicastRemoteObject implements
		GameService {

	private static final long serialVersionUID = 7999272435762156455L;

	protected final String nodeId;
	protected final GamePlayersKeeper playersKeeper;

	private final DistributedController distributedController;

	public AbstractGameService(String nodeId,
			GamePlayersKeeper playersKeeper,
			DistributedController distributedController) throws RemoteException {
		super();
		
		this.nodeId = nodeId;
		this.playersKeeper = playersKeeper;
		this.distributedController = distributedController;
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
}
