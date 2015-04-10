package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Check implements ActionListener {
	
//	private Model model;
//	private View view;
//	
//	public Check(Model model, View view) {
//		this.model = model;
//		this.view = view;
//	}
	
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Prova");
//		try {
//			if (model.getService().getToken()) {
			
			//TODO 
			//Broadcast del check
			//Passa il token al next
			
			
			
//			model.getService().getGlobal().setAction(model.getService().getId(), Action.CHECK);
//			model.getService().getGlobal().next(new RMIMessage(RMICommand.CHECK));
//			model.getService().setToken(false);
//
//			view.unsetToken(model.getService().getId());
//
//			if (model.isRunning())
//				view.setToken(model.getTurn());
			
//			} else {
//				JOptionPane.showMessageDialog(view.frame,
//						"Non è il mio turno...");
//			}
//		} 
//		catch (RemoteException e1) {
//			e1.printStackTrace();
//		}
	}
}
