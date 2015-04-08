package breads_and_aces.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.FlowLayout;

public class GlobalWindow {
	
//	private final Display display;
//	private final Shell shell;
	private Text text;

	/*public GlobalWindow() {
//		display = Display.getDefault();
		
		shell = new Shell();
		shell.setSize(791, 300);
		RowLayout rowlayoutShell = new RowLayout(SWT.HORIZONTAL);
		shell.setLayout(rowlayoutShell);
		addComponents(shell);
		shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 300);
	}*/
	public GlobalWindow(Shell shell) {
		addComponents(shell);
	}
	
//	public Shell getShell() {
//		return shell;
//	}
	
//	public void show() {
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		display.dispose();
//	}
	
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
		
		Composite containerMyCardsAndSum = new Composite(containerLeft, SWT.NONE);
		containerMyCardsAndSum.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 10));
		
		Composite containerMyCards = new Composite(containerMyCardsAndSum, SWT.NONE);
		containerMyCards.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 1));
		
		Canvas canvasMyCard1 = new Canvas(containerMyCards, SWT.NONE);
		canvasMyCard1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		Canvas canvasMyCard2 = new Canvas(containerMyCards, SWT.NONE);
		canvasMyCard2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		
//		Composite compositeMySum = new Composite(compositeMyCardsAndPots, SWT.NONE);
		
		Composite containerMySum = new Composite(containerMyCardsAndSum, SWT.NONE);
		containerMySum.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label sumLabel = new Label(containerMySum, SWT.NONE);
		sumLabel.setText("Sum");
		
		Label sumCounter = new Label(containerMySum, SWT.NONE);
		sumCounter.setText("0");
		
		Composite containerButtonsAndTimer = new Composite(containerLeft, SWT.NONE);
		containerButtonsAndTimer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		Composite containerButtons = new Composite(containerButtonsAndTimer, SWT.NONE);
		containerButtons.setLayout(new GridLayout(5, false));
		
		Button buttonCheck = new Button(containerButtons, SWT.NONE);
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
		
		text = new Text(containerButtons, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(containerButtons, SWT.NONE);
		
		Composite containerTimer = new Composite(containerButtonsAndTimer, SWT.NONE);
		
		
		
		Composite containerRight = new Composite(container, SWT.NO_BACKGROUND);
		containerRight.setLayout(new FillLayout(SWT.HORIZONTAL));
		containerRight.setLayoutData(new RowData(263, 275));

		UsersTableAdapter usersTableAdapter = new UsersTableAdapter(containerRight);
//		Table table = usersTableAdapter.getTable();
//		table.setVisible(true);
//		System.out.println(table.isEnabled()+" "+table.isVisible());
//		Table table = buildTable(compositeRight); // TODO restore
	}
}
