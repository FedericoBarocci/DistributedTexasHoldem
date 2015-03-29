package breads_and_aces.services.rmi.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.Game;
import breads_and_aces.game.registry.PlayersShelf;

public abstract class AbstractGameService 
	extends UnicastRemoteObject 
	implements GameService {

	protected final Game game;
	protected final String nodeId;
	protected final PlayersShelf playersShelf;

	public AbstractGameService(String nodeId
			, Game game 
			, PlayersShelf playersShelf
			) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.game = game;
		this.playersShelf = playersShelf;
	}

	private static final long serialVersionUID = 7999272435762156455L;

	@Override
	public void echo(String playerId, String string) throws RemoteException {
		if (game.isStarted())
			System.out.println(playerId+" says : "+string);
		else
			System.out.println("uhmm");
	}
	
	@Override
	public void receiveToken() throws RemoteException {
		playersShelf.getPlayer(nodeId).receiveToken();
		System.out.println("received token!");
	}
	
}
