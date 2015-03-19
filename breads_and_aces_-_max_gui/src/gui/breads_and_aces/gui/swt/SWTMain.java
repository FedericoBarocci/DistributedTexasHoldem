package breads_and_aces.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class SWTMain {

//	private final Runnable shellRunnable;
	
	public SWTMain() {
		Display display = Display.getDefault();

//		shellRunnable = new Runnable() {
//			@Override
//			public void run() {
				Shell shell = new Shell(display);
				createContents(shell, display/*, this*/);
				shell.pack();
				shell.open();
				shell.layout();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
//			}
//		};
//		shellRunnable.run();
		
		display.dispose();
	}
	
//	public Runnable getShellRunnable() {
//		return shellRunnable;
//	}
	
	protected Shell createContents(Shell shell, Display display/*, Runnable runnable*/) {
//		Shell shell = new Shell();
		shell.setText("Texas Hold'Em");
		new GlobalWindow(shell, display/*, runnable*/);
		shell.setSize(324, 300);
		RowLayout rowlayoutShell = new RowLayout(SWT.HORIZONTAL);
		shell.setLayout(rowlayoutShell);
		shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 320);
		return shell;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		new SWTMain();
	}
}
