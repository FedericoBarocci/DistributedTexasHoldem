package bread_and_aces.gui.view.elements.frame;


import org.limewire.inject.LazySingleton;

import bread_and_aces.gui.view.elements.BackgroundGUI;
import bread_and_aces.gui.view.elements.utils.EnumRectangle;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

@LazySingleton
//@Singleton
public class JFrameGame extends JFrameDefault {

	private static final long serialVersionUID = 795031168834302954L;

	public JFrameGame() {
		super();
	}
	
	public void init() {
		super.init();
		
		setTitle("Poker Distributed Hold'em");
		
		BackgroundGUI bg = GuiUtils.INSTANCE.background;
		bg.setLayout(null);
		bg.setPreferredSize(GuiUtils.INSTANCE.getRectangle(EnumRectangle.frame).getSize());
		bg.setBounds(GuiUtils.INSTANCE.getRectangle(EnumRectangle.frame));
		
		setContentPane(bg);
		pack();
	}
}
