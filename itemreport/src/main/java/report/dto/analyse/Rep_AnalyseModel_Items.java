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
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import report.dto.ItemInfo;
import extend.dto.Suit;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：AnalyseModel_Items</P>
 * *********************************<br>
 * <P>类描述：分析模型--指标列表</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午10:33:48<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午10:33:48<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_AnalyseModel_Items")
@IEntity(navigationName="分析-指标",description="分析-指标")
public class Rep_AnalyseModel_Items implements Serializable {
	
	/**  **/
	private static final long serialVersionUID = 8714895058784245076L;

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
	
	@IColumn(description="主题")
	@Transient
	private Suit suit;
	
	/** 指标  **/
	@IColumn(description="指标")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="itemCode",nullable = false)
	private ItemInfo itemInfo;
	
	public Rep_AnalyseModel_Items(){
		
	}
	
	public Rep_AnalyseModel_Items(String modelId,String itemId){
		this.model = new Rep_AnalyseModel();
		this.model.setUuid(modelId);
		this.itemInfo = new ItemInfo();
		this.itemInfo.setStrItemCode(itemId);
	}
	
	public Rep_AnalyseModel_Items(Rep_AnalyseModel model,String itemId){
		this.model = model;
		this.itemInfo = new ItemInfo();
		this.itemInfo.setStrItemCode(itemId);
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

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

}
