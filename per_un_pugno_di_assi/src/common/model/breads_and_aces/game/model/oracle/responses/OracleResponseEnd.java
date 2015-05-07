package breads_and_aces.game.model.oracle.responses;

import breads_and_aces.game.model.controller.Communication;
import breads_and_aces.gui.view.ViewControllerDelegate;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

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
		viewControllerDelegate.endGame(fromPlayer);
		return Communication.END;
	}
	
	@Override
	public String toString() {
		return "End";
	}
}
