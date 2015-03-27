package breads_and_aces.main;

import breads_and_aces._di._guice.module.TexasHoldemPokerModule;
import breads_and_aces.node.Node;
import breads_and_aces.node.builder.NodeBuilder;
import breads_and_aces.node.builder.NodeBuilderFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
//	public static Random Randomizer = new Random();

	public static void main(String[] args) {
		if (args.length<1) {
			System.out.println("minimum 1 arguments: \n"
					+"first argument must be an inet address to bind on - if one and only one argument is provided, this host will act as session initializer\n\n"
					+"in order to connect to a initializer host, you must specify address and host, so:\n"
					+"first argument will be local address to bind on, second argument will be remote host address, third argument will be remote host port");
			System.exit(1);
		}
			
		String addressToBind = args[0];
		String me = "NODE-"+addressToBind;
		
		boolean actAsServer = false;
		
		if (args.length==1) {
			actAsServer = true;
		}
		
		Injector injector = Guice.createInjector(new TexasHoldemPokerModule());
		
		NodeBuilderFactory nodeBuilderFactory = injector.getInstance(NodeBuilderFactory.class);
		NodeBuilder nodeBuilder = null;
		if (actAsServer) {
			nodeBuilder = nodeBuilderFactory.createAsServable(me, addressToBind);
		} else {
			String initializingHostAddress = args[1];
			int initializingHostPort = 33333;
			if (args.length==3)
				initializingHostPort = Integer.parseInt(args[2]);
			nodeBuilder = nodeBuilderFactory.createAsClientable(me, addressToBind, initializingHostAddress, initializingHostPort);
		}
		Node node = nodeBuilder.build();
		node.start();
		
		System.exit(0);
	}
	
}
