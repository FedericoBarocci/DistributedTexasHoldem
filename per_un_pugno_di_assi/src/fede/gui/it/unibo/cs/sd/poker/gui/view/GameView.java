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
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.Color;
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

import breads_and_aces.game.model.players.player.Player;

public class GameView {
	public JFrame frame = new JFrame();
	
	private BackgroundGUI background = new BackgroundGUI("elements/bg.jpg", 1300, 480);
	// TODO usare playerskeeper
	private Map<String, PlayerGUI> playersGui = new LinkedHashMap<>();
	
	public List<CardGUI> tableCardsGui = new ArrayList<>();
	public CardGUI card1;
	public CardGUI card2;
	
	private final Map<String, JButton> buttons = new HashMap<>();
	public JLabel lblPlayerName = new JLabel("", SwingConstants.CENTER);
	public JLabel lblScore = new JLabel("", SwingConstants.CENTER);
	public JLabel lblCoins = new JLabel("", SwingConstants.CENTER);
	public JLabel lblBet = new JLabel("", SwingConstants.CENTER);
	public JLabel lblPot = new JLabel("", SwingConstants.CENTER);
	public JLabel lblWinners = new JLabel("");
	
	public GameView() {
		super();
		frame.setLayout(null);
		frame.setTitle("Poker Distributed Hold'em");
		frame.setContentPane(background);
		frame.setBounds(GuiUtils.INSTANCE.getBound("frame"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblTitle = new JLabel("<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>", SwingConstants.CENTER);
		GuiUtils.INSTANCE.initLabel(lblTitle, "title", "gold", "B22");
		frame.getContentPane().add(lblTitle);
	}
	
	private void initTableGui(List<Card> tableCards) {
		// TODO use tableCards from model
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(395 + 150*i, 52);
			tableCardsGui.add(c);
			addElementGui(c);
		}
		
		JPanel panel = new TransparentPanel();
		GuiUtils.INSTANCE.initPanel(panel, "cardPanel", "alphaBlue", "cardBox");
		frame.getContentPane().add(panel);
	}
	
	public void initActionsGui(String clientPlayer, int coins, int score) {
		JPanel leftPanel = new TransparentPanel();
		JPanel bottomPanel = new TransparentPanel();

		GuiUtils.INSTANCE.initLabel(lblPlayerName, "name", "black", "B13", clientPlayer);
		GuiUtils.INSTANCE.initLabel(lblCoins, "coins", "black", "B13", "" + coins);
		GuiUtils.INSTANCE.initLabel(lblScore, "score", "black", "B13", "" + score);
		GuiUtils.INSTANCE.initLabel(lblWinners, "winners", "gold", "B16", "Il vincitore è ...");
		GuiUtils.INSTANCE.initLabel(lblBet, "bet", "black", "B25", "0");
		GuiUtils.INSTANCE.initLabel(lblPot, "pot", "black", "B18", "0/0");
		GuiUtils.INSTANCE.initPanel(leftPanel, "leftPanel", "alphaGreen");
		GuiUtils.INSTANCE.initPanel(bottomPanel, "bottom", "royalRed");
		
		ElementGUI leftBox = new ElementGUI( new ImageIcon("elements" + File.separatorChar + "left.png"), GuiUtils.INSTANCE.getBound("leftBox") );
		ElementGUI up   = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("up.png"), 	GuiUtils.INSTANCE.getBound("up") 	);
		ElementGUI down = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("down.png"),	GuiUtils.INSTANCE.getBound("down") 	);
		ElementGUI ok   = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("ok.png"),	GuiUtils.INSTANCE.getBound("ok") 	);
		ElementGUI fold = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("fold.png"), 	GuiUtils.INSTANCE.getBound("fold") 	);
		ElementGUI info = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("info.png"),	GuiUtils.INSTANCE.getBound("info") 	);
		
		up.setName("UP");
		down.setName("DOWN");

		MouseListener betClick = new betButton(lblBet, lblCoins, coins);
		up.addMouseListener( betClick );
		down.addMouseListener( betClick );
		ok.addMouseListener( new okButton() );
		fold.addMouseListener( new foldButton() );
		info.addMouseListener( new infoButton() );
		
		frame.getContentPane().add(lblPlayerName);
		frame.getContentPane().add(lblCoins);
		frame.getContentPane().add(lblScore);
		frame.getContentPane().add(lblWinners);
		frame.getContentPane().add(lblBet);
		frame.getContentPane().add(lblPot);
		frame.getContentPane().add(up);
		frame.getContentPane().add(down);
		frame.getContentPane().add(ok);
		frame.getContentPane().add(fold);
		frame.getContentPane().add(info);
		frame.getContentPane().add(leftBox);
		frame.getContentPane().add(leftPanel);
		frame.getContentPane().add(bottomPanel);
	}

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
		int x, y;
		int i = playersGui.size();
		
		x = 195 + (140*i);
		y = 250;
				
		PlayerGUI playerGui = new PlayerGUI(p.getName(),  
				p.getCards().get(0), 
				p.getCards().get(1),
				x, y, 1000);
		
		/*test*/ 
		playerGui.setScore(Math.floorDiv(1000, 7) * i);
		
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
