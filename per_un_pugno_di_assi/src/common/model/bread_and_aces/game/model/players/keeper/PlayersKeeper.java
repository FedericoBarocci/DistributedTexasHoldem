package bread_and_aces.game.model.players.keeper;

import java.util.List;

import bread_and_aces.game.model.players.player.Player;

public interface PlayersKeeper {
	Player getPlayer(String playerId);
	List<Player> getPlayers();
	boolean contains(String playerId);
	void remove(String targetplayerId);
	Player getFirst();
	
	String getMyName();
	void setMyName(String me);
	Player getMyPlayer();
}
