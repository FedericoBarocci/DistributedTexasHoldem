package breads_and_aces.main;

import java.awt.EventQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import breads_and_aces._di._guice.module.TexasHoldemPokerModule;
import breads_and_aces.main.start.gui.StartOrRegisterGUI;
import breads_and_aces.node.Node;
import breads_and_aces.node.initializer.NodeInitializer;
import breads_and_aces.node.initializer.NodeInitializerFactory;
import breads_and_aces.utils.misc.MemoryUtil;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
	public static Injector Injector;
	public static String nodeid;

	public static void main(String[] args) {
		if (args.length<1) {
			System.out.println("minimum 1 arguments: \n"
					+"first argument must be an inet address to bind on - if one and only one argument is provided, this host will act as session initializer\n\n"
					+"in order to connect to a initializer host, you must specify address and host, so:\n"
					+"first argument will be local address to bind on, second argument will be remote host address, third argument will be remote host port");
			System.exit(1);
		}

		String addressToBind = args[0];
		
		CountDownLatch registrationLatch = new CountDownLatch(1);
		AtomicReference<LoginResult> loginResultAtomicReference = new AtomicReference<>();
		AtomicReference<StartOrRegisterGUI> startOrRegistrarReference = new AtomicReference<>();
		
		EventQueue.invokeLater( ()->{ 
			StartOrRegisterGUI rg = new StartOrRegisterGUI(registrationLatch, loginResultAtomicReference);
			startOrRegistrarReference.set(rg);
		});
		
		try {
			registrationLatch.await();
			StartOrRegisterGUI startOrRegisterGUI = startOrRegistrarReference.get();
			startOrRegisterGUI.dispose();
			startOrRegisterGUI = null;
			LoginResult loginResult = loginResultAtomicReference.get();
		
			Injector = Guice.createInjector(new TexasHoldemPokerModule());
		
			NodeInitializerFactory nodeInitializerFactory = Injector.getInstance(NodeInitializerFactory.class);
			NodeInitializer nodeInitializer = null;
			
			String meId = loginResult.username;
			nodeid = meId;
			
			if (loginResult.asServable) {
				// waiter gui for players
//				CountDownLatch initializerLatch = new CountDownLatch(1);
				nodeInitializer = nodeInitializerFactory.createAsServable(meId, addressToBind
						);
				/*EventQueue.invokeLater(()->{
				});*/
//				initializerLatch.await();
			} else {
				String initializingHostAddress = loginResult.serverHost;
//				CountDownLatch registrarLatch = new CountDownLatch(1);
				nodeInitializer  = nodeInitializerFactory.createAsClientableWithInitializerPort(
						meId, 
						addressToBind, 
						initializingHostAddress, 
						Integer.parseInt(loginResult.serverPort)
//						,registrarLatch
						);
				// waiter gui for confirmed registration
				
				/*EventQueue.invokeLater(()->{
				});*/
//				registrarLatch.await();
			}
			Node node = nodeInitializer.get();
			
			MemoryUtil.runGarbageCollector();
			nodeInitializer = null;
			loginResult = null;
			MemoryUtil.runGarbageCollector();
			
			node.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
//		System.exit(0);
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
