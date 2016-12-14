package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.Id;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="企业征信-机构基本信息查询")
public class View_JG_JGJBXX  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DKKBM")
	@IColumn(description="贷款卡编码")
	private String DKKBM;

	@Column(name = "JGZWMC")
	@IColumn(description="机构名称")
	private String JGZWMC;

	@Column(name = "INSTINFO")
	@IColumn(description="机构代码")
	private String INSTINFO;
	
	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}

	public String getJGZWMC() {
		return JGZWMC;
	}

	public void setJGZWMC(String jGZWMC) {
		JGZWMC = jGZWMC;
	}

	public String getINSTINFO() {
		return INSTINFO;
	}

	public void setINSTINFO(String iNSTINFO) {
		INSTINFO = iNSTINFO;
	}

}
