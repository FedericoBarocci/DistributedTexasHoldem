package breads_and_aces.game.model.players.keeper;

import java.util.List;

import breads_and_aces.game.model.players.player.Player;

public interface PlayersKeeper {
	public Player getPlayer(String playerId);
	public List<Player> getPlayers();
	boolean contains(String playerId);
	void remove(String targetplayerId);
	
	String getMyName();
	void setMyName(String me);
	Player getMyPlayer();
}
