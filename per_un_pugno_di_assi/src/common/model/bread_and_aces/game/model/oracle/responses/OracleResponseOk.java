package bread_and_aces.game.model.oracle.responses;

import bread_and_aces.game.model.controller.Communication;

public class OracleResponseOk implements OracleResponse {

	@Override
	public Communication exec() {
		return Communication.ACTION;
	}
	
	@Override
	public String toString() {
		return "Ok";
	}

	@Override
	public void finaly() {
		return;
	}
}
