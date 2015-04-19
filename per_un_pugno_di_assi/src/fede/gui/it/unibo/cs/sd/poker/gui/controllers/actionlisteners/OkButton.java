package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.game.core.Deck;
import it.unibo.cs.sd.poker.gui.controllers.ControllerLogic;
import it.unibo.cs.sd.poker.gui.controllers.GameUpdater;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.DealEventException;
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

public class OkButton implements MouseListener {

	private final Communicator communicator;
	private String nodeId;
	private Game game;
	private GameView view;
	private ControllerLogic controller;
	private GameUpdater gameUpdater;

	@Inject
	public OkButton(Communicator communicator, Game game) {
		this.communicator = communicator;
		this.game = game;
	}
	
	public void setup(GameView view, String nodeId) {
		this.nodeId = nodeId;
		this.view = view;
		
		this.controller = new ControllerLogic(game, view, nodeId);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/*List<String> crashed = */
		if(view.isSetRefresh()) {
			view.refresh(game.getPlayers(), nodeId, game.getGoal());
		}
		else {
			try {
				if(controller.check(nodeId, game.getNextId())) {
					communicator.toAll(nodeId, this::performCheck);
				}
			} catch (DealEventException e1) {
				gameUpdater = new GameUpdater(game.getPlayers(), new Deck());
				communicator.toAll(nodeId, this::performCheckAndDeal);
				game.update(gameUpdater);
			}
		}
	}
	
	private void performCheck(GameService gameService) {
		try {
			gameService.receiveCheck(nodeId, game.getNextId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void performCheckAndDeal(GameService gameService) {
		try {
			gameService.receiveCheckAndDeal(nodeId, game.getNextId(), gameUpdater);
		} catch (RemoteException e) {
			e.printStackTrace();
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
