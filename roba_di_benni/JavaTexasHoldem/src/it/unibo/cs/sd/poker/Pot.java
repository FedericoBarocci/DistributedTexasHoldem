package it.unibo.cs.sd.poker;

public class Pot {
	
	private Integer value = new Integer(0);
	
	Pot(Integer value) {
		this.setValue(value);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public Integer put(Integer value) {
		this.value += value;
		
		return this.value;
	}

}
