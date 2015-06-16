package breads_and_aces.gui.view;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.gui.labels.LabelBet;
import breads_and_aces.gui.labels.LabelCoins;
import breads_and_aces.gui.labels.LabelMessage;
import breads_and_aces.gui.labels.LabelPot;
import breads_and_aces.gui.labels.LabelScore;

@Singleton
public class LabelHandler {

	private final LabelBet lblBet;
	private final LabelCoins lblCoins;
	private final LabelPot lblPot;
	private final LabelScore lblScore;
	private final LabelMessage lblMessage;

	@Inject
	public LabelHandler(LabelBet lblBet, LabelCoins lblCoins, LabelPot lblPot, LabelScore lblScore, LabelMessage lblMessage) {
		this.lblBet = lblBet;
		this.lblCoins = lblCoins;
		this.lblPot = lblPot;
		this.lblScore = lblScore;
		this.lblMessage = lblMessage;
	}
	
	public void init() {
		lblCoins.resetValue();
		lblPot.setValue(0);
		lblBet.setValue(0);
	}
	
	public int getCoins() {
		return lblCoins.getValue();
	}

	public void printMessage(String string) {
		lblMessage.setText(string);
	}

	public void setValue(int sumallpot, int minbet) {
		lblPot.setValue(sumallpot);
		lblBet.setValue(minbet);
		lblCoins.setValue(lblCoins.getMax() - minbet);
	}

	public int savebet(int minBet) {
		int result = lblCoins.getMax() - minBet;
		
		lblCoins.setMax(result);
		lblBet.setValue(0);
		
		return result;
	}
	
	public void setScore(int score) {
		lblScore.setValue(score);
	}
}
