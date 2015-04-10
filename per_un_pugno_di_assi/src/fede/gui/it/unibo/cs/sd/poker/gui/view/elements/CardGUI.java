package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import javax.swing.ImageIcon;

public class CardGUI extends ElementGUI {

	private static final long serialVersionUID = -8908998211754205809L;
		
	public CardGUI(int x, int y) {
		super(CardsUtils.INSTANCE_BIG.getBackCard(), x, y);
	}
	
	public CardGUI(ImageIcon card, int x, int y) {
		//super(CardsUtils.INSTANCE_BIG.getImageCard(card), x, y);
		super(card, x, y);
	}
	
	/*public CardGUI(ImageIcon card, int x, int y) {
		//super(CardsUtils.INSTANCE_SMALL.getImageCard(card), x, y, w, h);
		super(card, x, y);
	}*/

	private void setCard(ImageIcon card) {
		super.setSource(card);
		//super.setBounds( x, y, card.getIconWidth(), card.getIconHeight() );
	}
}
