package breads_and_aces.node;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.init.GameInitializerClientable;
import breads_and_aces.rmi.game.init.GameInitializerClientableFactory;
import breads_and_aces.rmi.game.init.GameInitializerServable;
import breads_and_aces.rmi.services.rmi.game.GameService;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerConnectable;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerServable;
import breads_and_aces.rmi.services.rmi.game.utils.ServiceUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeBuilderClassOld {

//	private String me;
//	private final GameServiceAsSessionInitializerServable service;
	private final NodeConnectionInfo nodeConnectionInfo;
//	private final String addressToBind;
//	private final Map<String, GameService> populateNodesGameServices;

	/*public NodeBuilder(@Assisted(value="nodeId") String me, 
			@Assisted(value="addressToBind") String addressToBind, 
			@Assisted GameService service) throws RemoteException, MalformedURLException, NotBoundException, IOException {
//		this.addressToBind = addressToBind;
		this.nodeConnectionInfo = startListen(me, addressToBind, service);
		// TODO restore
		//		populateNodesGameServices = populateNodesGameServices(me, game);
	}*/
	
	/**
	 * use this constructor if host acts as session initializer/server
	 */
	@AssistedInject
	public NodeBuilderClassOld(GameInitializerServable gameInitializer,
			GameServiceAsSessionInitializerServable gameServiceAsSessionInitializerServable,
			@Assisted(value="nodeId") String me, 
			@Assisted(value="addressToBind") String addressToBind) 
					throws RemoteException, MalformedURLException, NotBoundException, IOException {
//		this.addressToBind = addressToBind;
		this.nodeConnectionInfo = startListen(me, addressToBind, gameServiceAsSessionInitializerServable);
		
		gameInitializer.initialize(nodeConnectionInfo);
	}
	
	/**
	 * use this constructor if host acts as session client, connecting to a initializer/server
	 */
	/*@AssistedInject
	public NodeBuilder(@Assisted(value="nodeId") String me, 
			@Assisted(value="addressToBind") String addressToBind,
			GameInitializerConnectableFactory gameInitializerConnectableFactory,
			@Assisted(value="initializerHostAddress") String initializerHostAddress,
			@Assisted(value="initializerHostPort") int initializerHostPort,
			GameServiceAsSessionInitializerConnectable gameServiceAsSessionInitializerConnectable) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		GameInitializerConnectable gameInitializer = gameInitializerConnectableFactory.create(initializerHostAddress, initializerHostPort);
//		this.addressToBind = addressToBind;
		this.nodeConnectionInfo = startListen(me, addressToBind, gameServiceAsSessionInitializerConnectable);
		
		gameInitializer.initialize(nodeConnectionInfo);
	}*/
	
	
//	public Map<String, GameService> getPopulateNodesGameServices() {
//		return populateNodesGameServices;
//	}

//	public NodeBuilder createService() throws RemoteException, MalformedURLException, IOException {
//		nodeConnectionInfo = startListen(me, service);
//		return this;
//	}
	
	
}
