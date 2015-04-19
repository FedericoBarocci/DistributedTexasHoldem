package breads_and_aces.utils.observatory;

public interface Observable<T> {
	void addObserver(Observer<T> observer);
	void removeObserver(Observer<T> observer);
	void notifyObservers(T data);
//	void notifyObservers(Collection<T> data);
}
