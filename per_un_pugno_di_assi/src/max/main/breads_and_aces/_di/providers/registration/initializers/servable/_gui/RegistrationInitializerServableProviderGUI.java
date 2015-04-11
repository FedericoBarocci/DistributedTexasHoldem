package breads_and_aces._di.providers.registration.initializers.servable._gui;

import javax.inject.Inject;

import breads_and_aces._di.providers.registration.initializers.servable.RegistrationInitializerServableProvider;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import breads_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUI;
import breads_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUIFactory;

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
