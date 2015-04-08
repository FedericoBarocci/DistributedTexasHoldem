package breads_and_aces.main.guis;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import breads_and_aces.main.Main.LoginResult;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class StartOrRegisterGUI extends JFrame {
	
	private final JLabel titleLabel = new JLabel("Texas Hold'Em Registration");
	private final JLabel nameLabel = new JLabel("Your name");
	private final JTextField usernameField = new JTextField();
	private final JLabel portLabel = new JLabel("Port");
	private final JLabel hostLabel = new JLabel("Host");
	private final JTextField hostTextField = new JTextField();
	private final JTextField portTextField = new JTextField("33333");
	private final JCheckBox servableCheckBox = new JCheckBox();
	private final JLabel registrarLabel = new JLabel("As Registrar");
	private final JButton loginButton = new JButton("Register");
	private final JLabel resultLabel = new JLabel("Check 'As Registrar' box if your node acts as initializer");
	
	public StartOrRegisterGUI(CountDownLatch latch, AtomicReference<LoginResult> loginResultAtomicReference) {
		createLayout();
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String serverHost = hostTextField.getText();
				hostTextField.setEnabled(false);
				String serverPort = portTextField.getText();
				portTextField.setEnabled(false);
				boolean asServable = servableCheckBox.isSelected();
				servableCheckBox.setEnabled(false);
				String username = usernameField.getText();
				usernameField.setEnabled(false);
				
				LoginResult loginResult = new LoginResult(serverHost, serverPort, asServable, username);
				loginResultAtomicReference.set(loginResult);
				latch.countDown();
			}
		});
		
		servableCheckBox.addActionListener(l->{
			if (servableCheckBox.isSelected()) {
				hostTextField.setEnabled(false);
				portTextField.setEnabled(false);
			} else {
				hostTextField.setEnabled(true);
				portTextField.setEnabled(true);
			}
		});
	}
	

	private static final long serialVersionUID = 4397221565390084929L;
	
	
	private void createLayout() {
		Container contentPane = getContentPane();
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("0px"),
				ColumnSpec.decode("191px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("4dlu:grow"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		titleLabel.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 16));
		
		contentPane.add(titleLabel, "6, 2, 14, 1, center, center");
		
		getContentPane().add(nameLabel, "6, 4, center, center");
		
		getContentPane().add(usernameField, "7, 4, 14, 1, fill, center");
		contentPane.add(hostLabel, "6, 6, center, center");
		contentPane.add(portLabel, "10, 6, 3, 1, center, center");
		
		getContentPane().add(registrarLabel, "14, 6, center, center");
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{hostLabel, hostTextField, portLabel, portTextField}));
		contentPane.add(hostTextField, "6, 8, fill, center");
		contentPane.add(portTextField, "10, 8, 3, 1, center, center");
		
		getContentPane().add(servableCheckBox, "14, 8, center, center");
		
		contentPane.add(loginButton, "6, 10, 15, 1, fill, center");
		getContentPane().add(resultLabel, "6, 14, 14, 3, center, center");
		setSize(430, 230);
		
		setVisible(true);
	}
}
