package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.Check;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

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
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final Map<String, JButton> buttons = new HashMap<>();
	private final JPanel box = new TransparentPanel();
	
	private final static int xOffset = 60;
	
	public PlayerGUI(String playerId, Card card0, Card card1, Integer x, Integer y) {
		this.interactive = true;
		this.x = x;
		this.y = y;
		
		this.card1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card0), x, y);
		this.card2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(card1), x + xOffset, y);
		
		this.name.setText(playerId);
		
		this.box.setBackground(new Color(188, 221, 17, 70));
		this.box.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		//this.box.setBounds(this.x-10, this.y-10, 135, 170);
		this.box.setBounds(this.x-10, this.y+25, 135, 135);
		
		populateButtonsMap();
	}
	
	private void populateButtonsMap() {
		for (String	s: new String[]{"Check", "Call"}) {
			buttons.put(s, new JButton(s));
		}
	}
	
	/*public PlayerGUI(Player player, Boolean yesIsInteractive, Integer x, Integer y) {
		this.interactive = yesIsInteractive;
		this.x = x;
		this.y = y;
		
		if (interactive) {
			this.card1 = new CardGUI(player.getCards().get(0), x, y, cardDefaultWidth , cardDefaultHeight);
			this.card2 = new CardGUI(player.getCards().get(1), x + xOffset, y, cardDefaultWidth , cardDefaultHeight );
		}
		
//		else {
//			this.card1 = new CardGUI(this.x, this.y, cardDefaultWidth, cardDefaultHeight);
//			this.card2 = new CardGUI(this.x + xOffset, this.y, cardDefaultWidth, cardDefaultHeight);
//		}
		
		this.name.setText(player.getName());
		
		this.box.setBackground(new Color(188, 221, 17, 70));
		this.box.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		//this.box.setBounds(this.x-10, this.y-10, 135, 170);
		this.box.setBounds(this.x-10, this.y+25, 135, 135);
	}*/
	
	public CardGUI getCard1() {
		return card1;
	}
	
//	public void setCard1(Card card) {
//		card1.setCard(card);
//	}
	
	public CardGUI getCard2() {
		return card2;
	}
	
//	public void setCard2(Card card) {
//		card2.setCard(card);
//	}

	public String getName() {
		return name.getText();
	}
	
	public JLabel getLabel() {
		return name;
	}

	public void draw(JFrame frame) {
		JButton checkButton = buttons.get("Check");
		
		if (this.interactive)
			checkButton.setBounds(this.x+17, this.y + 120, 80, 30);
		
		name.setBounds(this.x, this.y + 80, 115, 40);
		name.setForeground(new Color(0, 0, 0));
		name.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		frame.getContentPane().add(card1);
		frame.getContentPane().add(card2);
		frame.getContentPane().add(name);
		
		if (this.interactive) {
			frame.getContentPane().add(checkButton);
			checkButton.addActionListener( new Check(/*model, view*/) );
		}
		
		frame.getContentPane().add(box);
	}
	
	/*@Override
	public String toString() {
		return name.getText() + " " + card1.getCardImgPath() + " "
				+ card2.getCardImgPath() + " " + x + " " + y;
	}*/

	public void clearFromGui(JFrame frame) {
		// TODO Auto-generated method stub
		frame.getContentPane().remove( card1 );
		frame.getContentPane().remove( card2 );
		frame.getContentPane().remove( name );
//		playerGui.clearButtonsMap(frame);
		buttons.values().stream().forEach(b->frame.getContentPane().remove(b));
		frame.getContentPane().remove( box );
	}
}
