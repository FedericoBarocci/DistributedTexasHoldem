package breads_and_aces.registration.initializers.clientable._gui;

import static javax.swing.GroupLayout.Alignment.LEADING;
import it.unibo.cs.sd.poker.gui.view.elements.JFrameDefault;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;

public class WaiterGameStartGUI {
	
	private final JFrameDefault frame = new JFrameDefault("Poker Distributed Hold'em");
	
	private final String labelStateTextWaitingRegistration = "Waiting for registration...";
	private final String labelStateTextAccepted = "You are confirmed as new player";
	private final String labelStateTextRejected = "Sorry, your registration has been rejected, because:";
	private final String labelStateTextError = "Sorry, there was an error:";
	
	private final String labelMessageTextPleaseWait = "Please wait game start";

	private final JLabel labelState = new JLabel(labelStateTextWaitingRegistration);
	private final JLabel labelMessage = new JLabel();
	private final JButton buttonExit = new JButton("Exit");
	
	public WaiterGameStartGUI() {
		buttonExit.setVisible(false);
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		frame.getContentPane().setLayout(refresh());
		frame.pack();
	}
	
	public void setVisible(boolean isVisible) {
		frame.setVisible(isVisible);
	}

	public void onError(String message) {
		labelState.setText(labelStateTextError );
		labelMessage.setText(message);
		buttonExit.setVisible(true);
		
		frame.getContentPane().setLayout(refresh());
		frame.pack();
	}

	public void onAccepted() {
		labelState.setText(labelStateTextAccepted);
		labelMessage.setText(labelMessageTextPleaseWait);
		
		frame.getContentPane().setLayout(refresh());
		frame.pack();
	}

	public void onRejected(RegistrationResult registrationResult) {
		labelState.setText(labelStateTextRejected);
		labelMessage.setText(registrationResult.getCause().toString());
		buttonExit.setVisible(true);
		
		frame.getContentPane().setLayout(refresh());
		frame.pack();
	}

	public void dispose() {
		frame.dispose();		
	}
	
	private GroupLayout refresh() {
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
			.addComponent(labelState)
			.addComponent(labelMessage)
			.addComponent(buttonExit, GroupLayout.Alignment.TRAILING)
		);
	       
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(labelState)
			.addComponent(labelMessage)
			.addComponent(buttonExit)
		);
		
		return layout;
	}
}

