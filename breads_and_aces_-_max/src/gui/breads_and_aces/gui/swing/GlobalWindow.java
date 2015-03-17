package breads_and_aces.gui.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.FlowLayout;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;

import breads_and_aces.gui.swt.UsersTableAdapter;
import breads_and_aces.model.SimpleBean;

public class GlobalWindow {
	private DataBindingContext m_bindingContext;
	

	private SimpleBean sb = new SimpleBean();
	private Label mysumCounter;


	private Display display;
	
	
	public GlobalWindow(Shell shell, Display display/*, Runnable runnable*/) {
		this.display = display;
		addComponents(shell);
	}
	
	private void addComponents(Shell shell) {
		Composite container = new Composite(shell, SWT.NONE);
		container.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Composite containerLeft = new Composite(container, SWT.NONE);
		containerLeft.setLayout(new FillLayout(SWT.VERTICAL));
		containerLeft.setLayoutData(new RowData(483, 274));
		
		Composite containerDesk = new Composite(containerLeft, SWT.NONE);
		containerDesk.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		Composite containerDeskCards = new Composite(containerDesk, SWT.NONE);
		containerDeskCards.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Canvas canvasTableCard1 = new Canvas(containerDeskCards, SWT.NONE);
		Canvas canvasTableCard2 = new Canvas(containerDeskCards, SWT.NONE);
		Canvas canvasTableCard3 = new Canvas(containerDeskCards, SWT.NONE);
		Canvas canvasTableCard4 = new Canvas(containerDeskCards, SWT.NONE);
		Canvas canvasTableCard5 = new Canvas(containerDeskCards, SWT.NONE);
		
		Composite containerDeck = new Composite(containerDesk, SWT.NONE);
		
		Composite containerPots = new Composite(containerDesk, SWT.NONE);
		
		Composite containerPot1 = new Composite(containerPots, SWT.NONE);
//		Composite compositePot2 = new Composite(containerPots, SWT.NONE);
		
		Composite containerMycardsAndMysum = new Composite(containerLeft, SWT.NONE);
		containerMycardsAndMysum.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 10));
		
		Composite containerMyCards = new Composite(containerMycardsAndMysum, SWT.NONE);
		containerMyCards.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 1));
		
		Canvas canvasMyCard1 = new Canvas(containerMyCards, SWT.NONE);
		canvasMyCard1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		Canvas canvasMyCard2 = new Canvas(containerMyCards, SWT.NONE);
		canvasMyCard2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		
//		Composite compositeMySum = new Composite(compositeMyCardsAndPots, SWT.NONE);
		
		Composite containerMysum = new Composite(containerMycardsAndMysum, SWT.NONE);
		containerMysum.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label mysumLabel = new Label(containerMysum, SWT.NONE);
		mysumLabel.setText("Sum");
		
		mysumCounter = new Label(containerMysum, SWT.NONE);
		mysumCounter.setText("0");
		
		Composite containerButtonsAndTimer = new Composite(containerLeft, SWT.NONE);
		containerButtonsAndTimer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		Composite containerButtons = new Composite(containerButtonsAndTimer, SWT.NONE);
		containerButtons.setLayout(new GridLayout(5, false));
		
		Button buttonCheck = new Button(containerButtons, SWT.NONE);
		buttonCheck.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				sb.setThestring("1");
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				sb.setThestring("1");
			}
		});
		buttonCheck.setSize(10, 10);
		buttonCheck.setBounds(0, 0, 138, 58);
		buttonCheck.setText("Check");
		
		Button buttonFold = new Button(containerButtons, SWT.NONE);
		buttonFold.setText("Fold");
		
		Button buttonCall = new Button(containerButtons, SWT.NONE);
		buttonCall.setText("Call");
		
		Button buttonRaise = new Button(containerButtons, SWT.NONE);
		buttonRaise.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		buttonRaise.setText("Raise");
		
		Button buttonAllin = new Button(containerButtons, SWT.NONE);
		buttonAllin.setText("ALL IN!");
		new Label(containerButtons, SWT.NONE);
		new Label(containerButtons, SWT.NONE);
		new Label(containerButtons, SWT.NONE);
		
//		text = new Text(containerButtons, SWT.BORDER);
//		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(containerButtons, SWT.NONE);
		new Label(containerButtons, SWT.NONE);
		
		Composite containerTimer = new Composite(containerButtonsAndTimer, SWT.NONE);
		
		
		
		Composite containerRight = new Composite(container, SWT.NO_BACKGROUND);
		containerRight.setLayout(new FillLayout(SWT.HORIZONTAL));
		containerRight.setLayoutData(new RowData(263, 275));

		UsersTableAdapter usersTableAdapter = new UsersTableAdapter(containerRight);
		m_bindingContext = initDataBindings();
//		Table table = usersTableAdapter.getTable();
//		table.setVisible(true);
//		System.out.println(table.isEnabled()+" "+table.isVisible());
//		Table table = buildTable(compositeRight); // TODO restore
	}
	protected DataBindingContext initDataBindings() {
		UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
//		updateValueStrategy.set
//		Realm defaultRealm = Realm.
//		System.out.println(defaultRealm);
//		Realm.runWithDefault(SWTObservables.getRealm(display), runnable);
		Realm defaultRealm = Realm.getDefault();
		DataBindingContext bindingContext = new DataBindingContext(defaultRealm);
		
//		IObservableValue modelSimplebean = PojoProperties.value(SimpleBean.class,"theString").observe(sb);
//		IObservableValue targetMysum = WidgetProperties.text(SWT.Modify).observe(mysumCounter);
//		bindingContext.bindValue(modelSimplebean, targetMysum);
		
		return bindingContext;
	}
}
