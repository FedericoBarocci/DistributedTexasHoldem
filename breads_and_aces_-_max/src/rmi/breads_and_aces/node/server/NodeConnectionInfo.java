package breads_and_aces.node.server;

import java.io.Serializable;

public class NodeConnectionInfo implements Serializable {

	private static final long serialVersionUID = -7618547420110997571L;
	private final String addressToBind;
	private final int localPort;
	private final String nodeId;

	public NodeConnectionInfo(String nodeId, String addressToBind, int localPort) {
		this.nodeId = nodeId;
		this.addressToBind = addressToBind;
		this.localPort = localPort;
	}
	
	public String getId() {
		return nodeId;
	}

	public final String getAddress() {
		return addressToBind;
	}

	public final int getPort() {
		return localPort;
	}
}
