package breads_and_aces.node.initializer;

import com.google.inject.assistedinject.Assisted;

public interface NodeInitializerFactory {
	
	NodeInitializer createAsServable(
			@Assisted(value="nodeIdAsServable") String me, 
			@Assisted(value="addressToBindAsServable") String addressToBind 
			);

	NodeInitializer createAsClientableWithInitializerPort(@Assisted(value="nodeIdAsConnectableWithPort") String me, 
			@Assisted(value="addressToBindAsConnectableWithPort") String addressToBind,
			@Assisted(value="initializerHostAddressWithPort") String initializerHostAddress,
			@Assisted(value="initializerHostPortWithPort") int initializerHostPort 
			);
	
	NodeInitializer createAsClientable(@Assisted(value="nodeIdAsConnectableWithoutPort") String me, 
			@Assisted(value="addressToBindAsConnectableWithoutPort") String addressToBind,
			@Assisted(value="initializerHostAddressWithoutPort") String initializerHostAddress
			);
}