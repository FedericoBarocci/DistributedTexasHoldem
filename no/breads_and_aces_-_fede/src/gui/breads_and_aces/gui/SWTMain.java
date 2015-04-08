package breads_and_aces.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class SWTMain {
	
	

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		/*Display display = Display.getDefault();
		
		Shell shell = 
//				new GlobalWindow().getShell();
				new Shell();
		shell.setSize(324, 300);
		RowLayout rowlayoutShell = new RowLayout(SWT.HORIZONTAL);
		shell.setLayout(rowlayoutShell);
//		new GlobalWindow(shell);
//		addComponents(shell);
//		shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 300);
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();*/
		
		new SWTMain().open();
	}
	
	public void open() {
		Display display = Display.getDefault();
		Shell shell = createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected Shell createContents() {
		Shell shell = new Shell();
		shell.setText("Texas Hold'Em");
		new GlobalWindow(shell);
		shell.setSize(324, 300);
		RowLayout rowlayoutShell = new RowLayout(SWT.HORIZONTAL);
		shell.setLayout(rowlayoutShell);
		shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 320);
		return shell;
	}
}
