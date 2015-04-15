package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.utils.ButtonsUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class foldButton implements MouseListener {
	

	public foldButton() {}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(ButtonsUtils.INSTANCE
				.getImageGui("fold_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(ButtonsUtils.INSTANCE
				.getImageGui("fold_over.png"));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(ButtonsUtils.INSTANCE
				.getImageGui("fold_over.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(ButtonsUtils.INSTANCE
				.getImageGui("fold.png"));
	}

}
