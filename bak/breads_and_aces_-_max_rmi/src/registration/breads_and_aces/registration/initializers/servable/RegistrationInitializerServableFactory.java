package breads_and_aces.registration.initializers.servable;

import breads_and_aces.registration.initializers.servable.gui.RegistrationInitializerServableGUI;

public interface RegistrationInitializerServableFactory {
//	RegistrationInitializerServableUsingShellInput create(String nodeId);
	RegistrationInitializerServableGUI create(String nodeId);
}
