package breads_and_aces.dummy;

import java.util.Scanner;

import breads_and_aces.game.bucket.BucketHandler;
import breads_and_aces.utils.misc.InputUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class InputHandler {
	
	private final static String END_GAME = "END";
	
	BucketHandler bucketHandler;

	private final String meId;

	@AssistedInject
	public InputHandler(@Assisted String meId, BucketHandler bucketHandler) {
		this.meId = meId;
		this.bucketHandler = bucketHandler;
	}


	public void exec() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while(!next.equals(END_GAME)) {
			next = scanner.next();
//			System.out.println("\t said: "+next);
			// do something with next
			bucketHandler.play(meId, next);
		}
		scanner.close();
	}
}
