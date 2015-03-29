package breads_and_aces.node;

import java.util.Scanner;

import javax.inject.Inject;

import breads_and_aces.game.BuckHandler;
import breads_and_aces.utils.misc.InputUtils;

public class InputHandler {
	
	private final static String END_GAME = "END";
	
	BuckHandler buckHandler;

	@Inject
	public InputHandler(BuckHandler buckHandler) {
		this.buckHandler = buckHandler;
	}


	public void exec() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while(!next.equals(END_GAME)) {
			next = scanner.next();
			System.out.println(next);
			// do something with next
			// TODO restore
			//			sendBroadcast(next);
			//			passToken();
			buckHandler.play(next);
		}
		scanner.close();
	}
}
