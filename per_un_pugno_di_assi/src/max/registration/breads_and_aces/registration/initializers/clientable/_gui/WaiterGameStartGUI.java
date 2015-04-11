package breads_and_aces.registration.initializers.clientable._gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class WaiterGameStartGUI {
	
	private final JFrame jFrame;
	private final JLabel labelState;
	private final JLabel labelMessage;
	private final JButton buttonExit;
	
	private final String labelStateTextWaitingRegistration = "Waiting for registration...";
	private final String labelStateTextAccepted = "You are confirmed as new player";
	private final String labelMessageTextPleaseWait = "Please wait game start";
	
	private final String labelStateTextRejected = "Sorry, your registration has been rejected, because:";
	private final String buttonTextExit = "Exit";
	private final String labelStateTextError = "Sorry, there was an error:";

	public WaiterGameStartGUI() {
		jFrame = new JFrame();
		labelState = new JLabel(labelStateTextWaitingRegistration);
		buttonExit = new JButton(buttonTextExit  );
		labelMessage = new JLabel();
		buildLayout();
	}
	
	private void buildLayout() {
		jFrame.setSize(350, 100);
		jFrame.setTitle("Poker");
		jFrame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.MIN_ROWSPEC,
				FormSpecs.MIN_ROWSPEC,
				FormSpecs.MIN_ROWSPEC,}));
		
		
		jFrame.getContentPane().add(labelState, "1, 1, 5, 1, center, top");
		
		jFrame.getContentPane().add(labelMessage, "1, 2, 5, 1, center, center");
		
		buttonExit.setVisible(false);
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jFrame.getContentPane().add(buttonExit, "3, 3, fill, top");
	}
	
	public void setVisible(boolean isVisible) {
		jFrame.setVisible(isVisible);
	}

	public void onError(String message) {
		labelState.setText(labelStateTextError );
		labelMessage.setText(message);
		buttonExit.setVisible(true);		
	}

	public void onAccepted() {
		labelState.setText(labelStateTextAccepted);
		labelMessage.setText(labelMessageTextPleaseWait);
	}

	public void onRejected(RegistrationResult registrationResult) {
		labelState.setText(labelStateTextRejected);
		labelMessage.setText(registrationResult.getCause().name().toLowerCase().replace("_", " "));
		buttonExit.setVisible(true);
	}

	public void dispose() {
		jFrame.dispose();		
	}
}

