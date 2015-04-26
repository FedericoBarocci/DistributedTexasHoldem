package breads_and_aces.utils.misc;

public class Waiter {

	public static void sleep(double sec) {
		try {
			Thread.sleep((int)sec*1000);
		} catch (InterruptedException e) {}
	}
	
	@FunctionalInterface
	public interface BooleanFunctor {
		boolean exec();
	}
	
	public static void sleep(BooleanFunctor booleanFunctor, double sec) {
		while (!booleanFunctor.exec()) {
			sleep(sec);
		}
	}
}
