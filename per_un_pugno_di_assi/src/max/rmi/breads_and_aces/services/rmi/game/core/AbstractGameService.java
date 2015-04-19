package breads_and_aces.services.rmi.game.core;

import it.unibo.cs.sd.poker.gui.controllers.ControllerLogic;
import it.unibo.cs.sd.poker.gui.controllers.GameUpdater;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.DealEventException;
import it.unibo.cs.sd.poker.gui.view.GameView;
import it.unibo.cs.sd.poker.gui.view.GameViewInitializerReal;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;

public abstract class AbstractGameService extends UnicastRemoteObject implements GameService {

	private static final long serialVersionUID = 7999272435762156455L;
	
	protected final Game game;
	protected final String nodeId;
	protected final GamePlayersKeeper playersKeeper;
	
	private final GameViewInitializerReal gameViewInitializer;
	private ControllerLogic controllerLogic;

	public AbstractGameService(String nodeId, Game game, GamePlayersKeeper playersKeeper, GameViewInitializerReal gameViewInitializer) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.game = game;
		this.playersKeeper = playersKeeper;
		this.gameViewInitializer = gameViewInitializer;

		this.controllerLogic = new ControllerLogic(game, gameViewInitializer.get(), nodeId);
	}

	@Override
	public void echo(String playerId, String string) throws RemoteException {
		if (game.isStarted())
			System.out.println(playerId+" says : "+string);
	}
	
	@Override
	public void receiveStartGame(String whoHasToken) {
		playersKeeper.getPlayer(whoHasToken).receiveToken();
		
		System.out.println("Game can start!");
		
		GameView gameView = gameViewInitializer.get();
		gameView.setViewToken(playersKeeper.getPlayer(whoHasToken).getName());
	}
	
	@Override
	public void receiveBucket() throws RemoteException {
		playersKeeper.getPlayer(nodeId).receiveToken();
		
		System.out.println("Ho ricevuto il token");
		
		GameView gameView = gameViewInitializer.get();
		gameView.setViewToken(playersKeeper.getPlayer(nodeId).getName());
	}
	
	@Override
	public void receiveCheck(String fromPlayer, String toPlayer) {
		try {
			controllerLogic.check(fromPlayer, toPlayer);
		} catch (DealEventException e) {
			System.out.println("receiveCheck should not be here");
		}
	}
	
	@Override
	public void receiveCheckAndDeal(String fromPlayer, String toPlayer, GameUpdater gameUpdater) {
		try {
			controllerLogic.check(fromPlayer, toPlayer);
		} catch (DealEventException e) {
			game.update(gameUpdater);
		}
	}
	
	/*@Override
	public void sayIHaveToken(String from, String id) {
		playersKeeper.getPlayer(id).receiveBucket(from);
		
		GameView gameView = gameViewInitializer.get();
		gameView.setViewToken(playersKeeper.getPlayer(id).getName());
	}*/
	
	@Override
	public void receiveCall(String fromPlayer, int coins) {
		// TODO Auto-generated method stub
		
	}
}
