package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;

import extend.dto.Suit;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "InstRptRela")
@IEntity(navigationName="机构报表关系",description="机构报表关系")
public class InstRptRela implements java.io.Serializable {
	
	/**  **/
	private static final long serialVersionUID = -7044863985691384408L;

	@Id
	@Column(name="ID",length =32)
	//@IColumn(description="ID",isKeyName=true)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String ID;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	@IColumn(description="机构名称",isNullable=false)
	private InstInfo instInfo;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	@IColumn(description="报表名称",isNullable=false)
	private RptInfo rptInfo	;
	
	@Transient
	@IColumn(isIdListField = true, target="rptInfo")
	private String[] rptInfoIdList;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strSuitCode", nullable = false)	
	@IColumn(description="主题名称",isNullable=false)
	private Suit suit;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strCurrencyCode", nullable = false)
	@IColumn(description="币种名称",isNullable=false)
	private CurrencyType currencyType;
	
	public String[] getInstInfoList() {
		return instInfoList;
	}

	public void setInstInfoList(String[] instInfoList) {
		this.instInfoList = instInfoList;
	}

	@Transient
	@IColumn(isIdListField = true, target="currencyType")
	private String[] currencyTypeList;
	@Transient
	@IColumn(isIdListField = true, target="instInfo")
	private String[] instInfoList;
	

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public RptInfo getRptInfo() {
		return rptInfo;
	}

	public void setRptInfo(RptInfo rptInfo) {
		this.rptInfo = rptInfo;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public String[] getRptInfoIdList() {
		return rptInfoIdList;
	}

	public void setRptInfoIdList(String[] rptInfoIdList) {
		this.rptInfoIdList = rptInfoIdList;
	}

	public void setCurrencyTypeList(String[] currencyTypeList) {
		this.currencyTypeList = currencyTypeList;
	}

	public String[] getCurrencyTypeList() {
		return currencyTypeList;
	}


}
