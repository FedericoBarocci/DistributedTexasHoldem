package bread_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;

import org.limewire.inject.LazySingleton;

@LazySingleton
public class LabelMessage extends JLabel {

	private static final long serialVersionUID = -1919648505962074193L;

	@Inject
	public LabelMessage() {
		super("");
	}
//
//	public void printMessage(String msg) {
//		this.setText(msg);
//	}
}
