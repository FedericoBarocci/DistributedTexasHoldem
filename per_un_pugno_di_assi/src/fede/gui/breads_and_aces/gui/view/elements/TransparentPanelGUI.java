package breads_and_aces.gui.view.elements;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class TransparentPanelGUI extends JPanel {
	
	private static final long serialVersionUID = 3578871709589960050L;
	
	public TransparentPanelGUI() {
        setOpaque(false);
    }
	
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }
}
