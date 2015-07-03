package bread_and_aces.game.model.players.keeper;

import bread_and_aces.game.model.players.player.Player;

public interface NextablePlayersKeeper {
	Player getNext(String playerId);
}
