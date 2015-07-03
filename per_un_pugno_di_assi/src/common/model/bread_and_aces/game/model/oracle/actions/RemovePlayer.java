package bread_and_aces.game.model.oracle.actions;

import java.io.Serializable;
import java.util.Optional;

public class RemovePlayer implements Serializable, Message {

	private static final long serialVersionUID = -3426731451953660943L;
	
	private final Optional<String> player;

	public RemovePlayer(String player) {
		this.player = Optional.of(player);
	}

	@Override
	public Action getAction() {
		return null;
	}

	@Override
	public int getValue() {
		return 0;
	}

	@Override
	public Optional<String> getCrashed() {
		return player;
	}
}
