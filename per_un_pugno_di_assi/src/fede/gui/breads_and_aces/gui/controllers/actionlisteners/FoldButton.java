package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.inject.Inject;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.controller.Communication;
import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.GuiUtils;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public class FoldButton implements MouseListener {
	
	private final Communicator communicator;
	private final DistributedController distributedController;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	@Inject
	public FoldButton(Communicator communicator,
			DistributedController distributedController, GamePlayersKeeper gamePlayersKeeper) {
		this.communicator = communicator;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.distributedController = distributedController;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			System.out.println(gamePlayersKeeper.getMyName() + " executing FOLD");
			
			Communication c = distributedController.setLocalAction(Action.FOLD);
			
			switch (c) {
				case ACTION:
					communicator.toAll(gamePlayersKeeper.getMyName(), this::performAction);
					break;
					
				case DEAL:
					GameUpdater gameUpdater = new GameUpdater(gamePlayersKeeper.getPlayers(), new Deck());
					communicator.toAll(gamePlayersKeeper.getMyName(), this::performActionAndDeal, gameUpdater);
					distributedController.update(gameUpdater);
					break;
					
				case END:
					communicator.toAll(gamePlayersKeeper.getMyName(), this::performWinnerEndGame);
					break;
			}
			
//			distributedController.setLocalActionAndPropagate(Action.FOLD, communicator);
		}
	}
	
	private void performAction(GameService gameService) {
		try {
			gameService.receiveAction(gamePlayersKeeper.getMyName(), Action.FOLD);
		} catch (RemoteException e) {
			//Game Recovery
			e.printStackTrace();
		}
	}
	
	private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
		try {
			gameService.receiveActionAndDeal(gamePlayersKeeper.getMyName(), Action.FOLD, gameUpdater);
		} catch (RemoteException e) {
			//Game Recovery
			e.printStackTrace();
		}
	}
	
	private void performWinnerEndGame(GameService gameService) {
		try {
			gameService.receiveWinnerEndGame(gamePlayersKeeper.getMyName(), Action.FOLD);
		} catch (RemoteException e) {
			//Game Recovery
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
