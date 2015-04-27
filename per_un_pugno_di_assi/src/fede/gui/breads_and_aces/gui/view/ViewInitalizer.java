package breads_and_aces.gui.view;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import breads_and_aces.gui.labels.LabelBet;
import breads_and_aces.gui.labels.LabelCoins;
import breads_and_aces.gui.view.elements.ImageGUI;
import breads_and_aces.gui.view.elements.TransparentPanelGUI;
import breads_and_aces.gui.view.elements.frame.JFrameGameProvider;
import breads_and_aces.gui.view.elements.utils.EnumColor;
import breads_and_aces.gui.view.elements.utils.EnumFont;
import breads_and_aces.gui.view.elements.utils.EnumLine;
import breads_and_aces.gui.view.elements.utils.EnumRectangle;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

@Singleton
public class ViewInitalizer extends GameViewHandler {

	private final LabelBet lblBet;
	private final LabelCoins lblCoins;
	
	private JLabel lblPlayerName = new JLabel("", SwingConstants.CENTER);
	private JLabel lblScore = new JLabel("", SwingConstants.CENTER);
	private JLabel lblPot = new JLabel("", SwingConstants.CENTER);
	private JLabel lblMessage = new JLabel("");
	
	@Inject
	public ViewInitalizer(JFrameGameProvider jFrameGameProvider, LabelBet lblBet, LabelCoins lblCoins) {
		super(jFrameGameProvider);
		this.lblBet = lblBet;
		this.lblCoins = lblCoins;
	}

	public void init(String clientPlayer, Integer coins) {
		String title = "<html><p style='text-align:center; border-bottom:1px solid #000;padding-bottom:10px;'>Poker Distributed Hold'em</p></html>";

		ImageGUI leftBox = new ImageGUI(new ImageIcon("elements" + File.separatorChar + "left.png"), GuiUtils.INSTANCE.getRectangle(EnumRectangle.leftBox));
		
		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		JPanel tablePanel = new TransparentPanelGUI();
		JPanel leftPanel = new TransparentPanelGUI();
		JPanel bottomPanel = new TransparentPanelGUI();

		GuiUtils.INSTANCE.initLabel(lblTitle, EnumRectangle.title, EnumColor.gold, EnumFont.B22);
		GuiUtils.INSTANCE.initPanel(tablePanel, EnumRectangle.cardPanel, EnumColor.alphaBlue, EnumLine.cardBox);
		GuiUtils.INSTANCE.initLabel(lblPlayerName, EnumRectangle.name, EnumColor.black, EnumFont.B13, clientPlayer);
		GuiUtils.INSTANCE.initLabel(lblCoins, EnumRectangle.coins, EnumColor.black, EnumFont.B13, "" + coins);
		GuiUtils.INSTANCE.initLabel(lblScore, EnumRectangle.score, EnumColor.black, EnumFont.B13, "" + coins);
		GuiUtils.INSTANCE.initLabel(lblMessage, EnumRectangle.message, EnumColor.gold, EnumFont.B16, "Let's start the game!");
		GuiUtils.INSTANCE.initLabel(lblBet, EnumRectangle.bet, EnumColor.black, EnumFont.B25, "0");
		GuiUtils.INSTANCE.initLabel(lblPot, EnumRectangle.pot, EnumColor.black, EnumFont.B18, "0/0");
		GuiUtils.INSTANCE.initPanel(leftPanel, EnumRectangle.leftPanel, EnumColor.alphaGreen);
		GuiUtils.INSTANCE.initPanel(bottomPanel, EnumRectangle.bottom, EnumColor.royalRed);
		
		
		super.addElement(lblTitle);
		super.addElement(tablePanel);
		super.addElement(lblPlayerName);
		super.addElement(lblCoins);
		super.addElement(lblScore);
		super.addElement(lblMessage);
		super.addElement(lblBet);
		super.addElement(lblPot);
		
		super.addElement(leftBox);
		
		super.addElement(leftPanel);
		super.addElement(bottomPanel);
		
		repaint();
		show();
	}
}
