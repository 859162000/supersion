package zxptsystem.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="个人查询成本管理")
public class PROC_COST_GR implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "pBeginDate")
	@IColumn(description="开始日期")
	private Date beginDate;
	
	@Column(name = "pEndDate")
	@IColumn(description="结束日期")
	private Date endDate;
	
	@Column(name = "pCostUnit")
	@IColumn(description="单位成本")
	private String costUnit;
	
	@Column(name = "pCategory")
	@IColumn(tagMethodName="getcategory",description="方式")
	private String category;
	public static Map<String,String> getcategory(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("I", "机构");
		map.put("U", "用户");
		return map;
	}
	
	@Column(name = "displayName")
	@IColumn(description="名称")
	private String displayName;
	
	@Column(name = "total")
	@IColumn(description="总计查询次数")
	private String total;
	
	@Column(name = "local")
	@IColumn(description="本地查询次数")
	private String local;
	
	@Column(name = "remote")
	@IColumn(description="远程查询次数")
	private String remote;
	
	@Column(name = "expect")
	@IColumn(description="预计费用")
	private String expect;
	
	@Column(name = "reality")
	@IColumn(description="实际费用")
	private String reality;
	
	@Column(name = "ratio", length = 50)
	@IColumn(description="节省比列")
	private String ratio;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

	public String getReality() {
		return reality;
	}

	public void setReality(String reality) {
		this.reality = reality;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCostUnit() {
		return costUnit;
	}

	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	
}
