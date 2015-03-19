package breads_and_aces.node.builder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.LinkedHashMap;

import breads_and_aces.node.Node;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.model.ConnectionInfo;
import breads_and_aces.rmi.game.init.GameInitializer;
import breads_and_aces.rmi.game.init.clientable.GameInitializerClientableFactory;
import breads_and_aces.rmi.game.init.servable.GameInitializerServable;
import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.services.rmi.game.GameService;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerClientable;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerServable;
import breads_and_aces.rmi.services.rmi.game.utils.ServiceUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeBuilder {
	
	private static int NODE_PORT = 33333;
	private final ConnectionInfo connectionInfo;
	private final String nodeId;
	private final NodeFactory nodeFactory;
	
	@AssistedInject
	public NodeBuilder(
			GameServiceAsSessionInitializerServable gameServiceAsSessionInitializerServable,
			@Assisted(value="nodeIdAsServable") String id, 
			@Assisted(value="addressToBindAsServable") String addressToBind,
			GameInitializerServable gameInitializer,
			NodeFactory nodeFactory
			) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		this(id, addressToBind, gameServiceAsSessionInitializerServable, nodeFactory, gameInitializer);

		// TODO restore ?
		//		populateNodesGameServices = populateNodesGameServices(me, game);
	}
	
	@AssistedInject
	public NodeBuilder(
			GameServiceAsSessionInitializerClientable gameServiceAsSessionInitializerConnectable,
			@Assisted(value="nodeIdAsConnectable") String nodeId, 
			@Assisted(value="addressToBindAsConnectable") String addressToBind,
			NodeFactory nodeFactory,
			GameInitializerClientableFactory gameInitializerConnectableFactory,
			@Assisted(value="initializerHostAddress") String initializerHostAddress,
			@Assisted(value="initializerHostPort") int initializerHostPort
			) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		this(nodeId, addressToBind, gameServiceAsSessionInitializerConnectable, nodeFactory, 
				gameInitializerConnectableFactory.create(initializerHostAddress, initializerHostPort) );
	}
	
	public Node build() {
		return nodeFactory.create(nodeId/*, new Player(nodeId), connectionInfo, new LinkedHashMap<String, GameService>()*/ );
	}
	
	private NodeBuilder(String nodeId, String addressToBind, GameService gameService, NodeFactory nodeFactory, GameInitializer gameInitializer) throws RemoteException, IOException, NotBoundException {
		this.nodeId = nodeId;
		this.nodeFactory = nodeFactory;
		this.connectionInfo = startListen(addressToBind, gameService);
		gameInitializer.initialize(nodeId, connectionInfo);
	}
	private static ConnectionInfo startListen(String addressToBind, GameService service) throws IOException {
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
System.out.println("Node started on port "+localPort);
		return new ConnectionInfo(addressToBind, localPort);
	}
	
	
	

	/*private Player startListenServable(String meNodeId, String addressToBind, GameServiceServable service) throws IOException {
		int localPort = getPort();
		createThreadForServable(addressToBind, localPort, service);
		System.out.println("Node"+" "+meNodeId+" "+"started on port "+localPort);
		return new Player(meNodeId, new ConnectionInfo(addressToBind, localPort));
	}
	private Player startListenClientable(String meNodeId, String addressToBind, GameServiceClientable service) throws IOException {
		int localPort = getPort();
		createThreadForClientable(addressToBind, localPort, service);
		System.out.println("Node"+" "+meNodeId+" "+"started on port "+localPort);
		return new Player(meNodeId, new ConnectionInfo(addressToBind, localPort));
	}*/
	/*private void createThreadForServable(String addressToBind, int localPort, GameServiceServable service) throws RemoteException {
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
	}*/
	private static int getPort() throws IOException {
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
	
	/*private Map<String, GameService> populateNodesGameServices(String meNodeId, Game game) throws MalformedURLException, RemoteException, NotBoundException {
		final Map<String, GameService> nodeGameserviceMap = new LinkedHashMap<>();
		Iterator<Entry<String, Player>> iterator = game.getPlayers().getPlayersNodeInfos().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, Player> next = iterator.next();
			String key = next.getKey();
			if (key.equals(meNodeId)) {
				continue;
			}
			Player value = next.getValue();
			ConnectionInfo connectionInfo = value.getConnectionInfo();
			GameService remoteNodeGameService = ServiceUtils.lookup(connectionInfo.getAddress(), connectionInfo.getPort());
			nodeGameserviceMap.put(key, remoteNodeGameService);
		}
		return nodeGameserviceMap;
	}*/
}
