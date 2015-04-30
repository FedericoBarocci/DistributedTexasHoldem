package breads_and_aces.gui.labels;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.oracle.actions.ActionSimple;

@LazySingleton
public class LabelBet extends JLabel {

	private static final long serialVersionUID = 3680782073061254144L;
	
	private int value = 0;
	
	public LabelBet() {
		super("", SwingConstants.CENTER);
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
