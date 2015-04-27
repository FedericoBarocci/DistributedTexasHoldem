package breads_and_aces.gui.view.elements;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.gui.view.GameViewHandler;
import breads_and_aces.gui.view.elements.utils.CardsUtils;
import breads_and_aces.gui.view.elements.utils.EnumColor;
import breads_and_aces.gui.view.elements.utils.EnumFont;
import breads_and_aces.gui.view.elements.utils.EnumLine;
import breads_and_aces.gui.view.elements.utils.EnumRectangle;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class PlayerGUIHandler extends GameViewHandler {

	private final Player player;
	private final Integer x;
	private final Integer y;
	private final Integer goal;
	
	private CardGUI cardGui1;
	private CardGUI cardGui2;
	
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final JLabel action = new JLabel("", SwingConstants.CENTER);
	private final JLabel score = new JLabel("", SwingConstants.CENTER);
	private final JPanel box = new TransparentPanelGUI();
	private final JPanel scoreContainer = new TransparentPanelGUI();
	private final JPanel scoreLevel = new TransparentPanelGUI();
	
	public PlayerGUIHandler(Player player, Integer x, Integer y, Integer goal, Boolean showCards) {
		super();
		
		this.player = player;
		this.x = x;
		this.y = y;
		this.goal = goal;
		
		if (showCards) {
			cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(player.getFirstCard()), x + CardsUtils.span1, y);
			cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(player.getSecondCard()), x + CardsUtils.span2, y);
		}
		else {
			cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getBackCard(), x + CardsUtils.span1, y);
			cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getBackCard(), x + CardsUtils.span2, y);
		}
		
		String playerName = "<html><div style='border-bottom:2px solid black'>"+ player.getName() + "</div></html>";
		
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.glass, EnumLine.playerBox, x + 10, y + 25);
		GuiUtils.INSTANCE.initPanel(scoreContainer, EnumRectangle.playerLevel, EnumColor.glass2, x, y + 25);
		GuiUtils.INSTANCE.initLabel(name, EnumRectangle.playerName, EnumColor.black, EnumFont.B15, playerName , x + 10, y + 90);
		GuiUtils.INSTANCE.initLabel(action, EnumRectangle.playerAction, EnumColor.black, EnumFont.B11, "WAIT", x + 10, y + 120);
		GuiUtils.INSTANCE.initLabel(score, EnumRectangle.playerScore, EnumColor.black, EnumFont.B11, "SCORE: 0", x + 10, y + 135);

		setScore(player.getScore());
		
		if (player.hasToken()) {
			setTokenView();
		}
	}
	
	public void setScore(Integer score) {
		int proportional = Math.floorDiv(135 * score, goal);
		int red = 200 + Math.floorDiv(55 * score, goal);
		int green = Math.floorDiv(230 * score, goal);
		
		this.scoreLevel.setBackground(new Color(red, green, 0));
		this.scoreLevel.setBounds(this.x, this.y + 160 - proportional, 10, proportional);
		this.score.setText("SCORE: " + score);
	}
	
	public void showCards() {
		this.removeElement( cardGui1 );
		this.removeElement( cardGui2 );
		cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(player.getFirstCard()), x + CardsUtils.span1, y);
		cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(player.getSecondCard()), x + CardsUtils.span2, y);
		this.addElement( cardGui1 );
		this.addElement( cardGui2 );
	}
	
	public void unsetTokenView() {
		this.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.glass, EnumLine.playerBox, x + 10, y + 25);
		this.addElement( box );
	}
	
	public void setTokenView() {
		this.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaGreen, EnumLine.playerToken, x + 10, y + 25);
		this.addElement( box );
	}
	
	public void setWinner() {
		this.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaGold, EnumLine.winner, x + 10, y + 25);
		this.addElement( box );
	}
	
	public void setLoser() {
		this.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaBlue, EnumLine.loser, x + 10, y + 25);
		this.addElement( box );
	}
	
	public void draw() {
		this.addElement( cardGui1 );
		this.addElement( cardGui2 );
		this.addElement( name );
		this.addElement( action );
		this.addElement( score );
		this.addElement( box );
		this.addElement( scoreContainer );
		this.addElement( scoreLevel );
	}
	
	public void clearFromGui() {
		this.removeElement( cardGui1 );
		this.removeElement( cardGui2 );
		this.removeElement( name );
		this.removeElement( action );
		this.removeElement( score );
		this.removeElement( box );
		this.removeElement( scoreContainer );
		this.removeElement( scoreLevel );
	}
	
	public String getId() {
		return player.getName(); 
	}
}
