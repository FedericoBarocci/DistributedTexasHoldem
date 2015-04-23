package breads_and_aces.game.model.players.keeper;

import java.util.Map;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;

public interface RegistrarPlayersKeeper extends PlayersKeeper {
	void addPlayer(PlayerRegistrationId playerRegistrationId, Player player);
	void addPlayers(Map<PlayerRegistrationId, Player> players);
	Map<PlayerRegistrationId, Player> getRegisteredPlayers();
//	void addPlayer(/*String name,*/ Player player);
//	void addPlayers(List<Player> players);
//	List<Player> getRegistredPlayers();
	void setMyName(String playerId);
}
