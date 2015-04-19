package it.unibo.cs.sd.poker.gui.controllers;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.core.Deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import breads_and_aces.game.model.players.player.Player;

public class GameUpdater implements Serializable {
	
	private static final long serialVersionUID = -5181566627348057259L;
	
	private List<Card> table = new ArrayList<Card>();
	private List<PlayerElements> players = new ArrayList<PlayerElements>();
	
	public GameUpdater(List<Player> players, Deck deck) {
		for(int i=0; i<5; i++)
			table.add(deck.pop());
		
		for(Player p : players) {
			this.players.add(new PlayerElements(p.getName(), deck.pop(), deck.pop(), p.getScore()));
		}
	}
	
	public List<Card> getTable() {
		return this.table;
	}
	
	public List<PlayerElements> getPlayers() {
		return this.players;
	}
}
