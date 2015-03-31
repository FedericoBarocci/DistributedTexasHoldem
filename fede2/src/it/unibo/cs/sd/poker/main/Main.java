package it.unibo.cs.sd.poker.main;

import it.unibo.cs.sd.poker.mvc.Controller;
import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.mvc.View;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model model = new Model();
					View view = new View();
					//Controller controller = 
					new Controller(model, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
