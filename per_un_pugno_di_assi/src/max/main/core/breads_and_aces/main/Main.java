package breads_and_aces.main;

import java.awt.EventQueue;
import java.lang.reflect.Field;
import java.util.Set;
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
import com.google.inject.spi.Message;

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
			
			
			// TODO only for test during development - it works only with: ./run HOST_IP playerName [0|1,true|false] 
			if (args.length>2) {
				System.out.println("dev");
				loginResult.username = args[1];
				loginResult.asServable = Boolean.parseBoolean(args[2]);
				System.out.println(loginResult.username+" "+loginResult.asServable);
			}
			

			String myId = loginResult.username;
			
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
			int coins = loginResult.coins;
			int goal = loginResult.goal;
			
			MemoryUtil.runGarbageCollector();
			nodeInitializer = null;
			loginResult = null;
			MemoryUtil.runGarbageCollector();
			
			node.start(goal, coins);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			if (e1.getCause()!=null) { 
				e1.printStackTrace();
			} else {
				try {
					Field field = e1.getClass().getDeclaredField("messages");
					field.setAccessible(true);
					@SuppressWarnings("unchecked")
					Set<Message> messages = (Set<Message>) field.get(e1);
					messages.forEach(m->{
						System.err.println(m);
					});
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
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
