package bread_and_aces.services.rmi.utils.crashhandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrashedHolder {
	private List<String> crashed = new ArrayList<String>();
	public CrashedHolder(Collection<String> crashed) {
		this.crashed.addAll(crashed);
	}
	public List<String> getCrashed() {
		return crashed;
	}
}