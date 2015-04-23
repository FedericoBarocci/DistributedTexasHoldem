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
			
			String myId = loginResult.username;
			
			//TODO
			//Pass these two server values to clients and gameViewReal
			
			/*
			int coins = loginResult.coins;
			int goal = loginResult.goal;
			*/
			
			if (loginResult.asServable) {
				nodeInitializer = nodeInitializerFactory.createAsServable(myId, addressToBind);
			} 
			else {
				String initializingHostAddress = loginResult.serverHost;
				nodeInitializer = nodeInitializerFactory
						.createAsClientableWithInitializerPort(myId,
								addressToBind, initializingHostAddress,
								Integer.parseInt(loginResult.serverPort));
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
	}
	
	static public class LoginResult {
		String username;
		String serverHost;
		String serverPort;
		boolean asServable;
		int coins;
		int goal;

		public LoginResult(String serverHost, String serverPort, boolean asServable, String username, int coins, int goal) {
			this.serverHost = serverHost;
			this.serverPort = serverPort;
			this.asServable = asServable;
			this.username = username;
			this.coins = coins;
			this.goal = goal;
		}
	}
}
