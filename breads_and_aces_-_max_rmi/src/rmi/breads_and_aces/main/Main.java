package breads_and_aces.main;

import breads_and_aces._di._guice.module.TexasHoldemPokerModule;
import breads_and_aces.node.Node;
import breads_and_aces.node.initializer.NodeInitializer;
import breads_and_aces.node.initializer.NodeInitializerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
	public static void main(String[] args) {
		if (args.length<1) {
			System.out.println("minimum 1 arguments: \n"
					+"first argument must be an inet address to bind on - if one and only one argument is provided, this host will act as session initializer\n\n"
					+"in order to connect to a initializer host, you must specify address and host, so:\n"
					+"first argument will be local address to bind on, second argument will be remote host address, third argument will be remote host port");
			System.exit(1);
		}
			
		String addressToBind = args[0];
		String meId = "NODE-"+addressToBind;
		
		boolean actAsServer = false;
		
		if (args.length==1) {
			actAsServer = true;
		}
		
		Injector injector = null;
		try {
			injector = Guice.createInjector(new TexasHoldemPokerModule());
		
			NodeInitializerFactory nodeInitializerFactory = injector.getInstance(NodeInitializerFactory.class);
			NodeInitializer nodeInitializer = null;
			if (actAsServer) {
				nodeInitializer  = nodeInitializerFactory.createAsServable(meId, addressToBind);
			} else {
				String initializingHostAddress = args[1];
				if (args.length==3) {
					int initializingHostPort = Integer.parseInt(args[2]);
					nodeInitializer  = nodeInitializerFactory.createAsClientableWithInitializerPort(meId, addressToBind, initializingHostAddress, initializingHostPort);
				} else
					nodeInitializer  = nodeInitializerFactory.createAsClientable(meId, addressToBind, initializingHostAddress);
			}
			
			Node node = nodeInitializer.get();
//			MemoryUtil.runGarbageCollector();
//			nodeInitializer = null;
//			MemoryUtil.runGarbageCollector();
			node.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
}
