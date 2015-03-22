package it.unibo.cs.sd.poker.mvc;

import it.unibo.cs.sd.poker.Player;
import it.unibo.cs.sd.poker.gui.BackgroundGUI;
import it.unibo.cs.sd.poker.gui.CardGUI;
import it.unibo.cs.sd.poker.gui.ElementGUI;
import it.unibo.cs.sd.poker.gui.PlayerGUI;
import it.unibo.cs.sd.poker.gui.TransparentPanel;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class View {
	public JFrame frame = new JFrame();
	
	private BackgroundGUI background = new BackgroundGUI("bg.jpg", 1280, 720);
	private List<PlayerGUI> players = new ArrayList<PlayerGUI>();
	
	public JLabel lblWinners = new JLabel("", SwingConstants.CENTER);
	
	public JButton btnDeal = new JButton("START");
	public JButton btnFlop = new JButton("Flop");
	public JButton btnTurn = new JButton("Turn");
	public JButton btnRiver = new JButton("River");
	public JButton btnWinners = new JButton("Winner(s)");
	
	public CardGUI tableCard1 = new CardGUI(300, 250);
	public CardGUI tableCard2 = new CardGUI(450, 250);
	public CardGUI tableCard3 = new CardGUI(600, 250);
	public CardGUI tableCard4 = new CardGUI(750, 250);
	public CardGUI tableCard5 = new CardGUI(900, 250);
	
	public View(){ 
		frame.setLayout(null);
		frame.setContentPane(background);
		frame.setBounds(0, 0, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel panel = new TransparentPanel();
		panel.setBackground(new Color(174, 234, 255, 70));
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 1));
        panel.setBounds(275, 225, 740, 185);
        
		this.add(tableCard1);
		this.add(tableCard2);
		this.add(tableCard3);
		this.add(tableCard4);
		this.add(tableCard5);
		
		frame.getContentPane().add(panel);
		
		btnFlop.setEnabled(false);
		btnTurn.setEnabled(false);
		btnRiver.setEnabled(false);
		btnWinners.setEnabled(false);
		
		lblWinners.setForeground(new Color(0, 0, 0));
		lblWinners.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
		lblWinners.setBounds(400, 50, 500, 50);
		frame.getContentPane().add(lblWinners);
		
		btnDeal.setBounds(12, 12, 103, 25);
		frame.getContentPane().add(btnDeal);
		
		btnFlop.setBounds(12, 42, 103, 25);
		frame.getContentPane().add(btnFlop);
		
		btnTurn.setBounds(12, 72, 103, 25);
		frame.getContentPane().add(btnTurn);

		btnRiver.setBounds(12, 102, 103, 25);
		frame.getContentPane().add(btnRiver);

		btnWinners.setBounds(12, 132, 103, 25);
		frame.getContentPane().add(btnWinners);
	}
	
	public void add(ElementGUI element) {
		frame.getContentPane().add(element);
	}
	
	public List<PlayerGUI> getPlayers() {
		return this.players;
	}
	
	public void showPlayersCards(List<Player> playerList) {
		for(int i = 0; i < playerList.size(); i++) {
			this.players.get(i).setCard1(playerList.get(i).getCards().get(0));
			this.players.get(i).setCard2(playerList.get(i).getCards().get(1));
		}
	}
	
	public void addPlayer(Player p) {
		PlayerGUI pgui = new PlayerGUI(p, (180 * players.size()) + 110, 500);
		players.add(pgui);
		pgui.draw(frame);
		frame.getContentPane().repaint();
		//System.out.println(pgui.toString());
	}
	
	public void clear() {
		for(PlayerGUI p : this.players) {
			frame.getContentPane().remove(p.getCard1());
			frame.getContentPane().remove(p.getCard2());
			frame.getContentPane().remove(p.getName());
			frame.getContentPane().remove(p.check);
			frame.getContentPane().remove(p.box);
		}
		
		this.players.clear();
		this.lblWinners.setText("");
	}

	public void setToken(Integer i) {
		players.get(i).getName().setForeground(new Color(204, 0, 0));
	}

	public void unsetToken(Integer i) {
		players.get(i).getName().setForeground(new Color(0, 0, 0));
	}

	public void resetToken() {
		for(int i=0; i < players.size(); i++) {
			if(i == 0) 
				setToken(i);
			else 
				unsetToken(i);
		}
	}
}
