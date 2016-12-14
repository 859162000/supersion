package report.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ItemDataHistory</P>
 * *********************************<br>
 * <P>类描述：上报指标差异性历史值</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-15 下午4:37:57<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-15 下午4:37:57<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */

@Entity
@Table(name="ItemDataHistory")
public class ItemDataHistory implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -195019432884425643L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	/** 指标 **/
	@Column(name = "strItemCode",length=50)
	private String strItemCode;
	
	/** 报表日期 **/
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate")
	private Date dtDate;
	
	/** 机构 **/
	@Column(name = "strInstCode",length=50)
	private String strInstCode;
	
	/** 频度 **/
	@Column(name = "strFreq",length=50)
	private String strFreq;
	
	/** 币种 **/
	@Column(name = "currencyType")
	private String currencyType;
	
	/** 指标属性【余额】 **/
	@Column(name = "strPropertyCode")
	private String strPropertyCode;
	
	/** 扩展维度1 **/
	@Column(name = "strExtendDimension1", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag2",description="扩展维度1")
	private String strExtendDimension1;
	
	/** 扩展维度2 **/
	@Column(name = "strExtendDimension2", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag2",description="扩展维度2")
	private String strExtendDimension2;
	
	/** 现值 **/
	@Column(name = "strValue",length=50)
	private String strValue;
	
	/** 前值 **/
	@Column(name = "strPreValue",length=50)
	private String strPreValue;
	
	/** 修改者 **/
	@Column(name = "author")
	private String author;
	
	/** 更新时间 **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	private Date updateTime;
	
	public ItemDataHistory(){
		
	}

	public ItemDataHistory(ItemData itemData){
		this.strItemCode = itemData.getItemInfo().getStrItemCode();
		this.dtDate = itemData.getDtDate();
		this.strInstCode = itemData.getInstInfo().getStrInstCode();
		this.strFreq = itemData.getStrFreq();
		this.currencyType = itemData.getCurrencyType().getStrCurrencyCode();
		this.strPropertyCode = itemData.getItemProperty().getStrPropertyCode();
		this.strExtendDimension1 = itemData.getStrExtendDimension1();
		this.strExtendDimension2 = itemData.getStrExtendDimension2();
		this.strValue = itemData.getStrValue();
	}
	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
	}

	public String getStrInstCode() {
		return strInstCode;
	}

	public void setStrInstCode(String strInstCode) {
		this.strInstCode = strInstCode;
	}

	public String getStrFreq() {
		return strFreq;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrExtendDimension1() {
		return strExtendDimension1;
	}

	public void setStrExtendDimension1(String strExtendDimension1) {
		this.strExtendDimension1 = strExtendDimension1;
	}

	public String getStrExtendDimension2() {
		return strExtendDimension2;
	}

	public void setStrExtendDimension2(String strExtendDimension2) {
		this.strExtendDimension2 = strExtendDimension2;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getStrPreValue() {
		return strPreValue;
	}

	public void setStrPreValue(String strPreValue) {
		this.strPreValue = strPreValue;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
