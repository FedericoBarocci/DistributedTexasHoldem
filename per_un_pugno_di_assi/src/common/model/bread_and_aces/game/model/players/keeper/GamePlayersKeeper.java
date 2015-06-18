package bread_and_aces.game.model.players.keeper;

import java.util.List;

import bread_and_aces.game.model.players.player.Player;


public interface GamePlayersKeeper extends PlayersKeeper {
	public Player getNext(String playerId);
	public void resetActions(boolean forceReset);
	public List<Player> getActivePlayers();
}
