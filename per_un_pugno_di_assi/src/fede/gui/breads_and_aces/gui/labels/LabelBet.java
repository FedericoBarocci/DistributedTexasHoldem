package breads_and_aces.gui.labels;

import javax.inject.Singleton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@Singleton
public class LabelBet extends JLabel {

	private static final long serialVersionUID = 3680782073061254144L;
	
	public LabelBet() {
		super("", SwingConstants.CENTER);
	}
}
