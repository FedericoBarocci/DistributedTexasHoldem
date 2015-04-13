package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.game.core.MaxReachedException;
import it.unibo.cs.sd.poker.game.core.NegativeIntegerException;
import it.unibo.cs.sd.poker.game.core.PositiveInteger;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class betButton implements MouseListener {

	private JLabel lblBet;
	private JLabel lblScore;
	private Integer max;
	
	public betButton(JLabel lblBet, JLabel lblScore, Integer max) {
		// TODO Auto-generated constructor stub
		this.lblBet = lblBet;
		this.lblScore = lblScore;
		this.max = max;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		PositiveInteger i = new PositiveInteger(Integer.parseInt(lblBet.getText()), max);
		
		switch (((JLabel) e.getSource()).getName()) {
			case "UP" :
				try {
					i.add(10);
				} catch (MaxReachedException e1) {
					//e1.printStackTrace();
				}
				break;
				
			case "DOWN" :
				try {
					i.substract(10);
				} catch (NegativeIntegerException e1) {
					//e1.printStackTrace();
				}
				break;
		}
		
		lblBet.setText("" + i.getIntValue());
		int score = max - i.getIntValue();
		lblScore.setText("Coins: " + score);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
