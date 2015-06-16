package breads_and_aces.game.core;

import bread_and_aces.game.exceptions.MaxReachedException;
import bread_and_aces.game.exceptions.NegativeIntegerException;

public class BoundInteger {

	private int value;
	private int max;
	private int min;

	public BoundInteger(int integer, int min, int max) {
		this.value = Math.abs(integer);
		this.min = min;
		this.max = max;
	}

	public int add(int integer) throws MaxReachedException {
		int res = value + integer;
		
		if (res > max) {
			value = max;
			throw new MaxReachedException();
		}
		
		value = res;
		
		return res;
	}

	public int subtract(int integer) throws NegativeIntegerException {
		int res = value - integer;
		
		if (res < min) {
			value = min;
			throw new NegativeIntegerException();
		}
		
		value = res;
		
		return res;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public int getMax() {
		return max;
	}
	
	public int getMin() {
		return min;
	}

	public int getValue() {
		return value;
	}
}
