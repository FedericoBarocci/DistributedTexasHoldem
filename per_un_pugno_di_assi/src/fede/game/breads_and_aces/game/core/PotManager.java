package breads_and_aces.game.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.oracle.actions.*;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.state.GameState;

@Singleton
public class PotManager {

	private final GamePlayersKeeper gamePlayersKeeper;
	private final GameState gameState;
	private final Game game;

	private int currentPot = 0;
	private int currentBet = 0;

	@Inject
	public PotManager(GamePlayersKeeper gamePlayersKeeper, GameState gameState,
			Game game) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.gameState = gameState;
		this.game = game;
	}

	// il piatto totale della mano (buio, controbuio, primo giro di puntate, secondo, etc..
	public int getCurrentPot() {
		return this.currentPot;
	}

	public void setCurrentPot(int pot) {
		this.currentPot = pot;
	}

	// la puntata per vedere la fase successiva della mano (turn, river, flop)
	public int getCurrentBet() {
		return this.currentBet;
	}

	public void setCurrentBet(int bet) {
		this.currentBet = bet;
	}

	public int getMax() {
		return game.getCoins();
	}

	public Action getAction(int value) {
		Action result = ActionSimple.CHECK;

		// come avvertire il giocatore che deve puntare almeno quanto currentBet...?
		
		if (value < currentBet) 
			result = ActionSimple.NONE;
		
		if ((value == currentBet) && (currentBet == 0))
			result = ActionSimple.CHECK;
		
		else {
			result.setValue(value);
			// va veramente aggiornato in questo modo..?
			currentPot = currentPot + value; // <<---------
			
			if ((value == currentBet) && (currentBet > 0) && (currentBet < game.getCoins()))
				result = ActionValue.CALL;

			if ((value > currentBet) && (currentBet < game.getCoins())) {
				result = ActionValue.RAISE;
				// va veramente aggiornato in questo modo.. ?
				currentBet = value;  // <<--------
			}

			if ((value == game.getCoins())) {
				// va veramente aggiornato in questo modo.. ?
				currentBet = value; // <<---------
				result = ActionSimple.ALLIN;
			}
		}
		
		return result;
	}
}
