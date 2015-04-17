package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.betButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.foldButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.infoButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.okButton;
import it.unibo.cs.sd.poker.gui.view.elements.CardGUI;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.PlayerGUI;
import it.unibo.cs.sd.poker.gui.view.elements.TransparentPanel;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import breads_and_aces.game.model.players.player.Player;

public class GameView {
	public JFrame frame = new JFrame();
	
	// TODO usare playerskeeper
	private Map<String, PlayerGUI> playersGui = new LinkedHashMap<>();
	
	public List<CardGUI> tableCardsGui = new ArrayList<>();
	public CardGUI card1;
	public CardGUI card2;
	
	public JLabel lblPlayerName = new JLabel("", SwingConstants.CENTER);
	public JLabel lblScore = new JLabel("", SwingConstants.CENTER);
	public JLabel lblCoins = new JLabel("", SwingConstants.CENTER);
	public JLabel lblBet = new JLabel("", SwingConstants.CENTER);
	public JLabel lblPot = new JLabel("", SwingConstants.CENTER);
	public JLabel lblMessage = new JLabel("");
	
	public GameView() {
		super();
		frame.setLayout(null);
		frame.setTitle("Poker Distributed Hold'em");
		frame.setContentPane(GuiUtils.INSTANCE.background);
		frame.setBounds(GuiUtils.INSTANCE.getBound("frame"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		String title = "<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>";
		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		GuiUtils.INSTANCE.initLabel(lblTitle, "title", "gold", "B22");
		frame.getContentPane().add(lblTitle);
	}
	
	public void initTableCards(List<Card> tableCards) {
		tableCardsGui.forEach(c->frame.getLayeredPane().remove(c));
		
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(GuiUtils.tableCardX + GuiUtils.tableCardSpan * i, GuiUtils.tableCardY);
			tableCardsGui.add(c);
			frame.getLayeredPane().add(c);
		}
		
		JPanel panel = new TransparentPanel();
		GuiUtils.INSTANCE.initPanel(panel, "cardPanel", "alphaBlue", "cardBox");
		frame.getContentPane().add(panel);
	}
	
	public void initPlayers(List<Player> players, String myname, int goal, int score) {
		int size = players.size();
		int span = Math.floorDiv(GuiUtils.playerSpan, size+1);
		
		for (int i = 0; i < size; i++) {
			int x = GuiUtils.playerX + (span * (i+1));
			int y = GuiUtils.playerY;
			Player p = players.get(i);
			
			/*XXX*/	/* *** only for testing *** override param *** */ 
				score = Math.floorDiv(goal, 7) * i;
			
			Boolean hideCards = (myname != p.getName());	
			
			PlayerGUI playerGui = new PlayerGUI(p.getName(), p.getCards().get(0), p.getCards().get(1), x, y, goal, score, hideCards);
			playerGui.draw(frame);
			playersGui.put(p.getName(), playerGui);
		}
		
		frame.getContentPane().repaint();
	}
	
	public void initActionsGui(String clientPlayer, int coins, int score) {
		JPanel leftPanel = new TransparentPanel();
		JPanel bottomPanel = new TransparentPanel();

		GuiUtils.INSTANCE.initLabel(lblPlayerName, "name", "black", "B13", clientPlayer);
		GuiUtils.INSTANCE.initLabel(lblCoins, "coins", "black", "B13", "" + coins);
		GuiUtils.INSTANCE.initLabel(lblScore, "score", "black", "B13", "" + score);
		GuiUtils.INSTANCE.initLabel(lblMessage, "message", "gold", "B16", "Let's start the game!");
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
		frame.getContentPane().add(lblMessage);
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

	public void showPlayersCards() {
		Collection<PlayerGUI> collection = playersGui.values();
		collection.forEach(p->p.showCards(frame));
	}
	
	public void setViewToken(String playerName) {
		Collection<PlayerGUI> collection = playersGui.values();
		collection.forEach(p->p.unsetTokenView(frame));
		playersGui.get(playerName).setTokenView(frame);
	}
}
