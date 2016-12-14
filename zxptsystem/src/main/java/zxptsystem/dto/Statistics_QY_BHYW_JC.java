package zxptsystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="保函业务")
public class Statistics_QY_BHYW_JC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "autoID", length = 32)
	@IColumn(description="自动ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoID;
			
	@Column(name = "BS")
	@IColumn(description="保函笔数")
	private String BS;
	
	@Column(name = "JE")
	@IColumn(description="保函发生额")
	private String JE;
	
	@Column(name = "YE")
	@IColumn(description="保函余额")
	private String YE;
	

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public String getJE() {
		return JE;
	}

	public void setJE(String jE) {
		JE = jE;
	}

	public String getYE() {
		return YE;
	}

	public void setYE(String yE) {
		YE = yE;
	}

	public String getBS() {
		return BS;
	}

	public void setBS(String bS) {
		BS = bS;
	}

	
	
}
