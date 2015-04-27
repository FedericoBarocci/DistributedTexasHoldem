package breads_and_aces.gui.view.elements.frame;

import javax.inject.Provider;
import javax.inject.Singleton;

//public enum JFrameGameProvider {
@Singleton
public class JFrameGameProvider implements Provider<JFrameGame> {

//	INSTANCE;
	
	private final JFrameGame frame;
	
//	private JFrameGameProvider() {
	public JFrameGameProvider() {
		frame = new JFrameGame();
	}
	
	@Override
	public JFrameGame get() {
		return frame;
	}
}
