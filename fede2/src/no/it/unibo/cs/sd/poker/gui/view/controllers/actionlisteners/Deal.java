package it.unibo.cs.sd.poker.gui.view.controllers.actionlisteners;

import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.mvc.View;
import it.unibo.cs.sd.poker.rmi.RMICommand;
import it.unibo.cs.sd.poker.rmi.RMIMessage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Deal implements ActionListener {
	
	private Model model;
	private View view;
	
	public Deal(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		view.tableCard1.clear();
		view.tableCard2.clear();
		view.tableCard3.clear();
		view.tableCard4.clear();
		view.tableCard5.clear();
		view.btnDeal.setEnabled(false);
		view.btnFlop.setEnabled(true);
		view.clearPlayers();
		
		if (model.isLeader()) {
			try {
				model.setup();
				model.getService().broadcast(new RMIMessage(RMICommand.START));
				model.getService().getGlobal().next( new RMIMessage(RMICommand.DEAL) );
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		else {
			view.frame.getContentPane().removeAll();
			JLabel lblWait = new JLabel("Attendendo l'inizio della partita", SwingConstants.CENTER);
			lblWait.setForeground(new Color(0, 0, 0));
			lblWait.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
			lblWait.setBounds(400, 50, 500, 50);
			view.frame.getContentPane().add(lblWait);
			view.frame.repaint();
		}
			
		view.resetToken();
	}
}
