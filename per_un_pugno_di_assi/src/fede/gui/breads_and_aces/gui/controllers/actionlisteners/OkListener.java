package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import breads_and_aces.game.core.BetManager;
import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.gui.labels.LabelCoins;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class OkListener implements MouseListener {

	private final DistributedController distributedController;
	private final BetManager betManager;
	private final LabelCoins lblCoins;


	@Inject
	public OkListener(DistributedController distributedController, 
			BetManager betManager, LabelCoins lblCoins) {
		this.distributedController = distributedController;
		this.betManager = betManager;
		this.lblCoins = lblCoins;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			//lblCoins.setMax(lblCoins.getValue());
			
			System.out.println("voglio puntare " + betManager.getBet().getValue());
			System.out.println("il mio max vale " + betManager.getMax());
			//betManager.setMax(betManager.getMax() - betManager.getBet().getValue());
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
