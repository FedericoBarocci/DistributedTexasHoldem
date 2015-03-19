package breads_and_aces.node.model;

import breads_and_aces.rmi.game.model.Player;

public class PlayerConnectionInfosHolder {

	private final Player player;
	private final ConnectionInfo connectionInfo;
	
	public PlayerConnectionInfosHolder(Player player, ConnectionInfo connectionInfo) {
		this.player = player;
		this.connectionInfo = connectionInfo;
	}
	
	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}
	
	public Player getPlayer() {
		return player;
	}
}
