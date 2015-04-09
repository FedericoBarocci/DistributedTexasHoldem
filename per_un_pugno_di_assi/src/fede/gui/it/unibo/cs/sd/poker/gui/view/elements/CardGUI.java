package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import javax.swing.ImageIcon;

public class CardGUI extends ElementGUI {

	private static final long serialVersionUID = -8908998211754205809L;
		
	private Card card = null;
	
//	private int x = 0;
//	private int y = 0;
//	private int w = 0;
//	private int h = 0;
	
	/*public CardGUI() {
//		super(imgDir + imgBack);
//		this.w = super.getWidth();
//		this.h = super.getHeight();
		super(imgDir+File.separatorChar+imgBack);
	}*/
	
	/*public CardGUI(Card card) {
		this.card = card;
		setSource(getCardImgPath());
		this.w = super.getWidth();
		this.h = super.getHeight();
	}*/
	
	public CardGUI(int x, int y) {
//		super(imgDir + imgBack, x, y);
//		this.x = x;
//		this.y = y;
		
		super(CardsUtils.INSTANCE_BIG.getBackCard(), x, y);
//		this.w = super.getWidth();
//		this.h = super.getHeight();
	}
	
	public CardGUI(Card card, int x, int y) {
		super(CardsUtils.INSTANCE_BIG.getImageCard(card), x, y);
		this.card = card;
		this.setBounds(x, y, this.getWidth(), this.getHeight());
//		this.w = super.getWidth();
//		this.h = super.getHeight();
	}
	
	/*public CardGUI(int x, int y, int w, int h) {
		super(imgDir + imgBack, x, y, w, h);
//		this.x = x;
//		this.y = y;
//		this.w = w;
//		this.h = h;
	}*/
	
	public CardGUI(Card card, int x, int y, int w, int h) {
		super(CardsUtils.INSTANCE_SMALL.getImageCard(card), x, y, w, h);
		this.card = card;
	}
	
	/*public void setCard(Card card) {
		this.card = card;
		super.setSource( getCardImgPath() );
		super.setBounds( x, y, w, h);
	}*/
	public void setCard(ImageIcon card) {
		super.setSource(card);
		super.setBounds( x, y, card.getIconWidth(), card.getIconHeight() );
	}
	
//	public Card getCard() {
//		return card;
//	}
	
	public void clear() {
//		this.card = null;
		//super.setSource( CardsUtils.INSTANCE.getBackCard() );
	}
	
//	private Boolean isBlank() {
//		return card == null;
//	}
	
	@Override
	public String toString() {
		return CardsUtils.INSTANCE_BIG.getCardImgPath(card);
	}
	
//	private ImageIcon getDefaultCard() {
//		return null;
//	}
	
	
}
