package bread_and_aces.game;

import javax.inject.Singleton;

@Singleton
public class Game {
	
	private boolean isStarted = false;
	private int goal = 0;
	private int coins = 0;
	
	public void start() {
		isStarted = true;
	}

	public void stop() {
		isStarted = false;
	}

	public boolean isStarted() {
		return isStarted;
	}
	
	public void setGoal(int initialGoal) {
		this.goal = initialGoal;
	}
	
	public int getGoal() {
		return goal;
	}
	
	public void setCoins(int initialCoins) {
		this.coins = initialCoins;
	}
	
	public int getCoins() {
		return coins;
	}
}
