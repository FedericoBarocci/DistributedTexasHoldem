package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.game.model.oracle.actions.ActionSimple;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.gui.labels.LabelBet;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class OkButton implements MouseListener {

//	private final Communicator communicator;
//	private final String nodeId;
	private final DistributedController distributedController;
//	private GameUpdater gameUpdater;
	private ActionSimple myAction;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final LabelBet labelBet;

	@Inject
	public OkButton(/*Communicator communicator, */DistributedController distributedController,
			GamePlayersKeeper gamePlayersKeeper, LabelBet labelBet) {
//		this.communicator = communicator;
		this.gamePlayersKeeper = gamePlayersKeeper;
//		this.nodeId = gamePlayersKeeper.getMyName();
		this.distributedController = distributedController;
		this.labelBet = labelBet;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			System.out.println(gamePlayersKeeper.getMyName() + " executing Check");
			myAction = ActionSimple.CHECK;
			
			/*Communication c = */
			distributedController.setAction(myAction);
			
			/*switch (c) {
				case ACTION:
					communicator.toAll(gamePlayersKeeper.getMyName(), this::performAction);
					break;
					
				case DEAL: 
					gameUpdater = new GameUpdater(gamePlayersKeeper.getPlayers(), new Deck());
					communicator.toAll(gamePlayersKeeper.getMyName(), this::performActionAndDeal);
					distributedController.update(gameUpdater);
					break;
					
				case END:
					communicator.toAll(gamePlayersKeeper.getMyName(), this::performWinnerEndGame);
					break;
			}*/
		}
	}

	/*private void performAction(GameService gameService) {
		try {
			gameService.receiveAction(gamePlayersKeeper.getMyName(), myAction);
		} catch (RemoteException e) {
			// Game Recovery
			e.printStackTrace();
		}
	}

	private void performActionAndDeal(GameService gameService) {
		try {
			gameService.receiveActionAndDeal(gamePlayersKeeper.getMyName(), myAction, gameUpdater);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void performWinnerEndGame(GameService gameService) {
		try {
			gameService.receiveWinnerEndGame(gamePlayersKeeper.getMyName(), myAction);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public void mousePressed(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
				.getImageGui("ok_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
				.getImageGui("ok_over.png"));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
				.getImageGui("ok_over.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE
				.getImageGui("ok.png"));
	}

}
