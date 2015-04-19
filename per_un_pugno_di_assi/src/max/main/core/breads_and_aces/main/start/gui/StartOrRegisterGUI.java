package breads_and_aces.main.start.gui;

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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import breads_and_aces.main.Main.LoginResult;

public class StartOrRegisterGUI extends JFrameDefault {
	
	private static final long serialVersionUID = 4397221565390084929L;
	
	private static final JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);

	private final JLabel titleLabel = new JLabel("Poker Distributed Hold'em Registration");
	private final JLabel nameLabel = new JLabel("Your name");
	private final JLabel portLabel = new JLabel("Port");
	private final JLabel hostLabel = new JLabel("Host");
	private final JLabel coinsLabel = new JLabel("Coins");
	private final JLabel goalLabel = new JLabel("Goal");
	
	private final JTextField usernameField = new JTextField();
	private final JTextField hostTextField = new JTextField("10.0.0.1");
	private final JTextField portTextField = new JTextField("33333");
	private final JTextField coinsTextField = new JTextField("200");
	private final JTextField goalTextField = new JTextField("1000");
	
	private final JCheckBox servableCheckBox = new JCheckBox("Check if your client acts as initializer");
	
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
					int coins = Integer.parseInt(coinsTextField.getText());
					int goal = Integer.parseInt(goalTextField.getText());
					
					portTextField.setEnabled(false);
					hostTextField.setEnabled(false);
					usernameField.setEnabled(false);
					servableCheckBox.setEnabled(false);
					
					LoginResult loginResult = new LoginResult(serverHost, serverPort, asServable, username, coins, goal);
					loginResultAtomicReference.set(loginResult);
	
					latch.countDown();
				}
			}
		});
		
		servableCheckBox.addActionListener(l->{
			if (servableCheckBox.isSelected()) {
				coinsTextField.setEnabled(true);
				goalTextField.setEnabled(true);
				hostTextField.setEnabled(false);
				portTextField.setEnabled(false);
				loginButton.setText("Accept Registrations");
			} 
			else {
				coinsTextField.setEnabled(false);
				goalTextField.setEnabled(false);
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
		coinsTextField.setEnabled(false);
		goalTextField.setEnabled(false);
		
		layout.setHorizontalGroup(layout.createParallelGroup(TRAILING)
			.addComponent(titleLabel)
			.addComponent(separator)
			.addGap(gap)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(TRAILING)
					.addComponent(nameLabel)
					.addComponent(hostLabel)
					.addComponent(coinsLabel)
				)
				.addGroup(layout.createParallelGroup(LEADING)
					.addComponent(usernameField)
					.addComponent(servableCheckBox)
					.addGap(gap)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(TRAILING)
							.addComponent(hostTextField)
							.addComponent(coinsTextField)
						)
						.addGroup(layout.createParallelGroup(TRAILING)
							.addComponent(portLabel)
							.addComponent(goalLabel)
						)
						.addGroup(layout.createParallelGroup(TRAILING)
							.addComponent(portTextField)
							.addComponent(goalTextField)
						)
					)
				)
			)
			.addGap(gap)
			.addComponent(loginButton)
		);
       
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(titleLabel)
			.addComponent(separator)
			.addGap(gap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(nameLabel)
				.addGroup(layout.createSequentialGroup()
					.addComponent(usernameField)
					.addComponent(servableCheckBox)
				)
			)
			.addGap(gap)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(hostLabel)
				.addComponent(hostTextField)
				.addComponent(portLabel)
				.addComponent(portTextField)
			)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(coinsLabel)
				.addComponent(coinsTextField)
				.addComponent(goalLabel)
				.addComponent(goalTextField)
			)
			.addGap(gap)
			.addComponent(loginButton)
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, loginButton, titleLabel);
		pack();
		setVisible(true);
	}
}
