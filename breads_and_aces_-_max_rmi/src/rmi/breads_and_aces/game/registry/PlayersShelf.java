package breads_and_aces.game.registry;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;

public interface PlayersShelf {

	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player);
	public NavigableMap<PlayerRegistrationId, Player> getIdsPlayersMap();
	public List<Player> getPlayers();
	public void setPlayers(Map<PlayerRegistrationId, Player> players);
	public Player getNext(String playerId);
	public boolean contains(String playerId);
	public void remove(String targetplayerId);
	public Player getPlayer(String playerId);
}
