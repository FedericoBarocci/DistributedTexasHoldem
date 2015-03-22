package breads_and_aces.services.rmi.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import breads_and_aces.game.Game;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;

public abstract class AbstractGameService 
	extends UnicastRemoteObject 
	implements GameService {

	protected final Game game;
	protected final NodesConnectionInfosRegistry nodesConnectionInfosRegistry;
	protected final PlayersRegistry playersRegistry;
	protected final String nodeId;

	public AbstractGameService(String nodeId, Game game, NodesConnectionInfosRegistry nodesRegistry, PlayersRegistry players) throws RemoteException {
		super();
		this.nodeId = nodeId;
		this.game = game;
		this.nodesConnectionInfosRegistry = nodesRegistry;
		this.playersRegistry = players;
	}

	private static final long serialVersionUID = 7999272435762156455L;

	@Override
	public void echo(String nodeId, String string) throws RemoteException {
		if (game.isStarted())
			System.out.println(nodeId+" says : "+string);
	}
	
	@Override
	public void receiveToken() throws RemoteException {
		
		
	}
}
