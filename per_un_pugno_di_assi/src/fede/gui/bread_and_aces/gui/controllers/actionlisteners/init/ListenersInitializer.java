package bread_and_aces.gui.controllers.actionlisteners.init;

import javax.inject.Inject;

import bread_and_aces.gui.controllers.actionlisteners.BetListener;
import bread_and_aces.gui.controllers.actionlisteners.FoldListener;
import bread_and_aces.gui.controllers.actionlisteners.InfoListener;
import bread_and_aces.gui.controllers.actionlisteners.OkListener;
import bread_and_aces.gui.view.AbstractViewHandler;
import bread_and_aces.gui.view.ButtonsViewHandler;
import bread_and_aces.gui.view.elements.ElementGUI;
import bread_and_aces.gui.view.elements.frame.JFrameGame;
import bread_and_aces.gui.view.elements.utils.EnumButton;

public class ListenersInitializer extends AbstractViewHandler<Void> {

	private final ButtonsViewHandler buttonsViewHandler;
	private final OkListener okButton;
	private final FoldListener foldButton;
	private final BetListener betButton;

	@Inject
	public ListenersInitializer(JFrameGame jFrameGame, ButtonsViewHandler buttonsViewHandler, OkListener okButton, FoldListener foldButton, BetListener betButton) {
		super(jFrameGame);
		
		this.buttonsViewHandler = buttonsViewHandler;
		this.okButton = okButton;
		this.foldButton = foldButton;
		this.betButton = betButton;
	}
	
	@Override
	public void init(Void noArg) {
		ElementGUI up   = 	buttonsViewHandler.getUp();
		ElementGUI down = 	buttonsViewHandler.getDown();
		ElementGUI ok   = 	buttonsViewHandler.getOk();
		ElementGUI fold = 	buttonsViewHandler.getFold();
		ElementGUI info = 	buttonsViewHandler.getInfo();
		
		up.setName(EnumButton.UP.name());
		down.setName(EnumButton.DOWN.name());
		
		up.addMouseListener( betButton );
		down.addMouseListener( betButton );
		ok.addMouseListener( okButton );
		fold.addMouseListener( foldButton );
		info.addMouseListener( new InfoListener() );
	}
}
