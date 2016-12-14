package testsystem.dto;

import java.util.Date;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;

public class TestStatistical {
	
	@IColumn(description="表单名称",isListShow=true)
	private String dtoName;
	@IColumn(description="数据时间",isListShow=true)
	private Date dtDate;
	@IColumn(description="未校验数据条数",isListShow=true)
	private Integer unCheckCount;
	@IColumn(description="未审核数据条数",isListShow=true)
	private Integer unReViewCount;
	public String getDtoName() {
		return dtoName;
	}
	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}
	public Date getDtDate() {
		return dtDate;
	}
	public void setDtDate(String dtDate) {
		this.dtDate = TypeParse.parseDate(dtDate);
	}
	public Integer getUnCheckCount() {
		return unCheckCount;
	}
	public void setUnCheckCount(Integer unCheckCount) {
		this.unCheckCount = unCheckCount;
	}
	public Integer getUnReViewCount() {
		return unReViewCount;
	}
	public void setUnReViewCount(Integer unReViewCount) {
		this.unReViewCount = unReViewCount;
	}
}
