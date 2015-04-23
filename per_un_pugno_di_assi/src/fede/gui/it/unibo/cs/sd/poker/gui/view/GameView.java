package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.BetButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.FoldButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.InfoButton;
import it.unibo.cs.sd.poker.gui.controllers.actionlisteners.OkButton;
import it.unibo.cs.sd.poker.gui.view.elements.BackgroundGUI;
import it.unibo.cs.sd.poker.gui.view.elements.CardGUI;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.JFrameDefault;
import it.unibo.cs.sd.poker.gui.view.elements.PlayerGUI;
import it.unibo.cs.sd.poker.gui.view.elements.TransparentPanel;
import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumButton;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumColor;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumFont;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumLine;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumRectangle;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import breads_and_aces.game.model.players.player.Player;

@Singleton
public class GameView {
	private JFrameDefault frame = new JFrameDefault("Poker Distributed Hold'em");
	
	private Map<String, PlayerGUI> playersGui = new LinkedHashMap<>();
	private List<CardGUI> tableCardsGui = new ArrayList<>();
	private int displayedCards;
	
	private JLabel lblPlayerName = new JLabel("", SwingConstants.CENTER);
	private JLabel lblScore = new JLabel("", SwingConstants.CENTER);
	private JLabel lblCoins = new JLabel("", SwingConstants.CENTER);
	private JLabel lblBet = new JLabel("", SwingConstants.CENTER);
	private JLabel lblPot = new JLabel("", SwingConstants.CENTER);
	private JLabel lblMessage = new JLabel("");

	private final OkButton okButton;
	private final FoldButton foldButton;

	@Inject
	public GameView(OkButton okButton, FoldButton foldButton) {
		this.okButton = okButton;
		this.foldButton = foldButton;
		
		BackgroundGUI bg = GuiUtils.INSTANCE.background;
		bg.setLayout(null);
		bg.setPreferredSize(GuiUtils.INSTANCE.getRectangle(EnumRectangle.frame).getSize());
		bg.setBounds(GuiUtils.INSTANCE.getRectangle(EnumRectangle.frame));
		
		frame.setContentPane(bg);
		frame.pack();
		
		String title = "<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>";
		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		GuiUtils.INSTANCE.initLabel(lblTitle, EnumRectangle.title, EnumColor.gold, EnumFont.B22);
		frame.getContentPane().add(lblTitle);
		
		JPanel panel = new TransparentPanel();
		GuiUtils.INSTANCE.initPanel(panel, EnumRectangle.cardPanel, EnumColor.alphaBlue, EnumLine.cardBox);
		frame.getContentPane().add(panel);
		
		initTableCards();
	}
	
	public void start() {
		frame.setVisible(true);
	}
	
	private void initTableCards() {
		tableCardsGui.forEach(c->frame.getLayeredPane().remove(c));
		tableCardsGui.clear();
		
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(GuiUtils.tableCardX + GuiUtils.tableCardSpan * i, GuiUtils.tableCardY);
			tableCardsGui.add(c);
			frame.getLayeredPane().add(c);
		}
		
