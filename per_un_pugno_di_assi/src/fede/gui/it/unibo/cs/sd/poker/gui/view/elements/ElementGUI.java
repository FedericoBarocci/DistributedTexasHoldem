package it.unibo.cs.sd.poker.gui.view.elements;

import it.unibo.cs.sd.poker.gui.view.elements.utils.CardsUtils;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ElementGUI extends JLabel {
	
	private static final long serialVersionUID = 2817608666404375068L;
	
	protected Rectangle r;

	public ElementGUI(ImageIcon icon, Rectangle r) {
		this.r = r;
		changeImage(icon);
	}

	public ElementGUI(ImageIcon icon, int x, int y) {
		this(icon, new Rectangle(x, y, icon.getIconWidth(), icon.getIconHeight()));
	}
	
	public void changeImage(ImageIcon icon) {
		setIcon(icon);
		setBounds(r);
	}
	
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		
		ImageIcon i = (ImageIcon) getIcon();
		i.setImage( CardsUtils.rescaleImage( i.getImage(), (int) r.getWidth(), (int) r.getHeight()) );
	}
}
