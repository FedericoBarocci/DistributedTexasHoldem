package bread_and_aces._di._guice.module;

import org.limewire.inject.LimeWireInjectModule;

import com.google.inject.assistedinject.FactoryModuleBuilder;

import bread_and_aces._di.providers.node.inputhandler.InputHandlerProvider;
import bread_and_aces._di.providers.node.inputhandler.gui.InputHandlerGUIProvider;
import bread_and_aces._di.providers.registration.initializers.clientable.RegistrationInitializerClientableProvider;
import bread_and_aces._di.providers.registration.initializers.clientable._gui.RegistrationInitializerClientableGUIProvider;
import bread_and_aces._di.providers.registration.initializers.servable.RegistrationInitializerServableProvider;
import bread_and_aces._di.providers.registration.initializers.servable._gui.RegistrationInitializerServableProviderGUI;
import bread_and_aces.game.GameInitializer;
import bread_and_aces.game.GameInitializerReal;
import bread_and_aces.game.model.controller.DistributedController;
import bread_and_aces.game.model.controller.DistributedControllerForRemoteHandling;
import bread_and_aces.game.model.oracle.responses.OracleResponseFactory;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.keeper.PlayersKeeperImpl;
import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.gui.view.elements.PlayerGUIHandlerFactory;
import bread_and_aces.node.NodeFactory;
import bread_and_aces.node.initializer.NodeInitializerFactory;
import bread_and_aces.node.inputhandler.InputHandler;
import bread_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;
import bread_and_aces.registration.initializers.clientable._gui.RegistrationInitializerClientableGUIFactory;
import bread_and_aces.registration.initializers.servable.RegistrationInitializerServable;
import bread_and_aces.registration.initializers.servable._gui.AccepterPlayersGUIFactory;
import bread_and_aces.registration.initializers.servable._gui.RegistrationInitializerServableGUIFactory;
import bread_and_aces.services.rmi.game.core.impl.GameServiceFactory;
import bread_and_aces.utils.keepers.KeepersUtilDelegate;
import bread_and_aces.utils.keepers.KeepersUtilDelegateForClientable;
import bread_and_aces.utils.keepers.KeepersUtilDelegateForServable;

public class TexasHoldemPokerModule extends 
LimeWireInjectModule {
//AbstractModule {

	@Override
	protected void configure() {
		// from Limewire inject, in order to have LazySingleton
		super.configure();
		
//		bind(Printer.class).to(ConsolePrinterReal.class);
		
		bind(RegistrarPlayersKeeper.class).to(PlayersKeeperImpl.class);
		bind(GamePlayersKeeper.class).to(PlayersKeeperImpl.class);
		
//		install(new FactoryModuleBuilder().build(MeFactory.class));
		
		bind(KeepersUtilDelegateForServable.class).to(KeepersUtilDelegate.class);
		bind(KeepersUtilDelegateForClientable.class).to(KeepersUtilDelegate.class);
		
		install(new FactoryModuleBuilder().build(GameServiceFactory.class));
		
//		install(new FactoryModuleBuilder().build(GameViewInitializerInstancer.class));
		bind(GameInitializer.class).to(GameInitializerReal.class);
		
		
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
		
		install(new FactoryModuleBuilder().build(PlayerGUIHandlerFactory.class));
		
		install(new FactoryModuleBuilder().build(OracleResponseFactory.class));
		
		
		bind(DistributedControllerForRemoteHandling.class).to(DistributedController.class);
	}

}
