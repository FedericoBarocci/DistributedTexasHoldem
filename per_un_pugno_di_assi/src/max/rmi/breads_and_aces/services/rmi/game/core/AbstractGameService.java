package breads_and_aces.services.rmi.game.core;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.gui.view.GameView;
import it.unibo.cs.sd.poker.gui.view.GameViewInitializerReal;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;

public abstract class AbstractGameService 
	extends UnicastRemoteObject 
	implements GameService {

	protected final Game game;
	protected final String nodeId;
	protected final GamePlayersKeeper playersKeeper;
	private final GameViewInitializerReal gameViewInitializer;

	public AbstractGameService(String nodeId
			, Game game 
			, GamePlayersKeeper playersKeeper
			, GameViewInitializerReal gameViewInitializer
			) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.game = game;
		this.playersKeeper = playersKeeper;
		this.gameViewInitializer = gameViewInitializer;
	}

	private static final long serialVersionUID = 7999272435762156455L;

	@Override
	public void echo(String playerId, String string) throws RemoteException {
		if (game.isStarted())
			System.out.println(playerId+" says : "+string);
	}
	
	@Override
	public void receiveBucket() throws RemoteException {
		playersKeeper.getPlayer(nodeId).receiveBucket();
	}
	
	@Override
	public void receiveCheck(String fromPlayer) {
		// TODO eventually fix with Benny code
		playersKeeper.getPlayer(fromPlayer).setAction(Action.CHECK);
		GameView gameView = gameViewInitializer.get();
		gameView.lblMessage.setText("Ho ricevuto check da " + fromPlayer);
		
	}
	@Override
	public void receiveCall(String fromPlayer, int coins) {
		// TODO Auto-generated method stub
		
	}
}
