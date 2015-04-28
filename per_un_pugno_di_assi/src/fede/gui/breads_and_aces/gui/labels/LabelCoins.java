package breads_and_aces.gui.labels;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

@LazySingleton
public class LabelCoins extends JLabel {

	private static final long serialVersionUID = 3854677502931158749L;

	public LabelCoins() {
		super("", SwingConstants.CENTER);
	}
}
