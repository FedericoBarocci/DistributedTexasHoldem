package breads_and_aces.main;

import breads_and_aces._di._guice.module.TexasHoldemPokerModule;
import breads_and_aces.node.Node;
import breads_and_aces.node.initializer.NodeInitializer;
import breads_and_aces.node.initializer.NodeInitializerFactory;
import breads_and_aces.utils.misc.MemoryUtil;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class MainShell {
	
	public static void main(String[] args) {
		if (args.length<1) {
			System.out.println("minimum 1 arguments: \n"
					+"first argument must be an inet address to bind on - if one and only one argument is provided, this host will act as session initializer\n\n"
					+"in order to connect to a initializer host, you must specify address and host, so:\n"
					+"first argument will be local address to bind on, second argument will be remote host address, third argument will be remote host port");
			System.exit(1);
		}

		boolean actAsServer = false;
		String addressToBind = args[0];
		
		final String meId = "NODE-"+addressToBind;
		
		if (args.length==1) {
			actAsServer = true;
		}
		
		/*CountDownLatch registrationLatch = new CountDownLatch(1);
		AtomicReference<LoginResult> loginResultAtomicReference = new AtomicReference<>();
		AtomicReference<StartOrRegisterGUI> rgReference = new AtomicReference<>();
		
		EventQueue.invokeLater( ()->{ 
			StartOrRegisterGUI rg = new StartOrRegisterGUI(registrationLatch, loginResultAtomicReference);
			rgReference.set(rg);
			});*/
		
		try {
		/*	registrationLatch.await();
			rgReference.get().dispose();
			LoginResult loginResult = loginResultAtomicReference.get();*/
		
			Injector injector = Guice.createInjector(new TexasHoldemPokerModule());
		
			NodeInitializerFactory nodeInitializerFactory = injector.getInstance(NodeInitializerFactory.class);
			NodeInitializer nodeInitializer = null;
			
//			String meId = loginResult.username;
			
			if (actAsServer) {
				nodeInitializer  = nodeInitializerFactory.createAsServable(meId, addressToBind, null);
			} else {
				String initializingHostAddress = 
						args[1];
//						loginResult.serverHost;
				if (args.length==3) {
					int initializingHostPort = Integer.parseInt(args[2]);
					nodeInitializer  = nodeInitializerFactory.createAsClientableWithInitializerPort(meId, addressToBind, initializingHostAddress, initializingHostPort, null);
				} else
					nodeInitializer  = nodeInitializerFactory.createAsClientable(meId, addressToBind, initializingHostAddress);
			}
			
			Node node = nodeInitializer.get();
			
			MemoryUtil.runGarbageCollector();
			nodeInitializer = null;
//			loginResult = null;
//			registrationLatch = null;
//			rgReference = null;
			MemoryUtil.runGarbageCollector();
			
			node.start();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.exit(0);
	}
	
	static public class LoginResult {

		String serverHost;
		String serverPort;
		boolean asServable;
		String username;

		public LoginResult(String serverHost, String serverPort, boolean asServable, String username) {
			this.serverHost = serverHost;
			this.serverPort = serverPort;
			this.asServable = asServable;
			this.username = username;
		}
		
	}
	
	
	
}
