package bread_and_aces.gui.controllers.actionlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.gui.view.ButtonsViewHandler;
import bread_and_aces.gui.view.LabelHandler;
import bread_and_aces.gui.view.elements.ElementGUI;
import bread_and_aces.gui.view.elements.utils.EnumButton;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

public class BetListener implements MouseListener {
	private final BetManager betManager;
	private final ButtonsViewHandler buttonsView;
	private final LabelHandler labelHandler;
	
	@Inject
	public BetListener(BetManager betManager, ButtonsViewHandler buttonsView, LabelHandler labelHandler) {
		this.betManager = betManager;
		this.buttonsView = buttonsView;
		this.labelHandler = labelHandler;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ElementGUI lbl = (ElementGUI) (e.getSource());

		if (lbl.isEnable()) {
			int value = 0;

			switch (EnumButton.valueOf(lbl.getName())) {
			case UP:
				value = betManager.bet(10);
				labelHandler.setBetValue(value, -10);
				break;

			case DOWN:
				value = betManager.unbet(10);
				labelHandler.setBetValue(value, +10);
				break;
			}
			
			betManager.setBet(value);
			
			buttonsView.updateText(betManager.getMessage().getAction());
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
