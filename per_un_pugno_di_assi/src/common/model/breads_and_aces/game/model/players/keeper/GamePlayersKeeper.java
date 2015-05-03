package breads_and_aces.game.model.players.keeper;

import java.util.List;

import breads_and_aces.game.model.players.player.Player;


public interface GamePlayersKeeper extends PlayersKeeper {
//	public Player getPlayer(String playerId);
//	public List<Player> getPlayers();
	public Player getNext(String playerId);
	
//	public void resetActions();
	public void resetActions(boolean forceReset);
//	public boolean contains(String playerId);
//	public void remove(String targetplayerId);
//	void resetPlayers(List<Player> players);

	public List<Player> getActivePlayers();
}
