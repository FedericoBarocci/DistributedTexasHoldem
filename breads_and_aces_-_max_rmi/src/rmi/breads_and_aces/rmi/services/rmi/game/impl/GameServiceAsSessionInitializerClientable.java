package breads_and_aces.rmi.services.rmi.game.impl;

import java.rmi.RemoteException;
import java.util.Map;

import javax.inject.Inject;

import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.game.model.Players;
import breads_and_aces.rmi.services.rmi.game.AbstractGameService;
import breads_and_aces.rmi.services.rmi.game.GameServiceClientable;

public class GameServiceAsSessionInitializerClientable 
	extends AbstractGameService 
	implements GameServiceClientable {

	private static final long serialVersionUID = 5332389646575258965L;
	
	@Inject
	public GameServiceAsSessionInitializerClientable(Game game, Players players) throws RemoteException {
		super(game, players);
	}

	@Override
	public void synchronizeAllPlayersFromInitiliazer(Map<String, Player> nodeConnectionInfos) throws RemoteException {
//		System.out.println("AH!");
		players.setPlayers(nodeConnectionInfos);
		
		// TODO too bad here, but it works
		game.setStarted();
	}
}
