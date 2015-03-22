package breads_and_aces._di._guice.module;

import breads_and_aces.game.init.clientable.GameInitializerClientableFactory;
import breads_and_aces.game.init.servable.GameInitializerServable;
import breads_and_aces.game.init.servable.GameInitializerServableUsingShellInput;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.game.registry.PlayersRegistryImpl;
import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.builder.NodeBuilderFactory;
import breads_and_aces.services.rmi.game.impl.GameServiceFactory;
import breads_and_aces.utils.printer.ConsolePrinter.ConsolePrinterReal;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TexasHoldemPokerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Printer.class).to(ConsolePrinterReal.class);
		
		
		bind(PlayersRegistry.class).to(PlayersRegistryImpl.class);
		
		install(new FactoryModuleBuilder().build(NodeFactory.class));
		install(new FactoryModuleBuilder().build(NodeBuilderFactory.class));
		
		bind(GameInitializerServable.class).to(GameInitializerServableUsingShellInput.class);
		
		install(new FactoryModuleBuilder().build(GameInitializerClientableFactory.class));
		
		install(new FactoryModuleBuilder().build(GameServiceFactory.class));
	}

}
