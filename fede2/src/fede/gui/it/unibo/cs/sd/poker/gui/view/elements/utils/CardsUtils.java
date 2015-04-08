package it.unibo.cs.sd.poker.gui.view.elements.utils;

import it.unibo.cs.sd.poker.game.core.Card;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public enum CardsUtils {
	
	INSTANCE;
	
	
	private final String imgDir = "elements"+File.separatorChar+"cardset";
	private final String imgBack = "back.gif";
	private final Map<Card,ImageIcon> cards = new HashMap<>();
	private final ImageIcon backCard;
	
	private CardsUtils() {
		backCard = new ImageIcon(imgDir+File.separatorChar+imgBack);
	}

	public ImageIcon getCard(Card card) {
		String cardImgPath = getCardImgPath(card);
		ImageIcon cardImage = cards.get(card);
		if (cardImage==null) {
			cardImage = new ImageIcon(cardImgPath);
			cards.put(card, cardImage );
		}
		return cardImage;
	}
	
	public ImageIcon getBackCard() {
		return backCard;
	}
	
	private String getCardImgPath(Card card) {
		String s = new String(imgDir);
		s+=File.separatorChar;

		/*switch (card.getRank()) {
		case _2:
			s += "02";
			break;
		case _3:
			s += "03";
			break;
		case _4:
			s += "04";
			break;
		case _5:
			s += "05";
			break;
		case _6:
			s += "06";
			break;
		case _7:
			s += "07";
			break;
		case _8:
			s += "08";
			break;
		case _9:
			s += "09";
			break;
		case _10:
			s += "10";
			break;
		case _J:
			s += "11";
			break;
		case _Q:
			s += "12";
			break;
		case _K:
			s += "13";
			break;
		case _A:
			s += "01";
			break;
		}*/
		
		int rank = card.getRankToInt().intValue();
		if (rank<10)
			s += "0";
		s+= ""+rank;

		/*switch (card.getSuit()) {
		case Cuori:
			s += "h";
			break;
		case Quadri:
			s += "d";
			break;
		case Fiori:
			s += "c";
			break;
		case Picche:
			s += "s";
			break;
		}*/
		s+= card.getSuit().getSuitChar();

		s += ".gif";

		return s;
	}
}
