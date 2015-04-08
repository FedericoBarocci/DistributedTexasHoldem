package it.unibo.cs.sd.poker.main;

import it.unibo.cs.sd.poker.mvc.GuiInitializer;

import java.awt.EventQueue;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
	public static void main(String[] args) {
	//	Model model = new Model();
		
		try {
		Injector injector = Guice.createInjector(new DummyModule());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiInitializer guiInitializer = injector.getInstance(GuiInitializer.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
