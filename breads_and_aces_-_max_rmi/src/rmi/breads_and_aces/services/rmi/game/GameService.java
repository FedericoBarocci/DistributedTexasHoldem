package breads_and_aces.services.rmi.game;

import breads_and_aces.services.rmi.game.echo.Echo;
import breads_and_aces.services.rmi.game.tokenable.TokenHandler;

public interface GameService extends Echo, TokenHandler {
	public static final String SERVICE_NAME = "TexasHoldemGameService";

//	void passToken(String playerId);
}
