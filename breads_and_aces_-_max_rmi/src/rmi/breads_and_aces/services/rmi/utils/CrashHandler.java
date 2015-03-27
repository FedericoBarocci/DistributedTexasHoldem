package breads_and_aces.services.rmi.utils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.init.registrar.utils.RegistriesUtils;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class CrashHandler {

	private final RegistriesUtils registriesUtils;
	private final Printer printer;
	
	private boolean isExistingCrash;
	
	@Inject
	public CrashHandler(boolean isExistingCrash, List<String> crashed, RegistriesUtils registriesUtils, Printer printer) {
		this.isExistingCrash = isExistingCrash;
		this.registriesUtils = registriesUtils;
		this.printer = printer;
	}

	public void setHappenedCrash(boolean isExistingCrashed) {
		this.isExistingCrash = isExistingCrashed;
	}

	public boolean isHappenedCrash() {
		return isExistingCrash;
	}

	public void handle(String id) {
		setHappenedCrash(true);
		registriesUtils.removeNodePlayerGameService(id);
		printer.println(id+" not responding, remove it.");
	}

	public void noMoreCrash() {
		setHappenedCrash(false);
	}
}
