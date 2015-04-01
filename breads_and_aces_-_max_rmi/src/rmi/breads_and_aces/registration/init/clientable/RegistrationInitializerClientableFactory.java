package breads_and_aces.registration.init.clientable;


public interface RegistrationInitializerClientableFactory {
	RegistrationInitializerClientable create(String initializingHostAddress, int initializingHostPort);
}
