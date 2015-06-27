package bread_and_aces.main;

import java.awt.EventQueue;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.spi.Message;

import bread_and_aces._di._guice.module.TexasHoldemPokerModule;
import bread_and_aces.main.start.gui.StartOrRegisterGUI;
import bread_and_aces.main.start.gui.StartOrRegisterGUI.RegistrationData;
import bread_and_aces.node.Node;
import bread_and_aces.node.initializer.NodeInitializer;
import bread_and_aces.node.initializer.NodeInitializerFactory;
import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.misc.MemoryUtil;

public class Main {
	
	private Injector injector;
	
//	private NodeInitializer nodeInitializer;
	
	public RegistrationData startGUI() {
		CountDownLatch registrationLatch = new CountDownLatch(1);
		AtomicReference<RegistrationData> registrationDataAtomicReference = new AtomicReference<>();
		AtomicReference<StartOrRegisterGUI> startOrRegistrarReference = new AtomicReference<>();
		
		EventQueue.invokeLater( ()->{ 
			StartOrRegisterGUI rg = new StartOrRegisterGUI(registrationLatch, registrationDataAtomicReference);
			startOrRegistrarReference.set(rg);
		});
		
		try {
			registrationLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		StartOrRegisterGUI startOrRegisterGUI = startOrRegistrarReference.get();
		startOrRegisterGUI.dispose();
		startOrRegisterGUI = null;
		
		RegistrationData registrationData = registrationDataAtomicReference.get();
		
		return registrationData;
	}
	
	private void initInjector() throws Exception {
		injector = Guice.createInjector(new TexasHoldemPokerModule());
	}
	
	private void startNode(RegistrationData registrationData, String addressToBind) {
//		injector = Guice.createInjector(new TexasHoldemPokerModule());
		
		NodeInitializerFactory nodeInitializerFactory = injector.getInstance(NodeInitializerFactory.class);
		NodeInitializer nodeInitializer = null;

		String myId = registrationData.username;
		if (registrationData.asServable) {
			nodeInitializer = nodeInitializerFactory.createAsServable(myId, addressToBind);
		} 
		else {
			String initializingHostAddress = registrationData.serverHost;
			nodeInitializer = nodeInitializerFactory.createAsClientableWithInitializerPort(myId,
							addressToBind, initializingHostAddress, Integer.parseInt(registrationData.serverPort) );
		}
		
		Node node = nodeInitializer.get();
		int coins = registrationData.coins;
		int goal = registrationData.goal;
		
		nodeInitializerFactory = null;
		nodeInitializer = null;
		registrationData = null;
		
		MemoryUtil.runGarbageCollector();
		
		node.start(goal, coins);
	}
	
//	private 
	public static void main(String[] args) {
		if (args.length<1) {
			System.out.println("minimum 1 arguments: \n\n"
					+args[0]+ "local address to bind on\n\n" );
			System.exit(1);
		}

		String addressToBind = args[0];
		
		Main main = new Main();
		RegistrationData loginResult = main.startGUI();
		// TODO only for test during development - it works only with: ./run HOST_IP playerName [0|1,true|false] 
		handleLoginResultDev(args, loginResult);
		
		try {
			main.initInjector();
			
			main.startNode(loginResult, addressToBind);
		} catch (Exception e) {
			DevPrinter.println( /*new Throwable(),*/ "main exception");
			handleException(e);
		}
	} // main
	
	private static void handleLoginResultDev(String[] args, RegistrationData loginResult) {
		if (args.length>2) {
			DevPrinter.println(/* new Throwable(), */"dev" );
			loginResult.username = args[1];
			loginResult.asServable = Boolean.parseBoolean(args[2]);
			System.out.println(loginResult.username+" "+ ( loginResult.asServable? "as servable": "as clientable") );
		}
	}
	
	public static void handleException(Exception e) {

//		if (e.getCause()!=null) {
//			System.out.println("no cause:");
//			e.printStackTrace();
//		} else {
			try {
				Field field = e.getClass().getDeclaredField("messages");
				field.setAccessible(true);
				@SuppressWarnings("unchecked")
				Set<Message> messages = (Set<Message>) field.get(e);
				messages.forEach(m->{
					System.err.println("Exception: "+m);
				});
//				e.printStackTrace();
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
				
				e.printStackTrace();
			}
//		}
	} // handleException
	
}
