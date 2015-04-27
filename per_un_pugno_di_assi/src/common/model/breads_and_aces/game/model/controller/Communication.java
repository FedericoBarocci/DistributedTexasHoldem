package breads_and_aces.game.model.controller;

import java.rmi.RemoteException;
import java.util.List;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

// TODO to improve with future actions (raise, call, etc)
public enum Communication {
	ACTION {
		@Override
		public void sendCommunication(String meId, Action action, List<Player> players, Communicator communicator) {
			class ActionClass {
				private void performAction(GameService gameService) {
					try {
						gameService.receiveAction(meId, action);
					} catch (RemoteException e) {
						//Game Recovery
						e.printStackTrace();
					}
				}
			}
			communicator.toAll(meId, new ActionClass()::performAction);
		};
	},
	DEAL {
		@Override
		public void sendCommunication(String meId, Action action, List<Player> players, Communicator communicator) {
			class ActionClass {
				private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
					try {
						gameService.receiveActionAndDeal(meId, action, gameUpdater);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			GameUpdater gameUpdater = new GameUpdater(players, new Deck());
			communicator.toAll(meId, new ActionClass()::performActionAndDeal, gameUpdater);
//			distributedController.update(gameUpdater);
		};
	},
	END {
		@Override
		public void sendCommunication(String meId, Action action, List<Player> players, Communicator communicator) {
			class ActionClass {
				private void performWinnerEndGame(GameService gameService) {
					try {
						gameService.receiveWinnerEndGame(meId, Action.FOLD);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			
			communicator.toAll(meId, new ActionClass()::performWinnerEndGame);
		}
		
	};

	abstract public void sendCommunication(String meId, Action action, List<Player> players, Communicator communicator);
}
