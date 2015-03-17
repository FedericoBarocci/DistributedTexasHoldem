package breads_and_aces.gui.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.script.Bindings;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import breads_and_aces.model.SimpleBean;

public class GUIApplication extends JPanel {

	private static final long serialVersionUID = -2448622200747953709L;
	
//	private BindingGroup m_bindingGroup;
	private SimpleBean simpleBean = new SimpleBean();
	private JSlider numJSlider;
	private JTextField thestringJTextField;

	public GUIApplication(SimpleBean newSimpleBean) {
		this();
		setSimpleBean(newSimpleBean);
	}

	public GUIApplication() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0E-4 };
		setLayout(gridBagLayout);

		JLabel numLabel = new JLabel("Num:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 0;
		add(numLabel, labelGbc_0);

		numJSlider = new JSlider();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 5);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 0;
		add(numJSlider, componentGbc_0);

		JLabel thestringLabel = new JLabel("Thestring:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 1;
		add(thestringLabel, labelGbc_1);

		thestringJTextField = new JTextField();
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 5);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 1;
		add(thestringJTextField, componentGbc_1);

		if (simpleBean != null) {
//			m_bindingGroup = initDataBindings();
		}
		
//		PropertyC
	}

/*	protected BindingGroup initDataBindings() {
		BeanProperty<SimpleBean, Integer> numProperty = BeanProperty.create("num");
		BeanProperty<JSlider, Integer> valueProperty = BeanProperty.create("value");
		AutoBinding<SimpleBean, Integer, javax.swing.JSlider, Integer> autoBinding = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ, simpleBean, numProperty, numJSlider, valueProperty);
		autoBinding.bind();
		//
		BeanProperty<SimpleBean, String> thestringProperty = BeanProperty.create("thestring");
		BeanProperty<JTextField, String> textProperty = BeanProperty.create("text");
		AutoBinding<SimpleBean, String, JTextField, String> autoBinding_1 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ, simpleBean, thestringProperty, thestringJTextField,
						textProperty);
		autoBinding_1.bind();
		//
		BindingGroup bindingGroup = new BindingGroup();
		bindingGroup.addBinding(autoBinding);
		bindingGroup.addBinding(autoBinding_1);
		//
		return bindingGroup;
	}*/

	public SimpleBean getSimpleBean() {
		return simpleBean;
	}

	public void setSimpleBean(SimpleBean newSimpleBean) {
		setSimpleBean(newSimpleBean, true);
	}

	public void setSimpleBean(SimpleBean newSimpleBean, boolean update) {
		simpleBean = newSimpleBean;
		/*if (update) {
			if (m_bindingGroup != null) {
				m_bindingGroup.unbind();
				m_bindingGroup = null;
			}
			if (simpleBean != null) {
				m_bindingGroup = initDataBindings();
			}
		}*/
	}

}
