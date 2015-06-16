package bread_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.core.PotManager;
import bread_and_aces.game.model.oracle.actions.Action;

@LazySingleton
public class LabelBet extends JLabel {

	private static final long serialVersionUID = 3680782073061254144L;

	private final PotManager potManager;

	private int value = 0;

	@Inject
	public LabelBet(PotManager potManager) {
		super("", SwingConstants.CENTER);
		this.potManager = potManager;
	}

	public void setValue(int value) {
		this.value = value;
		this.setText("" + value);
	}

	public int getValue() {
		return value;
	}

	public Action getAction() {

		return potManager.getAction(value);	
		
		/*
		Action result = null;
		
		if ((value == potManager.getCurrentPot()) && value == 0)
			result = ActionSimple.CHECK;

		if ((value == potManager.getCurrentPot()) && (value != 0) && (value != potManager.getMax())) {
		//	ActionValue.CALL.setValue(value);
			result = ActionValue.CALL;
			result.setValue(value);
		}
		if ((value > potManager.getCurrentPot()) && (value < potManager.getMax())) {
	//		ActionValue.RAISE.setValue(value);
			result = ActionValue.RAISE;
			result.setValue(value);
		}
		if (value == potManager.getMax())
			result = ActionSimple.ALLIN;

		return result; */

	}
}
