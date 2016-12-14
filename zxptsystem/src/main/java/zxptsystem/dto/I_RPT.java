package zxptsystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "I_RPT")
@IEntity(description= "个人征信报送流转概况表")
public class I_RPT implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "strBWM", length = 50)
	@IColumn(description="报文名")
	private String strBWM;
	
	@Column(name = "strJRJGDM", length = 50)
	@IColumn(description="金融机构代码")
	private String strJRJGDM;
	
	@Column(name = "strBWSCSJ", length = 50)
	@IColumn(description="报文生成时间")
	private String strBWSCSJ;
	
	@IColumn(description="报文类别")
	@Column(name = "strBWLB", length = 50)
	private String strBWLB;
	
	@Column(name = "strBWNJLTS", length = 50)
	@IColumn(description="报文内记录条数")
	private int strBWNJLTS;
	
	@IColumn(description="质量检查是否有错误（是/否）")
	@Column(name = "strZLJCSFYCW", length = 20)
	private String strZLJCSFYCW;
	
	@Column(name = "strCWJLTS", length = 50)
	@IColumn(description="如质量检查发现错误，则错误记录条数")
	private int strCWJLTS;
	
	
	@IColumn(description="是否收到错误反馈报文（是/否）")
	@Column(name = "strSFSDCWFKBW", length = 20)
	private String strSFSDCWFKBW;
	
	@Column(name = "strFKBWDRSJ", length = 50)
	@IColumn(description="反馈报文导入时间")
	private String strFKBWDRSJ;
	
	@Column(name = "strFKBWM", length = 500)
	@IColumn(description="反馈报文名")
	private String strFKBWM;
	
	@Column(name = "strFKCWJLTS", length = 50)
	@IColumn(description="反馈错误记录条数")
	private int strFKCWJLTS;
	
	@Column(name = "strCWJLZSWXGTS", length = 50)
	@IColumn(description="错误记录中尚未纠改条数")
	private int strCWJLZSWXGTS;
	
	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoID;

	public String getStrBWM() {
		return strBWM;
	}

	public void setStrBWM(String strBWM) {
		this.strBWM = strBWM;
	}

	public String getStrJRJGDM() {
		return strJRJGDM;
	}

	public void setStrJRJGDM(String strJRJGDM) {
		this.strJRJGDM = strJRJGDM;
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

	
	public String getStrZLJCSFYCW() {
		return strZLJCSFYCW;
	}

	public void setStrZLJCSFYCW(String strZLJCSFYCW) {
		this.strZLJCSFYCW = strZLJCSFYCW;
	}

	

	public String getStrSFSDCWFKBW() {
		return strSFSDCWFKBW;
	}

	public void setStrSFSDCWFKBW(String strSFSDCWFKBW) {
		this.strSFSDCWFKBW = strSFSDCWFKBW;
	}

	public String getStrFKBWDRSJ() {
		return strFKBWDRSJ;
	}

	public void setStrFKBWDRSJ(String strFKBWDRSJ) {
		this.strFKBWDRSJ = strFKBWDRSJ;
	}

	public String getStrFKBWM() {
		return strFKBWM;
	}

	public void setStrFKBWM(String strFKBWM) {
		this.strFKBWM = strFKBWM;
	}

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public int getStrBWNJLTS() {
		return strBWNJLTS;
	}

	public void setStrBWNJLTS(int strBWNJLTS) {
		this.strBWNJLTS = strBWNJLTS;
	}

	public int getStrCWJLTS() {
		return strCWJLTS;
	}

	public void setStrCWJLTS(int strCWJLTS) {
		this.strCWJLTS = strCWJLTS;
	}

	public int getStrFKCWJLTS() {
		return strFKCWJLTS;
	}

	public void setStrFKCWJLTS(int strFKCWJLTS) {
		this.strFKCWJLTS = strFKCWJLTS;
	}

	public int getStrCWJLZSWXGTS() {
		return strCWJLZSWXGTS;
	}

	public void setStrCWJLZSWXGTS(int strCWJLZSWXGTS) {
		this.strCWJLZSWXGTS = strCWJLZSWXGTS;
	}

	
	
}
