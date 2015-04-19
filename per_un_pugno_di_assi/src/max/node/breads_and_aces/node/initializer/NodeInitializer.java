package breads_and_aces.node.initializer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import breads_and_aces._di.providers.registration.initializers.clientable.RegistrationInitializerClientableProvider;
import breads_and_aces._di.providers.registration.initializers.servable.RegistrationInitializerServableProvider;
import breads_and_aces.node.Node;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.registration.initializers.RegistrationInitializer;
import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.core.impl.GameServiceFactory;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeInitializer {
	
	private static final int NODE_DEFAULT_PORT = 33333;
	private final Printer printer;
	private final Node node;

	// as servable
	@AssistedInject
	public NodeInitializer(
			@Assisted(value = "nodeIdAsServable") String nodeId,
			@Assisted(value = "addressToBindAsServable") String addressToBind,
			RegistrationInitializerServableProvider registrationInitializerServableProvider,
			GameServiceFactory gameServiceFactory, NodeFactory nodeFactory,
			Printer printer) throws RemoteException, MalformedURLException,
			NotBoundException, IOException {
		this(nodeId, addressToBind, registrationInitializerServableProvider
				.build(nodeId).get(), gameServiceFactory
				.createAsServable(nodeId), nodeFactory::create, printer);
	}
	
	// as clientable with port
	@AssistedInject
	public NodeInitializer(
			@Assisted(value = "nodeIdAsConnectableWithPort") String nodeId,
			@Assisted(value = "addressToBindAsConnectableWithPort") String addressToBind,
			RegistrationInitializerClientableProvider registrationInitializerClientableProvider,
			@Assisted(value = "initializerHostAddressWithPort") String initializerHostAddress,
			@Assisted(value = "initializerHostPortWithPort") int initializerHostPort,
			GameServiceFactory gameServiceFactory, NodeFactory nodeFactory,
			Printer printer) throws RemoteException, MalformedURLException,
			NotBoundException, IOException {
		this(nodeId, addressToBind, registrationInitializerClientableProvider
				.build(initializerHostAddress, initializerHostPort).get(),
				gameServiceFactory, nodeFactory::createAsClientable, printer);
	}
	
	// as clientable without port
	@AssistedInject
	public NodeInitializer(
			@Assisted(value = "nodeIdAsConnectableWithoutPort") String nodeId,
			@Assisted(value = "addressToBindAsConnectableWithoutPort") String addressToBind,
			GameServiceFactory gameServiceFactory,
			RegistrationInitializerClientableProvider registrationInitializerClientableProvider,
			@Assisted(value = "initializerHostAddressWithoutPort") String initializerHostAddress,
			NodeFactory nodeFactory, Printer printer) throws RemoteException,
			MalformedURLException, NotBoundException, IOException {
		this(nodeId, addressToBind, registrationInitializerClientableProvider
				.build(initializerHostAddress, NODE_DEFAULT_PORT).get(),
				gameServiceFactory, nodeFactory::createAsClientable, printer);
	}
	
	// as clientable
	/*private NodeInitializer(String nodeId, String addressToBind,
			RegistrationInitializerClientable registrationInitializerClientable,
			GameServiceAsSessionInitializerClientable gameServiceAsSessionInitializerClientable, 
			GameServiceFactory gameServiceFactory,
			CountDownLatch clientableLatch
			, NodeFactoryFunctor nodeFactoryFunctor,
//			RegistrarPlayersKeeper playersKeeper,
			Printer printer
			) throws RemoteException, IOException, NotBoundException {
		this.node = nodeFactoryFunctor.exec(nodeId);
		this.printer = printer;
		gameServiceFactory.createAsClientable(nodeId, clientableLatch);
	}*/
	
	// as clientable
	private NodeInitializer(
			String nodeId,
			String addressToBind,
			RegistrationInitializerClientable registrationInitializerClientable,
			GameServiceFactory gameServiceFactory,
			NodeFactoryFunctor nodeFactoryFunctor, Printer printer)
			throws RemoteException, IOException, NotBoundException {

		this(nodeId, addressToBind, gameServiceFactory.createAsClientable(
				nodeId, registrationInitializerClientable), nodeFactoryFunctor,
				registrationInitializerClientable, printer);
		
//		this.node = nodeFactoryFunctor.exec(nodeId);
//		this.printer = printer;
//		
//		GameServiceAsSessionInitializerClientable gameServiceAsSessionInitializerClientable = gameServiceFactory.createAsClientable(nodeId, registrationInitializerClientable);
//		final NodeConnectionInfos ownNodeConnectionInfos = startListen(nodeId, addressToBind, gameServiceAsSessionInitializerClientable);
//		registrationInitializerClientable.initialize(ownNodeConnectionInfos, nodeId);
	}
	
	// as servable
	private NodeInitializer(String nodeId, String addressToBind,
			RegistrationInitializer registrationInitializer,
			GameService gameService, NodeFactoryFunctor nodeFactoryFunctor,
			Printer printer) throws RemoteException, IOException,
			NotBoundException {
		this(nodeId, addressToBind, gameService, nodeFactoryFunctor,
				registrationInitializer, printer);
	}
	
	private NodeInitializer(String nodeId, String addressToBind,
			GameService gameService, NodeFactoryFunctor nodeFactoryFunctor,
			RegistrationInitializer registrationInitializer, Printer printer)
			throws RemoteException, IOException, NotBoundException {

		this.node = nodeFactoryFunctor.exec(nodeId);
		this.printer = printer;

		final NodeConnectionInfos ownNodeConnectionInfos = startListen(nodeId,
				addressToBind, gameService);
		registrationInitializer.initialize(ownNodeConnectionInfos, nodeId);
	}
	
	public Node get() {
		return node;
	}
	
	@FunctionalInterface
	private interface NodeFactoryFunctor {
		Node exec(String nodeId);
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
		
		printer.println("Node started on port " + localPort);
		
		return new NodeConnectionInfos(nodeId, addressToBind, localPort);
	}
	
	private static int getPort() throws IOException {
		ServerSocket ss;
		int localPort;
		
		try {
			ss = new ServerSocket(NODE_DEFAULT_PORT);
			localPort = NODE_DEFAULT_PORT;
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
