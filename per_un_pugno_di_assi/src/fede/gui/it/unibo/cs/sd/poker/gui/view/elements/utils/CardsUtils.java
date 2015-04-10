package it.unibo.cs.sd.poker.gui.view.elements.utils;

import it.unibo.cs.sd.poker.game.core.Card;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
//	private int width;
//	private int height;
	
	private CardsUtils(int width, int height) {
//		this.width = width;
//		this.height = height;
		backCard = new ImageIcon(imgDir + File.separatorChar + imgBack);
		backCard.setImage( rescaleImage( backCard.getImage(), width, height) );
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
	
	public static Image rescaleImage(Image srcImg, int w, int h) {
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    
	    return resizedImg;
	}
	
	interface CardDimensions {
		final static int width_small = 90;
		final static int height_small = 90;
		final static int width_big = 90;
		final static int height_big = 90;
	}
}
