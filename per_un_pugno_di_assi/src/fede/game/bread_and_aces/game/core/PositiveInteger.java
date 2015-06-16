package bread_and_aces.game.core;

import java.io.Serializable;

import bread_and_aces.game.exceptions.MaxReachedException;
import bread_and_aces.game.exceptions.NegativeIntegerException;

public class PositiveInteger implements Serializable {

	private static final long serialVersionUID = 9098900553621332431L;
	
	private int positiveInteger;
	private int max = 0;
	private int min = 0;

	public PositiveInteger(int integer) {
		this.positiveInteger = Math.abs(integer);
	}

	public PositiveInteger(int integer, int min, int max) {
		this(integer);
		this.min = min;
		this.max = max;
	}

	public PositiveInteger() {
		positiveInteger = 0;
	}

	public int add(int integer) throws MaxReachedException {
		int res = positiveInteger + integer;
		
		if (max != 0 && res > max) {
			positiveInteger = max;
			throw new MaxReachedException();
		}
		
		positiveInteger = res;
		
		return res;
	}

	public int substract(int integer) throws NegativeIntegerException {
		int res = positiveInteger - integer;
		
		if (res <= min) {
			positiveInteger = 0;
			throw new NegativeIntegerException();
		}
		
		positiveInteger = res;
		
		return res;
	}

	public int getIntValue() {
		return positiveInteger;
	}

	public void zero() {
		positiveInteger = 0;
	}

}
