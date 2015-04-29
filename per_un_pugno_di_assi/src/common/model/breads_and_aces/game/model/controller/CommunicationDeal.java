package breads_and_aces.game.model.controller;

import java.rmi.RemoteException;

import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.game.updater.GameUpdaterService;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public enum CommunicationDeal implements ICommunication {
	DEAL {
	/*	@Override
		public void sendCommunication(String me, Action action, GameUpdater gameUpdater) {
			class ActionClass {
				private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
					try {
						gameService.receiveActionAndDeal(me, action, gameUpdater);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
//			GameUpdater gameUpdater = new GameUpdater(communicate.getPlayers(), new Deck());
			communicate.getCommunicator().toAll(me, new ActionClass()::performActionAndDeal, gameUpdater);
//			distributedController.update(gameUpdater);
		}
*/
		@Override
		public boolean exec(Communicator communicator, String me, Action action) {
			class ActionClass {
				private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
					try {
						gameService.receiveActionAndDeal(me, action, gameUpdater);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			GameUpdater gameUpdater = GameUpdaterService.build();
			communicator.toAll(me, new ActionClass()::performActionAndDeal, gameUpdater);
			//distributedController.update(gameUpdater);
			return true;
		}
	};
}
