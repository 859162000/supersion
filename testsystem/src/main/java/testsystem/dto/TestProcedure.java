package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Id;

public class TestProcedure {
	@Id
	@Column(name = "strCodeId", length = 50)
	private String strCodeId;
	
	@Column(name = "strSetName", length = 50)
	private String strSetName;

	public void setStrCodeId(String strCodeId) {
		this.strCodeId = strCodeId;
	}

	public String getStrCodeId() {
		return strCodeId;
	}

	public void setStrSetName(String strSetName) {
		this.strSetName = strSetName;
	}

	public String getStrSetName() {
		return strSetName;
	} 
}
