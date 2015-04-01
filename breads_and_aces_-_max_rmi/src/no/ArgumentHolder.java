package breads_and_aces.services.rmi.utils;

public class ArgumentHolder<T> {

	private T value;

	public ArgumentHolder(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
}
