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
//						currentCrashedRef.set(communicator.getCurrentInterlocutor());
						crashed.addAll(communicator.broadcastRemoveServiceKeeper(gamePlayersKeeper.getMyName(), communicator.getCurrentInterlocutor()));
					}
				}
			}
			
			DevPrinter.println(/*new Throwable()*/);
			
			crashed.clear();
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performAction);

			return new GameHolder(crashed);
			
//			String result = currentCrashedRef.get();
//			DevPrinter.println(result);
//			currentCrashedRef.set(null);
//			DevPrinter.println(result);
			//return new GameHolder(Optional.ofNullable(result));
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
						//currentCrashedRef.set(communicator.getCurrentInterlocutor());
						crashed.addAll(communicator.broadcastRemoveServiceKeeper(gamePlayersKeeper.getMyName(), communicator.getCurrentInterlocutor()));
					}
				}
			}
			
			crashed.clear();
			GameUpdater gameUpdater = new GameUpdater( gamePlayersKeeper.getPlayers(), new Deck() );
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performActionAndDeal, gameUpdater);
			
			return new GameHolder(crashed, Optional.of(gameUpdater));
			
			//return new GameHolder(Optional.ofNullable(currentCrashedRef.get()), Optional.of(gameUpdater));
			
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
//						currentCrashedRef.set(communicator.getCurrentInterlocutor());
						crashed.addAll(communicator.broadcastRemoveServiceKeeper(gamePlayersKeeper.getMyName(), communicator.getCurrentInterlocutor()));
					}
				}
			}
			
			crashed.clear();
			communicator.toAll(gamePlayersKeeper.getMyName(), new ActionClass()::performWinnerEndGame);
			
			return new GameHolder(crashed);
			
			//return new GameHolder(Optional.ofNullable(currentCrashedRef.get()));
//			return Optional.ofNullable(null);
		}
	};
	

	//private static AtomicReference<String> currentCrashedRef = new AtomicReference<String>(null);
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
