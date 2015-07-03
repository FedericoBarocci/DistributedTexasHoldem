package bread_and_aces.game.model.controller;

import bread_and_aces.game.model.oracle.actions.Message;
import bread_and_aces.game.updater.GameUpdater;

public interface DistributedControllerForRemoteHandling {
	
	void receiveStartGame(String whoHasToken);
	void removePlayer(String playerId);
//	void setActionOnSend(ActionKeeper actionKeeper);
	void setActionOnReceive(String fromPlayer, Message message);
	void setActionOnReceive(String fromPlayer, Message message, GameUpdater gameUpdater);
}
