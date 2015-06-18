package bread_and_aces.game.model.controller;

import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.updater.GameUpdater;

public interface DistributedControllerRemote {
	
//	void receiveStartGame(String whoHasToken);
	Communication removePlayer(String playerId);
	void setActionOnSend(ActionKeeper actionKeeper);
	void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper);
	void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper, GameUpdater gameUpdater);

}
