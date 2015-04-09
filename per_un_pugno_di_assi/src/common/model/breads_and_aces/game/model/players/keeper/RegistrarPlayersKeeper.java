package breads_and_aces.game.model.players.keeper;

import java.util.Map;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;

public interface RegistrarPlayersKeeper extends PlayersKeeper {
	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player);
	public void addPlayers(Map<PlayerRegistrationId, Player> players);
	public Map<PlayerRegistrationId, Player> getIdsPlayersMap();
//	public Player getPlayer(String playerId);
//	public List<Player> getPlayers();
//	public boolean contains(String playerId);
//	public void remove(String targetplayerId);
//	public Player getNext(String playerId);
}
