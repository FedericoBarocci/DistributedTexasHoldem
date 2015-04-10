package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ElementGUI extends JLabel {
	
	private static final long serialVersionUID = 2817608666404375068L;
	
	protected int x = 0;
	protected int y = 0;
	protected int w = 0;
	protected int h = 0;
	
	public ElementGUI(ImageIcon icon) {
		setSource(icon);
		setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
	}
	
	public ElementGUI(ImageIcon icon, int x, int y) {
		setSource(icon);
		setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
	}
	
	public ElementGUI(ImageIcon icon, int x, int y, int w, int h) {
		setSource(icon);
		setBounds(x, y, w, h);
	}
	
	protected void setSource(ImageIcon icon) {
		super.setIcon(icon);
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		
		/* Metodo 1 */
//		getIcon().setImage( rescaleImage( getIcon().getImage(), w, h) );
		// we passed an ImageIcon
		ImageIcon i = (ImageIcon) getIcon();
		i.setImage( CardsUtils.rescaleImage( i.getImage(), w, h) );
		
		/* Metodo 2 */
//		this.icon = new ImageIcon(this.icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
//		this.setIcon(icon);
	}
	
	/*public int getWidth() {
		return getIcon().getIconWidth();
	}
	public int getHeight() {
		return getIcon().getIconHeight();
	}*/
	
	/*private Image rescaleImage(Image srcImg, int w, int h) {
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    
	    return resizedImg;
	}*/
}
