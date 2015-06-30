package bread_and_aces.game.updater;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bread_and_aces.game.core.Card;
import bread_and_aces.game.core.Deck;
import bread_and_aces.game.model.players.player.Player;

public class GameUpdater implements Serializable {
	
	private static final long serialVersionUID = -5181566627348057259L;
	
	private List<Card> tableCards = new ArrayList<Card>();
	private List<PlayerData> playersData = new ArrayList<PlayerData>();
	
	public GameUpdater(List<Player> players, Deck deck) {
		for(int i=0; i<5; i++)
			tableCards.add(deck.pop());
		
		for(Player p : players) {
			this.playersData.add(new PlayerData(p.getName(), deck.pop(), deck.pop()/*, p.getScore()*/));
		}
	}
	
	public List<Card> getTable() {
		return this.tableCards;
	}
	
	public List<PlayerData> getPlayers() {
		return this.playersData;
	}
}
