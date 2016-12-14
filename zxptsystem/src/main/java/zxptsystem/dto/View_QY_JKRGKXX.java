package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.Id;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="企业征信-借款人概况信息查询")
public class View_QY_JKRGKXX  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DKKBM")
	@IColumn(description="贷款卡编码")
	private String DKKBM;

	@Column(name = "JKRZWMC")
	@IColumn(description="借款人名称")
	private String JKRZWMC;
	
	@Column(name = "INSTINFO")
	@IColumn(description="机构代码")
	private String INSTINFO;

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}


	public String getJKRZWMC() {
		return JKRZWMC;
	}

	public void setJKRZWMC(String jKRZWMC) {
		JKRZWMC = jKRZWMC;
	}

	public String getINSTINFO() {
		return INSTINFO;
	}

	public void setINSTINFO(String iNSTINFO) {
		INSTINFO = iNSTINFO;
	}

}
