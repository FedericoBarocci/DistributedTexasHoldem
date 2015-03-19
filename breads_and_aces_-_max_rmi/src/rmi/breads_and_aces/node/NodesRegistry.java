package breads_and_aces.node;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * singleton
 */
@Singleton
public class NodesRegistry {

	private Map<String, Node> nodesMap = new LinkedHashMap<>();
	
	public void addNode(String nodeId, Node node) {
		nodesMap.put(nodeId, node);
	}
	
	public Node getNode(String nodeId) {
		return nodesMap.get(nodeId);
	}
	
	public Map<String, Node> getNodesMap() {
		return nodesMap;
	}
	
	public Collection<Node> getNodes() {
		return nodesMap.values();
	}
}
