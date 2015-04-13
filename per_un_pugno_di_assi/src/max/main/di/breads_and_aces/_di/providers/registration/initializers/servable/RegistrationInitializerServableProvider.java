package breads_and_aces._di.providers.registration.initializers.servable;

import javax.inject.Provider;

import breads_and_aces.registration.initializers.servable.RegistrationInitializerServable;

public interface RegistrationInitializerServableProvider extends Provider<RegistrationInitializerServable> {
	RegistrationInitializerServableProvider build(String nodeId);
}
