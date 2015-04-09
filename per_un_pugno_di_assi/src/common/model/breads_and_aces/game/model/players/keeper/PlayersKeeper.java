package breads_and_aces.game.model.players.keeper;

public interface PlayersKeeper {
	public boolean contains(String playerId);
	public void remove(String targetplayerId);
}
