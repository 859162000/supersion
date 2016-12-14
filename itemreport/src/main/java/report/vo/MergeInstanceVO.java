package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="MergeInstance")
public class MergeInstanceVO implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -7351153343900477101L;
	@Column
	private String autoMergeID;
	@Column
	private String strMergeName;
	@Column
	private String strDescription;
	public String getAutoMergeID() {
		return autoMergeID;
	}
	public void setAutoMergeID(String autoMergeID) {
		this.autoMergeID = autoMergeID;
	}
	public String getStrMergeName() {
		return strMergeName;
	}
	public void setStrMergeName(String strMergeName) {
		this.strMergeName = strMergeName;
	}
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	
}
