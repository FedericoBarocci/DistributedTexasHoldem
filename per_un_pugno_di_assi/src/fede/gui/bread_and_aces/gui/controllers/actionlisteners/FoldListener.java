package bread_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import bread_and_aces.game.model.controller.DistributedController;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.gui.view.elements.ElementGUI;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

public class FoldListener implements MouseListener {
	
//	private final Communicator communicator;
	private final DistributedController distributedController;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	@Inject
	public FoldListener(//Communicator communicator,
			DistributedController distributedController, GamePlayersKeeper gamePlayersKeeper) {
//		this.communicator = communicator;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.distributedController = distributedController;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			System.out.println(gamePlayersKeeper.getMyName() + " executing FOLD");
			
			/*Communication c = */
			distributedController.setActionOnSend(Action.FOLD);
			
			/*switch (c) {
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
			}*/
			
//			distributedController.setLocalActionAndPropagate(Action.FOLD, communicator);
		}
	}
	
/*	private void performAction(GameService gameService) {
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
	}*/
	
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
