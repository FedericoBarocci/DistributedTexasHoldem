package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.gui.view.GameView;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.inject.Inject;

import breads_and_aces.game.Game;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public class FoldButton implements MouseListener {
	
	private final Communicator communicator;
	private String nodeId;
	private Game game;
	private GameView view;

	@Inject
	public FoldButton(Communicator communicator, Game game) {
		this.communicator = communicator;
		this.game = game;
	}
	
	public void setup(GameView view, String nodeId) {
		this.nodeId = nodeId;
		this.view = view;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/*List<String> crashed = */
		
		if(game.getPlayersKeeper().getPlayer(nodeId).hasToken()){
			game.getPlayersKeeper().getPlayer(nodeId).setAction(Action.FOLD);
			communicator.toAll(nodeId, this::performFold);
		}
	}
	
	private void performFold(GameService gameService) {
		try {
			gameService.receiveCheck(nodeId, game.getPlayersKeeper().getNext(nodeId).getName());
		} catch (RemoteException e) {
//			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold_over.png"));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold_over.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("fold.png"));
	}
}
