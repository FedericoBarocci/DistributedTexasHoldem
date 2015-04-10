package breads_and_aces._di._guice.module;

import breads_and_aces._di.providers.InputHandlerGUIProvider;
import breads_and_aces._di.providers.InputHandlerProvider;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersKeeperImpl;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.initializer.NodeInitializerFactory;
import breads_and_aces.node.inputhandler.InputHandler;
import breads_and_aces.node.inputhandler.gui.GameViewInitializerInstancer;
import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientableFactory;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import breads_and_aces.registration.initializers.servable._gui.AccepterPlayersGUIFactory;
import breads_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUIFactory;
import breads_and_aces.registration.initializers.servable._shell.RegistrationInitializerServableUsingShellInput;
import breads_and_aces.services.rmi.game.core.impl.GameServiceFactory;
import breads_and_aces.utils.printer.ConsolePrinter.ConsolePrinterReal;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TexasHoldemPokerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Printer.class).to(ConsolePrinterReal.class);
		
		bind(RegistrarPlayersKeeper.class).to(PlayersKeeperImpl.class);
		bind(GamePlayersKeeper.class).to(PlayersKeeperImpl.class);
		
//		install(new FactoryModuleBuilder().build(MeFactory.class));
		
		bind(InputHandler.class).toProvider(InputHandlerProvider.class);
		bind(InputHandlerProvider.class).to(InputHandlerGUIProvider.class);
//		bind(InputHandlerProvider.class).to(InputHandlerShellProvider.class);
	
		
//		FactoryModuleBuilder inputHandlerFactoryModuleBuilder = new FactoryModuleBuilder().implement(InputHandler.class, InputHandlerShell.class);
//		install(inputHandlerFactoryModuleBuilder.build(InputHandlerShellFactory.class));
		
		install(new FactoryModuleBuilder().build(NodeFactory.class));
		install(new FactoryModuleBuilder().build(NodeInitializerFactory.class));
		
		
//		bind(RegistrationInitializerServable.class).to(RegistrationInitializerServableUsingShellInput.class);
//		install(new FactoryModuleBuilder().build(RegistrationInitializerServableFactory.class));
		
		install( new FactoryModuleBuilder()
			.implement(RegistrationInitializerServable.class, RegistrationInitializerServableUsingShellInput.class)
			.build(RegistrationInitializerServableGUIFactory.class)
		);
		
		install(new FactoryModuleBuilder().build(RegistrationInitializerClientableFactory.class));
		
		
		install(new FactoryModuleBuilder().build(AccepterPlayersGUIFactory.class));
//		bind(RegistrationInitializerServable.class).to
		
		
//		install(registrationInitializerServableFactoryModuleBuilder.build(AccepterPlayersGUIFactory.class));
		
		
		install(new FactoryModuleBuilder().build(GameServiceFactory.class));
		
		install(new FactoryModuleBuilder().build(GameViewInitializerInstancer.class));
//		install(new FactoryModuleBuilder().build(PlayerFactory.class));
		
		
	}

}
