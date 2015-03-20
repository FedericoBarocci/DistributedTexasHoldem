package it.unibo.cs.sd.poker.gui;

import it.unibo.cs.sd.poker.Card;

public class CardGUI extends ElementGUI {

	private static final long serialVersionUID = 2087881853075706384L;
	
	private static String imgDir = "elements/cardset/";
	private static String imgBack = "back006.gif";	
	private Card card = null;
	
	private int x = 0;
	private int y = 0;
	private int w = 0;
	private int h = 0;
	
	public CardGUI() {
		super(imgDir + imgBack);
		this.w = this.getWidth();
		this.h = this.getHeight();
	}
	
	public CardGUI(Card card) {
		super();
		
		this.card = card;
		this.setSource(getCardImgPath());
		this.w = this.getWidth();
		this.h = this.getHeight();
	}
	
	public CardGUI(int x, int y) {
		super(imgDir + imgBack, x, y);
		this.x = x;
		this.y = y;
		this.w = this.getWidth();
		this.h = this.getHeight();
		
	}
	
	public CardGUI(Card card, int x, int y) {
		super();
		
		this.card = card;
		this.setSource(getCardImgPath());
		this.setBounds(x, y, this.getWidth(), this.getHeight());
		this.x = x;
		this.y = y;
		this.w = this.getWidth();
		this.h = this.getHeight();
	}
	
	public CardGUI(int x, int y, int w, int h) {
		super(imgDir + imgBack, x, y, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public CardGUI(Card card, int x, int y, int w, int h) {
		super();
		
		this.card = card;
		this.setSource(getCardImgPath());
		this.setBounds(x, y, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setCard(Card card) {
		this.card = card;
		this.setSource(getCardImgPath());
		this.setBounds(x, y, w, h);
	}
	
	public Card getCard() {
		return this.card;
	}
	
	public void clear() {
		this.card = null;
		this.setSource(getCardImgPath());
	}
	
	private Boolean isBlank() {
		return this.card == null;
	}
	
	public String getCardImgPath() {
		if (isBlank()) {
			return imgDir + imgBack;
		}
		
		String s = new String(imgDir);
		
		switch (card.getRank()) {
			case _2:  s += "02"; break;
			case _3:  s += "03"; break;
			case _4:  s += "04"; break;
			case _5:  s += "05"; break;
			case _6:  s += "06"; break;
			case _7:  s += "07"; break;
			case _8:  s += "08"; break;
			case _9:  s += "09"; break;
			case _10: s += "10"; break;
			case _J:  s += "11"; break;
			case _Q:  s += "12"; break;
			case _K:  s += "13"; break;
			case _A:  s += "01"; break;
		}
		
		switch (card.getSuit()) {
			case Cuori:  s += "h"; break;
			case Quadri: s += "d"; break;
			case Fiori:  s += "c"; break;
			case Picche: s += "s"; break;
		}
		
		s += ".gif";
		
		return s;
	}
}
