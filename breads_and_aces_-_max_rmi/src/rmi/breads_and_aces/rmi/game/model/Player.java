package breads_and_aces.rmi.game.model;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = -7618547420110997571L;
	private final String id;

	public Player(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
