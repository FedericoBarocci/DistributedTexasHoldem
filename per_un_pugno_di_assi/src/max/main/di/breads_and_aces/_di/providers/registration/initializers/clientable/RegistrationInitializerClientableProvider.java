package breads_and_aces._di.providers.registration.initializers.clientable;

import javax.inject.Provider;

import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;

public interface RegistrationInitializerClientableProvider extends Provider<RegistrationInitializerClientable> {
	RegistrationInitializerClientableProvider build(String initializerHostAddress, int initializerHostPort);
}
