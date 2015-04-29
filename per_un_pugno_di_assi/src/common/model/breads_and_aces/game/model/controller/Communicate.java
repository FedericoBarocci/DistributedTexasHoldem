package breads_and_aces.game.model.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.oracle.actions.ActionSimple;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

@Singleton
public class Communicate {

	private final Communicator communicator;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	private ActionSimple action;
	private GameUpdater gameUpdater;

	@Inject
	public Communicate(Communicator communicator, GamePlayersKeeper gamePlayersKeeper) {
		this.communicator = communicator;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}
	
	public String getMe() {
		return gamePlayersKeeper.getMyName();
	}
	
	public List<Player> getPlayers() {
		return gamePlayersKeeper.getPlayers();
	}
	
	public Communicator getCommunicator() {
		return communicator;
	}
	
	public void setAction(ActionSimple action) {
		this.action = action;
	}
	
	public ActionSimple getAction() {
		return action;
	}
	
	public GameUpdater getGameUpdater() {
		return gameUpdater;
	}

	public void setGameUpdater(GameUpdater gameUpdater) {
		this.gameUpdater = gameUpdater;
	}
}
