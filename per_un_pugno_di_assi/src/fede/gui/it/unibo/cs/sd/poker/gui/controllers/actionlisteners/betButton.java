package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.game.core.MaxReachedException;
import it.unibo.cs.sd.poker.game.core.NegativeIntegerException;
import it.unibo.cs.sd.poker.game.core.PositiveInteger;
import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.utils.ButtonsUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class betButton implements MouseListener {
	private JLabel lblBet;
	private JLabel lblScore;
	private Integer coins;

	public betButton(JLabel lblBet, JLabel lblScore, Integer coins) {
		this.lblBet = lblBet;
		this.lblScore = lblScore;
		this.coins = coins;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		PositiveInteger i = new PositiveInteger(Integer.parseInt(lblBet
				.getText()), coins);

		switch (((JLabel) e.getSource()).getName()) {
		case "UP":
			try {
				i.add(10);
			} catch (MaxReachedException e1) {
				// e1.printStackTrace();
			}
			break;

		case "DOWN":
			try {
				i.substract(10);
			} catch (NegativeIntegerException e1) {
				// e1.printStackTrace();
			}
			break;
		}

		lblBet.setText("" + i.getIntValue());
		int score = coins - i.getIntValue();
		lblScore.setText("" + score);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (lbl.getName()) {
		case "UP":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("up_click.png"));
			break;

		case "DOWN":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("down_click.png"));
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (lbl.getName()) {
		case "UP":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("up_over.png"));
			break;

		case "DOWN":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("down_over.png"));
			break;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (lbl.getName()) {
		case "UP":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("up_over.png"));
			break;

		case "DOWN":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("down_over.png"));
			break;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (lbl.getName()) {
		case "UP":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("up.png"));
			break;

		case "DOWN":
			lbl.changeImage(ButtonsUtils.INSTANCE.getImageGui("down.png"));
			break;
		}
	}

}
