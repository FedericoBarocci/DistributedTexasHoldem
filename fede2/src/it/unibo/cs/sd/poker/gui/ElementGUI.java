package it.unibo.cs.sd.poker.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ElementGUI extends JLabel {
	
	private static final long serialVersionUID = -6647829928538419539L;

	private ImageIcon icon;
	private String path;
	
	public ElementGUI() {
		super();
	}
	
	public ElementGUI(String path) {
		super(path);
		
		this.setSource(path);
		this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
	}
	
	public ElementGUI(String path, int x, int y) {
		super(path);
		
		this.setSource(path);
		this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
	}
	
	public ElementGUI(String path, int x, int y, int w, int h) {
		super(path);
		
		this.setSource(path);
		this.setBounds(x, y, w, h);
	}
	
	public void setSource(String path) {
		this.path = path;
		this.icon = new ImageIcon(this.path);
		this.setIcon(icon);
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		
		/* Metodo 1 */
		this.icon.setImage(getScaledImage(this.icon.getImage(), w, h));
		
		/* Metodo 2 */
//		this.icon = new ImageIcon(this.icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
//		this.setIcon(icon);
	}
	
	public int getWidth() {
		return this.icon.getIconWidth();
	}
	
	public int getHeight() {
		return this.icon.getIconHeight();
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    
	    return resizedImg;
	}
}
