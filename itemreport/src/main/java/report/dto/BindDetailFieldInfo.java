package report.dto;

public class BindDetailFieldInfo implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -117827438334528054L;
	public BindDetailFieldInfo(
		String dtoName, String fieldName,int startIndex, int endIndex)
	{
	
		this.dtoName=dtoName;
		this.fieldName=fieldName;
		this.startIndex=startIndex;
		this.endIndex=endIndex;
	}
	public BindDetailFieldInfo()
		{
		    this.dtoName="";
			this.fieldName="";
			this.startIndex=-1;
			this.endIndex=-1;
		}
	
	
	
	 public String dtoName;
     public String fieldName;
     public int startIndex;
     public int endIndex;
}
