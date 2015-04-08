package breads_and_aces.dummy;

import java.awt.EventQueue;

import javax.inject.Inject;

import breads_and_aces.game.bucket.BucketHandler;

public class InputHandler {
	
	// TO SYSTEM
	private final static String END_GAME = "END";
	
	BucketHandler bucketHandler;

//	private final String meId;

	private GameViewInitializerInstancer gameViewInitializerInstancer;

	@Inject
	public InputHandler(/*@Assisted String meId,*/ BucketHandler bucketHandler, GameViewInitializerInstancer gameViewInitializerInstancer) {
//		this.meId = meId;
		this.bucketHandler = bucketHandler;
		this.gameViewInitializerInstancer = gameViewInitializerInstancer;
	}


	public void exec() {
		/*Scanner scanner = InputUtils.getScanner();
		String next = "";
		while(!next.equals(END_GAME)) {
			next = scanner.next();
//			System.out.println("\t said: "+next);
			// do something with next
			bucketHandler.play(meId, next);
		}
		scanner.close();*/
		System.out.println("here");
//		EventQueue.invokeLater( ()->{
			gameViewInitializerInstancer.create().start();
//		});
		
	}
}
