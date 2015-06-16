package breads_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import breads_and_aces.game.Game;
import breads_and_aces.game.core.BetManager;
import breads_and_aces.gui.labels.LabelBet;
import breads_and_aces.gui.labels.LabelCoins;
import breads_and_aces.gui.view.ButtonsViewHandler;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.utils.EnumButton;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class BetListener implements MouseListener {
	private final LabelBet lblBet;
	private final LabelCoins lblCoins;
	private final Game game;
	private final BetManager betManager;
	private final ButtonsViewHandler buttonsView;
	
	@Inject
	public BetListener(LabelBet lblBet, LabelCoins lblcoins, Game game, BetManager betManager, ButtonsViewHandler buttonsView) {
		this.lblBet = lblBet;
		this.lblCoins = lblcoins;
		this.game = game;
		this.betManager = betManager;
		this.buttonsView = buttonsView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		if (lbl.isEnable()) {
			int value = 0;

			switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				value = betManager.bet(10);
				lblCoins.setValue(lblCoins.getValue() -10);
				break;

			case DOWN:
				value = betManager.unbet(10);
				lblCoins.setValue(lblCoins.getValue() +10);
				break;
			}
			
			lblBet.setValue(value);
			betManager.setBet(value);
			
			buttonsView.updateText(betManager.getActionKeeper().getAction());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());
		
		if (lbl.isEnable()) {
			switch (EnumButton.valueOf(lbl.getName())) {
				case UP:
					lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up_click.png"));
					break;
				case DOWN:
					lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down_click.png"));
					break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		if (lbl.isEnable()) {
			switch (EnumButton.valueOf(lbl.getName())) {
				case UP:
					lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up_over.png"));
					break;
		
				case DOWN:
					lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down_over.png"));
					break;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		if (lbl.isEnable()) {
			switch (EnumButton.valueOf(lbl.getName())) {
				case UP:
					lbl.changeImage(GuiUtils.INSTANCE.getImageGui("up_over.png"));
					break;
		
				case DOWN:
					lbl.changeImage(GuiUtils.INSTANCE.getImageGui("down_over.png"));
					break;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		if (lbl.isEnable()) {
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

}
