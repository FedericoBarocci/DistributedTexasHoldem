package breads_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.PlayersKeeper;

public abstract class AbstractGameService 
	extends UnicastRemoteObject 
	implements GameService {

	protected final Game game;
	protected final String nodeId;
	protected final PlayersKeeper playersKeeper;

	public AbstractGameService(String nodeId
			, Game game 
			, PlayersKeeper playersKeeper
			) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.game = game;
		this.playersKeeper = playersKeeper;
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
	
}
