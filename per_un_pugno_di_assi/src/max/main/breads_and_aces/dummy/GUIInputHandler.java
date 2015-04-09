package breads_and_aces.dummy;

import javax.inject.Inject;

import breads_and_aces.node.dummy.InputHandler;

public class GUIInputHandler implements InputHandler {
	
	// TO SYSTEM
	
//	private final BucketHandler bucketHandler;
	private GameViewInitializerInstancer gameViewInitializerInstancer;

	@Inject
	public GUIInputHandler(/*@Assisted String meId,*/ /*BucketHandler bucketHandler,*/ GameViewInitializerInstancer gameViewInitializerInstancer) {
//		this.bucketHandler = bucketHandler;
		this.gameViewInitializerInstancer = gameViewInitializerInstancer;
	}

	public void exec() {
		gameViewInitializerInstancer.create().start();
	}
}