		this.displayedCards = 0;
	}
	
	public void initPlayers(List<Player> players, String myName, int goal) {
		int size = players.size();
		int span = Math.floorDiv(GuiUtils.playerSpan, size+1);
		
		playersGui.values().forEach(p->{
			p.clearFromGui(frame);
		});
		
		playersGui.clear();
		
		for (int i = 0; i < size; i++) {
			int x = GuiUtils.playerX + (span * (i+1));
			int y = GuiUtils.playerY;
			Player p = players.get(i);
			
			/*XXX*/	/* *** only for testing *** override param *** */ 
				p.setScore(Math.floorDiv(goal, 7) * i);
			
			Boolean showCards = p.getName().equals(myName);	
			
			PlayerGUI playerGui = new PlayerGUI(p.getName(), p.getCards().get(0), p.getCards().get(1), x, y, goal, p.getScore(), showCards);
			
			if (p.hasToken()) playerGui.setTokenView(frame);
			
			playerGui.draw(frame);
			playersGui.put(p.getName(), playerGui);
		}
		
		frame.getContentPane().repaint();
	}
	
	public void initActionsGui(String clientPlayer, int coins, int score) {
		JPanel leftPanel = new TransparentPanel();
		JPanel bottomPanel = new TransparentPanel();

		GuiUtils.INSTANCE.initLabel(lblPlayerName, EnumRectangle.name, EnumColor.black, EnumFont.B13, clientPlayer);
		GuiUtils.INSTANCE.initLabel(lblCoins, EnumRectangle.coins, EnumColor.black, EnumFont.B13, "" + coins);
		GuiUtils.INSTANCE.initLabel(lblScore, EnumRectangle.score, EnumColor.black, EnumFont.B13, "" + score);
		GuiUtils.INSTANCE.initLabel(lblMessage, EnumRectangle.message, EnumColor.gold, EnumFont.B16, "Let's start the game!");
		GuiUtils.INSTANCE.initLabel(lblBet, EnumRectangle.bet, EnumColor.black, EnumFont.B25, "0");
		GuiUtils.INSTANCE.initLabel(lblPot, EnumRectangle.pot, EnumColor.black, EnumFont.B18, "0/0");
		GuiUtils.INSTANCE.initPanel(leftPanel, EnumRectangle.leftPanel, EnumColor.alphaGreen);
		GuiUtils.INSTANCE.initPanel(bottomPanel, EnumRectangle.bottom, EnumColor.royalRed);
		
		ElementGUI leftBox = new ElementGUI( new ImageIcon("elements" + File.separatorChar + "left.png"), GuiUtils.INSTANCE.getRectangle(EnumRectangle.leftBox) );
		ElementGUI up   = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("up.png"), 	GuiUtils.INSTANCE.getRectangle(EnumRectangle.up) 	);
		ElementGUI down = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("down.png"),	GuiUtils.INSTANCE.getRectangle(EnumRectangle.down) 	);
		ElementGUI ok   = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("ok.png"),	GuiUtils.INSTANCE.getRectangle(EnumRectangle.ok) 	);
		ElementGUI fold = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("fold.png"), 	GuiUtils.INSTANCE.getRectangle(EnumRectangle.fold) 	);
		ElementGUI info = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("info.png"),	GuiUtils.INSTANCE.getRectangle(EnumRectangle.info) 	);
		
		up.setName(EnumButton.UP.name());
		down.setName(EnumButton.DOWN.name());
		
//		okButton.setup(clientPlayer);
//		foldButton.setup(clientPlayer);

		MouseListener betClick = new BetButton(lblBet, lblCoins, coins);
		up.addMouseListener( betClick );
		down.addMouseListener( betClick );
		ok.addMouseListener( okButton );
		fold.addMouseListener( foldButton );
		info.addMouseListener( new InfoButton() );
		
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
		playersGui.values().forEach(p->p.showCards(frame));
	}
	
	public void setViewToken(String playerName) {
		playersGui.values().forEach(p->{
			p.unsetTokenView(frame);
			if (p.getId().equals(playerName)) p.setTokenView(frame);
		});
		
		frame.repaint();
	}
	
	public void addTableCards(List<Card> cards) {
		//Show down
		if(cards.size() == 5) 
			displayedCards = 0;
		
		for (Card c : cards) {
			tableCardsGui.get(displayedCards).changeImage(CardsUtils.INSTANCE_BIG.getImageCard(c));
			displayedCards++;
		}
		
		frame.repaint();
	}
	
	public void showWinners(List<Player> players) {
		for(PlayerGUI pg : playersGui.values()) {
			boolean loser = true;
			for(Player p : players) {
				if (pg.getId().equals(p.getName())) {
					pg.setWinner(frame);
					loser = false;
					break;
				}
			}
			if (loser) {
				pg.setLoser(frame);
			}
		}
		
		frame.repaint();
	}
	
	public void refresh(List<Player> players, String myName, int goal) {
		System.out.println("REFRESHING GAME VIEW");
		initTableCards();
		initPlayers(players, myName, goal);
//		initPlayers(game.getPlayers(), nodeId, game.getGoal());
		
		frame.repaint();
	}
}
