package breads_and_aces.services.rmi.utils.crashhandler;

import java.util.Collection;

public class CrashedHolder {
	private Collection<String> crashed;
	public CrashedHolder(Collection<String> crashed) {
		this.crashed.addAll(crashed);
	}
	public Collection<String> getCrashed() {
		return crashed;
	}
}