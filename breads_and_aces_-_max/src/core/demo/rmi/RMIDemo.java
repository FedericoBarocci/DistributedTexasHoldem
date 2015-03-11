package demo.rmi;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RMIDemo implements Remote {

	
	Optional<List<Integer>> optionalIntegers = Optional.of(new ArrayList<Integer>());
	private Integer i;
	
	public RMIDemo() {
		
		int start = 0;
		Optional<Integer> n = Optional.ofNullable(start);
		n.ifPresent(v->System.out.println("n: "+v));
			
		Optional<Integer> flatMapGetInteger = n.flatMap(a->{
//			if (a.isPresent())
			System.out.println("a:"+a);
			Optional<Integer> integer = getInteger(a);
			return integer;
			});
		flatMapGetInteger.ifPresent(i->System.out.println("flatMapGetInteger: "+i));

		Optional<Integer> flatMapGetIntegerNull = flatMapGetInteger.flatMap(this::getIntegerNull);
		flatMapGetIntegerNull.ifPresent(v->System.out.println("flatMapGetIntegerNull: "+v));
		
		Optional<Integer> flatMapGetInteger1 = flatMapGetIntegerNull.flatMap(this::getInteger1);
		flatMapGetInteger1.ifPresent(v->System.out.println("flatMapGetInteger1: "+v));
		
		i = flatMapGetInteger1.orElse(4);

		
		Optional<Integer> o = Optional.of(0);
		
		
		
	}
	
	private Optional<Integer> getInteger(int a) {
		System.out.println("getInteger: "+a);
		return Optional.ofNullable(a);
	}
	
	private Optional<Integer> getIntegerNull(int no) {
		System.out.println("getIntegerNull: "+no);
		return Optional.ofNullable(null);
	}
	private Optional<Integer> getInteger1(Integer o) {
		System.out.println("getInteger1: "+o);
		int r = o+1;
		System.out.println("r: "+r);
		return Optional.ofNullable(r);
	}
	
	
	public static void main(String[] args) {
		RMIDemo rmiDemo = new RMIDemo();
		System.out.println("result: "+rmiDemo.i);
	}
	
//	private List<Integer> getList() {
//		return Collections.EMPTY_LIST;
//	}
//	
//	private Optional<List<Integer>> getOptionalList() {
//		return Optional.ofNullable(Collections.EMPTY_LIST);
//	}
}
