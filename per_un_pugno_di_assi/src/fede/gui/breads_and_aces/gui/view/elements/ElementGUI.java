package breads_and_aces.gui.view.elements;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import breads_and_aces.gui.view.elements.utils.CardsUtils;
import breads_and_aces.gui.view.elements.utils.EnumColor;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class ElementGUI extends JLabel {
	
	private static final long serialVersionUID = 2817608666404375068L;
	
	private boolean enable = true;
	
	protected Rectangle innerRectangle;

	public ElementGUI(ImageIcon icon, Rectangle inner, Rectangle outer) {
		this.innerRectangle = inner;
		
		setBounds(inner);
		setSize(outer.width, outer.height);
		changeImage(icon);
		setVerticalTextPosition(JLabel.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);
		setForeground(GuiUtils.INSTANCE.getColor(EnumColor.black));
	}
	
	public ElementGUI(ImageIcon icon, Rectangle r) {
		this(icon, r, r);
	}

	public ElementGUI(ImageIcon icon, int x, int y) {
		this(icon, new Rectangle(x, y, icon.getIconWidth(), icon.getIconHeight()));
	}
	
	public void changeImage(ImageIcon icon) {
		icon.setImage( CardsUtils.rescaleImage( icon.getImage(), 
				(int) innerRectangle.getWidth(), (int) innerRectangle.getHeight()) );
		setIcon(icon);
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isEnable() {
		return enable;
	}
}
