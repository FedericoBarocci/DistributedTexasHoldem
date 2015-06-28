package bread_and_aces.registration.initializers.clientable._gui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import bread_and_aces.gui.view.elements.frame.JFrameDefault;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;

public class WaiterGameStartGUI {
	
	private final JFrameDefault frame = new JFrameDefault();
	
	private final String labelStateTextWaitingRegistration = "Waiting for registration...";
	private final String labelStateTextAccepted = "You are confirmed as new player";
	private final String labelStateTextRejected = "Sorry, your registration has been rejected, because:";
	private final String labelStateTextError = "Sorry, there was an error:";
	
	private final String labelMessageTextPleaseWait = "Please wait game start";

	private final JLabel labelState = new JLabel(labelStateTextWaitingRegistration);
	private final JLabel labelMessage = new JLabel();
	private final JButton buttonExit = new JButton("Exit");

	private final JButton buttonTryAgain = new JButton("Try Again");
	
	public WaiterGameStartGUI() {
		frame.init();
		frame.setTitle("Poker Distributed Hold'em");
		
		buttonTryAgain.setVisible(false);
		buttonTryAgain.addActionListener(a->{
//			Main.clientWouldTryAgain = true;
			try {
				Runtime.getRuntime().exec("./run_dev a");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(0);			
		});
		
		buttonExit.setVisible(false);
		buttonExit.addActionListener(a-> { System.exit(0);} );
		
		frame.getContentPane().setLayout(refresh());
		frame.pack();
	}
	
	public void setVisible(boolean isVisible) {
		frame.setVisible(isVisible);
	}

	public void onError(String message) {
		labelState.setText(labelStateTextError );
		labelMessage.setText(message);
//		buttonTryAgain.setVisible(true);
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
		labelMessage.setText(registrationResult.getCause().toString()+" '"+registrationResult.getPlayerId()+"'");
		buttonTryAgain.setVisible(true);
		buttonExit.setVisible(true);
		
		frame.getContentPane().setLayout(refresh());
		frame.pack();
	}

	public void dispose() {
		frame.dispose();		
	}
	
	private GroupLayout refresh() {
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelState)
						.addComponent(labelMessage)
						.addGroup(layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(buttonTryAgain)
							.addGap(18)
							.addComponent(buttonExit)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(labelState)
					.addComponent(labelMessage)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonTryAgain)
						.addComponent(buttonExit))
					.addGap(10))
		);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		return layout;
	}
}

