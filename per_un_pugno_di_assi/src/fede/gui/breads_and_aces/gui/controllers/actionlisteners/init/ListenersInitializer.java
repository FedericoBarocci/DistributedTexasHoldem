package breads_and_aces.gui.controllers.actionlisteners.init;

import javax.inject.Inject;

import breads_and_aces.gui.controllers.actionlisteners.BetButton;
import breads_and_aces.gui.controllers.actionlisteners.FoldButton;
import breads_and_aces.gui.controllers.actionlisteners.InfoButton;
import breads_and_aces.gui.controllers.actionlisteners.OkButton;
import breads_and_aces.gui.view.AbstractViewHandler;
import breads_and_aces.gui.view.ButtonsViewHandler;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.frame.JFrameGame;
import breads_and_aces.gui.view.elements.utils.EnumButton;

public class ListenersInitializer extends AbstractViewHandler<Void> {

	private final ButtonsViewHandler buttonsViewHandler;
	private final OkButton okButton;
	private final FoldButton foldButton;
	private final BetButton betButton;

	@Inject
	public ListenersInitializer(JFrameGame/*Provider*/ jFrameGame/*Provider*/, ButtonsViewHandler buttonsViewHandler, OkButton okButton, FoldButton foldButton, BetButton betButton) {
		super(jFrameGame/*Provider*/);
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
		info.addMouseListener( new InfoButton() );
	}
}
