package report.dto.analyse;

import java.io.Serializable;
import java.util.Map;

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
import framework.interfaces.IEntity;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：Rep_AnalyseModel_Dimension1</P>
 * *********************************<br>
 * <P>类描述：分析模型--扩展维度1</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午10:34:15<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午10:34:15<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_AnalyseModel_Dimension1")
@IEntity(navigationName="分析-币种",description="分析-扩展维度1")
public class Rep_AnalyseModel_Dimension1 implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -1313946629038387727L;

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
	
	/** 扩展维度1 **/
	@Column(name = "strExtendDimension1", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag1",description="扩展维度1")
	private String strExtendDimension1;
	
	public static Map<String,String> getExtendPropertyTag1(){
		return ShowContext.getInstance().getShowEntityMap().get("extendProperty1");
	}
	
	public Rep_AnalyseModel_Dimension1(){
		
	}
	
	public Rep_AnalyseModel_Dimension1(String modelId,String strExtendDimension1){
		this.model = new Rep_AnalyseModel();
		this.model.setUuid(modelId);
		this.strExtendDimension1 = strExtendDimension1;
	}
	
	public Rep_AnalyseModel_Dimension1(Rep_AnalyseModel model,String strExtendDimension1){
		this.model = model;
		this.strExtendDimension1 = strExtendDimension1;
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

	public String getStrExtendDimension1() {
		return strExtendDimension1;
	}

	public void setStrExtendDimension1(String strExtendDimension1) {
		this.strExtendDimension1 = strExtendDimension1;
	}


}
