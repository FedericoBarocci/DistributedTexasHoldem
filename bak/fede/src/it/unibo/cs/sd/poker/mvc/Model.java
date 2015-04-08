package it.unibo.cs.sd.poker.mvc;

import it.unibo.cs.sd.poker.Action;
import it.unibo.cs.sd.poker.GameTexasHoldem;
import it.unibo.cs.sd.poker.Player;
import it.unibo.cs.sd.poker.exceptions.TurnException;

public class Model extends GameTexasHoldem {

	private static final long serialVersionUID = -6406716566720683736L;
	
	private Integer turn = 0;
	private Boolean execute = true;
	
	public Model() {
		super();
		this.turn = 0;
	}
	
	public void newGame() {
		super.newGame();
		this.turn = 0;
		
		resetPhase();
		startGame();
	}
	
	public Boolean isMyTurn(Integer myID) {
		return isRunning() && myID == turn;
	}
	
	public Integer getTurn() {
		return turn;
	}
	
	public Boolean executeCheck(Integer myID) {
		if (isRunning() && isMyTurn(myID)) {
			this.getPlayers().get(myID).setAction(Action.CHECK);
			turn = (turn + 1) % this.getPlayers().size();
			
			return true;
		}
		return false;
	}
	
	public Boolean completePhase() {
		try {
			for (Player p : this.getPlayers()) {
				if (! p.getAction().equals(Action.CHECK)) {
					throw(new TurnException());
				}
			}
		}
		catch(TurnException e) {
			return false;
		}
		
		return true;
	}
	
	public void resetPhase() {
		for (Player p : this.getPlayers()) {
			p.setAction(Action.NULL);
		}
		
		this.turn = 0;
	}
	
	public void stopGame() {
		this.execute = false;
	}
	
	public void startGame() {
		this.execute = true;
	}
	
	public Boolean isRunning() {
		return this.execute;
	}
}
