package it.unibo.cs.sd.poker.rmi;

import java.io.Serializable;

import it.unibo.cs.sd.poker.game.Player;

public class RMIMessage implements Serializable {
	
	private static final long serialVersionUID = -3427109552977744385L;

	private RMICommand header = null;
	private Player player = null;
	
	public RMIMessage(RMICommand header) {
		setHeader(header);
	}
	
	public RMIMessage(RMICommand header, Player player) {
		setHeader(header);
		setPlayer(player);
	}
	
	public RMICommand getHeader() {
		return header;
	}
	
	public void setHeader(RMICommand header) {
		this.header = header;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}
