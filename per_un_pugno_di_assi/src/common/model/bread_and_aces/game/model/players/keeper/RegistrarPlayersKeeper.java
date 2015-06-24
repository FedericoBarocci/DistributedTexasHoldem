package bread_and_aces.game.model.players.keeper;

import java.util.Map;

import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.players.player.PlayerRegistrationId;

public interface RegistrarPlayersKeeper extends PlayersKeeper {
	void addPlayer(PlayerRegistrationId playerRegistrationId, Player player);
	void addPlayers(Map<PlayerRegistrationId, Player> players);

	Map<PlayerRegistrationId, Player> getRegisteredPlayers();
	void setMyName(String playerId);
	
	public Player getNext(String playerId);
	void setMyselfAsLeader();
}
