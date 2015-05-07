package breads_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.oracle.actions.ActionSimple;
import breads_and_aces.game.model.state.GameState;

@LazySingleton
public class LabelBet extends JLabel {

	private static final long serialVersionUID = 3680782073061254144L;
	
	private int value = 0;

	private final GameState gameState;
	
	@Inject
	public LabelBet(GameState gameState) {
		super("", SwingConstants.CENTER);
		this.gameState = gameState;
	}
	
	public void setValue(int value) {
		this.value = value;
		this.setText("" + value);
	}
	
	public int getValue() {
		return value;
	}

	public Action getAction() {
		return ActionSimple.CHECK;
	}
}
