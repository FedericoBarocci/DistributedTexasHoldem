package breads_and_aces.node.inputhandler.gui;

import javax.inject.Inject;

import breads_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	// TO SYSTEM
	
//	private final BucketHandler bucketHandler;
	private GameViewInitializerInstancer gameViewInitializerInstancer;

	@Inject
	public InputHandlerGUI(/*@Assisted String meId,*/ /*BucketHandler bucketHandler,*/ GameViewInitializerInstancer gameViewInitializerInstancer) {
//		this.bucketHandler = bucketHandler;
		this.gameViewInitializerInstancer = gameViewInitializerInstancer;
	}

	public void exec() {
		gameViewInitializerInstancer.create().start();
	}
}
