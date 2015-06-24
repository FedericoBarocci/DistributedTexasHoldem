package bread_and_aces.utils;

public class DevPrinter {

//	private Throwable throwable;

	public static void println(/*Throwable throwable,*/ String msg) {
		System.err.println( getLine(new Throwable())+" "+msg);
	}
	public static void println(/*Throwable throwable*/) {
		System.err.println( getLine(new Throwable()) );
	}
	public static void print(/*Throwable throwable,*/ String msg) {
		System.err.print( getLine(new Throwable())+" "+msg);
	}
	public static void print(/*Throwable throwable*/) {
		System.err.print( getLine(new Throwable()) );
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
	
//	public DevPrinter(Throwable throwable) {
//		this.throwable = throwable;
//	}
}
