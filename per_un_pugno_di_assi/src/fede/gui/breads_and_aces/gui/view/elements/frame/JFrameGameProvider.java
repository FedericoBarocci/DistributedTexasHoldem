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
//		System.out.println(this.getClass().getName()+":18");
//		MemoryUtil.runGarbageCollector();
		frame = new JFrameGame();
//		System.out.println(this.getClass().getName()+":21");
//		MemoryUtil.runGarbageCollector();
	}
	
	@Override
	public JFrameGame get() {
		return frame;
	}
}
