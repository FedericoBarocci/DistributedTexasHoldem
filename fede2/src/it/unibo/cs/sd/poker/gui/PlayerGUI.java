package it.unibo.cs.sd.poker.gui;

import it.unibo.cs.sd.poker.game.Card;
import it.unibo.cs.sd.poker.game.Player;
import it.unibo.cs.sd.poker.gui.actionlisteners.Check;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PlayerGUI {
	
	private Boolean interactive;
	private Integer x;
	private Integer y;
	
	private CardGUI card1;
	private CardGUI card2;
	private JLabel name = new JLabel("", SwingConstants.CENTER);
	public JButton check = new JButton("Check");
	public JPanel box = new TransparentPanel();
	
	public PlayerGUI(Player player, Boolean interactive, Integer x, Integer y) {
		this.interactive = interactive;
		this.x = x;
		this.y = y;
		
		if (interactive) {
			this.card1 = new CardGUI(player.getCards().get(0), this.x, this.y, 54, 81);
			this.card2 = new CardGUI(player.getCards().get(1), this.x + 60, this.y, 54, 81);
		}
		else {
			this.card1 = new CardGUI(this.x, this.y, 54, 81);
			this.card2 = new CardGUI(this.x + 60, this.y, 54, 81);
		}
		
		this.name.setText(player.getName());
		
		this.box.setBackground(new Color(188, 221, 17, 70));
		this.box.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		//this.box.setBounds(this.x-10, this.y-10, 135, 170);
		this.box.setBounds(this.x-10, this.y+25, 135, 135);
	}
	
	public CardGUI getCard1() {
		return card1;
	}
	
	public void setCard1(Card card) {
		this.card1.setCard(card);
	}
	
	public CardGUI getCard2() {
		return card2;
	}
	
	public void setCard2(Card card) {
		this.card2.setCard(card);
	}

	public JLabel getName() {
		return name;
	}

	public void setName(JLabel name) {
		this.name = name;
	}

	public void draw(JFrame frame/*, Model model, GameView view*/) {
		if (this.interactive)
			check.setBounds(this.x+17, this.y + 120, 80, 30);
		
		name.setBounds(this.x, this.y + 80, 115, 40);
		name.setForeground(new Color(0, 0, 0));
		name.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		frame.getContentPane().add(card1);
		frame.getContentPane().add(card2);
		frame.getContentPane().add(name);
		
		if (this.interactive) {
			frame.getContentPane().add(check);
			check.addActionListener( new Check(/*model, view*/) );
		}
		
		frame.getContentPane().add(box);
	}
	
	@Override
	public String toString() {
		return name.getText() + " " + card1.getCardImgPath() + " "
				+ card2.getCardImgPath() + " " + x + " " + y;
	}
}
