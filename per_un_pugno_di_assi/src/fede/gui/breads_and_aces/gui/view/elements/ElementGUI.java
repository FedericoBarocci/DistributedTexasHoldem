package breads_and_aces.gui.view.elements;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import breads_and_aces.gui.view.elements.utils.CardsUtils;

public class ElementGUI extends JLabel {
	
	private static final long serialVersionUID = 2817608666404375068L;
	
	private boolean enable = true;
	
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
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isEnable() {
		return enable;
	}
}
