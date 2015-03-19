package breads_and_aces.rmi.game.init.clientable;


public interface GameInitializerClientableFactory {
	GameInitializerClientableUsingRMI create(String initializingHostAddress, int initializingHostPort);
}
