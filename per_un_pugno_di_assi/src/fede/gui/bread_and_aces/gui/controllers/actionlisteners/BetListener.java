package bread_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import bread_and_aces.game.Game;
import bread_and_aces.gui.labels.LabelBet;
import bread_and_aces.gui.labels.LabelCoins;
import bread_and_aces.gui.view.ButtonsViewHandler;
import bread_and_aces.gui.view.elements.ElementGUI;
import bread_and_aces.gui.view.elements.utils.EnumButton;
import bread_and_aces.gui.view.elements.utils.GuiUtils;
import breads_and_aces.game.core.BetManager;

public class BetListener implements MouseListener {
	private final LabelBet lblBet;
	private final LabelCoins lblCoins;
	private final Game game;
	private final BetManager potManager;
	private final ButtonsViewHandler buttonsView;
	
	@Inject
	public BetListener(LabelBet lblBet, LabelCoins lblcoins, Game game, BetManager potManager, ButtonsViewHandler buttonsView) {
		this.lblBet = lblBet;
		this.lblCoins = lblcoins;
		this.game = game;
		this.potManager = potManager;
		this.buttonsView = buttonsView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		if (lbl.isEnable()) {
			int value = 0;

			switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				value = potManager.bet(10);
				break;

			case DOWN:
				value = potManager.unbet(10);
				break;
			}
			
			lblBet.setValue(value);
			lblCoins.setText("" + (game.getCoins() - value));
			potManager.setBet(value);
			
			buttonsView.updateText(potManager.getActionKeeper().getAction());
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
