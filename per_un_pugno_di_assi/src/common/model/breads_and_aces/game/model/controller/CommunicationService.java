package breads_and_aces.game.model.controller;

import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.oracle.actions.ActionSimple;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.updater.GameUpdater;

public class CommunicationService {

	private final Communicate communicate;
	
	@Inject
	public CommunicationService(Communicate communicate) {
		this.communicate = communicate;
	}
	
	public void exec(Communication communication, ActionSimple action) {
		
		communicate.setAction(action);
		communication.sendCommunication(communicate);
		
	}
	
	public void makeGameUpdater(List<Player> players) {
		communicate.setGameUpdater(new GameUpdater(players, new Deck()));
	}
	
	public GameUpdater getGameUpdater() {
		return communicate.getGameUpdater();
	}
}
