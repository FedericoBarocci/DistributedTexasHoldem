package breads_and_aces.game.model.utils;

public class Pair<P> {

	private final P first;
	private final P second;

	public Pair(P first, P second) {
		this.first = first;
		this.second = second;
	}
	
	public P getFirst() {
		return first;
	}
	
	public P getSecond() {
		return second;
	}
}
