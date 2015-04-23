package breads_and_aces.game.model.players.player;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;

public class MeProvider {

	private final PlayersKeeper playersKeeper;

	public MeProvider(PlayersKeeper playersKeeper) {
		this.playersKeeper = playersKeeper;
		// TODO Auto-generated constructor stub
	}

	public Player getMyPlayer() {
		return playersKeeper.getMyPlayer();
	}
	
	public String getMe() {
		return playersKeeper.getMyName();
	}
}
