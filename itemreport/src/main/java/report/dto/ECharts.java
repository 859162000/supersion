package report.dto;

public class ECharts implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 711086248147005855L;
	private String chartType;
	private String option;
	private String[] switchCharts;
	private String height = "400px;";
	
	
	public ECharts(String chartType,String option)
	{
		this.chartType=chartType;
		this.option=option;
		this.switchCharts=new String[0];
	}
	
	public ECharts(String chartType,String option,String[] switchCharts)
	{
		this.chartType=chartType;
		this.option=option;
		this.switchCharts=switchCharts;
	}
	
	public String getChartType()
	{
		return this.chartType;
	}
	public String getOption()
	{
		return this.option;
	}
	public String[] getSwitchCharts()
	{
		return this.switchCharts;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
