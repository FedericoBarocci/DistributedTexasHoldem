package breads_and_aces.gui.view.elements.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import breads_and_aces.gui.view.elements.BackgroundGUI;

public enum GuiUtils {
	
	INSTANCE;
	
	private final String dir = "elements" + File.separatorChar + "buttons" + File.separatorChar;
	
	private final Map<String,ImageIcon> paths = new HashMap<>();
	
	private final EnumMap<EnumRectangle,Rectangle> rectangles = new EnumMap<EnumRectangle,Rectangle>(EnumRectangle.class);
	private final EnumMap<EnumColor,Color> colors = new EnumMap<EnumColor,Color>(EnumColor.class);
	private final EnumMap<EnumFont,Font> fonts = new EnumMap<EnumFont,Font>(EnumFont.class);
	private final EnumMap<EnumLine,LineBorder> lines = new EnumMap<EnumLine,LineBorder>(EnumLine.class);
	
	public BackgroundGUI background = new BackgroundGUI("elements" + File.separatorChar + "bg.jpg", 1300, 480);
	
	public static final int playerX = 45;
	public static final int playerY = 250;
	public static final int playerSpan = 1260;
	
	public static final int tableCardX = 395;
	public static final int tableCardY = 52;
	public static final int tableCardSpan = 150;
	
	private GuiUtils() {
		rectangles.put(EnumRectangle.frame, 	new Rectangle(0, 0, 1300, 480));
		rectangles.put(EnumRectangle.about, 	new Rectangle(0, 0, 800, 600));
		rectangles.put(EnumRectangle.title, 	new Rectangle(10, 10, 160, 100));
		rectangles.put(EnumRectangle.cardPanel, new Rectangle(370, 30, 740, 185));
		rectangles.put(EnumRectangle.name, 		new Rectangle(0, 134, 180, 50));
		rectangles.put(EnumRectangle.coins, 	new Rectangle(20, 185, 55, 50));
		rectangles.put(EnumRectangle.score, 	new Rectangle(105, 185, 55, 50));
		rectangles.put(EnumRectangle.message, 	new Rectangle(20, 430, 500, 50));
		rectangles.put(EnumRectangle.bet, 		new Rectangle(10, 330, 60, 50));
		rectangles.put(EnumRectangle.pot, 		new Rectangle(60, 240, 60, 50));
		rectangles.put(EnumRectangle.leftBox, 	new Rectangle(0, 0, 180, 670));
		rectangles.put(EnumRectangle.up, 		new Rectangle(15, 297, 50, 50));
		rectangles.put(EnumRectangle.down, 		new Rectangle(15, 363, 50, 50));
		rectangles.put(EnumRectangle.ok, 		new Rectangle(100, 300, 50, 50));
		rectangles.put(EnumRectangle.fold, 		new Rectangle(100, 365, 50, 50));
		rectangles.put(EnumRectangle.info, 		new Rectangle(1250, 15, 35, 35));
		rectangles.put(EnumRectangle.leftPanel, new Rectangle(0, 0, 180, 430));
		rectangles.put(EnumRectangle.bottom, 	new Rectangle(0, 430, 1300, 50));
		
		rectangles.put(EnumRectangle.playerName, 	new Rectangle(115, 25));
		rectangles.put(EnumRectangle.playerAction, 	new Rectangle(115, 25));
		rectangles.put(EnumRectangle.playerScore, 	new Rectangle(115, 25));
		rectangles.put(EnumRectangle.playerBox, 	new Rectangle(120, 135));
		rectangles.put(EnumRectangle.playerLevel, 	new Rectangle(10, 135));
		
		colors.put(EnumColor.black, 		new Color(0, 0, 0));
		colors.put(EnumColor.gold, 			new Color(255, 230, 0));
		colors.put(EnumColor.white, 		new Color(255, 255, 255));
		colors.put(EnumColor.royalRed, 		new Color(176, 23, 31));
		colors.put(EnumColor.blue,		 	new Color(0, 51, 102));
		colors.put(EnumColor.alphaBlue, 	new Color(174, 234, 255, 50));
		colors.put(EnumColor.alphaGreen, 	new Color(65, 146, 75, 200));
		colors.put(EnumColor.alphaGold, 	new Color(255, 230, 0, 160));
		colors.put(EnumColor.glass, 		new Color(255, 255, 255, 60));
		colors.put(EnumColor.glass2, 		new Color(255, 255, 255, 80));
		
		fonts.put(EnumFont.B11, new Font("SansSerif", Font.BOLD, 11));
		fonts.put(EnumFont.B13, new Font("SansSerif", Font.BOLD, 13));
		fonts.put(EnumFont.B15, new Font("SansSerif", Font.BOLD, 15));
		fonts.put(EnumFont.B16, new Font("SansSerif", Font.BOLD, 16));
		fonts.put(EnumFont.B18, new Font("SansSerif", Font.BOLD, 18));
		fonts.put(EnumFont.B22, new Font("SansSerif", Font.BOLD, 22));
		fonts.put(EnumFont.B25, new Font("SansSerif", Font.BOLD, 25));
		
		lines.put(EnumLine.cardBox, 	new LineBorder(getColor(EnumColor.white), 1));
		lines.put(EnumLine.loser, 		new LineBorder(getColor(EnumColor.blue), 3));
		lines.put(EnumLine.playerBox, 	new LineBorder(getColor(EnumColor.gold), 1));
		lines.put(EnumLine.winner, 		new LineBorder(getColor(EnumColor.gold), 3));
		lines.put(EnumLine.playerToken, new LineBorder(getColor(EnumColor.royalRed), 3));
	}
	
