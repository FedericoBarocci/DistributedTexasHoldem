package bread_and_aces.game.model.oracle.responses;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.gui.view.ViewControllerDelegate;

public class OracleResponseEnd implements OracleResponse {

	private final String fromPlayer;
	private final ViewControllerDelegate viewControllerDelegate;

	@AssistedInject
	public OracleResponseEnd(ViewControllerDelegate viewControllerDelegate, @Assisted String fromPlayer) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.fromPlayer = fromPlayer;
	}

	@Override
	public Communication exec() {
		return Communication.END;
	}
	
	@Override
	public String toString() {
		return "End";
	}

	@Override
	public void finaly() {
		viewControllerDelegate.endGame(fromPlayer);
	}
}
