package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.gui.view.elements.BackgroundGUI;
import it.unibo.cs.sd.poker.gui.view.elements.CardGUI;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.PlayerGUI;
import it.unibo.cs.sd.poker.gui.view.elements.TransparentPanel;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import breads_and_aces.game.model.players.player.Player;

public class GameView {
	public JFrame frame = new JFrame();
	
	private BackgroundGUI background = new BackgroundGUI("elements/bg.jpg", 1280, 720);
	// TODO usare playerskeeper
	private Map<String, PlayerGUI> playersGui = new LinkedHashMap<>();
	
	public JLabel lblWinners = new JLabel("", SwingConstants.CENTER);
	public List<CardGUI> tableCardsGui = new ArrayList<>();
	
	public GameView() {
		super();
		frame.setLayout(null);
		frame.setTitle("Poker Distributed Hold'em");
		frame.setContentPane(background);
		frame.setBounds(0, 0, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}
	
	private void initTableGui() {		
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(300 + 150*i, 250);
			tableCardsGui.add(c);
			addElementGui(c);
		}
	}
	
	public void init(List<Player> players) {
		initElementsGUI(players);
		initTableGui();
		
		lblWinners.setForeground(new Color(0, 0, 0));
		lblWinners.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
		lblWinners.setBounds(400, 50, 500, 50);
		frame.getContentPane().add(lblWinners);
		
		JPanel panel = new TransparentPanel();
		panel.setBackground(new Color(174, 234, 255, 70));
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 1));
		panel.setBounds(275, 225, 740, 185);
		frame.getContentPane().add(panel);
	}
	
	/* TODO Per la costruzione della gui di welcome
	public void createIntroGUI() {
		acceptButton.setBounds(12, 12, 103, 25);
		this.frame.getContentPane().add(acceptButton);
		acceptButton.addActionListener(new AcceptListener(model, view));
		
		addressField.setBounds(212, 12, 103, 25);
		this.frame.getContentPane().add(addressField);
		
		loginButton.setBounds(212, 42, 103, 25);
		this.frame.getContentPane().add(loginButton);
		loginButton.addActionListener(new LoginListener(model, view));
		
		usernameField.setBounds(412, 12, 103, 25);
		this.frame.getContentPane().add(usernameField);
	}
	*/
	
//	private String getUsername() {
//		return this.usernameField.getText();
//	}
	
	private void initElementsGUI(List<Player> players) {
		for (Player p : players) {
			createAndAddPlayerGui(p);
		}
		
		for (CardGUI c : tableCardsGui) {
			removeElementGui(c);
		}
		
		initTableGui();
		
		// TODO restore?
//		this.resetToken();
	}
	
	private void addElementGui(ElementGUI element) {
		frame.getContentPane().add(element);
	}
	
	private void removeElementGui(ElementGUI element) {
		frame.getContentPane().remove(element);
	}
	
	/*private Collection<PlayerGUI> getPlayers() {
		return playersGui.values();
	}*/
	
	private void clearPlayers() {
		playersGui.clear();
	}
	
	/*private void showPlayersCards(List<Player> playerList) {
//		for (PlayerGUI playerGui : playersGui.values()) {
//			
//		}
		for(int i = 0; i < playerList.size(); i++) {
			playersGui.get(i).setCard1(playerList.get(i).getCards().get(0));
			playersGui.get(i).setCard2(playerList.get(i).getCards().get(1));
		}
	}*/
	
	private void createAndAddPlayerGui(Player p) {		
		PlayerGUI playerGui = new PlayerGUI(p.getName(),  
				p.getCards().get(0), 
				p.getCards().get(1), 
				(180 * playersGui.size()) + 110, 
				500);
		
		playersGui.put(p.getName(), playerGui);
		playerGui.draw(frame);
		frame.getContentPane().repaint();
	}
	
	private void clear() {
		for(PlayerGUI playerGui : playersGui.values()) {
			playerGui.clearFromGui(frame);
		}
		
		playersGui.clear();
		lblWinners.setText("");
	}
	
	private void setToken(Integer i) {
		playersGui.get(i).getLabel().setForeground(new Color(204, 0, 0));
	}

//	private void unsetToken() {
//		for (int i = 0; i < getPlayers().size(); i++) {
//			unsetToken(i);
//		}
//	}
//	
//	private void unsetToken(Integer i) {
//		getPlayers().get(i).getName().setForeground(new Color(0, 0, 0));
//	}

//	private void resetToken() {
//		unsetToken();
//		setToken(0);
//	}

//	public void create(List<Player> players) {
//		// TODO Auto-generated method stub
//		createGamePlay(players);
//	}

//	public void populatePlayers(List<Player> players) {
//		// TODO Auto-generated method stub
//		this.initElementsGUI(players);
//	}
}
