package breads_and_aces.node.model;

public class ConnectionInfo {

	private final String addressToBind;
	private final int localPort;
	
	public ConnectionInfo(String addressToBind, int localPort) {
		this.addressToBind = addressToBind;
		this.localPort = localPort;
	}
	
	public String getAddress() {
		return addressToBind;
	}
	
	public int getPort() {
		return localPort;
	}
	
}
