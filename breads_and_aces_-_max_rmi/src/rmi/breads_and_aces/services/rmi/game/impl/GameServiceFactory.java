package breads_and_aces.services.rmi.game.impl;

public interface GameServiceFactory {

	GameServiceAsSessionInitializerClientable createClientable(String myplayerId);
	GameServiceAsSessionInitializerServable createServable(String myplayerId);
}
