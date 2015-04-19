package breads_and_aces.game.model.players.keeper;

import java.util.List;

import breads_and_aces.game.model.players.player.Player;

public interface RegistrarPlayersKeeper2 extends PlayersKeeper {
	void addPlayer(/*String name,*/ Player player);
	void addPlayers(List<Player> players);
	List<String> getIdsPlayersMap();
	void setMe(String playerId);
}
