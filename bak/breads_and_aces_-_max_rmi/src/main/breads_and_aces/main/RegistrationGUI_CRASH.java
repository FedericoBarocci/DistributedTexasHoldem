package breads_and_aces.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class RegistrationGUI extends JFrame {

	private static final long serialVersionUID = 5562934001679865515L;

	private final JLabel lblPort = new JLabel("Port");
	private final JLabel lblHost = new JLabel("Host");
	private final JTextArea hostTextArea = new JTextArea();
	private final JTextArea portTextArea = new JTextArea();
	private final JButton loginButton = new JButton();

	private String host;
//	private JDialog jDialog = new JDialog();

	private String port;
	
	
	public RegistrationGUI() {
		
		setVisible(true);
      setSize(500, 300);
      
//		getContentPane().setLayout(new MigLayout("", "[237px][241px]", "[82px][82px][87px]"));
//		getContentPane().add(lblPort, "cell 1 0,grow");
//		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
//		getContentPane().add(lblHost, "flowy,cell 0 1,grow");
//		
//		
//		getContentPane().add(portTextArea, "cell 1 1,grow");
//		getContentPane().add(loginButton, "cell 1 2,grow");
//		getContentPane().add(hostTextArea, "cell 0 1,grow");
		
		loginButton.addActionListener(l->{
			host = hostTextArea.getText();
			port = portTextArea.getText();
		});
	}
	
	public String getHost() {
		return host;
	}
	
	public String getPort() {
		return port;
	}
}
