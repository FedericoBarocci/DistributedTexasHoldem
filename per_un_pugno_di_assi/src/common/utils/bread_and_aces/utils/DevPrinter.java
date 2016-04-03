package bread_and_aces.utils;


public class DevPrinter {

	public static void println(Object o) {
		println(""+o);
	}
	public static void println(String msg) {
		System.err.println( getLine(new Throwable())+" "+msg);
//		Main.logger.info( getLine(new Throwable())+" "+msg );
	}
	public static void println() {
		System.err.println( getLine(new Throwable()) );
//		Main.logger.info( getLine(new Throwable()) );
	}
	
	public static void print(Object o) {
		print(""+o);
	}
	public static void print(String msg) {
		System.err.print( getLine(new Throwable())+" "+msg);
//		Main.logger.info( getLine(new Throwable()) +msg );
	}
	public static void print(/*Throwable throwable*/) {
		System.err.print( getLine(new Throwable()) );
//		Main.logger.info( getLine(new Throwable()) );
	}
	
	private static String getLine(Throwable throwable) {
		StackTraceElement stackTraceElement = 
//				new Throwable().getStackTrace()[0];
//				throwable.getStackTrace()[0];
				Thread.currentThread().getStackTrace()[3];
		String classNameWithPackage = stackTraceElement.getClassName();
		String[] arraypackage = classNameWithPackage.split(".");
		
		if (arraypackage.length > 0) {
			classNameWithPackage = arraypackage[arraypackage.length - 1];
		}

		String methodName = stackTraceElement.getMethodName();
		int lineNumber = stackTraceElement.getLineNumber();
		String[] splitted = classNameWithPackage.split("\\.");
		
		String className = classNameWithPackage;
		if (splitted.length > 0) {
			className = splitted[splitted.length -1];
		}
		
		String line = className+"."+methodName+":"+lineNumber;
		return line;
	}
}
