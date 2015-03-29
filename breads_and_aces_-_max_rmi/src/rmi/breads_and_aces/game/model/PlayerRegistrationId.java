package breads_and_aces.game.model;

import java.io.Serializable;

public class PlayerRegistrationId implements Comparable<PlayerRegistrationId>, Serializable{

	private static final long serialVersionUID = 7994537110615064381L;

	private final String id;
	private final long registrationStamp;
	
	public PlayerRegistrationId(String id, long registrationStamp) {
		this.id = id;
		this.registrationStamp = registrationStamp;
	}
	
	public PlayerRegistrationId(String id) {
		this.id = id;
		this.registrationStamp = 0;
	}
	
	public String getId() {
		return id;
	}
	
	public long getRegistrationTimestamp() {
		return registrationStamp;
	}

	@Override
	public int compareTo(PlayerRegistrationId o) {
		if (this.registrationStamp < o.registrationStamp) return -1;
		if (this.registrationStamp > o.registrationStamp) return 1;
		return 0;
	}
	
	/*@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlayerRegistrationId)) {
//			System.out.println("no");
			return false;
		}
		PlayerRegistrationId p = (PlayerRegistrationId) obj;
//System.out.println(p.getId()+" "+id);
		if (p.getId().equals(id)) {
//			System.out.println("yes");
			return true;
		}
//		System.out.println("uff");
		return false;
	}*/
}
