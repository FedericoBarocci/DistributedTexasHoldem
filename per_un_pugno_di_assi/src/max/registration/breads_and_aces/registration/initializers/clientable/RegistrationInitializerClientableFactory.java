package breads_and_aces.registration.initializers.clientable;


public interface RegistrationInitializerClientableFactory {
	RegistrationInitializerClientableShell create(String initializingHostAddress, int initializingHostPort);
}
