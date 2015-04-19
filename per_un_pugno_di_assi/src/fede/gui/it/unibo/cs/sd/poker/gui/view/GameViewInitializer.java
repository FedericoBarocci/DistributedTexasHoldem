package it.unibo.cs.sd.poker.gui.view;

public interface GameViewInitializer {
	void start(String myName);
	GameView get();
}
