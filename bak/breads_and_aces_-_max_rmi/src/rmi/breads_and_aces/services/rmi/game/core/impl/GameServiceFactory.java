package breads_and_aces.services.rmi.game.core.impl;

public interface GameServiceFactory {
	GameServiceAsSessionInitializerClientable createAsClientable(String myplayerId);
	GameServiceAsSessionInitializerServable createAsServable(String myplayerId);
}
