package breads_and_aces._di._guice.module;

import breads_and_aces.dummy.GameViewInitializerInstancer;
import breads_and_aces.dummy.ShellInputHandler;
import breads_and_aces.dummy.ShellInputHandlerFactory;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersKeeperImpl;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.dummy.InputHandler;
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
		
		bind(RegistrarPlayersKeeper.class).to(PlayersKeeperImpl.class);
		bind(GamePlayersKeeper.class).to(PlayersKeeperImpl.class);
		
//		install(new FactoryModuleBuilder().build(MeFactory.class));
		
		FactoryModuleBuilder inputHandlerFactoryModuleBuilder = new FactoryModuleBuilder().implement(InputHandler.class, ShellInputHandler.class);
		install(inputHandlerFactoryModuleBuilder.build(ShellInputHandlerFactory.class));
		
		install(new FactoryModuleBuilder().build(NodeFactory.class));
		install(new FactoryModuleBuilder().build(NodeInitializerFactory.class));
		
		
//		bind(RegistrationInitializerServable.class).to(RegistrationInitializerServableUsingShellInput.class);
//		install(new FactoryModuleBuilder().build(RegistrationInitializerServableFactory.class));
		FactoryModuleBuilder registrationInitializerServableFactoryModuleBuilder = new FactoryModuleBuilder()
			.implement(RegistrationInitializerServable.class, RegistrationInitializerServableUsingShellInput.class);
		install(registrationInitializerServableFactoryModuleBuilder.build(RegistrationInitializerServableFactory.class));
		
//		bind(RegistrationInitializerServable.class).to
		
		
//		install(registrationInitializerServableFactoryModuleBuilder.build(AccepterPlayersGUIFactory.class));
		install(new FactoryModuleBuilder().build(AccepterPlayersGUIFactory.class));
		
		
		install(new FactoryModuleBuilder().build(RegistrationInitializerClientableFactory.class));
		
		install(new FactoryModuleBuilder().build(GameServiceFactory.class));
		
//		install(new FactoryModuleBuilder().build(PlayerFactory.class));
		
		install(new FactoryModuleBuilder().build(GameViewInitializerInstancer.class));
	}

}
