package report.dto;

public class ItemBindInfo implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 8317357058899339463L;
	//ItemCode;Currency;Property;date;inst;ext1;ext2

	public String itemCode;
	public String currency;
	public String property;
	public String date;
	public String instCode;
	public String ext1;
	public String ext2;
	public String period;
	public String freq;
	public String color;
	public String editable;
	public String value;
	public int row;
	public int col;

	public ItemBindInfo() {
		itemCode = "";
		currency = "";
		property = "";
		date = "";
		instCode = "";
		ext1 = "";
		ext2 = "";
		freq = "";
		color = "";
		editable = "";
		period = "1";
		value = "";
	}

}
