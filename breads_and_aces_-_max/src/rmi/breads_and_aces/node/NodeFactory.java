package breads_and_aces.node;

import java.util.Map;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.services.rmi.game.GameService;

public interface NodeFactory {
	Node create(String nodeId, NodeConnectionInfo nodeConnectionInfo, Map<String, GameService> map);
}
