package report.dto;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "ItemInfo")
public class ItemInfo implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -6603191226934054898L;

	@Id
	@Column(name = "strItemCode", length = 50)
	@IColumn(description="指标代码")
	private String strItemCode;
	
	@IColumn(description="指标名称", isKeyName=true, isSpecialCharCheck=true)
	@Column(name = "strItemName", length = 500)
	private String strItemName;
	
	@IColumn(description="数据类型", tagMethodName="getDataTypeTag", isNullable = false)
	@Column(name = "strDataType", length = 10)
	private String strDataType;
	
	@Temporal(TemporalType.DATE)
	@Column(name="startdate")
	@IColumn(description="开始日期")
	private Date startdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="enddate")
	@IColumn(description="结束日期")
	private Date enddate;
	
	public static Map<String,String> getDataTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("itemDataType");
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;
	
	@Column(name="strBusiTypeCode",length = 50)
	@IColumn(description="业务类别",isNullable = true,tagMethodName="getBusiTypeTag")
	private String strBusiTypeCode;
	
	public static Map<String,String> getBusiTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("busiType");
	}
	
	@Column(name = "remark",length=4000)
	@IColumn(description="填报说明",isNullable = true)
	private String remark;
	
	
	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setStrDataType(String strDataType) {
		this.strDataType = strDataType;
	}

	public String getStrDataType() {
		return strDataType;
	}

	public void setStartdate(String startdate) {
		this.startdate =TypeParse.parseDate(startdate);
	}
	
	public Date getStartdate() {
		return startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = TypeParse.parseDate(enddate);
	}

	public Date getEnddate() {
		return enddate;
	}

	public String getStrBusiTypeCode() {
		return strBusiTypeCode;
	}

	public void setStrBusiTypeCode(String strBusiTypeCode) {
		this.strBusiTypeCode = strBusiTypeCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
