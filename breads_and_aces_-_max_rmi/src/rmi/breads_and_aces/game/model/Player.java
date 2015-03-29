package breads_and_aces.game.model;

import java.io.Serializable;

import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class Player implements Serializable, Comparable<Player> {

	private static final long serialVersionUID = -7618547420110997571L;
	private final String id;
	private final Printer printer;
	
	private long registrationTime;
	private boolean hasBucket;

	@AssistedInject
	public Player(@Assisted String id, Printer printer) {
		this.id = id;
		this.printer = printer;
	}
	
	public String getId() {
		return id;
	}
	
	public void receiveBucket() {
		hasBucket = true;
		printer.println("bucket received");
	}
	public void receiveBucket(String receivedFrom) {
		hasBucket = true;
		printer.println("bucket received from "+receivedFrom);
	}
	
	public void passBucket() {
		hasBucket = false;
		printer.println("bucket passed");
	}
	public void passBucket(String passedTo) {
		hasBucket = false;
		printer.println("bucket passed to "+passedTo);
	}
	
	public boolean hasBucket() {
		return hasBucket;
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
