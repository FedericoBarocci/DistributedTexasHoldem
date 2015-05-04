package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.gui.labels.LabelBet;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class OkListener implements MouseListener {

	private final DistributedController distributedController;
	private final LabelBet labelBet;

	@Inject
	public OkListener(DistributedController distributedController,
			LabelBet labelBet) {
		this.distributedController = distributedController;
		this.labelBet = labelBet;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			//TODO x Benny
			//Qui è tutto ok, cerca di far ritornare CHECK, RAISE o ALLIN da labelBet.getAction()
			distributedController.setAction(labelBet.getAction());
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
