package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumColor;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumFont;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumLine;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumRectangle;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerGUI {
	
	private Integer x;
	private Integer y;
	private Integer goal;
	
	private CardGUI cardGui1;
	private CardGUI cardGui2;
	
	private Card card1;
	private Card card2;
	
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final JLabel action = new JLabel("", SwingConstants.CENTER);
	private final JLabel score = new JLabel("", SwingConstants.CENTER);
	private final JPanel box = new TransparentPanel();
	private final JPanel scoreContainer = new TransparentPanel();
	private final JPanel scoreLevel = new TransparentPanel();
	
	public PlayerGUI(String playerId, Card card1, Card card2, Integer x, Integer y, Integer goal, Integer scoreVal, Boolean hideCards) {
		this.x = x;
		this.y = y;
		this.goal = goal;
		this.card1 = card1;
		this.card2 = card2;
		
		if (hideCards) {
			cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getBackCard(), x + CardsUtils.span1, y);
			cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getBackCard(), x + CardsUtils.span2, y);
		}
		else {
			cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card1), x + CardsUtils.span1, y);
			cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card2), x + CardsUtils.span2, y);
		}
		
		String s = "<html><div style='border-bottom:2px solid black'>"+ playerId + "</div></html>";
		
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.glass, EnumLine.playerBox, x + 10, y + 25);
		GuiUtils.INSTANCE.initPanel(scoreContainer, EnumRectangle.playerLevel, EnumColor.glass2, x, y + 25);
		GuiUtils.INSTANCE.initLabel(name, EnumRectangle.playerName, EnumColor.black, EnumFont.B15, s , x + 10, y + 90);
		GuiUtils.INSTANCE.initLabel(action, EnumRectangle.playerAction, EnumColor.black, EnumFont.B11, "WAIT", x + 10, y + 120);
		GuiUtils.INSTANCE.initLabel(score, EnumRectangle.playerScore, EnumColor.black, EnumFont.B11, "SCORE: 0", x + 10, y + 135);

		setScore(scoreVal);
	}
	
	public void setScore(Integer score) {
		int proportional = Math.floorDiv(135 * score, goal);
		int red = 200 + Math.floorDiv(55 * score, goal);
		int green = Math.floorDiv(230 * score, goal);
		
		this.scoreLevel.setBackground(new Color(red, green, 0));
		this.scoreLevel.setBounds(this.x, this.y + 160 - proportional, 10, proportional);
		this.score.setText("SCORE: " + score);
	}
	
	public void showCards(JFrame frame) {
		frame.getLayeredPane().remove( cardGui1 );
		frame.getLayeredPane().remove( cardGui2 );
		cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card1), x + CardsUtils.span1, y);
		cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card2), x + CardsUtils.span2, y);
		frame.getLayeredPane().add( cardGui1 );
		frame.getLayeredPane().add( cardGui2 );
	}
	
	public void unsetTokenView(JFrame frame) {
		frame.getContentPane().remove( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.glass, EnumLine.playerBox, x + 10, y + 25);
		frame.getContentPane().add( box );
	}
	
	public void setTokenView(JFrame frame) {
		frame.getContentPane().remove( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaGreen, EnumLine.playerToken, x + 10, y + 25);
		frame.getContentPane().add( box );
	}

	public void draw(JFrame frame) {
		frame.getContentPane().add( cardGui1 );
		frame.getContentPane().add( cardGui2 );
		frame.getContentPane().add( name );
		frame.getContentPane().add( action );
		frame.getContentPane().add( score );
		frame.getContentPane().add( box );
		frame.getContentPane().add( scoreContainer );
		frame.getContentPane().add( scoreLevel );
	}
	
	public void clearFromGui(JFrame frame) {
		frame.getContentPane().remove( cardGui1 );
		frame.getContentPane().remove( cardGui2 );
		frame.getContentPane().remove( name );
		frame.getContentPane().remove( action );
		frame.getContentPane().remove( score );
		frame.getContentPane().remove( box );
		frame.getContentPane().remove( scoreContainer );
		frame.getContentPane().remove( scoreLevel );
	}
}
