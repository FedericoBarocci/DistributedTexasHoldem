package breads_and_aces.gui.controllers.actionlisteners.init;

import javax.inject.Inject;

import breads_and_aces.gui.controllers.actionlisteners.BetButton;
import breads_and_aces.gui.controllers.actionlisteners.FoldButton;
import breads_and_aces.gui.controllers.actionlisteners.InfoButton;
import breads_and_aces.gui.controllers.actionlisteners.OkButton;
import breads_and_aces.gui.view.AbstractViewHandler;
import breads_and_aces.gui.view.InitableView;
import breads_and_aces.gui.view.elements.ElementGUI;
import breads_and_aces.gui.view.elements.frame.JFrameGame;
import breads_and_aces.gui.view.elements.utils.EnumButton;
import breads_and_aces.gui.view.elements.utils.EnumRectangle;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

public class ListenersInitializer extends AbstractViewHandler implements InitableView<Void> {

	private final OkButton okButton;
	private final FoldButton foldButton;
	private final BetButton betButton;

	@Inject
	public ListenersInitializer(JFrameGame/*Provider*/ jFrameGame/*Provider*/, OkButton okButton, FoldButton foldButton, BetButton betButton) {
		super(jFrameGame/*Provider*/);
		this.okButton = okButton;
		this.foldButton = foldButton;
		this.betButton = betButton;
	}
	
	@Override
	public void init(Void noArg) {
		ElementGUI up   = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("up.png"), 	GuiUtils.INSTANCE.getRectangle(EnumRectangle.up) 	);
		ElementGUI down = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("down.png"),	GuiUtils.INSTANCE.getRectangle(EnumRectangle.down) 	);
		ElementGUI ok   = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("ok.png"),	GuiUtils.INSTANCE.getRectangle(EnumRectangle.ok) 	);
		ElementGUI fold = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("fold.png"), 	GuiUtils.INSTANCE.getRectangle(EnumRectangle.fold) 	);
		ElementGUI info = 	new ElementGUI( GuiUtils.INSTANCE.getImageGui("info.png"),	GuiUtils.INSTANCE.getRectangle(EnumRectangle.info) 	);
		
		up.setName(EnumButton.UP.name());
		down.setName(EnumButton.DOWN.name());
		
		up.addMouseListener( betButton );
		down.addMouseListener( betButton );
		ok.addMouseListener( okButton );
		fold.addMouseListener( foldButton );
		info.addMouseListener( new InfoButton() );
		
		super.addElement(up);
		super.addElement(down);
		super.addElement(ok);
		super.addElement(fold);
		super.addElement(info);
	}
}
