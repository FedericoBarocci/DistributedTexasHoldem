package breads_and_aces.node.builder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import breads_and_aces.game.init.GameInitializer;
import breads_and_aces.game.init.clientable.GameInitializerClientableFactory;
import breads_and_aces.game.init.servable.GameInitializerServable;
import breads_and_aces.node.NodeContainer;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.game.impl.GameServiceFactory;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeBuilder {
	
	private static int NODE_PORT = 33333;
	private final NodeConnectionInfos nodeConnectionInfo;
	private final String nodeId;
	private final NodeFactory nodeFactory;
	private final Printer printer;
	
	@AssistedInject
	public NodeBuilder(
			@Assisted(value="nodeIdAsServable") String nodeId,
			@Assisted(value="addressToBindAsServable") String addressToBind,
//			GameServiceAsSessionInitializerServable gameServiceAsSessionInitializerServable,
			GameServiceFactory gameServiceFactory,
			GameInitializerServable gameInitializer,
			NodeFactory nodeFactory,
			Printer printer
			) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		this(nodeId, addressToBind, 
//				gameServiceAsSessionInitializerServable,
				gameServiceFactory.createServable(nodeId),
				gameInitializer, nodeFactory, printer);

		// TODO restore ?
		//		populateNodesGameServices = populateNodesGameServices(me, game);
	}
	
	@AssistedInject
	public NodeBuilder(
			@Assisted(value="nodeIdAsConnectable") String nodeId,
			@Assisted(value="addressToBindAsConnectable") String addressToBind,
//			GameServiceAsSessionInitializerClientable gameServiceAsSessionInitializerConnectable,
			GameServiceFactory gameServiceFactory,
			GameInitializerClientableFactory gameInitializerConnectableFactory,
			@Assisted(value="initializerHostAddress") String initializerHostAddress,
			@Assisted(value="initializerHostPort") int initializerHostPort,
			NodeFactory nodeFactory
			, Printer printer
			) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		this(nodeId, addressToBind, 
//				gameServiceAsSessionInitializerConnectable,
				gameServiceFactory.createClientable(nodeId),
				gameInitializerConnectableFactory.create(initializerHostAddress, initializerHostPort), nodeFactory, printer);
	}
	
	public NodeContainer build() {
		return nodeFactory.create(nodeId, nodeConnectionInfo/*, new Player(nodeId), connectionInfo, new LinkedHashMap<String, GameService>()*/ );
	}
	
	private NodeBuilder(String nodeId, String addressToBind, GameService gameService, GameInitializer gameInitializer, NodeFactory nodeFactory, Printer printer) throws RemoteException, IOException, NotBoundException {
		this.nodeId = nodeId;
		this.nodeFactory = nodeFactory;
		this.printer = printer;
		this.nodeConnectionInfo = startListen(nodeId, addressToBind, gameService);
		gameInitializer.initialize(nodeConnectionInfo, nodeId);
	}
	private NodeConnectionInfos startListen(String nodeId, String addressToBind, GameService service) throws IOException {
		int localPort = getPort();
		LocateRegistry.createRegistry(localPort);
		new Thread() {
			@Override
			public void run() {
				try {
					Naming.rebind( ServiceUtils.getRMIURLString(addressToBind, localPort, GameService.SERVICE_NAME), service);
				} catch (RemoteException e) {
					printer.println("Unable to register RMI service: exiting");
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}.start();
		printer.println("Node started on port "+localPort);
		return new NodeConnectionInfos(nodeId, addressToBind, localPort);
	}

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
