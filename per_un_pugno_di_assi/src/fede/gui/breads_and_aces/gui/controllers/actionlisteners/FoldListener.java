package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.oracle.actions.ActionKeeperFactory;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class FoldListener implements MouseListener {
	
	private final DistributedController distributedController;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	@Inject
	public FoldListener(DistributedController distributedController, GamePlayersKeeper gamePlayersKeeper) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.distributedController = distributedController;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			System.out.println(gamePlayersKeeper.getMyName() + " executing FOLD");
			
			distributedController.setActionOnSend(ActionKeeperFactory.get(Action.FOLD));
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold_click.png"));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold_over.png"));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold_over.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (((ElementGUI) (e.getSource())).isEnable()) {
			((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold.png"));
		}
	}
}
