package breads_and_aces.node.builder;

import java.net.MalformedURLException;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import breads_and_aces.node.Node;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.init.GameInitializer;
import breads_and_aces.rmi.game.init.clientable.GameInitializerClientableFactory;
import breads_and_aces.rmi.game.init.servable.GameInitializerServable;
import breads_and_aces.rmi.services.rmi.game.GameService;
import breads_and_aces.rmi.services.rmi.game.GameServiceClientable;
import breads_and_aces.rmi.services.rmi.game.GameServiceServable;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerClientable;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerServable;
import breads_and_aces.rmi.services.rmi.game.utils.ServiceUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeBuilder {
	
	private int NODE_PORT = 33333;
	private final NodeConnectionInfo nodeConnectionInfo;
	private final String me;
	private final NodeFactory nodeFactory;
	
	@AssistedInject
	public NodeBuilder(
			GameServiceAsSessionInitializerServable gameServiceAsSessionInitializerServable,
			@Assisted(value="nodeIdAsServable") String me, 
			@Assisted(value="addressToBindAsServable") String addressToBind,
			GameInitializerServable gameInitializer,
			NodeFactory nodeFactory
			) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		this(me, addressToBind, gameServiceAsSessionInitializerServable, nodeFactory, gameInitializer);
//		this.me = me;
//		this.nodeFactory = nodeFactory;
//		this.nodeConnectionInfo = startListenServable(me, addressToBind, gameServiceAsSessionInitializerServable);
//		gameInitializer.initialize(nodeConnectionInfo);
		

		// TODO restore
		//		populateNodesGameServices = populateNodesGameServices(me, game);
	}
	
	@AssistedInject
	public NodeBuilder(
			GameServiceAsSessionInitializerClientable gameServiceAsSessionInitializerConnectable,
			@Assisted(value="nodeIdAsConnectable") String me, 
			@Assisted(value="addressToBindAsConnectable") String addressToBind,
			NodeFactory nodeFactory,
			GameInitializerClientableFactory gameInitializerConnectableFactory,
			@Assisted(value="initializerHostAddress") String initializerHostAddress,
			@Assisted(value="initializerHostPort") int initializerHostPort
			) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		this(me, addressToBind, gameServiceAsSessionInitializerConnectable, nodeFactory, gameInitializerConnectableFactory.create(initializerHostAddress, initializerHostPort) );
//		this.me = me;
//		this.nodeFactory = nodeFactory;
//		this.nodeConnectionInfo = startListenClientable(me, addressToBind, gameServiceAsSessionInitializerConnectable);
//		GameInitializerClientable gameInitializer = gameInitializerConnectableFactory.create(initializerHostAddress, initializerHostPort);
//		gameInitializer.initialize(nodeConnectionInfo);
	}
	
	private NodeBuilder(String me, String addressToBind, GameService gameService, NodeFactory nodeFactory, GameInitializer gameInitializer) throws RemoteException, IOException, NotBoundException {
		this.me = me;
		this.nodeFactory = nodeFactory;
		this.nodeConnectionInfo = startListen(me, addressToBind, gameService);
		gameInitializer.initialize(nodeConnectionInfo);
	}
	private NodeConnectionInfo startListen(String meNodeId, String addressToBind, GameService service) throws IOException {
		int localPort = getPort();
		LocateRegistry.createRegistry(localPort);
		new Thread() {
			@Override
			public void run() {
				try {
					Naming.rebind( ServiceUtils.getRMIURLString(addressToBind, localPort, GameService.SERVICE_NAME), service);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}.start();
		System.out.println("Node"+" "+meNodeId+" "+"started on port "+localPort);
		return new NodeConnectionInfo(meNodeId, addressToBind,localPort);
	}
	
	
	

	private NodeConnectionInfo startListenServable(String meNodeId, String addressToBind, GameServiceServable service) throws IOException {
		int localPort = getPort();
		createThreadForServable(addressToBind, localPort, service);
		System.out.println("Node"+" "+meNodeId+" "+"started on port "+localPort);
		return new NodeConnectionInfo(meNodeId, addressToBind,localPort);
	}
	private NodeConnectionInfo startListenClientable(String meNodeId, String addressToBind, GameServiceClientable service) throws IOException {
		int localPort = getPort();
		createThreadForClientable(addressToBind, localPort, service);
		System.out.println("Node"+" "+meNodeId+" "+"started on port "+localPort);
		return new NodeConnectionInfo(meNodeId, addressToBind,localPort);
	}
	private void createThreadForServable(String addressToBind, int localPort, GameServiceServable service) throws RemoteException {
		LocateRegistry.createRegistry(localPort);
		new Thread() {
			@Override
			public void run() {
				try {
					Naming.rebind( ServiceUtils.getRMIURLString(addressToBind, localPort, GameService.SERVICE_NAME), service);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	private void createThreadForClientable(String addressToBind, int localPort, GameServiceClientable service) throws RemoteException {
		LocateRegistry.createRegistry(localPort);
		new Thread() {
			@Override
			public void run() {
				try {
					Naming.rebind( ServiceUtils.getRMIURLString(addressToBind, localPort, GameService.SERVICE_NAME), service);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	private int getPort() throws IOException {
		ServerSocket ss;
		int localPort;
		try {
			ss = new ServerSocket(NODE_PORT);
			localPort = NODE_PORT;
		} catch (IOException e) {
			ss = new ServerSocket(0);
			localPort = ss.getLocalPort();
		}
		ss.close();
		return localPort;
	}
	
	private Map<String, GameService> populateNodesGameServices(String meNodeId, Game game) throws MalformedURLException, RemoteException, NotBoundException {
		final Map<String, GameService> nodeGameserviceMap = new LinkedHashMap<>();
		Iterator<Entry<String, NodeConnectionInfo>> iterator = game.getPlayers().getPlayersNodeInfos().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, NodeConnectionInfo> next = iterator.next();
			String key = next.getKey();
			if (key.equals(meNodeId)) {
				continue;
			}
			NodeConnectionInfo value = next.getValue();
			GameService remoteNodeGameService = ServiceUtils.lookup(value.getAddress(),value.getPort());
			nodeGameserviceMap.put(key, remoteNodeGameService);
		}
		return nodeGameserviceMap;
	}
	
	public NodeConnectionInfo getNodeConnectionInfo() {
		return nodeConnectionInfo;
	}
	
	public Node build() {
		return nodeFactory.create(me, nodeConnectionInfo, new LinkedHashMap<String, GameService>() );
	}
}
