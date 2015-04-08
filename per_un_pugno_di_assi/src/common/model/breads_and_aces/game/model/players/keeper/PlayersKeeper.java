package breads_and_aces.game.model.players.keeper;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;

public interface PlayersKeeper {

	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player);
	public NavigableMap<PlayerRegistrationId, Player> getIdsPlayersMap();
	public List<Player> getPlayers();
	public void setPlayers(Map<PlayerRegistrationId, Player> players);
	public Player getNext(String playerId);
	public boolean contains(String playerId);
	public void remove(String targetplayerId);
	public Player getPlayer(String playerId);
}
