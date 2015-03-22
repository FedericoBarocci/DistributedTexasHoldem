package breads_and_aces.game.init.clientable.old_distinguish_abstract;


public interface GameInitializerClientableFactory {
	GameInitializerClientableUsingRMI create(String initializingHostAddress, int initializingHostPort);
}
