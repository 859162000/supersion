package zxptsystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="担保信息")
public class Statistics_QY_DBXX_JC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "autoID", length = 32)
	@IColumn(description="自动ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoID;
			
	@Column(name = "BZBS")
	@IColumn(description="保证合同笔数")
	private String BZBS;
	
	@Column(name = "BZJE")
	@IColumn(description="保证金额")
	private String BZJE;
	
	@Column(name = "ZRRBZBS")
	@IColumn(description="自然人保证合同笔数")
	private String ZRRBZBS;
	
	@Column(name = "ZRRBZJE")
	@IColumn(description="自然人保证金额")
	private String ZRRBZJE;
	
	@Column(name = "DYBS")
	@IColumn(description="抵押合同笔数")
	private String DYBS;
	
	@Column(name = "DYJE")
	@IColumn(description="抵押金额")
	private String DYJE;
	
	@Column(name = "ZRRDYBS")
	@IColumn(description="自然人抵押合同笔数")
	private String ZRRDYBS;
	
	@Column(name = "ZRRDYJE")
	@IColumn(description="自然人抵押金额")
	private String ZRRDYJE;
	
	@Column(name = "ZYBS")
	@IColumn(description="质押合同笔数")
	private String ZYBS;
	
	@Column(name = "ZYJE")
	@IColumn(description="质押金额")
	private String ZYJE;
	
	@Column(name = "ZRRZYBS")
	@IColumn(description="自然人质押合同笔数")
	private String ZRRZYBS;
	
	@Column(name = "ZRRZYJE")
	@IColumn(description="自然人质押金额")
	private String ZRRZYJE;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public String getBZBS() {
		return BZBS;
	}

	public void setBZBS(String bZBS) {
		BZBS = bZBS;
	}

	public String getBZJE() {
		return BZJE;
	}

	public void setBZJE(String bZJE) {
		BZJE = bZJE;
	}

	public String getZRRBZBS() {
		return ZRRBZBS;
	}

	public void setZRRBZBS(String zRRBZBS) {
		ZRRBZBS = zRRBZBS;
	}

	public String getZRRBZJE() {
		return ZRRBZJE;
	}

	public void setZRRBZJE(String zRRBZJE) {
		ZRRBZJE = zRRBZJE;
	}

	public String getDYBS() {
		return DYBS;
	}

	public void setDYBS(String dYBS) {
		DYBS = dYBS;
	}

	public String getDYJE() {
		return DYJE;
	}

	public void setDYJE(String dYJE) {
		DYJE = dYJE;
	}

	public String getZRRDYBS() {
		return ZRRDYBS;
	}

	public void setZRRDYBS(String zRRDYBS) {
		ZRRDYBS = zRRDYBS;
	}

	public String getZRRDYJE() {
		return ZRRDYJE;
	}

	public void setZRRDYJE(String zRRDYJE) {
		ZRRDYJE = zRRDYJE;
	}

	public String getZYBS() {
		return ZYBS;
	}

	public void setZYBS(String zYBS) {
		ZYBS = zYBS;
	}

	public String getZYJE() {
		return ZYJE;
	}

	public void setZYJE(String zYJE) {
		ZYJE = zYJE;
	}

	public String getZRRZYBS() {
		return ZRRZYBS;
	}

	public void setZRRZYBS(String zRRZYBS) {
		ZRRZYBS = zRRZYBS;
	}

	public String getZRRZYJE() {
		return ZRRZYJE;
	}

	public void setZRRZYJE(String zRRZYJE) {
		ZRRZYJE = zRRZYJE;
	}
	
	
}
