package breads_and_aces.node;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.services.rmi.GameServicesShelf;

@Singleton
public class TokenRinger {
	
	private final NodesConnectionInfosShelf nodesConnectionInfosRegistry;
	private final PlayersShelf playersRegistry;
	private final GameServicesShelf GameServicesRegistry;
	
	@Inject
	public TokenRinger(NodesConnectionInfosShelf nodesConnectionInfosRegistry, PlayersShelf playersRegistry, GameServicesShelf GameServicesRegistry) {
		this.nodesConnectionInfosRegistry = nodesConnectionInfosRegistry;
		this.playersRegistry = playersRegistry;
		this.GameServicesRegistry = GameServicesRegistry;
	}

	public boolean passToken(Player player) {
		nodesConnectionInfosRegistry.getNode(player.getId());
//		Iterator<Entry<String, GameService>> iterator = nodeGameserviceMap.entrySet().iterator();
	//	boolean crashed = false;
//		boolean passed = false;
//		while (iterator.hasNext() && !passed) {
//			Entry<String, GameService> next = iterator.next();
//			try {
//				// TODO change with new passToken method
//				next.getValue().echo(null, null);
//				passed = true;
//			} catch (RemoteException e) {
//				// if here, node is crashed
//			} 
//		}
		return false;
	}
}
