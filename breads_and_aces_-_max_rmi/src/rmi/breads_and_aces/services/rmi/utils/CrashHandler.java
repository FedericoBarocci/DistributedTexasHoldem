package breads_and_aces.services.rmi.utils;

import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.init.registrar.utils.ShelfsUtils;
import breads_and_aces.main.Me;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class CrashHandler {

	private final ShelfsUtils registriesUtils;
	private final Printer printer;
	
	private boolean isExistingCrash = false;
	
//	private final Set<String> crashed = new HashSet<>();
	
	@Inject
	public CrashHandler(ShelfsUtils registriesUtils, Me me, Printer printer) {
		this.registriesUtils = registriesUtils;
		this.printer = printer;
	}
	
	public void noMoreCrash() {
		setHappenedCrash(false);
//		crashed.clear();
	}
	private void setHappenedCrash(boolean isExistingCrashed) {
		this.isExistingCrash = isExistingCrashed;
	}

	public boolean isHappenedCrash() {
		return isExistingCrash;
	}

	public void handleRemovingLocally(String id) {
		setHappenedCrash(true);
		registriesUtils.removeNodePlayerGameService(id);
		printer.println(id+" not responding, removed it.");
//		crashed.remove(id);
	}
	public void handleRemovingLocally(List<String> crashedDuringSync) {
		setHappenedCrash(true);
		ListIterator<String> listIterator = crashedDuringSync.listIterator();
		while (listIterator.hasNext()) {
			String next = listIterator.next();
			registriesUtils.removeNodePlayerGameService(next);
			listIterator.remove();
		}
		
	}

//	public void addCrashed(String id) {
//		crashed.add(id);
//	}
	
}
