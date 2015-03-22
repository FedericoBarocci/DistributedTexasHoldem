package breads_and_aces.node;

import breads_and_aces.game.Game;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.utils.misc.Waiter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeContainer {

	private final String nodeId;
	private final InputHandler inputHandler;
	private final Game game;
	private NodeConnectionInfos connectionInfo;
	
	@AssistedInject
	public NodeContainer(@Assisted String thisNodeId, 
//			@Assisted Player player,
//			@Assisted Map<String, GameService> nodeGameserviceMap,
			@Assisted NodeConnectionInfos connectionInfo,
			Game game // dummy presence
			, InputHandler inputHandler
			) {
		this.nodeId = thisNodeId;
		this.connectionInfo = connectionInfo;
		this.game = game;
//		this.thisPlayer = player;
//		this.nodeGameserviceMap = nodeGameserviceMap;
		this.inputHandler = inputHandler;
	}
	
	public String getId() {
		return nodeId;
	}
	
	public void start() {
		// this while is necessary if node acts as clientable
		Waiter.sleep(game::isStarted, 1);
//		handleInput();
		inputHandler.exec();
	}
	
	// TODO restore
	/*private void sendBroadcast(String message) {
//		game.getPlayers().getPlayersNodeInfos()
		nodeGameserviceMap
		.values().stream()
		.forEach(ngs->{
			try {
				ngs.echo(nodeId, message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}*/
	
	// TODO restore
	/*private void passToken() {
//		nodesGameServices.entrySet().
//		new LinkedHashMap<Integer, String>().;
		Iterator<Entry<String, GameService>> iterator = nodeGameserviceMap.entrySet().iterator();
//		boolean crashed = false;
		boolean passed = false;
		while (iterator.hasNext() && !passed) {
			Entry<String, GameService> next = iterator.next();
			try {
				// TODO change with new passToken method
				next.getValue().echo(null, null);
				passed = true;
			} catch (RemoteException e) {
				// if here, node is crashed
			} 
		}
	}*/
	
//	private Map<String, GameService> populateNodesGameServices(String me, Game game) throws MalformedURLException, RemoteException, NotBoundException {
//		final Map<String, GameService> nodeGameserviceMap = new LinkedHashMap<>();
//		Iterator<Entry<String, NodeConnectionInfo>> iterator = game.getPlayers().getPlayersNodeInfos().entrySet().iterator();
//		while(iterator.hasNext()) {
//			Entry<String, NodeConnectionInfo> next = iterator.next();
//			String key = next.getKey();
//			if (key.equals(me)) {
//				continue;
//			}
//			NodeConnectionInfo value = next.getValue();
//			GameService remoteNodeGameService = ServiceUtils.lookup(value.getAddress(),value.getPort());
//			nodeGameserviceMap.put(key, remoteNodeGameService);
//		}
//		return nodeGameserviceMap;
//	}
}
