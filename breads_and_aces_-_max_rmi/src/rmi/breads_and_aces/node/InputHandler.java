package breads_and_aces.node;

import java.util.Scanner;

import javax.inject.Inject;

import breads_and_aces.game.BucketHandler;
import breads_and_aces.utils.misc.InputUtils;

public class InputHandler {
	
	private final static String END_GAME = "END";
	
	BucketHandler bucketHandler;

	@Inject
	public InputHandler(BucketHandler buckHandler) {
		this.bucketHandler = buckHandler;
	}


	public void exec() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while(!next.equals(END_GAME)) {
			next = scanner.next();
//			System.out.println("\t said: "+next);
			// do something with next
			bucketHandler.play(next);
		}
		scanner.close();
	}
}
