package it.unibo.cs.sd.poker.gui.view.elements.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public enum GuiUtils {
	
	INSTANCE;
	
	private final String dir = "elements" + File.separatorChar + "buttons" + File.separatorChar;
	
	private final Map<String,ImageIcon> paths = new HashMap<>();
	private final Map<String,Rectangle> bounds = new HashMap<>();
	private final Map<String,Color> colors = new HashMap<>();
	private final Map<String,Font> fonts = new HashMap<>();
	private final Map<String,LineBorder> lines = new HashMap<>();
	
	private GuiUtils() {
		bounds.put("frame", 	new Rectangle(0, 0, 1300, 480));
		bounds.put("title", 	new Rectangle(10, 10, 160, 100));
		bounds.put("cardPanel", new Rectangle(370, 30, 740, 185));
		bounds.put("name", 		new Rectangle(0, 134, 180, 50));
		bounds.put("coins", 	new Rectangle(20, 185, 55, 50));
		bounds.put("score", 	new Rectangle(105, 185, 55, 50));
		bounds.put("winners", 	new Rectangle(20, 430, 500, 50));
		bounds.put("bet", 		new Rectangle(10, 330, 60, 50));
		bounds.put("pot", 		new Rectangle(60, 240, 60, 50));
		bounds.put("leftBox", 	new Rectangle(0, 0, 180, 670));
		bounds.put("up", 		new Rectangle(15, 297, 50, 50));
		bounds.put("down", 		new Rectangle(15, 363, 50, 50));
		bounds.put("ok", 		new Rectangle(100, 300, 50, 50));
		bounds.put("fold", 		new Rectangle(100, 365, 50, 50));
		bounds.put("info", 		new Rectangle(1250, 15, 35, 35));
		bounds.put("leftPanel", new Rectangle(0, 0, 180, 430));
		bounds.put("bottom", 	new Rectangle(0, 430, 1300, 50));
		
		bounds.put("playerName", 	new Rectangle(115, 25));
		bounds.put("playerAction", 	new Rectangle(115, 25));
		bounds.put("playerScore", 	new Rectangle(115, 25));
		bounds.put("playerBox", 	new Rectangle(120, 135));
		bounds.put("playerLevel", 	new Rectangle(10, 135));
		
		colors.put("black", 		new Color(0, 0, 0));
		colors.put("gold", 			new Color(255, 230, 0));
		colors.put("white", 		new Color(255, 255, 255));
		colors.put("royalRed", 		new Color(176, 23, 31));
		colors.put("yellow", 		new Color(255, 230, 0));
		colors.put("alphaBlue", 	new Color(174, 234, 255, 50));
		colors.put("alphaGreen", 	new Color(65, 146, 75, 200));
		colors.put("glass", 		new Color(255, 255, 255, 60));
		colors.put("glass2", 		new Color(255, 255, 255, 80));
		
		fonts.put("B11", new Font("SansSerif", Font.BOLD, 11));
		fonts.put("B13", new Font("SansSerif", Font.BOLD, 13));
		fonts.put("B15", new Font("SansSerif", Font.BOLD, 15));
		fonts.put("B16", new Font("SansSerif", Font.BOLD, 16));
		fonts.put("B18", new Font("SansSerif", Font.BOLD, 18));
		fonts.put("B22", new Font("SansSerif", Font.BOLD, 22));
		fonts.put("B25", new Font("SansSerif", Font.BOLD, 25));
		
		lines.put("cardBox", 	new LineBorder(getColor("white"), 1));
		lines.put("playerBox", 	new LineBorder(getColor("yellow"), 1));
	}
	
	public ImageIcon getImageGui(String element) {
		ImageIcon icon = paths.get(element);
		
		if (icon == null) {
			icon = new ImageIcon(dir + element);
			paths.put(element, icon );
		}
		
		return icon;
	}
	
	public JLabel initLabel(JLabel lbl, String bound, String color, String font) {
		lbl.setBounds(getBound(bound));
		lbl.setForeground(getColor(color));
		lbl.setFont(getFont(font));
		
		return lbl;
	}
	
	public JLabel initLabel(JLabel lbl, String bound, String color, String font, String text) {
		lbl = initLabel(lbl, bound, color, font);
		
		lbl.setText(text);
		
		return lbl;
	}
	
	public JLabel initLabel(JLabel lbl, String bound, String color, String font, String text, int dx, int dy) {
		Rectangle r = new Rectangle(getBound(bound));
		r.translate(dx, dy);
		
		lbl.setBounds(r);
		lbl.setForeground(getColor(color));
		lbl.setFont(getFont(font));
		lbl.setText(text);
		
		return lbl;
	}
	
	public JPanel initPanel(JPanel panel, String bound, String color) {
		panel.setBounds(getBound(bound));
		panel.setBackground(getColor(color));
		
		return panel;
	}
	
	public JPanel initPanel(JPanel panel, String bound, String color, String line) {
		panel = initPanel(panel, bound, color);
		
		panel.setBorder(getLine(line));
		
		return panel;
	}
	
	public JPanel initPanel(JPanel panel, String bound, String color, int dx, int dy) {
		Rectangle r = new Rectangle(getBound(bound));
		r.translate(dx, dy);
		
		panel.setBounds(r);
		panel.setBackground(getColor(color));
		
		return panel;
	}
	
	public JPanel initPanel(JPanel panel, String bound, String color, String line, int dx, int dy) {
		panel = initPanel(panel, bound, color, dx, dy);
		
		panel.setBorder(getLine(line));
		
		return panel;
	}
	
	public Rectangle getBound(String s) {
		return bounds.get(s);
	}
	
	private Color getColor(String color) {
		return colors.get(color);
	}
	
	private Font getFont(String font) {
		return fonts.get(font);
	}
	
	private LineBorder getLine(String line) {
		return lines.get(line);
	}
}
