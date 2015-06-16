package bread_and_aces.gui.view.elements;

import javax.swing.ImageIcon;

import bread_and_aces.gui.view.elements.utils.CardsUtils;

public class CardGUI extends ElementGUI {

	private static final long serialVersionUID = -8908998211754205809L;
		
	public CardGUI(int x, int y) {
		super(CardsUtils.INSTANCE_BIG.getBackCard(), x, y);
	}
	
	public CardGUI(ImageIcon card, int x, int y) {
		super(card, x, y);
	}
}
