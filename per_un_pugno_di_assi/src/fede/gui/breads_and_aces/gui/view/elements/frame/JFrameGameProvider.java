package breads_and_aces.gui.view.elements.frame;

public enum JFrameGameProvider {

	INSTANCE;
	
	private final JFrameGame frame;
	
	private JFrameGameProvider() {
		frame = new JFrameGame();
	}
	
	public JFrameGame get() {
		return frame;
	}
}
