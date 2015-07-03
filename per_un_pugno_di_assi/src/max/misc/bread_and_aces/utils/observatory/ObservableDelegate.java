package bread_and_aces.utils.observatory;

import java.util.LinkedList;
import java.util.List;

public class ObservableDelegate<T> implements Observable<T> {

	private final List<Observer<T>> observers = new LinkedList<Observer<T>>();

	@Override
	public void addObserver(Observer<T> observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Tried to add a null observer");
		}
		if (observers.contains(observer)) {
			return;
		}
		observers.add(observer);
	}

	@Override
	public void notifyObservers(T data) {
		observers.stream().forEach(o -> o.update(/*this,*/ data));
	}

	@Override
	public void removeObserver(Observer<T> observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}
}
