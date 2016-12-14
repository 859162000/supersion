package eas.dto;

import extend.dto.Suit;
import framework.interfaces.IColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TOOLS_EAS_DATAQUALITY")
public class TOOLS_EAS_DATAQUALITY implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "REPORT_DATE", length = 10, nullable = true)
	@IColumn(description="日期")
	private String REPORT_DATE;
	
	@Column(name = "IN_ID", length = 299, nullable = true)
	@IColumn(description="表代码")
	private String IN_ID;
	
	@Column(name = "IN_NAME", length = 299, nullable = true)
	@IColumn(description="表中文名")
	private String IN_NAME;
	
	@Column(name = "IN_COUNT", length = 299, nullable = true)
	@IColumn(description="数据量")
	private String IN_COUNT;
	
	@Column(name = "IN_ITEM_ID", length = 299, nullable = true)
	@IColumn(description="字段代码")
	private String IN_ITEM_ID;
	
	@Column(name = "IN_ITEM_NAME", length = 299, nullable = true)
	@IColumn(description="字段中文名")
	private String IN_ITEM_NAME;
	
	@Column(name = "I_BED_COUNT", length = 299, nullable = true)
	@IColumn(description="为空的条数")
	private String I_BED_COUNT;
	
	@Column(name = "THIS_MONTH_AMT", length = 100, nullable = true)
	@IColumn(description="本月额")
	private String THIS_MONTH_AMT;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	public String getREPORT_DATE() {
		return REPORT_DATE;
	}

	public void setREPORT_DATE(String rEPORTDATE) {
		REPORT_DATE = rEPORTDATE;
	}

//	public void setREPORT_DATE(Date rEPORTDATE) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		REPORT_DATE = sdf.format(rEPORTDATE);
//	}

	public String getIN_ID() {
		return IN_ID;
	}

	public void setIN_ID(String iNID) {
		IN_ID = iNID;
	}

	public String getIN_NAME() {
		return IN_NAME;
	}

	public void setIN_NAME(String iNNAME) {
		IN_NAME = iNNAME;
	}

	public String getIN_COUNT() {
		return IN_COUNT;
	}

	public void setIN_COUNT(String iNCOUNT) {
		IN_COUNT = iNCOUNT;
	}

	public String getIN_ITEM_ID() {
		return IN_ITEM_ID;
	}

	public void setIN_ITEM_ID(String iNITEMID) {
		IN_ITEM_ID = iNITEMID;
	}

	public String getIN_ITEM_NAME() {
		return IN_ITEM_NAME;
	}

	public void setIN_ITEM_NAME(String iNITEMNAME) {
		IN_ITEM_NAME = iNITEMNAME;
	}

	public String getI_BED_COUNT() {
		return I_BED_COUNT;
	}

	public void setI_BED_COUNT(String iBEDCOUNT) {
		I_BED_COUNT = iBEDCOUNT;
	}

	public String getTHIS_MONTH_AMT() {
		return THIS_MONTH_AMT;
	}

	public void setTHIS_MONTH_AMT(String tHISMONTHAMT) {
		THIS_MONTH_AMT = tHISMONTHAMT;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}



}

