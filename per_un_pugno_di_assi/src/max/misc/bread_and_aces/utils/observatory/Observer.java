package bread_and_aces.utils.observatory;

public interface Observer<T> {
   public void update(/*Observable<T> observable,*/ T data);
}
