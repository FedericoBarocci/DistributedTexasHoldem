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

public class OkButton implements MouseListener {

	private final Communicator communicator;
//	private final String nodeId;
	private final DistributedController distributedController;
	private GameUpdater gameUpdater;
	private Action myAction;
	private final GamePlayersKeeper gamePlayersKeeper;

	@Inject
	public OkButton(Communicator communicator, DistributedController distributedController,
			GamePlayersKeeper gamePlayersKeeper) {
		this.communicator = communicator;
		this.gamePlayersKeeper = gamePlayersKeeper;
//		this.nodeId = gamePlayersKeeper.getMyName();
		this.distributedController = distributedController;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (distributedController.leader()) {
			System.out.println(gamePlayersKeeper.getMyName() + " executing Check");
			myAction = Action.CHECK;
			
			Communication c = distributedController.setLocalAction(myAction);
			
			switch (c) {
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
			}
		}
	}

	private void performAction(GameService gameService) {
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
	}

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
