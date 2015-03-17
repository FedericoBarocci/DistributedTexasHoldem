package breads_and_aces._di._guice.module;

import breads_and_aces.node.NodeFactory;
import breads_and_aces.node.builder.NodeBuilderFactory;
import breads_and_aces.rmi.game.init.clientable.GameInitializerClientableFactory;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TexasHoldemPokerModule extends AbstractModule {

	@Override
	protected void configure() {
		/*install(
				new FactoryModuleBuilder()
				.implement(NodeBuilder.class, Names.named("Servable"), NodeBuilderServable.class)
//				.implement(NodeBuilder.class, Names.named("Clientable"), NodeBuilderClientable.class )
				.build(NodeBuilderServableFactory.class)
		);*/
		install(new FactoryModuleBuilder().build(NodeFactory.class));
		install(new FactoryModuleBuilder().build(NodeBuilderFactory.class));
		
		
		install(new FactoryModuleBuilder().build(GameInitializerClientableFactory.class));
	}

}
