package report.dto.analyse.condition;

import java.util.Date;

import framework.interfaces.ICondition;

public class Rep_AnalyseModel_Condition implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -2721126848629868407L;

	@ICondition(isASC = false,isVisible=false,order=1)
	private Date createTime;

	@ICondition(isASC = true,order=2)
	private String modelName;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
}
