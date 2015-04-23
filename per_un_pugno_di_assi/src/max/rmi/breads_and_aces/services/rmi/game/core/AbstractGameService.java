package breads_and_aces.services.rmi.game.core;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.gui.controllers.ControllerLogic;
import it.unibo.cs.sd.poker.gui.controllers.GameUpdater;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.DealEventException;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.SinglePlayerException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;

public abstract class AbstractGameService extends UnicastRemoteObject implements
		GameService {

	private static final long serialVersionUID = 7999272435762156455L;

	protected final Game game;
	protected final String nodeId;
	protected final GamePlayersKeeper playersKeeper;

//	private final GameViewInitializerReal gameViewInitializer;
	private ControllerLogic controllerLogic;

	public AbstractGameService(String nodeId, Game game,
			GamePlayersKeeper playersKeeper,
//			GameViewInitializerReal gameViewInitializer,
			ControllerLogic controllerLogic) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.game = game;
		this.playersKeeper = playersKeeper;
//		this.gameViewInitializer = gameViewInitializer;
		this.controllerLogic = controllerLogic;

//		this.controllerLogic = new ControllerLogic(game,
//				gameViewInitializer.get(), nodeId);
	}

	@Override
	public void echo(String playerId, String string) throws RemoteException {
		if (game.isStarted())
			System.out.println(playerId + " says : " + string);
	}

	@Override
	public void receiveBucket() throws RemoteException {
		controllerLogic.handleToken();
	}

	/*@Override
	public void receiveStartGame(String whoHasToken) {
		playersKeeper.getPlayer(whoHasToken).receiveToken();

		System.out.println("Game can start!");

		GameView gameView = gameViewInitializer.get();
		gameView.setViewToken(playersKeeper.getPlayer(whoHasToken).getName());
	}*/

	@Override
	public void receiveAction(String fromPlayer, Action action) {
		System.out.println("receiveAction " + fromPlayer + " :: " + action);
		try {
			controllerLogic.updateAction(fromPlayer, action);
		} catch (DealEventException e) {
			System.out.println("receiveCheck should not be here");
		} catch (SinglePlayerException e) {
			System.out.println("receiveCheck should not be here");
		}
	}

	@Override
	public void receiveActionAndDeal(String fromPlayer, Action action,
			GameUpdater gameUpdater) {
		try {
			controllerLogic.updateAction(fromPlayer, action);
		} catch (DealEventException e) {
			System.out.println("receiveActionAndDeal DealEventException");
			controllerLogic.update(gameUpdater);
		} catch (SinglePlayerException e) {
			System.out.println("receiveActionAndDeal SinglePlayerException");
			controllerLogic.update(gameUpdater);
		}
	}

	@Override
	public void receiveWinnerEndGame(String fromPlayer, Action action) {
		try {
			controllerLogic.updateAction(fromPlayer, action);
		} catch (DealEventException e) {
			System.out.println("receiveWinnerEndGame DealEventException");
		} catch (SinglePlayerException e) {
			System.out.println("receiveWinnerEndGame SinglePlayerException");
		}
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
}
