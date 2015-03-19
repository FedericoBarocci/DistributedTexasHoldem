package breads_and_aces.rmi.services.rmi.game.impl;

import java.rmi.RemoteException;

import javax.inject.Inject;

import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.game.model.Players;
import breads_and_aces.rmi.services.rmi.game.AbstractGameService;
import breads_and_aces.rmi.services.rmi.game.GameServiceServable;

public class GameServiceAsSessionInitializerServable 
	extends AbstractGameService 
	implements GameServiceServable {
	
	@Inject
	public GameServiceAsSessionInitializerServable(Game game, Players players) throws RemoteException {
		super(game, players);
	}

	@Override
	public boolean registerPlayer(Player nodeConnectionInfo) throws RemoteException {
		players.addPlayer(nodeConnectionInfo);
		return true;
	}
	
	private static final long serialVersionUID = 4075894245372521497L;
}
