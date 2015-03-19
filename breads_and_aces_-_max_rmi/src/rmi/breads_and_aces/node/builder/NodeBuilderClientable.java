package breads_and_aces.node.builder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.rmi.game.init.clientable.GameInitializerClientable;
import breads_and_aces.rmi.game.init.clientable.GameInitializerClientableFactory;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerConnectable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeBuilderClientable extends AbstractNodeBuilder {

	@AssistedInject
	public NodeBuilderClientable(@Assisted(value="nodeIdAsConnectable") String me, 
			@Assisted(value="addressToBindAsConnectable") String addressToBind,
			GameInitializerClientableFactory gameInitializerConnectableFactory,
			@Assisted(value="initializerHostAddress") String initializerHostAddress,
			@Assisted(value="initializerHostPort") int initializerHostPort,
			GameServiceAsSessionInitializerConnectable gameServiceAsSessionInitializerConnectable) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		super(me,addressToBind);
		GameInitializerClientable gameInitializer = gameInitializerConnectableFactory.create(initializerHostAddress, initializerHostPort);
//		this.addressToBind = addressToBind;
		this.nodeConnectionInfo = startListen(me, addressToBind, gameServiceAsSessionInitializerConnectable);
		
		gameInitializer.initialize(nodeConnectionInfo);
	}
}
