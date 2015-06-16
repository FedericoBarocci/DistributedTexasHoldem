package bread_and_aces._di.providers.registration.initializers.servable._gui;

import javax.inject.Inject;

import bread_and_aces._di.providers.registration.initializers.servable.RegistrationInitializerServableProvider;
import bread_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import bread_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUI;
import bread_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUIFactory;

public class RegistrationInitializerServableProviderGUI implements RegistrationInitializerServableProvider {

	private final RegistrationInitializerServableGUIFactory registrationInitializerServableGUIFactory;
	private RegistrationInitializerServableGUI created;

	@Inject
	public RegistrationInitializerServableProviderGUI(RegistrationInitializerServableGUIFactory registrationInitializerServableGUIFactory) {
		this.registrationInitializerServableGUIFactory = registrationInitializerServableGUIFactory;
	}
	
	@Override
	public RegistrationInitializerServable get() {
		return created;
	}

	@Override
	public RegistrationInitializerServableProvider build(String nodeId) {
		created = registrationInitializerServableGUIFactory.createGUI(nodeId);
		return this;
	}

}
