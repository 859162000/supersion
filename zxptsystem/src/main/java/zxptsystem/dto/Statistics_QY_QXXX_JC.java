package zxptsystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="欠息业务")
public class Statistics_QY_QXXX_JC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "autoID", length = 32)
	@IColumn(description="自动ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoID;
			
	@Column(name = "BN")
	@IColumn(description="表内欠息余额")
	private String BN;
	
	@Column(name = "BW")
	@IColumn(description="表外欠息余额")
	private String BW;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public String getBN() {
		return BN;
	}

	public void setBN(String bN) {
		BN = bN;
	}

	public String getBW() {
		return BW;
	}

	public void setBW(String bW) {
		BW = bW;
	}


	
}
