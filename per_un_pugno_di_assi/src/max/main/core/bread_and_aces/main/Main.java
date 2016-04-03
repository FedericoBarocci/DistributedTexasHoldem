package bread_and_aces.main;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import bread_and_aces._di._guice.module.TexasHoldemPokerModule;
import bread_and_aces.main.start.gui.StartOrRegisterGUI;
import bread_and_aces.main.start.gui.StartOrRegisterGUI.RegistrationData;
import bread_and_aces.node.Node;
import bread_and_aces.node.initializer.NodeInitializer;
import bread_and_aces.node.initializer.NodeInitializerFactory;
import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.misc.MemoryUtil;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.spi.Message;

public class Main {
	
	private final static String DEV_MODE = "DEV";
	
	public static Logger logger;

	public static String run_cmd;
	
	public RegistrationData startRegistrationGUI(boolean isDevMode) {
		CountDownLatch registrationLatch = new CountDownLatch(1);
		AtomicReference<RegistrationData> registrationDataAtomicReference = new AtomicReference<>();
		AtomicReference<StartOrRegisterGUI> startOrRegistrarReference = new AtomicReference<>();
		
		EventQueue.invokeLater( ()->{ 
			StartOrRegisterGUI rg = new StartOrRegisterGUI(registrationLatch, registrationDataAtomicReference, isDevMode);
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
	
	private void start(RegistrationData registrationData, String addressToBind) {
		DevPrinter.println("registrationData: "+registrationData);
		
		Injector injector = Guice.createInjector(new TexasHoldemPokerModule());
		
		NodeInitializerFactory nodeInitializerFactory = injector.getInstance(NodeInitializerFactory.class);
		NodeInitializer nodeInitializer = null;

		String myId = registrationData.username;
		if (registrationData.asServable) {
			nodeInitializer = nodeInitializerFactory.createAsServable(myId, addressToBind);
		} 
		else {
			String initializingHostAddress = registrationData.serverHost;
			nodeInitializer = nodeInitializerFactory.createAsClientableWithInitializerPort(myId, addressToBind, initializingHostAddress, Integer.parseInt(registrationData.serverPort));
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
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File("run_to_exec"));
			run_cmd = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		String addressToBind = args[0];
		
		logger = Logger.getLogger("logger");
		logger.setUseParentHandlers(false);
		
		boolean isDevMode = false;
		RegistrationData registrationDataForDevMode = null;
		if (args.length > 1) {
			String arg1 = args[1];
			DevPrinter.println("arg1: "+arg1);
			isDevMode = (arg1.equalsIgnoreCase(DEV_MODE)) ? true : false;
			if (isDevMode)
				registrationDataForDevMode = handleArgsForDevMode(args);
		}

		
		
		try {
//			String suffix = "";
//			if (isDevMode)
//				suffix="_"+registrationDataForDevMode.username;
//			File file = new File("tmp");
//			file.mkdir();
//			FileHandler fileHandler = new FileHandler("tmp"+File.separatorChar+"trace" + suffix + ".log");
//			fileHandler.setFormatter(new LogFormatter());
//			logger.addHandler(fileHandler);
	     
	        ConsoleHandler ch = new ConsoleHandler();
	        ch.setFormatter(new LogFormatter());
	        logger.addHandler(ch);
	        
		 } catch(SecurityException e) {
			 e.printStackTrace();
		 }
		
		Main main = new Main();

		RegistrationData registrationData = main.startRegistrationGUI(isDevMode);
		// TODO only for test during development - it works only with: ./run host_ip DEV player_name [s]
//		if (isDevMode) {
//			DevPrinter.println("DevMode: "+isDevMode);
//			handleRegistrationDataInDevMode(registrationDataForDevMode, registrationData);
			/*try {
				logger.addHandler( new FileHandler("trace_"+registrationData.username+".log"));
				logger.removeHandler(fileHandler);
				// TODO complete this
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}*/
//		}
		
		
		try {
			if (isDevMode) {
//				DevPrinter.println(registrationDataForDevMode);
				main.start(registrationDataForDevMode, addressToBind);
			} else {
//				DevPrinter.println(registrationData);
				main.start(registrationData, addressToBind);
			}
		} catch (Exception e) {
			DevPrinter.println( "main exception");
			handleException(e);
		}
	} // main
	
	private static RegistrationData handleArgsForDevMode(String[] args) {
		RegistrationData registrationData = null;
		if (args.length<2) {
			System.out.println("ERROR:");
			System.out.println("DevMode: "+args[0]+" DEV HOST_IP playerName <s>");
			System.out.println("s is optional, and you have to add if you want start host as servable");
			System.exit(0);
		} else {
			registrationData = new RegistrationData();
			registrationData.username = args[2];
			if (args.length>3 ) {
				registrationData.asServable = (args[3].equals("s")) ? true: false;
				DevPrinter.println(registrationData.username+" "+ ( registrationData.asServable? "as servable": "as clientable") );
			}
			if (!registrationData.asServable) {
				DevPrinter.println("try to register "+registrationData.username);
				registrationData.serverHost = "10.0.0.1";
				registrationData.serverPort = "33333";
			} 
//			else {
				registrationData.coins = 200;
				registrationData.goal = 1000;
//			}
		}
		return registrationData;
	}
	
	/*private static void handleRegistrationDataInDevMode(RegistrationData registrationDataForDevMode, RegistrationData registrationData) {
		registrationData.username = registrationDataForDevMode.username;
		registrationData.serverHost = registrationDataForDevMode.serverHost;
		registrationData.serverPort = registrationDataForDevMode.serverPort;
		registrationData.username = registrationDataForDevMode.username;
		registrationData.username = registrationDataForDevMode.username;
		
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
	}*/
	
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
