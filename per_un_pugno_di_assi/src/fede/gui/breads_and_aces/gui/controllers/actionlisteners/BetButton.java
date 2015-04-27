package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import breads_and_aces.game.Game;
import breads_and_aces.game.core.PositiveInteger;
import breads_and_aces.game.exceptions.MaxReachedException;
import breads_and_aces.game.exceptions.NegativeIntegerException;
import breads_and_aces.gui.labels.LabelBet;
import breads_and_aces.gui.labels.LabelCoins;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.EnumButton;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class BetButton implements MouseListener {
	private final LabelBet lblBet;
	private final LabelCoins lblCoins;
	private final Game game;
	
	@Inject
	public BetButton(LabelBet lblBet, LabelCoins lblcoins, Game game) {
		this.lblBet = lblBet;
		this.lblCoins = lblcoins;
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());
		PositiveInteger i = new PositiveInteger(lblBet.getValue(), game.getCoins());

		switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				try {
					i.add(10);
				} catch (MaxReachedException e1) {
					// e1.printStackTrace();
				}
				break;
	
			case DOWN:
				try {
					i.substract(10);
				} catch (NegativeIntegerException e1) {
					// e1.printStackTrace();
				}
				break;
		}

		int value = i.getIntValue();

		lblBet.setValue(value);
		lblCoins.setText("" + (game.getCoins() - value));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up_click.png"));
				break;
	
			case DOWN:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down_click.png"));
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up_over.png"));
				break;
	
			case DOWN:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down_over.png"));
				break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up_over.png"));
				break;
	
			case DOWN:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down_over.png"));
				break;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up.png"));
				break;
	
			case DOWN:
				lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down.png"));
				break;
		}
	}

}
