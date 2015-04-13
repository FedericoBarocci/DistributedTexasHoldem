package breads_and_aces.node.inputhandler.shell;

import java.util.Scanner;

import breads_and_aces.game.bucket.BucketHandler;
import breads_and_aces.node.inputhandler.InputHandler;
import breads_and_aces.utils.misc.InputUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class InputHandlerShell implements InputHandler {
	
	private final static String END_GAME = "END";
	private final BucketHandler bucketHandler;
	private final String meId;

	@AssistedInject
	public InputHandlerShell(@Assisted String meId, BucketHandler bucketHandler) {
		this.meId = meId;
		this.bucketHandler = bucketHandler;
	}

	public void exec() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while(!next.equals(END_GAME)) {
			next = scanner.next();
			bucketHandler.play(meId, next);
		}
		scanner.close();
	}
}
