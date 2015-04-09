package breads_and_aces.game.model.players.keeper;

import java.util.List;

import breads_and_aces.game.model.players.player.Player;

public interface GamePlayersKeeper extends PlayersKeeper {
	public Player getPlayer(String playerId);
	public List<Player> getPlayers();
	public Player getNext(String playerId);
//	public boolean contains(String playerId);
//	public void remove(String targetplayerId);
}
