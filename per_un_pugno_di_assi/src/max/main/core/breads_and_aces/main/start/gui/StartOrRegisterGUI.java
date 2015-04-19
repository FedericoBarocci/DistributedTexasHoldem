package breads_and_aces.main.start.gui;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import it.unibo.cs.sd.poker.gui.view.elements.JFrameDefault;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumFont;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import breads_and_aces.main.Main.LoginResult;

public class StartOrRegisterGUI extends JFrameDefault {
	
	private static final long serialVersionUID = 4397221565390084929L;

	private final JLabel titleLabel = new JLabel("Poker Distributed Hold'em Registration");
	private final JLabel nameLabel = new JLabel("Your name");
	private final JLabel portLabel = new JLabel("Port");
	private final JLabel hostLabel = new JLabel("Host");
	
	private final JTextField usernameField = new JTextField();
	private final JTextField hostTextField = new JTextField("10.0.0.1");
	private final JTextField portTextField = new JTextField("33333");
	
	private final JCheckBox servableCheckBox = new JCheckBox("Check if your node acts as initializer");
	
	private final JButton loginButton = new JButton("Register");
	
	private static final int gap = 20;
	
	public StartOrRegisterGUI(CountDownLatch latch, AtomicReference<LoginResult> loginResultAtomicReference) {
		createLayout();
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You need to insert an unique name", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					String serverHost = hostTextField.getText();
					String serverPort = portTextField.getText();
					boolean asServable = servableCheckBox.isSelected();
					
					portTextField.setEnabled(false);
					hostTextField.setEnabled(false);
					usernameField.setEnabled(false);
					servableCheckBox.setEnabled(false);
					
					LoginResult loginResult = new LoginResult(serverHost, serverPort, asServable, username);
					loginResultAtomicReference.set(loginResult);
	
					latch.countDown();
				}
			}
		});
		
		servableCheckBox.addActionListener(l->{
			if (servableCheckBox.isSelected()) {
				hostTextField.setEnabled(false);
				portTextField.setEnabled(false);
				loginButton.setText("Accept Registrations");
			} 
			else {
				hostTextField.setEnabled(true);
				portTextField.setEnabled(true);
				loginButton.setText("Register");
			}
		});
	}
	
	private void createLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		titleLabel.setFont(GuiUtils.INSTANCE.getFont(EnumFont.B18));
		servableCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
			.addComponent(titleLabel)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(TRAILING)
					.addComponent(nameLabel)
					.addComponent(hostLabel)
				)
				.addGroup(layout.createParallelGroup(LEADING)
					.addComponent(usernameField)
					.addGroup(layout.createSequentialGroup()
						.addComponent(hostTextField)
						.addComponent(portLabel)
						.addComponent(portTextField)
					)
					.addComponent(servableCheckBox)
				)
			)
			.addGap(gap)
			.addComponent(loginButton)
		);
       
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(titleLabel)
			.addGroup(layout.createParallelGroup(BASELINE)
				.addComponent(nameLabel)
				.addComponent(usernameField)
			)
			.addGroup(layout.createParallelGroup(BASELINE)
				.addComponent(hostLabel)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(hostTextField)
						.addComponent(portLabel)
						.addComponent(portTextField)
					)
					.addComponent(servableCheckBox)
				)
			)
			.addGap(gap)
			.addComponent(loginButton)
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, loginButton, titleLabel);
		pack();
		setVisible(true);
	}
}
