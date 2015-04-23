package breads_and_aces.game.model.players.keeper;

import breads_and_aces.game.model.players.player.Player;

public interface PlayersKeeper {
	boolean contains(String playerId);
	void remove(String targetplayerId);
	String getMyName();
	void setMyName(String me);
	Player getMyPlayer();
}
