package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="CheckInstance")
public class CheckInstanceVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -966449876910881469L;
	@Column
	private String instanceName;
	@Column
    private Double dblTolerance;
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public Double getDblTolerance() {
		return dblTolerance;
	}
	public void setDblTolerance(Double dblTolerance) {
		this.dblTolerance = dblTolerance;
	}
	
}
