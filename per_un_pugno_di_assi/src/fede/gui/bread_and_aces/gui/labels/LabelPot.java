package bread_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;

@LazySingleton
public class LabelPot extends JLabel {

	private static final long serialVersionUID = -5705641205579791329L;
	
	private final GamePlayersKeeper gamePlayersKeeper;

	@Inject
	public LabelPot(GamePlayersKeeper gamePlayersKeeper) {
		super("", SwingConstants.CENTER);
	
		this.gamePlayersKeeper = gamePlayersKeeper;
	}

	public void setValue(int value) {
		int mybet =	gamePlayersKeeper.getMyPlayer().getBet() + gamePlayersKeeper.getMyPlayer().getTotalBet();
		this.setText("" + mybet + " / " + value);
	}
}
