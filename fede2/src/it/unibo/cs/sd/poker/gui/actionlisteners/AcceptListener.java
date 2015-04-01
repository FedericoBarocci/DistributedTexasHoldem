package it.unibo.cs.sd.poker.gui.actionlisteners;

import it.unibo.cs.sd.poker.game.Player;
import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.mvc.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

public class AcceptListener implements ActionListener {
	
	private Model model;
	private View view;
	
	public AcceptListener(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		model.startAsServer();
		
		//new GlobalListener(model, view);

		try {
			model.registerPlayer( new Player(view.getUsername()) );
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		JButton beginButton = new JButton("Begin");
		beginButton.setBounds(12, 42, 103, 25);
		view.frame.getContentPane().add(beginButton);
		beginButton.addActionListener(new BeginListener(model));
		
		view.frame.repaint();
	}
}
