package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerGUI {
	
	private Integer x;
	private Integer y;
	private Integer winnerScore;
	
	private CardGUI card1;
	private CardGUI card2;
	
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final JLabel action = new JLabel("", SwingConstants.CENTER);
	private final JLabel score = new JLabel("", SwingConstants.CENTER);
	private final JPanel box = new TransparentPanel();
	private final JPanel scoreContainer = new TransparentPanel();
	private final JPanel scoreLevel = new TransparentPanel();
	
	public PlayerGUI(String playerId, Card card0, Card card1, Integer x, Integer y, Integer winnerScore) {
		this.x = x;
		this.y = y;
		this.winnerScore = winnerScore;
		this.card1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card0), x + 15, y);
		this.card2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card1), x + 72, y);
		
		String txt = "<html><div style='border-bottom:2px solid black'>"+ playerId + "</div></html>";
		
		GuiUtils.INSTANCE.initPanel(box, "playerBox", "glass", "playerBox", x+10, y+25);
		GuiUtils.INSTANCE.initPanel(scoreContainer, "playerLevel", "glass2", x, y+25);
		GuiUtils.INSTANCE.initLabel(name, "playerName", "black", "B15",txt , x+10, y+90);
		GuiUtils.INSTANCE.initLabel(action, "playerAction", "black", "B11", "WAIT", x+10, y+120);
		GuiUtils.INSTANCE.initLabel(score, "playerScore", "black", "B11", "SCORE: 0", x+10, y+135);

		setScore(0);
	}
	
	public void setScore(Integer score) {
		int proportional = Math.floorDiv(135*score, winnerScore);
		int red = 200 + Math.floorDiv(55*score, winnerScore);
		int green = Math.floorDiv(230*score, winnerScore);
		
		this.scoreLevel.setBackground(new Color(red, green, 0));
		this.scoreLevel.setBounds(this.x, this.y + 160 - proportional, 10, proportional);
		this.score.setText("SCORE: " + score);
	}

	public void draw(JFrame frame) {
		frame.getContentPane().add( card1 );
		frame.getContentPane().add( card2 );
		frame.getContentPane().add( name );
		frame.getContentPane().add( action );
		frame.getContentPane().add( score );
		frame.getContentPane().add( box );
		frame.getContentPane().add( scoreContainer );
		frame.getContentPane().add( scoreLevel );
	}
	
	public void clearFromGui(JFrame frame) {
		frame.getContentPane().remove( card1 );
		frame.getContentPane().remove( card2 );
		frame.getContentPane().remove( name );
		frame.getContentPane().remove( action );
		frame.getContentPane().remove( score );
		frame.getContentPane().remove( box );
		frame.getContentPane().remove( scoreContainer );
		frame.getContentPane().remove( scoreLevel );
	}
}
