package breads_and_aces.game.init.clientable;


public interface GameInitializerClientableFactory {
	GameInitializerClientable create(String initializingHostAddress, int initializingHostPort);
}
