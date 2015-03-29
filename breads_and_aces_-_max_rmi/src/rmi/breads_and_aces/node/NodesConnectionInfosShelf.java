package breads_and_aces.node;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import breads_and_aces.node.model.NodeConnectionInfos;

/**
 * singleton
 */
@Singleton
public class NodesConnectionInfosShelf {

	private final Map<String, NodeConnectionInfos> nodesConnectionInfosMap = new LinkedHashMap<>();
	
	public void addNodeInfo( NodeConnectionInfos nodeConnectionInfo) {
		nodesConnectionInfosMap.put(nodeConnectionInfo.getNodeId(), nodeConnectionInfo);
	}
	
	public NodeConnectionInfos getNode(String nodeId) {
		return nodesConnectionInfosMap.get(nodeId);
	}
	
	public Map<String, NodeConnectionInfos> getNodesConnectionInfosMap() {
		return nodesConnectionInfosMap;
	}
	
	public List<NodeConnectionInfos> getNodesConnectionInfos() {
		return new LinkedList<NodeConnectionInfos>(nodesConnectionInfosMap.values());
	}

	public void setNodesConnectionInfos(Map<String, NodeConnectionInfos> nodesConnectionInfosMap) {
		this.nodesConnectionInfosMap.putAll(nodesConnectionInfosMap);
	}
	
	/*public void setNodesConnectionInfos(Collection<NodeConnectionInfos> nodeConnectionInfos) {
		// guice don't like lambda in init phase
//		nodeConnectionInfos.iterator().forEachRemaining(nodeConnectionInfo->{
//			this.nodesConnectionInfosMap.put(nodeConnectionInfo.getNodeId(),nodeConnectionInfo);
//		});
		for (NodeConnectionInfos nodeConnectionInfo : nodeConnectionInfos) {
			this.nodesConnectionInfosMap.put(nodeConnectionInfo.getNodeId(),nodeConnectionInfo);
		}
	}*/

	public boolean contains(String id) {
		return nodesConnectionInfosMap.containsKey(id);
	}

	public void removeNode(String id) {
		nodesConnectionInfosMap.remove(id);		
	}
}
