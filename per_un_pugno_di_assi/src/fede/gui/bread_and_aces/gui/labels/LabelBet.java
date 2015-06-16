package bread_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

@LazySingleton
public class LabelBet extends JLabel {

	private static final long serialVersionUID = -4190796864327315720L;

	@Inject
	public LabelBet() {
		super("", SwingConstants.CENTER);
	}

	public void setValue(int value) {
		this.setText("" + value);
	}
}
