package breads_and_aces.node.initializer;

import java.util.concurrent.CountDownLatch;

import com.google.inject.assistedinject.Assisted;

public interface NodeInitializerFactory {
	
	NodeInitializer createAsServable(
			@Assisted(value="nodeIdAsServable") String me, 
			@Assisted(value="addressToBindAsServable") String addressToBind 
//			,@Assisted(value="latchForServable") CountDownLatch initializerLatch
			);

	NodeInitializer createAsClientableWithInitializerPort(@Assisted(value="nodeIdAsConnectableWithPort") String me, 
			@Assisted(value="addressToBindAsConnectableWithPort") String addressToBind,
			@Assisted(value="initializerHostAddressWithPort") String initializerHostAddress,
			@Assisted(value="initializerHostPortWithPort") int initializerHostPort, 
			@Assisted(value="latchForClientableWithPort") CountDownLatch registrarLatch
			);
	
	NodeInitializer createAsClientable(@Assisted(value="nodeIdAsConnectableWithoutPort") String me, 
			@Assisted(value="addressToBindAsConnectableWithoutPort") String addressToBind,
			@Assisted(value="initializerHostAddressWithoutPort") String initializerHostAddress
			);
}
