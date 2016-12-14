package szzxpt.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WGJ_DownLoadRecord")
public class AutoDTO_WGJ_DownLoadRecord  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoID;
	
	@Column(name = "strUNO")
	private String strUNO;
	
	@Column(name = "strDownLoadNo")
	private String strDownLoadNo;
	
	@Column(name = "strDtoName")
	private String strDtoName;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public String getStrDownLoadNo() {
		return strDownLoadNo;
	}

	public void setStrDownLoadNo(String strDownLoadNo) {
		this.strDownLoadNo = strDownLoadNo;
	}

	public String getStrDtoName() {
		return strDtoName;
	}

	public void setStrDtoName(String strDtoName) {
		this.strDtoName = strDtoName;
	}

	public void setStrUNO(String strUNO) {
		this.strUNO = strUNO;
	}

	public String getStrUNO() {
		return strUNO;
	}

}
