package breads_and_aces.gui.swt;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import breads_and_aces.model.Action;

// TODO @Singleton
public class UsersTableAdapter {
	
//	private final Composite parent;
	private final Table table;
	private final Map<String, TableRow> rowsMap = new HashMap<>();

	public UsersTableAdapter(Composite parent) {
//		this.parent = parent;
		table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		buildTable(table);

		populateTable(table);
	}
	
	private void populateTable(Table table) {
		// demo zone start
		rowsMap.put("user1", new TableRow("user1", 1000, Action.All_In));
		rowsMap.put("user2", new TableRow("user2", 150, Action.Call));
		rowsMap.put("user3", new TableRow("user3", 2000, Action.Fold));
		rowsMap.put("user4", new TableRow("user4", 300, Action.Check));
		// demo zone end
		
		rowsMap.values().forEach(s->{
			TableItem tableItem = new TableItem(table, SWT.NONE);
			setTableItemText(tableItem, s.getUser(), s.getSum(), s.getAction());
		});
	}

	private Table buildTable(Table table) {
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tablecolumnUser = new TableColumn(table, SWT.NONE);
		tablecolumnUser.setWidth(90);
		tablecolumnUser.setText(ColumnText.User.name());
		
		TableColumn tablecolumnSum = new TableColumn(table, SWT.NONE);
		tablecolumnSum.setWidth(90);
		tablecolumnSum.setText(ColumnText.Sum.name());
		
		TableColumn tablecolumnStatus = new TableColumn(table, SWT.NONE);
		tablecolumnStatus.setWidth(61);
		tablecolumnStatus.setText(ColumnText.Status.name());
		
		SortListener sortListener = new SortListener(table);
		tablecolumnUser.addListener(SWT.Selection, sortListener);
		tablecolumnSum.addListener(SWT.Selection, sortListener);
		tablecolumnStatus.addListener(SWT.Selection, sortListener);
		table.setSortColumn(tablecolumnSum);
		table.setSortDirection(SWT.UP);
		
		return table;
	}
	
	private void setTableItemText(TableItem tableItem, String user, int sum, Action action) {
		tableItem.setText( new String[] {user, ""+sum, action.name()} );
	}
	
	private class SortListener implements Listener {

		private final Table table;

		public SortListener(Table table) {
			this.table = table;
//			Map<String, TableRow> m = new HashMap<>();
//			Arrays.sort(rowsMap.values().toArray(new TableRow[0]));
		}

		@Override
		public void handleEvent(Event e) {
			TableColumn column = (TableColumn) e.widget;
			String columnText = column.getText();
			TableRow[] rows = rowsMap.values().toArray(new TableRow[0]);
			switch(ColumnText.valueOf(columnText)) {
				case User:
					Arrays.sort(rows, Comparators.BY_USER);
					break;
				case Sum:
					Arrays.sort(rows, Comparators.BY_SUM);
//					for (TableRow tr: rows)
//						System.out.println(tr.getUser()+" "+tr.getSum());
					break;
				case Status:
					Arrays.sort(rows, Comparators.BY_ACTION);
					break;
			}
			
//			if (column == intColumn)
//				Arrays.sort(rows, Comparators.BY_USER);
//			if (column == strColumn)
//				Arrays.sort(rows, Comparators.BY_SUM);
//			if (column == dateColumn)
//				Arrays.sort(rows, Comparators.BY_ACTION);
			table.setSortColumn(column);
			updateTable(rows);
		}

	}
	
	private void updateTable(TableRow[] rows) {
		table.removeAll();
		for (TableRow row : rows/*Map.values()*/) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(row.asString());
		}
	}
	
	enum ColumnText {
		User, Sum, Status;
	}
	
	public interface Comparators {

		public final Comparator<TableRow> BY_SUM = new Comparator<TableRow>() {
			@Override
	      public int compare(TableRow o1, TableRow o2) {
				System.out.println(o1.getSum()+" "+o2.getSum());
				if (o1.getSum() < o2.getSum())
					return -1;
				if (o1.getSum() > o2.getSum())
					return 1;
				return 0;
	      }
		}; 
	
		public final Comparator<TableRow> BY_USER = new Comparator<TableRow>() {
	      @Override
	      public int compare(TableRow o1, TableRow o2) {
	          return o1.getUser().compareTo(o2.getUser());
	      }
		}; 
	
		public final Comparator<TableRow> BY_ACTION = new Comparator<TableRow>() {
	      @Override
	      public int compare(TableRow o1, TableRow o2) {
	          return o1.getAction().name().compareTo(o2.getAction().name());
	      }
		};
	}
	
	public Table getTable() {
		return table;
	}
}
