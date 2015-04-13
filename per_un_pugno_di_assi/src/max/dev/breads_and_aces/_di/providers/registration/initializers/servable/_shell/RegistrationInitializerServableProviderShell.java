package breads_and_aces._di.providers.registration.initializers.servable._shell;

import javax.inject.Inject;

import breads_and_aces._di.providers.registration.initializers.servable.RegistrationInitializerServableProvider;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import breads_and_aces.registration.initializers.servable._shell.RegistrationInitializerServableShell;
import breads_and_aces.registration.initializers.servable._shell.RegistrationInitializerServableShellFactory;

public class RegistrationInitializerServableProviderShell implements RegistrationInitializerServableProvider {
	
	private final RegistrationInitializerServableShellFactory registrationInitializerServableShellFactory;
	private RegistrationInitializerServableShell created;

	@Inject
	public RegistrationInitializerServableProviderShell(RegistrationInitializerServableShellFactory registrationInitializerServableShellFactory) {
		this.registrationInitializerServableShellFactory = registrationInitializerServableShellFactory;
	}

	@Override
	public RegistrationInitializerServableProvider build(String nodeId) {
		created = registrationInitializerServableShellFactory.create(nodeId);
		return this;
	}
	
	@Override
	public RegistrationInitializerServable get() {
		return created;
	}

}
