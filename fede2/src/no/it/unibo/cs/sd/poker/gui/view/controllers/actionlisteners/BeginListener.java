package it.unibo.cs.sd.poker.gui.view.controllers.actionlisteners;

import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.rmi.RMICommand;
import it.unibo.cs.sd.poker.rmi.RMIMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class BeginListener implements ActionListener {
	
	private Model model;
	
	public BeginListener(Model model) {
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			model.setup();
			model.getService().broadcast(new RMIMessage(RMICommand.START));
			model.getService().getGlobal().next( new RMIMessage(RMICommand.DEAL) );
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
}