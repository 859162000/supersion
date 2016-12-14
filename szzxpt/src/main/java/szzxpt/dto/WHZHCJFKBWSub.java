package szzxpt.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;

@Entity
@Table(name = "WHZHCJFKBWSub")
public class WHZHCJFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false,isSingleTagHidden=true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strWHZHCJFKBWId", nullable = false)
	private WHZHCJFKBW WHZHCJFKBW;
	
	@IColumn(description="业务数据主键")
	@Column(name = "BUSSNO", length = 100)
	private String BUSSNO;
	
	@IColumn(description="出错字段英文标识")
	@Column(name = "ERRFIELD", length = 100)
	private String ERRFIELD;

	@IColumn(description="出错字段中文标识")
	@Column(name = "ERRFIELDCN", length = 100)
	private String ERRFIELDCN;
	
	@IColumn(description="出错原因")
	@Column(name = "ERRDESC", length = 50)
	private String ERRDESC;
	
	@IColumn(description="修改数据")
	@Column(name = "strLinkCCXXJL", length = 200)
	private String strLinkCCXXJL;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WHZHCJFKBW getWHZHCJFKBW() {
		return WHZHCJFKBW;
	}

	public void setWHZHCJFKBW(WHZHCJFKBW wHZHCJFKBW) {
		WHZHCJFKBW = wHZHCJFKBW;
	}

	public String getBUSSNO() {
		return BUSSNO;
	}

	public void setBUSSNO(String bUSSNO) {
		BUSSNO = bUSSNO;
	}

	public String getERRFIELD() {
		return ERRFIELD;
	}

	public void setERRFIELD(String eRRFIELD) {
		ERRFIELD = eRRFIELD;
	}

	public String getERRFIELDCN() {
		return ERRFIELDCN;
	}

	public void setERRFIELDCN(String eRRFIELDCN) {
		ERRFIELDCN = eRRFIELDCN;
	}

	public String getERRDESC() {
		return ERRDESC;
	}

	public void setERRDESC(String eRRDESC) {
		ERRDESC = eRRDESC;
	}

	public String getStrLinkCCXXJL() {
		return strLinkCCXXJL;
	}

	public void setStrLinkCCXXJL(String strLinkCCXXJL) {
		this.strLinkCCXXJL = strLinkCCXXJL;
	}

}
