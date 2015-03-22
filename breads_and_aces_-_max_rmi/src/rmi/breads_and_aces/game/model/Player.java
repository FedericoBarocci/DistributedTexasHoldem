package breads_and_aces.game.model;

import java.io.Serializable;

public class Player implements Serializable, Comparable<Player> {

	private static final long serialVersionUID = -7618547420110997571L;
	private final String id;
	private long registrationTime;
	private boolean hasToken;

	public Player(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void giveToken() {
		hasToken = true;
	}
	
	public void passToken() {
		hasToken = false;
	}
	
	public boolean hasToken() {
		return hasToken;
	}

	public void setRegisterPosition(long registrationTime) {
		this.registrationTime = registrationTime;
	}

	@Override
	public int compareTo(Player player) {
		if (this.registrationTime < player.registrationTime) return -1;
		if (this.registrationTime > player.registrationTime) return 1;
		return 0;
	}
	
	/*public static class PlayersComparator implements Comparator<Player> {
		@Override
		public int compare(Player p1, Player p2) {
			return p1.compareTo(p2);
		}
	}*/
}
