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

import report.dto.ItemProperty;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：AnalyseModel_ItemPros</P>
 * *********************************<br>
 * <P>类描述：分析模型--指标属性列表</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午10:34:00<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午10:34:00<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_AnalyseModel_ItemPros")
@IEntity(navigationName="分析-指标属性",description="分析-指标属性")
public class Rep_AnalyseModel_ItemPros implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 1301021661786787295L;

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
	
	/** 指标属性id **/
	@IColumn(description="指标属性")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="itemProId",nullable = false)
	private ItemProperty itemProperty;
	
	public Rep_AnalyseModel_ItemPros(){
		
	}
	
	public Rep_AnalyseModel_ItemPros(String modelId,String itemProId){
		this.model = new Rep_AnalyseModel();
		this.model.setUuid(modelId);
		this.itemProperty = new ItemProperty();
		this.itemProperty.setStrPropertyCode(itemProId);
	}
	
	public Rep_AnalyseModel_ItemPros(Rep_AnalyseModel model,String itemProId){
		this.model = model;
		this.itemProperty = new ItemProperty();
		this.itemProperty.setStrPropertyCode(itemProId);
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

	public ItemProperty getItemProperty() {
		return itemProperty;
	}

	public void setItemProperty(ItemProperty itemProperty) {
		this.itemProperty = itemProperty;
	}
}
