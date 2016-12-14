package report.helper;

public class CellInfo {
	
	public CellInfo(String cell){
		int point =GetBreakPoint(cell);
		rowIndex=Integer.parseInt(cell.substring(point));
		collIndex=cell.substring(0, point);
	}
	private int rowIndex;
	private String collIndex;
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getCollIndex() {
		return collIndex;
	}
	public void setCollIndex(String collIndex) {
		this.collIndex = collIndex;
	}	
	
	int GetBreakPoint(String cell){
		int index;
		char[] chars = cell.toCharArray();
		for(index=0;index<chars.length;index++){
			if(chars[index]>48 && chars[index]<58){
				break;
			}
		}
		return index;
	}
}
