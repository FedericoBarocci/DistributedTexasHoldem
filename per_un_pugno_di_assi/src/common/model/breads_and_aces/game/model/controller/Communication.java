package breads_and_aces.game.model.controller;

import java.rmi.RemoteException;
import java.util.Optional;

import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public enum Communication {
	
	ACTION {
		@Override
		public Optional<GameUpdater> exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, Action action) {
			class ActionClass {
				private void performAction(GameService gameService) {
					try {
						gameService.receiveAction(gamePlayersKeeper.getMyName(), action);
					} catch (RemoteException e) {
						//Game Recovery
						e.printStackTrace();
					}
				}
			}
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performAction);
			
			return Optional.ofNullable(null);
		}
	},
	DEAL {
		@Override
		public Optional<GameUpdater> exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, Action action) {
			class ActionClass {
				private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
					try {
						gameService.receiveActionAndDeal(gamePlayersKeeper.getMyName(), action, gameUpdater);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			GameUpdater gameUpdater = new GameUpdater( gamePlayersKeeper.getPlayers(), new Deck() );
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performActionAndDeal, gameUpdater);
			
			return Optional.ofNullable(gameUpdater);
		}
	},
	END {
		@Override
		public Optional<GameUpdater> exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, Action action) {
			class ActionClass {
				private void performWinnerEndGame(GameService gameService) {
					try {
						gameService.receiveWinnerEndGame(gamePlayersKeeper.getMyName(), action);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
			
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performWinnerEndGame);
			
			return Optional.ofNullable(null);
		}
	};
	
	abstract public Optional<GameUpdater> exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, Action action);
}
