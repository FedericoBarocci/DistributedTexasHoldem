package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import javax.swing.ImageIcon;

public class CardGUI extends ElementGUI {

	private static final long serialVersionUID = -8908998211754205809L;
		
//	private Card card = null;
	
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
		
		super(CardsUtils.INSTANCE.getBackCard(), x, y);
//		this.w = super.getWidth();
//		this.h = super.getHeight();
	}
	
	/*public CardGUI(Card card, int x, int y) {
//		super();
		
		this.card = card;
		this.setSource(getCardImgPath());
		this.setBounds(x, y, this.getWidth(), this.getHeight());
		this.x = x;
		this.y = y;
		this.w = super.getWidth();
		this.h = super.getHeight();
	}*/
	
	/*public CardGUI(int x, int y, int w, int h) {
		super(imgDir + imgBack, x, y, w, h);
//		this.x = x;
//		this.y = y;
//		this.w = w;
//		this.h = h;
	}*/
	
	public CardGUI(ImageIcon/*Card */card, int x, int y, int w, int h) {
//		super(getCardImgPath(card), x, y, w, h);
		super(card, x, y, w, h);
//		this.card = card;
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
		super.setSource( CardsUtils.INSTANCE.getBackCard() );
	}
	
//	private Boolean isBlank() {
//		return card == null;
//	}
	
	/*public String getCardImgPath() {
		if (card==null) {
			return imgDir +File.separatorChar+ imgBack;
		}
		return CardsUtils.getCardImgPath(card);
	}*/
	
//	private ImageIcon getDefaultCard() {
//		return null;
//	}
	
	
}
