package breads_and_aces._di._guice.module;

import breads_and_aces.dummy.InputHandlerFactory;
import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersKeeperImpl;
import breads_and_aces.game.model.players.player.PlayerFactory;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.initializer.NodeInitializerFactory;
import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientableFactory;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServableFactory;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServableUsingShellInput;
import breads_and_aces.registration.initializers.servable.gui.AccepterPlayersGUIFactory;
import breads_and_aces.services.rmi.game.core.impl.GameServiceFactory;
import breads_and_aces.utils.printer.ConsolePrinter.ConsolePrinterReal;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TexasHoldemPokerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Printer.class).to(ConsolePrinterReal.class);
		
		bind(PlayersKeeper.class).to(PlayersKeeperImpl.class);
		
//		install(new FactoryModuleBuilder().build(MeFactory.class));
		
		install(new FactoryModuleBuilder().build(InputHandlerFactory.class));
		
		install(new FactoryModuleBuilder().build(NodeFactory.class));
		install(new FactoryModuleBuilder().build(NodeInitializerFactory.class));
		
		
//		bind(RegistrationInitializerServable.class).to(RegistrationInitializerServableUsingShellInput.class);
//		install(new FactoryModuleBuilder().build(RegistrationInitializerServableFactory.class));
		FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder().implement(RegistrationInitializerServable.class, RegistrationInitializerServableUsingShellInput.class);
		install(factoryModuleBuilder.build(RegistrationInitializerServableFactory.class));
		
		install(factoryModuleBuilder.build(AccepterPlayersGUIFactory.class));
		
		install(new FactoryModuleBuilder().build(RegistrationInitializerClientableFactory.class));
		
		install(new FactoryModuleBuilder().build(GameServiceFactory.class));
		
		install(new FactoryModuleBuilder().build(PlayerFactory.class));
	}

}
