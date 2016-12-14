package report.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CurrencyRate")
@IEntity(navigationName="币种汇率管理",description="币种汇率管理")
public class CurrencyRate implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -4918782703734535087L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "originCurrencyType")
	@IColumn(description="原币种")
	private CurrencyType originCurrencyType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyType")
	@IColumn(description="币种")
	private CurrencyType currencyType;
	
	@Column(name = "strRate", length = 50, nullable = false)
	@IColumn(description="汇率", isKeyName=false, isNullable = false)
	private String strRate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate", nullable = false)
	@IColumn(description="汇率日期", isKeyName=false, isNullable = false)
	private Date dtDate;

	public void setStrRate(String strRate) {
		this.strRate = strRate;
	}

	public String getStrRate() {
		return strRate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = TypeParse.parseDate(dtDate);
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setOriginCurrencyType(CurrencyType originCurrencyType) {
		this.originCurrencyType = originCurrencyType;
	}

	public CurrencyType getOriginCurrencyType() {
		return originCurrencyType;
	}
	
}

