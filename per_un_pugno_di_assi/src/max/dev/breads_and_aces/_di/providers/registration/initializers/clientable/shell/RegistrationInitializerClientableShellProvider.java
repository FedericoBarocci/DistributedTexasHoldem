package breads_and_aces._di.providers.registration.initializers.clientable.shell;

import javax.inject.Inject;

import breads_and_aces._di.providers.registration.initializers.clientable.RegistrationInitializerClientableProvider;
import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;
import breads_and_aces.registration.initializers.clientable._shell.RegistrationInitializerClientableShell;
import breads_and_aces.registration.initializers.clientable._shell.RegistrationInitializerClientableShellFactory;

public class RegistrationInitializerClientableShellProvider implements RegistrationInitializerClientableProvider {

	private final RegistrationInitializerClientableShellFactory registrationInitializerClientableShellFactory;
	private RegistrationInitializerClientableShell created;

	@Inject
	public RegistrationInitializerClientableShellProvider(RegistrationInitializerClientableShellFactory registrationInitializerClientableShellFactory) {
		this.registrationInitializerClientableShellFactory = registrationInitializerClientableShellFactory;
	}
	
	@Override
	public RegistrationInitializerClientable get() {
		return created;
	}

	@Override
	public RegistrationInitializerClientableProvider build(String initializerHostAddress, int initializerHostPort) {
		created = registrationInitializerClientableShellFactory.create(initializerHostAddress, initializerHostPort);
		return this;
	}

}
