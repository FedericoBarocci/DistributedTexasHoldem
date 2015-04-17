package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.main.Main;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public class OkButton implements MouseListener {

	private final Communicator communicator;
	private final String playerId;

	@Inject
	public OkButton(Communicator communicator) {
		this.communicator = communicator;
		playerId = Main.nodeid;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		List<String> crashed = communicator.toAll("", this::performCheck);
	}
	
	
	private void performCheck(GameService gameService) {
		try {
			gameService.receiveCheck(playerId);
		} catch (RemoteException e) {
//			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("ok_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("ok_over.png"));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("ok_over.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("ok.png"));
	}

}
