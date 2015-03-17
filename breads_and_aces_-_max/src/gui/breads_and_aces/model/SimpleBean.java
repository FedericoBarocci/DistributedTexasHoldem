package breads_and_aces.model;

public class SimpleBean {

	private String theString;
	private int num;
	
	public final String getThestring() {
		return theString;
	}
	public final void setThestring(String string) {
		this.theString = string;
	}
	public final int getNum() {
		return num;
	}
	public final void setNum(int num) {
		this.num = num;
		this.theString = ""+num;
	}
	
	
}
