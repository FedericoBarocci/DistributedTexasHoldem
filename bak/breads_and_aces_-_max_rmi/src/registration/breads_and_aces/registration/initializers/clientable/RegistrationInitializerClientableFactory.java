package breads_and_aces.registration.initializers.clientable;


public interface RegistrationInitializerClientableFactory {
	RegistrationInitializerClientable create(String initializingHostAddress, int initializingHostPort);
}
