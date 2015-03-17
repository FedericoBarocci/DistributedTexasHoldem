package breads_and_aces.rmi.services.rmi.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.Players;

public abstract class AbstractGameService 
	extends UnicastRemoteObject 
	implements GameService {

	protected final Game game;
	protected final Players players;

	public AbstractGameService(Game game, Players players) throws RemoteException {
		super();
		this.game = game;
		this.players = players;
	}

	private static final long serialVersionUID = 7999272435762156455L;

	@Override
	public void echo(String nodeId, String string) throws RemoteException {
		if (game.isStarted())
			System.out.println(nodeId+" says : "+string);
	}
}
