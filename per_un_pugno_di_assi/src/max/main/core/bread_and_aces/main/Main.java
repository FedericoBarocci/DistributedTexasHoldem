package bread_and_aces.main;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

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
	
	public static String myPath;

	public static Logger logger;

	public static boolean isDevMode;
	
	private Injector injector;
	
//	private NodeInitializer nodeInitializer;
	
	public RegistrationData startRegistrationGUI() {
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
					+args[0]+ "local_address_to_bind_on\n\n" );
			System.exit(1);
		}
		
		logger = Logger.getLogger("logger");
		logger.setUseParentHandlers(false);
		 try {  
	        // This block configure the logger with handler and formatter  
	        FileHandler fh = new FileHandler("trace.log");
	        fh.setFormatter(new LogFormatter());
	        logger.addHandler(fh);
	        
	        ConsoleHandler ch = new ConsoleHandler();
	        ch.setFormatter(new LogFormatter());
	        logger.addHandler(ch);
	        
		 } catch(SecurityException|IOException e) {
			 e.printStackTrace();
		 }
		 
		String addressToBind = args[0];
		if (args.length>1) {
			isDevMode = (args[1].equalsIgnoreCase("DEV")) ? true : false;
		}
		
		Main main = new Main();
		
		myPath = main.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("bin", "");

		RegistrationData loginResult;
		loginResult = main.startRegistrationGUI();
		// TODO only for test during development - it works only with: ./run host_ip DEV player_name [s]
		if (isDevMode) {
			handleLoginResultInDevMode(args, loginResult);
		}
		
		try {
			main.initInjector();
			
			main.startNode(loginResult, addressToBind);
		} catch (Exception e) {
			DevPrinter.println( "main exception");
			handleException(e);
		}
	} // main
	
	private static void handleLoginResultInDevMode(String[] args, RegistrationData registrationData) {
		DevPrinter.println("DevMode: "+isDevMode);
		if (args.length<2) {
			System.out.println("ERROR:");
			System.out.println("DevMode: "+args[0]+" DEV HOST_IP playerName <s>");
			System.out.println("s is optional, and you have to add if you want start host as servable");
			System.exit(0);
		} else {
			registrationData.username = args[2];
			if (args.length>3 ) {
				registrationData.asServable = (args[3].equals("s")) ? true: false;
				DevPrinter.println(registrationData.username+" "+ ( registrationData.asServable? "as servable": "as clientable") );
			}
			if (!registrationData.asServable) {
				DevPrinter.println("try to register "+registrationData.username);
			}
		}
	}
	
	public static void handleException(Exception e) {
			try {
				Field field = e.getClass().getDeclaredField("messages");
				field.setAccessible(true);
				@SuppressWarnings("unchecked")
				Set<Message> messages = (Set<Message>) field.get(e);
				messages.forEach(m->{
					System.err.println("Exception: "+m);
				});
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
				
				e.printStackTrace();
			}
	} // handleException
	
	static class LogFormatter extends Formatter {

	    static final String LINE_SEPARATOR = System.getProperty("line.separator");

	    @Override
	    public String format(LogRecord record) {
	        StringBuilder sb = new StringBuilder();
	        record.setSourceClassName("");
	        sb.append(new Date(record.getMillis()))
	            .append(" - ")
	            .append(formatMessage(record))
	            .append(LINE_SEPARATOR)
	            ;

	        if (record.getThrown() != null) {
	            try {
	                StringWriter sw = new StringWriter();
	                PrintWriter pw = new PrintWriter(sw);
	                record.getThrown().printStackTrace(pw);
	                pw.close();
	                sb.append(sw.toString());
	            } catch (Exception ex) {
	                // ignore
	            }
	        }

	        return sb.toString();
	    }
	}
}
