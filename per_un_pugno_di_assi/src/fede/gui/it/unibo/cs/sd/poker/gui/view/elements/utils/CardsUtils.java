package it.unibo.cs.sd.poker.gui.view.elements.utils;

import it.unibo.cs.sd.poker.game.core.Card;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public enum CardsUtils {
	
	INSTANCE_SMALL(CardDimensions.width_small, CardDimensions.height_small),
	INSTANCE_BIG(CardDimensions.width_big, CardDimensions.height_big);
	
	private final String imgDir = "elements" + File.separatorChar + "cardset";
	private final String imgBack = "back.gif";
	private final Map<Card,ImageIcon> cards = new HashMap<>();
	private final ImageIcon backCard;
	private int w;
	private int h;
	
	private CardsUtils(int w, int h) {
		this.w = w;
		this.h = h;
		backCard = new ImageIcon(imgDir + File.separatorChar + imgBack);
		backCard.setImage( rescaleImage( backCard.getImage(), w, h) );
	}

	public ImageIcon getImageCard(Card card) {
		String cardImgPath = getCardImgPath(card);
		ImageIcon cardImage = cards.get(card);
		
		if (cardImage == null) {
			cardImage = new ImageIcon(cardImgPath);
			cards.put(card, cardImage );
		}
		
		return cardImage;
	}
	
	public ImageIcon getBackCard() {
		return backCard;
	}
	
	public String getCardImgPath(Card card) {
		if (card == null) {
			return imgDir + File.separatorChar + imgBack;
		}
		else {
			String s = new String(imgDir);
	
			s += File.separatorChar;
			
			int rank = card.getRankToInt().intValue();
			
			if (rank < 10) {
				s += "0";
			}
			
			s += "" + rank;
			s += card.getSuit().getSuitChar();
			s += ".gif";
	
			return s;
		}
	}
	
	interface CardDimensions {
		final static int width_small = 90;
		final static int height_small = 90;
		final static int width_big = 90;
		final static int height_big = 90;
	}
}
