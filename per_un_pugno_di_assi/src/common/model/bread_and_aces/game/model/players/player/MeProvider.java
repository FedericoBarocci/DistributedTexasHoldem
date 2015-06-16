package bread_and_aces.game.model.players.player;

import javax.inject.Inject;

import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;

public class MeProvider {

	private final GamePlayersKeeper playersKeeper;

	@Inject
	public MeProvider(GamePlayersKeeper playersKeeper) {
		this.playersKeeper = playersKeeper;
	}

	public Player getMyPlayer() {
		return playersKeeper.getMyPlayer();
	}
	
	public String getMe() {
		return playersKeeper.getMyName();
	}
}
