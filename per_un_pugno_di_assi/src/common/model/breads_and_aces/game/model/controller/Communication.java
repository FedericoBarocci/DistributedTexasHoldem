package breads_and_aces.game.model.controller;

import java.rmi.RemoteException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import bread_and_aces.utils.DevPrinter;
import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.oracle.actions.ActionKeeper;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public enum Communication {
	
	ACTION {
		@Override
		public GameHolder exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, ActionKeeper actionKeeper) {
			class ActionClass {
				private void performAction(GameService gameService) {
					try {
						gameService.receiveAction(gamePlayersKeeper.getMyName(), actionKeeper);
					} catch (RemoteException e) {
						//Game Recovery
						currentCrashedRef.set(communicator.getCurrent());
						communicator.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(gamePlayersKeeper.getMyName(), currentCrashedRef.get());
					}
				}
			}
			
			new DevPrinter(new Throwable()).println();
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performAction);
			
			return new GameHolder(Optional.ofNullable(currentCrashedRef.get()));
			
//			return Optional.ofNullable(null);
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
						currentCrashedRef.set(communicator.getCurrent());
						communicator.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(gamePlayersKeeper.getMyName(), currentCrashedRef.get());
					}
				}
			}
			
			GameUpdater gameUpdater = new GameUpdater( gamePlayersKeeper.getPlayers(), new Deck() );
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performActionAndDeal, gameUpdater);
			
			return new GameHolder(Optional.ofNullable(currentCrashedRef.get()), Optional.of(gameUpdater));
			
//			return Optional.ofNullable(gameUpdater);
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
						currentCrashedRef.set(communicator.getCurrent());
						communicator.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(gamePlayersKeeper.getMyName(), currentCrashedRef.get());
					}
				}
			}
			
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performWinnerEndGame);
			
			return new GameHolder(Optional.ofNullable(currentCrashedRef.get()));
//			return Optional.ofNullable(null);
		}
	};
	

	private static AtomicReference<String> currentCrashedRef = new AtomicReference<String>(null);
	
	abstract public GameHolder exec(Communicator communicator, GamePlayersKeeper gamePlayersKeeper, ActionKeeper actionKeeper);
	
	public static class GameHolder {
		private final Optional<String> crashedOptional;
		private final Optional<GameUpdater> gameupdaterOptional;
		
		public GameHolder(Optional<String> crashedOptional) {
			this.crashedOptional = crashedOptional;
			this.gameupdaterOptional = Optional.ofNullable(null);
		}
		
		public GameHolder(Optional<String> crashedOptional, Optional<GameUpdater> gameupdaterOptional) {
			this.crashedOptional = crashedOptional;
			this.gameupdaterOptional = gameupdaterOptional;
		}

		public Optional<String> getCrashedOptional() {
			return crashedOptional;
		}
		
		public Optional<GameUpdater> getGameupdaterOptional() {
			return gameupdaterOptional;
		}
	}
}
