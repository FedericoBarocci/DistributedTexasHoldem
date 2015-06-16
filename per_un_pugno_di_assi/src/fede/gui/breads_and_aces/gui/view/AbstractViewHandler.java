package breads_and_aces.gui.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.ImageGUI;
import breads_and_aces.gui.view.elements.frame.JFrameGame;

abstract public class AbstractViewHandler<T> implements InitableView<T> {
	
	private final JFrameGame frame;
	
	public AbstractViewHandler(JFrameGame jFrameGame) {
		frame = jFrameGame;
	}
	
	protected void addElement(JLabel e) {
		frame.getLayeredPane().add(e);	//getContentPane
	}
	
	protected void addElement(JPanel e) {
		frame.getContentPane().add(e);
	}
	
	protected void addElement(ImageGUI e) {
		frame.getContentPane().add(e);
	}
	
	protected void addElement(ElementGUI e) {
		frame.getLayeredPane().add(e);
	}
	
	protected void removeElement(JLabel e) {
		frame.getLayeredPane().remove(e);	//getContentPane
	}
	
	protected void removeElement(JPanel e) {
		frame.getContentPane().remove(e);
	}
	
	protected void removeElement(ImageGUI e) {
		frame.getContentPane().remove(e);
	}
	
	protected void removeElement(ElementGUI e) {
		frame.getLayeredPane().remove(e);
	}
	
	protected void repaint() {
		frame.repaint();
	}
	
	protected void show() {
		frame.setVisible(true);
	}
}
