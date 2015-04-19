package breads_and_aces.game.model.players.keeper;

import java.util.List;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.game.model.players.player.Player;

public interface RegistrarPlayersKeeper extends PlayersKeeper {
//	void addPlayer(PlayerRegistrationId playerRegistrationId, Player player);
//	void addPlayers(Map<PlayerRegistrationId, Player> players);
//	Map<PlayerRegistrationId, Player> getIdsPlayersMap();
//	void setMe(String playerId);
////	public Player getPlayer(String playerId);
////	public List<Player> getPlayers();
////	public boolean contains(String playerId);
////	public void remove(String targetplayerId);
////	public Player getNext(String playerId);
	void addPlayer(/*String name,*/ Player player);
	void addPlayers(List<Player> players);
	List<Player> getRegistredPlayers();
	void setMe(String playerId);
}
