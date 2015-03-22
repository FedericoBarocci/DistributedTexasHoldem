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
public class NodesConnectionInfosRegistry {

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
	
	public void setNodesConnectionInfos(Collection<NodeConnectionInfos> nodeConnectionInfos) {
		nodeConnectionInfos.iterator().forEachRemaining(nodeConnectionInfo->{
			this.nodesConnectionInfosMap.put(nodeConnectionInfo.getNodeId(),nodeConnectionInfo);
		});
	}

	public boolean contains(String playerId) {
		// TODO Auto-generated method stub
		return false;
	}
}
