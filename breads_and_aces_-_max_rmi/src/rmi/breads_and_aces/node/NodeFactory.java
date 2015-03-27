package breads_and_aces.node;

import breads_and_aces.node.model.NodeConnectionInfos;


public interface NodeFactory {
//	Node create(String nodeId, Player player, ConnectionInfo connectionInfo, Map<String, GameService> map);
	Node create(String nodeId, NodeConnectionInfos connectionInfo);
}
