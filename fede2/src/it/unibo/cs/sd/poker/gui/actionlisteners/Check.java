package it.unibo.cs.sd.poker.gui.actionlisteners;

import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.mvc.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class Check implements ActionListener {
	
	private Model model;
	private View view;
	
	public Check(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			if (model.executeCheck()) {
				view.unsetToken(model.getService().getId());
				
				if(model.isRunning()) 
					view.setToken(model.getTurn());
			}
			else {
				JOptionPane.showMessageDialog(view.frame, "Non è il mio turno...");
			}
		} 
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
}
