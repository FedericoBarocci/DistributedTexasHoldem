package it.unibo.cs.sd.poker.game.core;

public class Assert {
	
	public static void assertTrue(Boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void assertTrue(Boolean condition){
		assertTrue(condition, "The condition is not true");
	}

}
