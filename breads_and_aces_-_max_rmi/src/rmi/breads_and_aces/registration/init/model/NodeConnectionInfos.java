package breads_and_aces.registration.init.model;

import java.io.Serializable;

public class NodeConnectionInfos implements Serializable, Comparable<NodeConnectionInfos> {

	private static final long serialVersionUID = -8647506914524193039L;

	private final String addressToBind;
	private final int localPort;
	private final String nodeId;

	private long registrationTime;
	
	public NodeConnectionInfos(String nodeId, String addressToBind, int localPort) {
		this.nodeId = nodeId;
		this.addressToBind = addressToBind;
		this.localPort = localPort;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	
	public String getAddress() {
		return addressToBind;
	}
	
	public int getPort() {
		return localPort;
	}

	public void setRegisterTime(long registrationTime) {
		this.registrationTime = registrationTime;
	}

	@Override
	public int compareTo(NodeConnectionInfos nodeConnectionInfos) {
		if (this.registrationTime < nodeConnectionInfos.registrationTime) return -1;
		if (this.registrationTime > nodeConnectionInfos.registrationTime) return 1;
		return 0;
	}
	
	
	
}
