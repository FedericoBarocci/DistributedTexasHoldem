package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.betButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.foldButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.infoButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.okButton;
import it.unibo.cs.sd.poker.gui.view.elements.BackgroundGUI;
import it.unibo.cs.sd.poker.gui.view.elements.CardGUI;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.PlayerGUI;
import it.unibo.cs.sd.poker.gui.view.elements.TransparentPanel;
import it.unibo.cs.sd.poker.gui.view.elements.utils.ButtonsUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import breads_and_aces.game.model.players.player.Player;

public class GameView {
	public JFrame frame = new JFrame();
	
	private BackgroundGUI background = new BackgroundGUI("elements/bg.jpg", 1300, 480);
	// TODO usare playerskeeper
	private Map<String, PlayerGUI> playersGui = new LinkedHashMap<>();
	
	public JLabel lblWinners = new JLabel("");	//, SwingConstants.CENTER);
	public List<CardGUI> tableCardsGui = new ArrayList<>();
	public CardGUI card1;
	public CardGUI card2;
	
	private final Map<String, JButton> buttons = new HashMap<>();
	public JLabel lblClientPlayerName = new JLabel("", SwingConstants.CENTER);
	public JLabel lblClientPlayerScore = new JLabel("", SwingConstants.CENTER);
	public JLabel lblClientPlayerCoins = new JLabel("", SwingConstants.CENTER);
	public JLabel lblBet = new JLabel("", SwingConstants.CENTER);
	public JLabel lblPot = new JLabel("", SwingConstants.CENTER);
	
