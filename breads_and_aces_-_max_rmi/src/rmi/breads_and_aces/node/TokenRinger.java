package breads_and_aces.node;

import javax.inject.Inject;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.registry.PlayersRegistry;


public class TokenRinger {
	
	private NodesConnectionInfosRegistry nodesConnectionInfosRegistry;
	private PlayersRegistry playersRegistry;
	
	@Inject
	public TokenRinger(NodesConnectionInfosRegistry nodesConnectionInfosRegistry, PlayersRegistry playersRegistry) {
		this.nodesConnectionInfosRegistry = nodesConnectionInfosRegistry;
		this.playersRegistry = playersRegistry;
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
