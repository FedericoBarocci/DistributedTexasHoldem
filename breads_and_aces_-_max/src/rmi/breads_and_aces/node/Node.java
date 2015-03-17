package breads_and_aces.node;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.services.rmi.game.GameService;
import breads_and_aces.utils.InputUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class Node {

	private final NodeConnectionInfo nodeConnectionInfo;
	private final Game game;
	private final String me;
	private final Map<String, GameService> nodeGameserviceMap;
//	= new LinkedHashMap<>();
	
	@AssistedInject
	public Node(@Assisted String nodeId, 
			@Assisted NodeConnectionInfo nodeConnectionInfo,
			@Assisted Map<String, GameService> nodeGameserviceMap,
			Game game
			) {
		this.me = nodeId;
		this.game = game;
		this.nodeConnectionInfo = nodeConnectionInfo;
		this.nodeGameserviceMap = nodeGameserviceMap;
	}
	
	public void play() {
		// this while is necessary if node acts as clientable
		while (!game.isStarted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		handleInput();
	}
	
	private static String END_GAME = "END";
	private void handleInput() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while(!next.equals(END_GAME)) {
			next = scanner.next();
			System.out.println(next);
			sendBroadcast(next);
			passToken();
		}
		scanner.close();
	}
	
	private void sendBroadcast(String message) {
//		game.getPlayers().getPlayersNodeInfos()
		nodeGameserviceMap
		.values().stream()
		.forEach(ngs->{
			try {
				ngs.echo(me, message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void passToken() {
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
	}
	
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
