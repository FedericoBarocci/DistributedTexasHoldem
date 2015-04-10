package it.unibo.cs.sd.poker.main;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersKeeperImpl;

import com.google.inject.AbstractModule;

public class DummyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GamePlayersKeeper.class).to(PlayersKeeperImpl.class);
	}

}
