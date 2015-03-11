package breads_and_aces.gui;

import breads_and_aces.model.Action;

public class TableRow {
	
	private final String user;
	private final int sum;
	private final Action action;

	public TableRow(String user, int sum, Action action) {
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



	public String[] asString() {
		return new String[] { user, Integer.toString(sum), action.name() };
	}
	
//	public interface Comparators {
//
//		public final Comparator<TableRow> BY_SUM = new Comparator<TableRow>() {
//			@Override
//	      public int compare(TableRow o1, TableRow o2) {
//	          if (o1.sum < o2.sum) return -1;
//	          if (o1.sum > o2.sum) return 1;
//	          return 0;
//	      }
//		}; 
//	
//		public final Comparator<TableRow> BY_USER = new Comparator<TableRow>() {
//	      @Override
//	      public int compare(TableRow o1, TableRow o2) {
//	          return o1.user.compareTo(o2.user);
//	      }
//		}; 
//	
//		public final Comparator<TableRow> BY_ACTION = new Comparator<TableRow>() {
//	      @Override
//	      public int compare(TableRow o1, TableRow o2) {
//	          return o1.action.compareTo(o2.action);
//	      }
//		};
//	}
}
