package breads_and_aces.node.initializer;

import com.google.inject.assistedinject.Assisted;

public interface NodeInitializerFactory {
	
	NodeInitializer createAsServable(
			@Assisted(value="nodeIdAsServable") String me, 
			@Assisted(value="addressToBindAsServable") String addressToBind);
	
	NodeInitializer createAsClientable(@Assisted(value="nodeIdAsConnectable2") String me, 
			@Assisted(value="addressToBindAsConnectable2") String addressToBind,
			@Assisted(value="initializerHostAddress2") String initializerHostAddress
			);
	
	NodeInitializer createAsClientableWithInitializerPort(@Assisted(value="nodeIdAsConnectable1") String me, 
			@Assisted(value="addressToBindAsConnectable1") String addressToBind,
			@Assisted(value="initializerHostAddress1") String initializerHostAddress,
			@Assisted(value="initializerHostPort1") int initializerHostPort
			);
}
