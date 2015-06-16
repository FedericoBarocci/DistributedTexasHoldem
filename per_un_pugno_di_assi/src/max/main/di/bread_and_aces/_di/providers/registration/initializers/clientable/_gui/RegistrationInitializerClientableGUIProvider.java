package bread_and_aces._di.providers.registration.initializers.clientable._gui;

import javax.inject.Inject;

import bread_and_aces._di.providers.registration.initializers.clientable.RegistrationInitializerClientableProvider;
import bread_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;
import bread_and_aces.registration.initializers.clientable._gui.RegistrationInitializerClientableGUI;
import bread_and_aces.registration.initializers.clientable._gui.RegistrationInitializerClientableGUIFactory;

public class RegistrationInitializerClientableGUIProvider implements RegistrationInitializerClientableProvider {

	private final RegistrationInitializerClientableGUIFactory registrationInitializerClientableGUIFactory;
	private RegistrationInitializerClientableGUI created;

	@Inject
	public RegistrationInitializerClientableGUIProvider(RegistrationInitializerClientableGUIFactory registrationInitializerClientableGUIFactory) {
		this.registrationInitializerClientableGUIFactory = registrationInitializerClientableGUIFactory;
	}
	
	@Override
	public RegistrationInitializerClientable get() {
		return created;
	}

	@Override
	public RegistrationInitializerClientableProvider build(String initializerHostAddress, int initializerHostPort) {
		created = registrationInitializerClientableGUIFactory.create(initializerHostAddress, initializerHostPort);
		return this;
	}

}
