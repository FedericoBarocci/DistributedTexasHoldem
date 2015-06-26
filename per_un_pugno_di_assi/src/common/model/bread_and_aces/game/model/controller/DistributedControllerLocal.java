package bread_and_aces.game.model.controller;

import java.util.List;

public interface DistributedControllerLocal {

//	void handleToken();
//	void receiveStartGame(String whoHasToken);
	void removePlayerLocally(String playerId);
	void removePlayerLocally(List<String> playerIds);
}
