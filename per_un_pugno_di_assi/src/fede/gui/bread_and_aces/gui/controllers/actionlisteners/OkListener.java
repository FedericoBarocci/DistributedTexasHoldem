package bread_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.controller.DistributedController;
import bread_and_aces.gui.view.elements.ElementGUI;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

public class OkListener implements MouseListener {

	private final DistributedController distributedController;
	private final BetManager betManager;

	@Inject
	public OkListener(DistributedController distributedController, 
			BetManager betManager) {
		this.distributedController = distributedController;
		this.betManager = betManager;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader(false)) {
			distributedController.setActionOnSend(betManager.getActionKeeper());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
					.getImageGui("ok_click.png"));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
					.getImageGui("ok_over.png"));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
					.getImageGui("ok_over.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
					.getImageGui("ok.png"));
		}
	}

}
