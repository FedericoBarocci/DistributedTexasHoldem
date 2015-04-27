package breads_and_aces.game.bucket;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.services.rmi.utils.communicator.Communicator.NextHolder;

@Singleton
public class BucketHandler {

	private final GamePlayersKeeper playersKeeper;
	private final Communicator communicator;
	private String currentMyId;
	
//	private String tmpDummyString;
	
	@Inject
	public BucketHandler(GamePlayersKeeper playersKeeper, /*Me me,*/ Communicator communicator) {
		this.playersKeeper = playersKeeper;
//		this.meId = me.getId();
		this.communicator = communicator;
	}
	
	public void passBucket(String meId) {
//		String next = "";
//		do {
//			next = playersKeeper.getNext(meId).getName();
//		} while (communicator.toNext( this::passBucket, next ));
//		NextHolder n = communicator.toNext( meId, this::passBucket, "" );
		NextHolder n = communicator.toNext( meId, this::passBucketWithNoArg, null );
	}
	private void passBucket(GameService gameServiceExternalInjected, String dummyArg) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
		//communicator.toAll(this.currentMyId, this::sayToAllIHaveToken);
	}
	
	private void passBucketWithNoArg(GameService gameServiceExternalInjected, Void noArg) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
	}
	
	
	public void play(String meId, String dummyString) {
		this.currentMyId = meId;
		Player playerMe = playersKeeper.getPlayer(currentMyId);
		if (playerMe.hasToken()) {
			System.out.println("\t said: "+dummyString);
//			communicator.toAll(Comparator::comparingDouble, dummyString, null);
//			this.tmpDummyString = dummyString;
			communicator.toAll(meId, this::sayToAll, dummyString);
			Player next = playersKeeper.getNext(currentMyId);
			NextHolder n = communicator.toNext(meId, this::passBucket, next.getName());
			playerMe.sendToken();
		}
	}
	
	private void sayToAll(GameService gameServiceExternalInjected, String dummyString) {
		try {
			gameServiceExternalInjected.echo(currentMyId, dummyString);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/*
	private void sayToAllIHaveToken(GameService gameServiceExternalInjected) {
		try {
			gameServiceExternalInjected.sayIHaveToken(this.currentMyId, currentMyId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}*/
}
