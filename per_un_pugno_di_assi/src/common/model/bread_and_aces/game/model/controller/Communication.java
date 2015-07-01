package bread_and_aces.game.model.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import bread_and_aces.game.core.Deck;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.updater.GameUpdater;
import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.services.rmi.utils.communicator.Communicator;
import bread_and_aces.utils.DevPrinter;

public enum Communication {
	
	ACTION {
		@Override
		public GameHolder exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, ActionKeeper actionKeeper) {
			class ActionClass {
				private void performAction(GameService gameService) {
					try {
						gameService.receiveAction(gamePlayersKeeper.getMyName(), actionKeeper);
					} catch (RemoteException e) {
						crashed.add(communicator.getCurrentInterlocutor());
					}
				}
			}
			
			DevPrinter.println();
			
			crashed.clear();
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performAction);
//			crashHandler.recursiveBroadcastRemoveCrashed(gamePlayersKeeper.getMyName(), crashed);

			return new GameHolder(crashed);
		}
	},
	DEAL {
		@Override
		public GameHolder exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, ActionKeeper actionKeeper) {
			class ActionClass {
				private void performActionAndDeal(GameService gameService, GameUpdater gameUpdater) {
					try {
						gameService.receiveActionAndDeal(gamePlayersKeeper.getMyName(), actionKeeper, gameUpdater);
					} catch (RemoteException e) {
						crashed.add(communicator.getCurrentInterlocutor());
					}
				}
			}
			
			crashed.clear();
			GameUpdater gameUpdater = new GameUpdater( gamePlayersKeeper.getPlayers(), new Deck() );
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performActionAndDeal, gameUpdater);
//			communicator.recursiveBroadcastRemoveCrashed(gamePlayersKeeper.getMyName(), crashed);

			return new GameHolder(crashed, Optional.of(gameUpdater));
		}
	},
	END {
		@Override
		public GameHolder exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, ActionKeeper actionKeeper) {
			class ActionClass {
				private void performWinnerEndGame(GameService gameService) {
					try {
						gameService.receiveWinnerEndGame(gamePlayersKeeper.getMyName(), actionKeeper);
					} catch (RemoteException e) {
						crashed.add(communicator.getCurrentInterlocutor());
					}
				}
			}
			
			crashed.clear();
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performWinnerEndGame);
//			communicator.recursiveBroadcastRemoveCrashed(gamePlayersKeeper.getMyName(), crashed);

			return new GameHolder(crashed);
		}
	};
	

	private static List<String> crashed = new ArrayList<String>();
	
	abstract public GameHolder exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, ActionKeeper actionKeeper);
	
	public static class GameHolder {
		private final List<String> crashedPeers;
		private final Optional<GameUpdater> gameupdaterOptional;
		
		public GameHolder(List<String> crashedOptional) {
			this.crashedPeers = crashedOptional;
			this.gameupdaterOptional = Optional.ofNullable(null);
		}
		
		public GameHolder(List<String> crashedOptional, Optional<GameUpdater> gameupdaterOptional) {
			this.crashedPeers = crashedOptional;
			this.gameupdaterOptional = gameupdaterOptional;
		}

		public List<String> getCrashedPeers() {
			return crashedPeers;
		}
		
		public Optional<GameUpdater> getGameupdaterOptional() {
			return gameupdaterOptional;
		}

		public boolean hasCrashed() {
			return crashedPeers.size() > 0;
		}
	}
}
