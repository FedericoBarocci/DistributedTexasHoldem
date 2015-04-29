package breads_and_aces.game.model.controller;

import java.rmi.RemoteException;

import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.game.core.GameService;

public enum Communication implements ICommunication {
	
	ACTION {
		@Override
		public void sendCommunication(Communicate communicate) {
			class ActionClass {
				private void performAction(GameService gameService) {
					try {
						gameService.receiveAction(communicate.getMe(), communicate.getAction());
					} catch (RemoteException e) {
						//Game Recovery
						e.printStackTrace();
					}
				}
			}
			communicate.getCommunicator().toAll(communicate.getMe(), new ActionClass()::performAction);
		}
	},
	DEAL {
		@Override
		public void sendCommunication(Communicate communicate) {
			class ActionClass {
				private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
					try {
						gameService.receiveActionAndDeal(communicate.getMe(), communicate.getAction(), gameUpdater);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
//			GameUpdater gameUpdater = new GameUpdater(communicate.getPlayers(), new Deck());
			communicate.getCommunicator().toAll(communicate.getMe(), new ActionClass()::performActionAndDeal, communicate.getGameUpdater());
//			distributedController.update(gameUpdater);
		};
	},
	END {
		@Override
		public void sendCommunication(Communicate communicate) {
			class ActionClass {
				private void performWinnerEndGame(GameService gameService) {
					try {
						gameService.receiveWinnerEndGame(communicate.getMe(), communicate.getAction());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			
			communicate.getCommunicator().toAll(communicate.getMe(), new ActionClass()::performWinnerEndGame);
		}
		
	};
	
	abstract public void sendCommunication(Communicate communicate);
}
