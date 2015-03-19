package demo.gui;

import model.Action;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class JFaceMain extends ApplicationWindow {
	private Table table;

	/**
	 * Create the application window,
	 */
	public JFaceMain() {
		super(null);
		createActions();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		RowLayout containerRowlayout = new RowLayout(SWT.HORIZONTAL);
		
		container.setLayout(
				containerRowlayout
				);
		
		Composite containerLeft = new Composite(container, SWT.NONE);
		containerLeft.setLayoutData(new RowData(411, 398));
		
		Composite containerRight = new Composite(container, SWT.NONE);
//		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		containerRight.setLayoutData(
				new RowData(193, 398)
//				tableColumnLayout
				);
		
		TableViewer tableViewer = new TableViewer(containerRight, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(0, 0, 193, 398);
//		table.setLayoutData(new RowData(160, 372));
		
		TableViewerColumn tableViewerColumnUser = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnUser = tableViewerColumnUser.getColumn();
		tableColumnUser.setWidth(61);
		tableColumnUser.setText("User");
		
		TableViewerColumn tableViewerColumnSum = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnSum = tableViewerColumnSum.getColumn();
		tableColumnSum.setWidth(61);
		tableColumnSum.setText("Sum");
		
		TableViewerColumn tableViewerColumnAction = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnAction = tableViewerColumnAction.getColumn();
		tableColumnAction.setWidth(61);
		tableColumnAction.setText("Action");
		
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new TableLabelProvider());
		
		tableViewer.add( new UserInfo("user1", 2050, Action.Call));
		tableViewer.add( new UserInfo("user2", 250, Action.Check));
		tableViewer.add( new UserInfo("user3", 1000, Action.All_In));
		
		tableViewer.refresh();
		
//		columnUser.setEditingSupport( new EditingSupport);

		return container;
	}
	
	private class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof UserInfo) {
				System.out.println("userinfo");
				UserInfo p = (UserInfo) element;
				String result = "";
				switch(columnIndex){
				case 0:
					result = p.getUser();
					break;
				case 1:
					result = ""+p.getSum();
					break;
				case 2:
					result = p.getAction().name();
					break;
				default:
					//should not reach here
					result = "";
				}
				return result;
			}
			if (element instanceof String)
				return (String)element;
			return element.toString();
		}
	}
	
	private class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			UserInfo ui = (UserInfo) inputElement;
			return ui.asArray();
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	private class UserInfo {
		private final String user;
		private final int sum;
		private final Action action;

		public UserInfo(String user, int sum, Action action) {
			this.user = user;
			this.sum = sum;
			this.action = action;
		}

		public String getUser() {
			return user;
		}

		public int getSum() {
			return sum;
		}

		public Action getAction() {
			return action;
		}
		
		public Object[] asArray() {
			return new Object[] {user,sum,action};
		}

	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			JFaceMain window = new JFaceMain();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("JFace THE");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(614, 430);
	}
}
