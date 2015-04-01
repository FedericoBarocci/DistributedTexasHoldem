package it.unibo.cs.sd.poker.main;

import it.unibo.cs.sd.poker.mvc.Controller;
import it.unibo.cs.sd.poker.mvc.GameView;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
	//	Model model = new Model();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameView view = new GameView();
					//Controller controller = 
					new Controller(/*model, */view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
