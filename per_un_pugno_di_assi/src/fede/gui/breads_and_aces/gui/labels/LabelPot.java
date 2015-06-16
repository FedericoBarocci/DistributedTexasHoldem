package breads_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

@LazySingleton
public class LabelPot extends JLabel {

	private static final long serialVersionUID = 3680782073061254144L;

	@Inject
	public LabelPot() {
		super("", SwingConstants.CENTER);
	}

	public void setValue(int value) {
		this.setText("" + value);
	}
}
