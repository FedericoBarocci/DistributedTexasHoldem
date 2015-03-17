package breads_and_aces.rmi.game.init.clientable;


public interface GameInitializerClientableFactory {
	GameInitializerClientable create(String initializingHostAddress, int initializingHostPort);
}
