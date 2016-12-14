package report.dto.analyse;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import report.dto.CurrencyType;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：AnalyseModel_Currs</P>
 * *********************************<br>
 * <P>类描述：分析模型--币种列表</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午10:34:15<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午10:34:15<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_AnalyseModel_Currs")
@IEntity(navigationName="分析-币种",description="分析-币种")
public class Rep_AnalyseModel_Currs implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 6520426666543929803L;

	/** uuid **/
	@Id
	@Column(name="uuid",length=32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String uuid;
	
	/** 模型id **/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="modelId",nullable = false)
	private Rep_AnalyseModel model;
	
	/** 币种id **/
	@IColumn(description="币种")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="currencyId",nullable = false)
	private CurrencyType currencyType;
	
	public Rep_AnalyseModel_Currs(){
		
	}
	
	public Rep_AnalyseModel_Currs(String modelId,String currencyId){
		this.model = new Rep_AnalyseModel();
		this.model.setUuid(modelId);
		this.currencyType = new CurrencyType();
		currencyType.setStrCurrencyCode(currencyId);
	}
	
	public Rep_AnalyseModel_Currs(Rep_AnalyseModel model,String currencyId){
		this.model = model;
		this.currencyType = new CurrencyType();
		currencyType.setStrCurrencyCode(currencyId);
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Rep_AnalyseModel getModel() {
		return model;
	}

	public void setModel(Rep_AnalyseModel model) {
		this.model = model;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

}
