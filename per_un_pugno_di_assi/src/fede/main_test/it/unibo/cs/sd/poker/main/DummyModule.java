package it.unibo.cs.sd.poker.main;

import it.unibo.cs.sd.poker.gui.view.GameViewInitializer;
import it.unibo.cs.sd.poker.gui.view.GameViewInitializerGUIDummy;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersKeeperImpl;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.utils.printer.Printer;
import breads_and_aces.utils.printer.ConsolePrinter.ConsolePrinterReal;

import com.google.inject.AbstractModule;

public class DummyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GamePlayersKeeper.class).to(PlayersKeeperImpl.class);
		bind(RegistrarPlayersKeeper.class).to(PlayersKeeperImpl.class);
		bind(Printer.class).to(ConsolePrinterReal.class);
		
		bind(GameViewInitializer.class).to(GameViewInitializerGUIDummy.class);
	}

}