	public ImageIcon getImageGui(String element) {
		ImageIcon icon = paths.get(element);
		
		if (icon == null) {
			icon = new ImageIcon(dir + element);
			paths.put(element, icon );
		}
		
		return icon;
	}
	
	public JLabel initLabel(JLabel lbl, EnumRectangle bound, EnumColor color, EnumFont font) {
		lbl.setBounds(getRectangle(bound));
		lbl.setForeground(getColor(color));
		lbl.setFont(getFont(font));
		
		return lbl;
	}
	
	public JLabel initLabel(JLabel lbl, EnumRectangle bound, EnumColor color, EnumFont font, String text) {
		lbl = initLabel(lbl, bound, color, font);
		
		lbl.setText(text);
		
		return lbl;
	}
	
	public JLabel initLabel(JLabel lbl, EnumRectangle bound, EnumColor color, EnumFont font, String text, int dx, int dy) {
		Rectangle r = new Rectangle(getRectangle(bound));
		r.translate(dx, dy);
		
		lbl.setBounds(r);
		lbl.setForeground(getColor(color));
		lbl.setFont(getFont(font));
		lbl.setText(text);
		
		return lbl;
	}
	
	public JPanel initPanel(JPanel panel, EnumRectangle bound, EnumColor color) {
		panel.setBounds(getRectangle(bound));
		panel.setBackground(getColor(color));
		
		return panel;
	}
	
	public JPanel initPanel(JPanel panel, EnumRectangle bound, EnumColor color, EnumLine line) {
		panel = initPanel(panel, bound, color);
		
		panel.setBorder(getLine(line));
		
		return panel;
	}
	
	public JPanel initPanel(JPanel panel, EnumRectangle bound, EnumColor color, int dx, int dy) {
		Rectangle r = new Rectangle(getRectangle(bound));
		r.translate(dx, dy);
		
		panel.setBounds(r);
		panel.setBackground(getColor(color));
		
		return panel;
	}
	
	public JPanel initPanel(JPanel panel, EnumRectangle bound, EnumColor color, EnumLine line, int dx, int dy) {
		panel = initPanel(panel, bound, color, dx, dy);
		
		panel.setBorder(getLine(line));
		
		return panel;
	}
	
	public Rectangle getRectangle(EnumRectangle s) {
		return rectangles.get(s);
	}
	
	public Font getFont(EnumFont font) {
		return fonts.get(font);
	}
	
	private Color getColor(EnumColor color) {
		return colors.get(color);
	}
	
	private LineBorder getLine(EnumLine line) {
		return lines.get(line);
	}
}