	public GameView() {
		super();
		frame.setLayout(null);
		frame.setTitle("Poker Distributed Hold'em");
		frame.setContentPane(background);
		frame.setBounds(0, 0, 1300, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblTitle = new JLabel("<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>", SwingConstants.CENTER);
		lblTitle.setForeground(new Color(255, 230, 0));
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblTitle.setBounds(10, 10, 160, 100);
		frame.getContentPane().add(lblTitle);
	}
	
	/*public void init(List<Player> players) {
		initElementsGUI(players);
		initTableGui();
		initActionsGui("Player Name", 200, 0);
	}*/
	
	private void initTableGui(List<Card> tableCards) {
		// TODO use tableCards from model
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(395 + 150*i, 52);
			tableCardsGui.add(c);
			addElementGui(c);
		}
		
		JPanel panel = new TransparentPanel();
		panel.setBackground(new Color(174, 234, 255, 30)); //alpha 70
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 1));
		panel.setBounds(370, 30, 740, 185);
		frame.getContentPane().add(panel);
	}
	
	public void initActionsGui(String clientPlayer, int coins, int score) {
		lblClientPlayerName.setBounds(0, 134, 180, 50);
		lblClientPlayerName.setForeground(new Color(0, 0, 0));
		lblClientPlayerName.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblClientPlayerName.setText(clientPlayer);
		frame.getContentPane().add(lblClientPlayerName);
		
		lblClientPlayerCoins.setBounds(20, 185, 55, 50);
		lblClientPlayerCoins.setForeground(new Color(0, 0, 0));
		lblClientPlayerCoins.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblClientPlayerCoins.setText("" + coins);
		frame.getContentPane().add(lblClientPlayerCoins);
		
		lblClientPlayerScore.setBounds(105, 185, 55, 50);
		lblClientPlayerScore.setForeground(new Color(0, 0, 0));
		lblClientPlayerScore.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblClientPlayerScore.setText("" + score);
		frame.getContentPane().add(lblClientPlayerScore);
		
		lblWinners.setForeground(new Color(255, 230, 0));
		lblWinners.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblWinners.setBounds(20, 430, 500, 50);
		lblWinners.setText("Il vincitore è ...");
		frame.getContentPane().add(lblWinners);
		
		lblBet.setBounds(10, 330, 60, 50);
		lblBet.setForeground(new Color(0, 0, 0));
		lblBet.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblBet.setText("0");
		frame.getContentPane().add(lblBet);
		
		lblPot.setBounds(60, 240, 60, 50);
		lblPot.setForeground(new Color(0, 0, 0));
		lblPot.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblPot.setText("0/0");
		frame.getContentPane().add(lblPot);
		
		ElementGUI leftUI = new ElementGUI(new ImageIcon("elements" + File.separatorChar + "left.png"), 0, 0, 180, 670);
		frame.getContentPane().add(leftUI);
		
		ElementGUI up = new ElementGUI(ButtonsUtils.INSTANCE.getImageGui("up.png"), 15, 297, 50, 50);
		ElementGUI down = new ElementGUI(ButtonsUtils.INSTANCE.getImageGui("down.png"), 15, 363, 50, 50);
		ElementGUI ok = new ElementGUI(ButtonsUtils.INSTANCE.getImageGui("ok.png"), 100, 300, 50, 50);
		ElementGUI fold = new ElementGUI(ButtonsUtils.INSTANCE.getImageGui("fold.png"), 100, 365, 50, 50);
		ElementGUI info = new ElementGUI(ButtonsUtils.INSTANCE.getImageGui("info.png"), 1250, 15, 35, 35);
		up.setName("UP");
		down.setName("DOWN");
		frame.getContentPane().add(up);
		frame.getContentPane().add(down);
		frame.getContentPane().add(ok);
		frame.getContentPane().add(fold);
		frame.getContentPane().add(info);
		
		MouseListener betClick = new betButton(lblBet, lblClientPlayerCoins, coins);
		up.addMouseListener( betClick );
		down.addMouseListener( betClick );
		
		ok.addMouseListener(new okButton());
		fold.addMouseListener(new foldButton());
		info.addMouseListener(new infoButton());
		
//		for (String	s: new String[]{"Check", "Fold"/*, "Bet"*/}) {
//			buttons.put(s, new JButton(s));
//		}
		
//		JButton checkButton = buttons.get("Check");
//		JButton foldButton = buttons.get("Fold");
//		JButton betButton = buttons.get("Bet");
		
//		checkButton.setBounds(90, 310, 80, 40);
//		foldButton.setBounds(90, 360, 80, 40);
//		betButton.setBounds(40, 460, 100, 40);
		
//		frame.getContentPane().add(checkButton);
//		frame.getContentPane().add(foldButton);
//		frame.getContentPane().add(betButton);
		
//		checkButton.addActionListener( new Check(/*model, view*/) );
//		foldButton.addActionListener( new Fold(/*model, view*/) );
//		betButton.addActionListener( new Bet(/*model, view*/) );
		
		JPanel leftPanel = new TransparentPanel();
		leftPanel.setBackground(new Color(65, 146, 75, 200)); //0-104-139
//		leftPanel.setBackground(new Color(188, 221, 17, 255));
		//leftPanel.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		leftPanel.setBounds(0, 0, 180, 430);
		frame.getContentPane().add(leftPanel);
		
		JPanel bottomPanel = new TransparentPanel();
		bottomPanel.setBackground(new Color(176, 23, 31, 255));
		//bottomPanel.setBorder(new LineBorder(new Color(65, 146, 75), 1));
		bottomPanel.setBounds(0, 430, 1300, 50);
		frame.getContentPane().add(bottomPanel);
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
	
	public void initPlayers(List<Player> players) {
		for (Player p : players) {
			createAndAddPlayerGui(p);
		}
	}
	public void initTableCards(List<Card> tableCards) {
		for (CardGUI c : tableCardsGui) {
			removeElementGui(c);
		}		
		initTableGui(tableCards);
	}
	
	// old
	/*private void initElementsGUI(List<Player> players) {
		for (Player p : players) {
			createAndAddPlayerGui(p);
		}
		
		for (CardGUI c : tableCardsGui) {
			removeElementGui(c);
		}		
		initTableGui();
		
		// TODO restore?
//		this.resetToken();
	}*/
	
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
		/*
		int i = playersGui.size();
		double t;
		
		if (i >= 4)
			t = ((i-4) * Math.PI * 2 / 9) + Math.PI;
		else
			t = (i * Math.PI * 2 / 9);
		
		t = t + Math.PI/6;
		
		int a = 480;
		int b = 250;
		int h = 670;
		int k = 255;
				
		int x = (int) (a*b/Math.sqrt(b*b+a*a*Math.tan(t) * Math.tan(t)));		
		int y = (int) (x*Math.tan(t));
		
		if (t > Math.PI * 3/2 || t < Math.PI/2) {
			x = x * (-1);
			y = y * (-1);
		}
		
		x = x + h;
		y = y + k;
		*/
		
		int x, y;
		int i = playersGui.size();
		
		/*if (i < 4) {
			x = 360 + (203*i);
			y = 250;
		}
		else {
			x = 360 + (203 * (i - 4));
			y = 450;
		}*/
		
		x = 195 + (140*i);
		y = 250;
				
		PlayerGUI playerGui = new PlayerGUI(p.getName(),  
				p.getCards().get(0), 
				p.getCards().get(1),
				x,y,1000);
				//(135 * playersGui.size()) + 195, 
				//230);
		
		/*test*/ playerGui.setScore(Math.floorDiv(1000, 7) * i);
		
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
