package bread_and_aces.utils;

public class DevPrinter {

	private Throwable throwable;

	public  void println(String msg) {
		System.err.println( getLine()+" "+msg);
	}
	public  void println() {
		System.err.println( getLine() );
	}
	
	public String getLine() {
		StackTraceElement stackTraceElement = 
//				new Throwable().getStackTrace()[0];
				throwable.getStackTrace()[0];
		String classNameWithPackage = stackTraceElement.getClassName();
		String className = classNameWithPackage;
		String[] arraypackage = classNameWithPackage.split(".");
		
		if (arraypackage.length > 0)
			className = arraypackage[arraypackage.length - 1];

		String methodName = stackTraceElement.getMethodName();
		int lineNumber = stackTraceElement.getLineNumber();
		String line = className+":"+methodName+":"+lineNumber;
		return line;
	}
	
	public DevPrinter(Throwable throwable) {
		this.throwable = throwable;
	}
}
