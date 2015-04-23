package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.game.core.Deck;
import it.unibo.cs.sd.poker.gui.controllers.ControllerLogic;
import it.unibo.cs.sd.poker.gui.controllers.GameUpdater;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.DealEventException;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.SinglePlayerException;
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
	private ControllerLogic controller;
	private GameUpdater gameUpdater;
	private Action myAction;

	@Inject
	public FoldButton(Communicator communicator, Game game) {
		this.communicator = communicator;
		this.game = game;
	}
	
	public void setup(String nodeId, GameView view) {
		this.nodeId = nodeId;
		this.view = view;
		this.controller = new ControllerLogic(game, view, nodeId);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(view.isSetRefresh()) {
			view.refresh(game.getPlayers(), nodeId, game.getGoal());
		}
		else {
			myAction = Action.FOLD;
			
			try {
				if(controller.updateAction(nodeId, Action.FOLD)) {
					System.out.println("Executing " + myAction);
					communicator.toAll(nodeId, this::performAction);
				}
			}catch (DealEventException e1) {
				gameUpdater = new GameUpdater(game.getPlayers(), new Deck());
				communicator.toAll(nodeId, this::performActionAndDeal);
				controller.update(gameUpdater);
			} catch (SinglePlayerException e1) {
//				gameUpdater = new GameUpdater(game.getPlayers(), new Deck());
				communicator.toAll(nodeId, this::performWinnerEndGame);
//				game.update(gameUpdater);
			}
		}
	}
	
	private void performAction(GameService gameService) {
		try {
			gameService.receiveAction(nodeId, myAction);
		} catch (RemoteException e) {
			//Game Recovery
			e.printStackTrace();
		}
	}
	
	private void performActionAndDeal(GameService gameService) {
		try {
			gameService.receiveActionAndDeal(nodeId, myAction, gameUpdater);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void performWinnerEndGame(GameService gameService) {
		try {
			gameService.receiveWinnerEndGame(nodeId, myAction);
		} catch (RemoteException e) {
			e.printStackTrace();
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
