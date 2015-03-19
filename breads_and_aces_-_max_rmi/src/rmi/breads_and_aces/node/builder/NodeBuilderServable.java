package breads_and_aces.node.builder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import breads_and_aces.rmi.game.init.servable.GameInitializerServable;
import breads_and_aces.rmi.services.rmi.game.impl.GameServiceAsSessionInitializerServable;


public class NodeBuilderServable extends AbstractNodeBuilder {

	@AssistedInject
	public NodeBuilderServable(GameInitializerServable gameInitializer,
			GameServiceAsSessionInitializerServable gameServiceAsSessionInitializerServable,
			@Assisted(value="nodeIdAsServable") String me, 
			@Assisted(value="addressToBindAsServable") String addressToBind) throws RemoteException, MalformedURLException, NotBoundException, IOException {
		super(me, addressToBind);
		this.nodeConnectionInfo = startListen(me, addressToBind, gameServiceAsSessionInitializerServable);
		// TODO restore
		//		populateNodesGameServices = populateNodesGameServices(me, game);
	}

}
