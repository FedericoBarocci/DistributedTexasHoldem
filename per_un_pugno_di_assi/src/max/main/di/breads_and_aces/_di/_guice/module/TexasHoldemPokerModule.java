package breads_and_aces._di._guice.module;

import it.unibo.cs.sd.poker.gui.view.GameViewInitializer;
import it.unibo.cs.sd.poker.gui.view.GameViewInitializerReal;
import breads_and_aces._di.providers.node.inputhandler.InputHandlerProvider;
import breads_and_aces._di.providers.node.inputhandler.gui.InputHandlerGUIProvider;
import breads_and_aces._di.providers.registration.initializers.clientable.RegistrationInitializerClientableProvider;
import breads_and_aces._di.providers.registration.initializers.clientable._gui.RegistrationInitializerClientableGUIProvider;
import breads_and_aces._di.providers.registration.initializers.servable.RegistrationInitializerServableProvider;
import breads_and_aces._di.providers.registration.initializers.servable._gui.RegistrationInitializerServableProviderGUI;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersKeeperImpl;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.initializer.NodeInitializerFactory;
import breads_and_aces.node.inputhandler.InputHandler;
import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;
import breads_and_aces.registration.initializers.clientable._gui.RegistrationInitializerClientableGUIFactory;
import breads_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import breads_and_aces.registration.initializers.servable._gui.AccepterPlayersGUIFactory;
import breads_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUIFactory;
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
		
		install(new FactoryModuleBuilder().build(GameServiceFactory.class));
		
//		install(new FactoryModuleBuilder().build(GameViewInitializerInstancer.class));
		bind(GameViewInitializer.class).to(GameViewInitializerReal.class);
		
		
		bind(InputHandler.class).toProvider(InputHandlerProvider.class);
		// gui
		bind(InputHandlerProvider.class).to(InputHandlerGUIProvider.class);
		// shell
//		bind(InputHandlerProvider.class).to(InputHandlerShellProvider.class);
//		install(new FactoryModuleBuilder().build(InputHandlerShellFactory.class));
	
		
//		FactoryModuleBuilder inputHandlerFactoryModuleBuilder = new FactoryModuleBuilder().implement(InputHandler.class, InputHandlerShell.class);
//		install(inputHandlerFactoryModuleBuilder.build(InputHandlerShellFactory.class));
		
		install(new FactoryModuleBuilder().build(NodeFactory.class));
		install(new FactoryModuleBuilder().build(NodeInitializerFactory.class));
		
		
		/*install( new FactoryModuleBuilder()
			.implement(RegistrationInitializerServable.class, RegistrationInitializerServableShell.class)
			.build(RegistrationInitializerServableGUIFactory.class)
		);*/
		bind(RegistrationInitializerServable.class).toProvider(RegistrationInitializerServableProvider.class);
		// gui
		bind(RegistrationInitializerServableProvider.class).to(RegistrationInitializerServableProviderGUI.class);
		install(new FactoryModuleBuilder().build(RegistrationInitializerServableGUIFactory.class));
		install(new FactoryModuleBuilder().build(AccepterPlayersGUIFactory.class));
		// shell
//		bind(RegistrationInitializerServableProvider.class).to(RegistrationInitializerServableProviderShell.class);
//		install(new FactoryModuleBuilder().build(RegistrationInitializerClientableShellFactory.class));
		
		bind(RegistrationInitializerClientable.class).toProvider(RegistrationInitializerClientableProvider.class);
		// gui
		bind(RegistrationInitializerClientableProvider.class).to(RegistrationInitializerClientableGUIProvider.class);
		install(new FactoryModuleBuilder().build(RegistrationInitializerClientableGUIFactory.class));
		// shell
//		bind(RegistrationInitializerClientableProvider.class).to(RegistrationInitializerClientableShellProvider.class);
//		install(new FactoryModuleBuilder().build(RegistrationInitializerClientableShellFactory.class));
		
	}

}
