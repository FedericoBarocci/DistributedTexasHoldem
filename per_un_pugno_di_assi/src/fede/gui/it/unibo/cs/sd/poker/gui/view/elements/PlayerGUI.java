package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PlayerGUI {
	
	private Integer x;
	private Integer y;
	private Integer winnerScore;
	
	private CardGUI card1;
	private CardGUI card2;
	
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final JLabel action = new JLabel("WAIT", SwingConstants.CENTER);
	private final JLabel scoreLabel = new JLabel("SCORE: 0", SwingConstants.CENTER);
	private final JPanel box = new TransparentPanel();
	private final JPanel scoreContainer = new TransparentPanel();
	private final JPanel scoreLevel = new TransparentPanel();
	
	public PlayerGUI(String playerId, Card card0, Card card1, Integer x, Integer y, Integer winnerScore) {
		this.x = x;
		this.y = y;
		this.winnerScore = winnerScore;
		
		this.card1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card0), x + 5, y);
		this.card2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card1), x + 62, y);
		
		this.name.setText("<html><div style='border-bottom:2px solid black'>"+playerId+"</div></html>");
		
		//this.box.setBackground(new Color(255, 230, 0, 200));
		this.box.setBackground(new Color(255, 255, 255, 80));
		this.box.setBorder(new LineBorder(new Color(255, 230, 0, 255), 1));
		this.box.setBounds(this.x, this.y+25, 120, 135);
		
		this.scoreContainer.setBackground(new Color(255, 255, 255, 60));
		this.scoreContainer.setBounds(this.x-10, this.y+25, 10, 135);
		
		
		setScore(0);
	}
	
	public void setScore(Integer score) {
		int proportional = Math.floorDiv(135*score, winnerScore);
		
		this.scoreLevel.setBackground(new Color(200 + Math.floorDiv(55*score, winnerScore), Math.floorDiv(230*score, winnerScore), 0, 255));
		this.scoreLevel.setBounds(this.x-10, this.y + 160 - proportional, 10, proportional);
		this.scoreLabel.setText("SCORE: " + score);
	}
	
	public CardGUI getCard1() {
		return card1;
	}
	
	public CardGUI getCard2() {
		return card2;
	}
	
	public String getName() {
		return name.getText();
	}
	
	public JLabel getLabel() {
		return name;
	}

	public void draw(JFrame frame) {
		name.setBounds(this.x, this.y + 90, 115, 25);
		name.setForeground(new Color(0, 0, 0));
		name.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		action.setBounds(this.x, this.y + 120, 115, 20);
		action.setForeground(new Color(0, 0, 0));
		action.setFont(new Font("SansSerif", Font.BOLD, 11));
		
		scoreLabel.setBounds(this.x, this.y + 135, 115, 20);
		scoreLabel.setForeground(new Color(0, 0, 0));
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
		
		/*JSlider slider = new JSlider(JSlider.VERTICAL, 0, 1000, 0);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(1000/20);
        slider.setMajorTickSpacing(1000/5);
        slider.setBounds(this.x+20, 400, 80, 250);
        slider.setOpaque(false);
        slider.setForeground(new Color(0, 0, 0));
        slider.setUI(new SliderGUI(slider));
        frame.getContentPane().add(slider);*/
		
		frame.getContentPane().add(card1);
		frame.getContentPane().add(card2);
		frame.getContentPane().add(name);
		frame.getContentPane().add(action);
		frame.getContentPane().add(scoreLabel);
		frame.getContentPane().add(box);
		frame.getContentPane().add(scoreContainer);
		frame.getContentPane().add(scoreLevel);
	}
	
	public void clearFromGui(JFrame frame) {
		frame.getContentPane().remove( card1 );
		frame.getContentPane().remove( card2 );
		frame.getContentPane().remove( name );
		frame.getContentPane().remove( action );
		frame.getContentPane().remove( scoreLabel );
		frame.getContentPane().remove( box );
		frame.getContentPane().remove( scoreContainer );
		frame.getContentPane().remove( scoreLevel );
	}
}
