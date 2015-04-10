package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.Bet;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.Check;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.Fold;
import it.unibo.cs.sd.poker.gui.view.elements.BackgroundGUI;
import it.unibo.cs.sd.poker.gui.view.elements.CardGUI;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.PlayerGUI;
import it.unibo.cs.sd.poker.gui.view.elements.TransparentPanel;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
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
	
	public JLabel lblWinners = new JLabel("");	//, SwingConstants.CENTER);
	public List<CardGUI> tableCardsGui = new ArrayList<>();
	public CardGUI card1;
	public CardGUI card2;
	
	private final Map<String, JButton> buttons = new HashMap<>();
	public JLabel lblClientPlayerName = new JLabel("", SwingConstants.CENTER);
	public JLabel lblClientPlayerScore = new JLabel("");
	public JLabel lblClientPlayerPot = new JLabel("");
	
	public GameView() {
		super();
		frame.setLayout(null);
		frame.setTitle("Poker Distributed Hold'em");
		frame.setContentPane(background);
		frame.setBounds(0, 0, 1280, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblTitle = new JLabel("<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>", SwingConstants.CENTER);
		lblTitle.setForeground(new Color(255, 230, 0));
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblTitle.setBounds(10, 10, 160, 100);
		frame.getContentPane().add(lblTitle);
	}
	
	public void init(List<Player> players) {
		initElementsGUI(players);
		initTableGui();
		initActionsGui("Player Name", 200, 0); ///*, players.get(0).getCards()*/);
	}
	
	private void initTableGui() {		
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(375 + 150*i, 40);
			tableCardsGui.add(c);
			addElementGui(c);
		}
		
		JPanel panel = new TransparentPanel();
		panel.setBackground(new Color(174, 234, 255, 70));
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 1));
		panel.setBounds(350, 15, 740, 185);
		frame.getContentPane().add(panel);
		
		lblWinners.setForeground(new Color(255, 230, 0));
		lblWinners.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblWinners.setBounds(20, 400, 500, 50);
		lblWinners.setText("Il vincitore è ...");
		frame.getContentPane().add(lblWinners);
	}
	
	private void initActionsGui(String clientPlayer, int chips, int score /*, List<Card> cards*/) {
		lblClientPlayerName.setBounds(0, 130, 180, 50);
		lblClientPlayerName.setForeground(new Color(0, 0, 0));
		lblClientPlayerName.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblClientPlayerName.setText(clientPlayer);
		frame.getContentPane().add(lblClientPlayerName);
		
		lblClientPlayerPot.setBounds(10, 330, 180, 50);
		lblClientPlayerPot.setForeground(new Color(0, 0, 0));
		lblClientPlayerPot.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblClientPlayerPot.setText("Fiches: "+ chips);
		frame.getContentPane().add(lblClientPlayerPot);
		
		lblClientPlayerScore.setBounds(10, 350, 180, 50);
		lblClientPlayerScore.setForeground(new Color(0, 0, 0));
		lblClientPlayerScore.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblClientPlayerScore.setText("Punteggio: " + score);
		frame.getContentPane().add(lblClientPlayerScore);
		
//		card1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(cards.get(0)), 24, 50);
//		card2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(cards.get(1)), 102, 50);
//		frame.getContentPane().add(card1);
//		frame.getContentPane().add(card2);
		
		for (String	s: new String[]{"Check", "Fold", "Bet"}) {
			buttons.put(s, new JButton(s));
		}
		
		JButton checkButton = buttons.get("Check");
		JButton foldButton = buttons.get("Fold");
		JButton betButton = buttons.get("Bet");
		
		checkButton.setBounds(40, 180, 100, 40);
		foldButton.setBounds(40, 230, 100, 40);
		betButton.setBounds(40, 280, 100, 40);
		
		frame.getContentPane().add(checkButton);
		frame.getContentPane().add(foldButton);
		frame.getContentPane().add(betButton);
		
		checkButton.addActionListener( new Check(/*model, view*/) );
		foldButton.addActionListener( new Fold(/*model, view*/) );
		betButton.addActionListener( new Bet(/*model, view*/) );
		
		JPanel panel = new TransparentPanel();
		panel.setBackground(new Color(0, 104, 139, 70));
//		panel.setBackground(new Color(188, 221, 17, 255));
		//panel.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		panel.setBounds(0, 0, 180, 400);
		frame.getContentPane().add(panel);
		
		JPanel panel2 = new TransparentPanel();
		panel2.setBackground(new Color(176, 23, 31, 255));
		//panel2.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		panel2.setBounds(0, 400, 1280, 50);
		frame.getContentPane().add(panel2);
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
				(135 * playersGui.size()) + 195, 
				230);
		
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
		buttons.values().stream().forEach(b->frame.getContentPane().remove(b));
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
