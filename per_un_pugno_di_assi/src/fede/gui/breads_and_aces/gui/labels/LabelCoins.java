package breads_and_aces.gui.labels;

import javax.inject.Singleton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@Singleton
public class LabelCoins extends JLabel {

	private static final long serialVersionUID = 3854677502931158749L;

	public LabelCoins() {
		super("", SwingConstants.CENTER);
	}
}
