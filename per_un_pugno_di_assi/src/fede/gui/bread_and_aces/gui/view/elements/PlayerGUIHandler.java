package bread_and_aces.gui.view.elements;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.gui.view.AbstractViewHandler;
import bread_and_aces.gui.view.elements.frame.JFrameGame;
import bread_and_aces.gui.view.elements.utils.CardsUtils;
import bread_and_aces.gui.view.elements.utils.EnumColor;
import bread_and_aces.gui.view.elements.utils.EnumFont;
import bread_and_aces.gui.view.elements.utils.EnumLine;
import bread_and_aces.gui.view.elements.utils.EnumRectangle;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

public class PlayerGUIHandler extends AbstractViewHandler<Void> {

	private final Player player;
	private final Integer x;
	private final Integer y;
	private final Integer goal;
	
	private final Boolean showCards;
	
	private CardGUI cardGui1;
	private CardGUI cardGui2;
	
	private final JLabel name = new JLabel("", SwingConstants.CENTER);
	private final JLabel action = new JLabel("", SwingConstants.CENTER);
	private final JLabel score = new JLabel("", SwingConstants.CENTER);
	private final JPanel box = new TransparentPanelGUI();
	private final JPanel scoreContainer = new TransparentPanelGUI();
	private final JPanel scoreLevel = new TransparentPanelGUI();

	@AssistedInject
	public PlayerGUIHandler(JFrameGame/*Provider */jFrameGame/*Provider*/, 
			@Assisted Player player, @Assisted(value="x") Integer x, @Assisted(value="y") Integer y, @Assisted(value="goal") Integer goal, @Assisted Boolean showCards) {
		super(jFrameGame/*Provider*/);
		
		this.player = player;
		this.x = x;
		this.y = y;
		this.goal = goal;
		this.showCards = showCards;
	
//		init(null);
	}
	
	@Override
	public void init(Void noArg) {
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

		action.setText(Action.NONE.toString());
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
		super.removeElement( cardGui1 );
		super.removeElement( cardGui2 );
		cardGui1 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(player.getFirstCard()), x + CardsUtils.span1, y);
		cardGui2 = new CardGUI(CardsUtils.INSTANCE_SMALL.getImageCard(player.getSecondCard()), x + CardsUtils.span2, y);
		super.addElement( cardGui1 );
		super.addElement( cardGui2 );
	}
	
	public void unsetTokenView() {
		super.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.glass, EnumLine.playerBox, x + 10, y + 25);
		super.addElement( box );
	}
	
	public void setTokenView() {
		super.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaGreen, EnumLine.playerToken, x + 10, y + 25);
		super.addElement( box );
	}
	
	public void setWinner(int score) {
		setScore(score);
		
		super.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaGold, EnumLine.winner, x + 10, y + 25);
		super.addElement( box );
		
		this.action.setText("WIN");
	}
	
	public void setLoser(int score) {
		setScore(score);
		
		super.removeElement( box );
		GuiUtils.INSTANCE.initPanel(box, EnumRectangle.playerBox, EnumColor.alphaBlue, EnumLine.loser, x + 10, y + 25);
		super.addElement( box );
		
		this.action.setText("LOSE");
	}
	
	public void draw() {
		super.addElement( cardGui1 );
		super.addElement( cardGui2 );
		super.addElement( name );
		super.addElement( action );
		super.addElement( score );
		super.addElement( box );
		super.addElement( scoreContainer );
		super.addElement( scoreLevel );
	}
	
	public void clearFromGui() {
		super.removeElement( cardGui1 );
		super.removeElement( cardGui2 );
		super.removeElement( name );
		super.removeElement( action );
		super.removeElement( score );
		super.removeElement( box );
		super.removeElement( scoreContainer );
		super.removeElement( scoreLevel );
	}
	
	public String getId() {
		return player.getName(); 
	}

	public void setAction(Action actionValue) {
		this.action.setText(actionValue.toString());
	}
}
