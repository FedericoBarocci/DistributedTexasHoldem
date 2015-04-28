package breads_and_aces.gui.labels;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

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
}
