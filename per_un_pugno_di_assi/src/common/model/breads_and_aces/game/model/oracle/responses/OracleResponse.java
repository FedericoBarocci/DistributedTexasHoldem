package breads_and_aces.game.model.oracle.responses;

import breads_and_aces.game.model.controller.Communication;

public interface OracleResponse {
	Communication exec();
	String toString();
}
