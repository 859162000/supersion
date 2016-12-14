package zxptsystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "I_RPT_DETAIL")
@IEntity(description= "个人征信报送流转明细表")
public class I_RPT_DETAIL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "strID", length = 50)
	@IColumn(description="ID")
	private String strID;
	
	@Column(name = "strBWM", length = 50)
	@IColumn(description="报文名")
	private String strBWM;
	
	@Column(name = "strBWSCSJ", length = 50)
	@IColumn(description="报文生成时间")
	private String strBWSCSJ;
	
	@IColumn(description="报文类别")
	@Column(name = "strBWLB", length = 50)
	private String strBWLB;
	
	@Column(name = "strFKBWM", length = 500)
	@IColumn(description="反馈报文名")
	private String strFKBWM;
	
	@Column(name = "strFKBWDRSJ", length = 50)
	@IColumn(description="反馈报文导入时间")
	private String strFKBWDRSJ;
	
	@Type(type="text")
	@Column(name = "strCWXXJLXXXX")
	@IColumn(description="错误信息记录详细信息")
	private String strCWXXJLXXXX;

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoID;

	public String getStrID() {
		return strID;
	}

	public void setStrID(String strID) {
		this.strID = strID;
	}

	public String getStrBWM() {
		return strBWM;
	}

	public void setStrBWM(String strBWM) {
		this.strBWM = strBWM;
	}

	public String getStrBWSCSJ() {
		return strBWSCSJ;
	}

	public void setStrBWSCSJ(String strBWSCSJ) {
		this.strBWSCSJ = strBWSCSJ;
	}

	public String getStrBWLB() {
		return strBWLB;
	}

	public void setStrBWLB(String strBWLB) {
		this.strBWLB = strBWLB;
	}

	public String getStrFKBWM() {
		return strFKBWM;
	}

	public void setStrFKBWM(String strFKBWM) {
		this.strFKBWM = strFKBWM;
	}

	public String getStrFKBWDRSJ() {
		return strFKBWDRSJ;
	}

	public void setStrFKBWDRSJ(String strFKBWDRSJ) {
		this.strFKBWDRSJ = strFKBWDRSJ;
	}

	public String getStrCWXXJLXXXX() {
		return strCWXXJLXXXX;
	}

	public void setStrCWXXJLXXXX(String strCWXXJLXXXX) {
		this.strCWXXJLXXXX = strCWXXJLXXXX;
	}

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}
	
	
}
