package it.unibo.cs.sd.poker.gui.actionlisteners;

import it.unibo.cs.sd.poker.game.Player;
import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.mvc.View;
import it.unibo.cs.sd.poker.rmi.GlobalListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LoginListener implements ActionListener {
	
	private Model model;
	private View view;
	
	public LoginListener(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		model.startAsClient();
		new GlobalListener(model, view);
		
		try {
			model.registerPlayer(new Player(view.getUsername()));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		view.frame.getContentPane().removeAll();
		JLabel lblWait = new JLabel("Attendendo l'inizio della partita", SwingConstants.CENTER);
		lblWait.setForeground(new Color(0, 0, 0));
		lblWait.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
		lblWait.setBounds(400, 50, 500, 50);
		view.frame.getContentPane().add(lblWait);
		view.frame.repaint();
	}
}
