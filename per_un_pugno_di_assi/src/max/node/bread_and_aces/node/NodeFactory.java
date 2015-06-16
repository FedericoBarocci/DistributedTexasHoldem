package bread_and_aces.node;

import com.google.inject.assistedinject.Assisted;

public interface NodeFactory {
//	Node create(String nodeId, Player player, ConnectionInfo connectionInfo, Map<String, GameService> map);
	DefaultNode createAsServable(@Assisted(value="nodeFactoryIdAsServable") String dummyNodeId/*, NodeConnectionInfos connectionInfo*/);
	NodeAsInitializerClientable createAsClientable(@Assisted(value="nodeFactoryIdAsClientable") String dummyNodeId);
}
