package bread_and_aces.gui.labels;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.Game;
import bread_and_aces.game.model.state.GameState;

@LazySingleton
public class LabelCoins extends JLabel {

	private static final long serialVersionUID = 375248315762318729L;

	private final Game game;
	private final GameState gameState;
	
	private int value = 0;
	private int max = 0;

	@Inject
	public LabelCoins(Game game, GameState gameState) {
		super("", SwingConstants.CENTER);
		this.game = game;
		this.gameState = gameState;
	}
	
	public void setValue(int value) {
		if(value >= gameState.getMinBet() && value <= (max - gameState.getMinBet())) {
			this.value = value;
			super.setText("" + this.value);
		}
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getValue() {
		return value;
	}
	
	public void resetValue() {
		gameState.reset();
		max = game.getCoins();
		this.setValue(game.getCoins());
	}
}
