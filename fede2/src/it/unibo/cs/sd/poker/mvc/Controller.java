package it.unibo.cs.sd.poker.mvc;

import java.io.Serializable;

public class Controller implements Serializable {
	
	private static final long serialVersionUID = 2065311863535299156L;
	
	/*private Model model;
	private View view;
	*/
	
	public Controller(Model model, View view) {
		//this.model = model;
		//this.view = view;
		
		view.createIntroGUI(model, view);
	}
}
