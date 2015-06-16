package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import bread_and_aces.game.model.players.player.Player;

public interface OracleResponseFactory {
	OracleResponseOk createOracleResponseOk();
	OracleResponseWinner createOracleResponseWinner(List<Player> winners);
	OracleResponseNextStep createOracleResponseNextStep(List<Player> players);
	OracleResponseEnd createOracleResponseEnd(String fromPlayer);
}
