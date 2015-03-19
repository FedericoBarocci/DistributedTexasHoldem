package breads_and_aces.node.builder;

import com.google.inject.assistedinject.Assisted;

public interface NodeBuilderFactory {
	
	NodeBuilder createAsServable(
			@Assisted(value="nodeIdAsServable") String me, 
			@Assisted(value="addressToBindAsServable") String addressToBind);
	
	NodeBuilder createAsClientable(@Assisted(value="nodeIdAsConnectable") String me, 
			@Assisted(value="addressToBindAsConnectable") String addressToBind,
			@Assisted(value="initializerHostAddress") String initializerHostAddress,
			@Assisted(value="initializerHostPort") int initializerHostPort
			);
}
