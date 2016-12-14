package report.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import framework.helper.TypeParse;





@Entity
@DiscriminatorValue("1")
public class TaskRptInstHistory extends TaskRptInst  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426006205313483879L;
	@Transient
	private String dtDate;
	public String getDtDate()
	{
		if(this.getTaskFlow()!=null)
		{
			return TypeParse.parseString(this.getTaskFlow().getDtTaskDate(), "yyyy-MM-dd");
		}
		return "";
	}

}
