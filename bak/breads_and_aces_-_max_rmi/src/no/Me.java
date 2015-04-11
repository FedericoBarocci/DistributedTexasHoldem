package breads_and_aces.main;

import javax.inject.Singleton;

@Singleton
public class Me {

	private String meId;

//	@AssistedInject
//	public Me(@Assisted String meId) {
//		this.meId = meId;
//	}
	
//	public Me() {}
	
	public void init(String meId) {
		this.meId = meId;
	}
	
	public String getId() {
		return meId;
	}
}
