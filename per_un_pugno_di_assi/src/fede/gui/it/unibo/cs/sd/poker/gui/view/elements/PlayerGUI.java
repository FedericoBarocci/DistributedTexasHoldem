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
	
	private CardGUI card1;
	private CardGUI card2;
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final JLabel action = new JLabel("ACTION", SwingConstants.CENTER);
	private final JLabel score = new JLabel("PUNTI: 0", SwingConstants.CENTER);
	private final JPanel box = new TransparentPanel();
	
	public PlayerGUI(String playerId, Card card0, Card card1, Integer x, Integer y) {
		this.x = x;
		this.y = y;
		
		this.card1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card0), x + 5, y);
		this.card2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card1), x + 62, y);
		
		this.name.setText(playerId);
		
		//this.box.setBackground(new Color(255, 230, 0, 200));
		this.box.setBackground(new Color(255, 255, 255, 60));
		this.box.setBorder(new LineBorder(new Color(255, 230, 0, 255), 1));
		//this.box.setBounds(this.x-10, this.y-10, 135, 170);
		this.box.setBounds(this.x, this.y+25, 120, 135);
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
		name.setBounds(this.x, this.y + 90, 115, 20);
		name.setForeground(new Color(0, 0, 0));
		name.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		action.setBounds(this.x, this.y + 120, 115, 20);
		action.setForeground(new Color(0, 0, 0));
		action.setFont(new Font("SansSerif", Font.BOLD, 11));
		
		score.setBounds(this.x, this.y + 135, 115, 20);
		score.setForeground(new Color(0, 0, 0));
		score.setFont(new Font("SansSerif", Font.BOLD, 11));
		
		frame.getContentPane().add(card1);
		frame.getContentPane().add(card2);
		frame.getContentPane().add(name);
		frame.getContentPane().add(action);
		frame.getContentPane().add(score);
		frame.getContentPane().add(box);
	}
	
	public void clearFromGui(JFrame frame) {
		frame.getContentPane().remove( card1 );
		frame.getContentPane().remove( card2 );
		frame.getContentPane().remove( name );
		frame.getContentPane().remove( box );
	}
}
