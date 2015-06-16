package bread_and_aces.gui.view;


import java.io.File;

import javax.inject.Inject;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

import bread_and_aces.gui.labels.LabelBet;
import bread_and_aces.gui.labels.LabelCoins;
import bread_and_aces.gui.view.ViewInitalizer.ViewInitializerInitArgs;
import bread_and_aces.gui.view.elements.ImageGUI;
import bread_and_aces.gui.view.elements.TransparentPanelGUI;
import bread_and_aces.gui.view.elements.frame.JFrameGame;
import bread_and_aces.gui.view.elements.utils.EnumColor;
import bread_and_aces.gui.view.elements.utils.EnumFont;
import bread_and_aces.gui.view.elements.utils.EnumLine;
import bread_and_aces.gui.view.elements.utils.EnumRectangle;
import bread_and_aces.gui.view.elements.utils.GuiUtils;
import breads_and_aces.gui.labels.LabelPot;

@LazySingleton
//@Singleton
public class ViewInitalizer extends AbstractViewHandler<ViewInitializerInitArgs> {

	private final LabelBet lblBet;
	private final LabelCoins lblCoins;
	private final JLabel lblPot;
	
	private JLabel lblPlayerName = new JLabel("", SwingConstants.CENTER);
	private JLabel lblScore = new JLabel("", SwingConstants.CENTER);
	private JLabel lblMessage = new JLabel("");
	
	@Inject
	public ViewInitalizer(JFrameGame/*Provider*/ jFrameGame/*Provider*/, LabelBet lblBet, LabelCoins lblCoins, LabelPot lblPot) {
		super(jFrameGame/*Provider*/);
		this.lblBet = lblBet;
		this.lblCoins = lblCoins;
		this.lblPot = lblPot;
	}
	
	public void init(ViewInitializerInitArgs initArgs) {
		init(initArgs.clientPlayer, initArgs.coins, initArgs.goal);
	}

	private void init(String clientPlayer, Integer coins, Integer goal) {
		String title = "<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>";

		ImageGUI leftBox = new ImageGUI(new ImageIcon("elements" + File.separatorChar + "left.png"), GuiUtils.INSTANCE.getRectangle(EnumRectangle.leftBG));
		
		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		JLabel lblGoal = new JLabel("", SwingConstants.CENTER);
		JPanel tablePanel = new TransparentPanelGUI();
		JPanel leftPanel = new TransparentPanelGUI();
		JPanel bottomPanel = new TransparentPanelGUI();

		GuiUtils.INSTANCE.initLabel(lblTitle, EnumRectangle.title, EnumColor.gold, EnumFont.B22);
		GuiUtils.INSTANCE.initPanel(tablePanel, EnumRectangle.cardPanel, EnumColor.alphaBlue, EnumLine.cardBox);
		GuiUtils.INSTANCE.initLabel(lblPlayerName, EnumRectangle.name, EnumColor.gold, EnumFont.B16, clientPlayer);
		GuiUtils.INSTANCE.initLabel(lblCoins, EnumRectangle.coins, EnumColor.black, EnumFont.B13, "" + coins);
		GuiUtils.INSTANCE.initLabel(lblScore, EnumRectangle.score, EnumColor.black, EnumFont.B13, "" + coins);
		GuiUtils.INSTANCE.initLabel(lblGoal, EnumRectangle.goal, EnumColor.black, EnumFont.B13, "" + goal);
		GuiUtils.INSTANCE.initLabel(lblMessage, EnumRectangle.message, EnumColor.gold, EnumFont.B16, "");
		GuiUtils.INSTANCE.initLabel(lblBet, EnumRectangle.bet, EnumColor.black, EnumFont.B25, "0");
		GuiUtils.INSTANCE.initLabel(lblPot, EnumRectangle.pot, EnumColor.black, EnumFont.B18, "0");
		GuiUtils.INSTANCE.initPanel(leftPanel, EnumRectangle.leftPanel, EnumColor.alphaGreen);
		GuiUtils.INSTANCE.initPanel(bottomPanel, EnumRectangle.bottom, EnumColor.royalRed);
		
		
		super.addElement(lblTitle);
		super.addElement(tablePanel);
		super.addElement(lblPlayerName);
		super.addElement(lblCoins);
		super.addElement(lblScore);
		super.addElement(lblGoal);
		super.addElement(lblMessage);
		super.addElement(lblBet);
		super.addElement(lblPot);
		
		super.addElement(leftBox);
		
		super.addElement(leftPanel);
		super.addElement(bottomPanel);
		
		repaint();
		show();
	}
	
	public void printMessage(String msg) {
		lblMessage.setText(msg);
	}
	
	public static class ViewInitializerInitArgs {
		String clientPlayer; 
		Integer coins;
		Integer goal;
		
		public ViewInitializerInitArgs(String clientPlayer, Integer coins, Integer goal) {
			this.clientPlayer = clientPlayer;
			this.coins = coins;
			this.goal = goal;
		}
	}
}
