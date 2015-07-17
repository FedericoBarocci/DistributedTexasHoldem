package bread_and_aces.game.model.oracle.responses;

import bread_and_aces.game.model.controller.Communication;

public interface OracleResponse {
	Communication exec();
	String toString();
	void complete();
}
