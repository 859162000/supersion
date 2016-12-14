package framework.show;

import java.util.ArrayList;
import java.util.List;

// 返回给ShowList.jsp页面用于显示的对象
public class ShowReport {
    private int maxLine;
    
    private int maxColumn;

    private List<List<Object>> cells; // 行列的每个元素
	
    // 每个元素为一List<Object>:
    // 空值List<Object> : 0
    // 指标List<Object> ： 1, name, value
    // 明细List<Object> ： 2, rows, cols, name, ShowList
    // 值     List<Object> ： 3, value
    // 表头List<Object> ： 4, value

	public void setMaxLine(int maxLine) {
		this.maxLine = maxLine;
	}

	public int getMaxLine() {
		return maxLine;
	}

	public void setMaxColumn(int maxColumn) {
		this.maxColumn = maxColumn;
	}

	public int getMaxColumn() {
		return maxColumn;
	}

	public void setCells(List<List<Object>> cells) {
		this.cells = cells;
	}

	public List<List<Object>> getCells() {
		return cells;
	}




}
