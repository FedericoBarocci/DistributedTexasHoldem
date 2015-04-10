package breads_and_aces.registration.initializers.clientable;

import javax.swing.JFrame;

import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.model.NodeConnectionInfos;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrationInitializerClientableGUI extends AbstractRegistrationInitializerClientable {
	
	private final JFrame jFrame;

	@AssistedInject
	public RegistrationInitializerClientableGUI(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, RegistrarPlayersKeeper playersRegistry) {
		super(initializingHostAddress, initializingHostPort, playersRegistry);
//		this.printer = printer;
		
		jFrame = new JFrame();
		buildLayout(jFrame);
		
	}
	
	private void buildLayout(JFrame jFrame) {
		jFrame.setSize(350, 100);
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
		
		JLabel lblNewLabel = new JLabel("New label");
		jFrame.getContentPane().add(lblNewLabel, "1, 1, 5, 1, center, top");
		
		JLabel label = new JLabel("New label");
		jFrame.getContentPane().add(label, "1, 2, 5, 1, center, center");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		jFrame.getContentPane().add(btnNewButton, "3, 3, fill, top");
	}
	
	@Override
	public void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId) {
//		((PlayersObservable) playersRegistry).addObserver( new NewPlayersObserverAsClientable( ) );
//		printer.print("Starting as client: ");
		super.initialize(nodeConnectionInfo, playerId);
	}
	
	@Override
	protected void onError(String message) {
//		printer.println(message);
//		System.exit(0);
	}

	@Override
	protected void onAccepted(RegistrationResult registrationResult) {
//		printer.println("initializer confirmed my registration as new player.");
	}

	@Override
	protected void onRejected(RegistrationResult registrationResult) {
//		printer.println("initializer rejected my registration as new player, because: "+registrationResult.getCause().name().toLowerCase().replace("_", " "));
	}

}
