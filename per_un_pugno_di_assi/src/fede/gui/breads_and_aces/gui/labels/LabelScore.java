package breads_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

@LazySingleton
public class LabelScore extends JLabel {

	private static final long serialVersionUID = 8195941055460814873L;

	@Inject
	public LabelScore() {
		super("", SwingConstants.CENTER);
	}

	public void setValue(int value) {
		this.setText("" + value);
	}
}