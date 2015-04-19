package breads_and_aces.game.model.players.keeper;

public interface PlayersKeeper {
	boolean contains(String playerId);
	void remove(String targetplayerId);
	String getMe();
	void setMe(String me);
}
