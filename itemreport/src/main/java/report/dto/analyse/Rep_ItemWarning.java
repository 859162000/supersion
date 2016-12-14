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

import report.dto.ItemInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：Rep_ItemWarning</P>
 * *********************************<br>
 * <P>类描述：预警指标设置</P>
 * 创建人：王川<br>
 * 创建时间：2016-9-1 下午4:09:06<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-1 下午4:09:06<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：Rep_ItemWarning</P>
 * *********************************<br>
 * <P>类描述：</P>
 * 创建人：王川<br>
 * 创建时间：2016-9-1 下午4:58:32<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-1 下午4:58:32<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_ItemWarning")
@IEntity(navigationName="预警指标",description="预警指标")
public class Rep_ItemWarning implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -8627621575455256370L;

	/** uuid **/
	@Id
	@Column(name="uuid",length=32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String uuid;

	/** 指标  **/
	@IColumn(description="指标")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="strItemCode",nullable = false)
	private ItemInfo itemInfo;
	
	/** 指标类型[Z:正向、F:负向、Q:区间] 做对比用 **/
	@IColumn(description="指标类型",tagMethodName="getOptItemWarningType")
	@Column(name="itemType",length=4)
	private String itemType;
	public static Map<String,String> getOptItemWarningType(){
		return ShowContext.getInstance().getShowEntityMap().get("itemWarningType");
	}
	
	/** 触发值 **/
	@IColumn(description="触发值")
	@Column(name="critical")
	private Double critical;
	
	/** 黄色 触发**/
	/** 触发区间上限 **/
	@IColumn(description="触发区间上限")
	@Column(name="criticalUp")
	private Double criticalUp;

	/** 触发区间下限 **/
	@IColumn(description="触发区间下限")
	@Column(name="criticalDown")
	private Double criticalDown;

	/** 绿色安全 **/
	/** 最优区间上限 **/
	@IColumn(description="最优区间上限")
	@Column(name="bestUp")
	private Double bestUp;

	/** 最优区间下限 **/
	@IColumn(description="最优区间下限")
	@Column(name="bestDown")
	private Double bestDown;

	/** 红色预警 **/
	/** 最差区间上限 **/
	@IColumn(description="最差区间上限")
	@Column(name="worstUp")
	private Double worstUp;

	/** 最差区间下限 **/
	@IColumn(description="最差区间下限")
	@Column(name="worstDown")
	private Double worstDown;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Double getCriticalUp() {
		return criticalUp;
	}

	public void setCriticalUp(Double criticalUp) {
		this.criticalUp = criticalUp;
	}

	public Double getCriticalDown() {
		return criticalDown;
	}

	public void setCriticalDown(Double criticalDown) {
		this.criticalDown = criticalDown;
	}

	public Double getCritical() {
		return critical;
	}

	public void setCritical(Double critical) {
		this.critical = critical;
	}

	public Double getBestUp() {
		return bestUp;
	}

	public void setBestUp(Double bestUp) {
		this.bestUp = bestUp;
	}

	public Double getBestDown() {
		return bestDown;
	}

	public void setBestDown(Double bestDown) {
		this.bestDown = bestDown;
	}

	public Double getWorstUp() {
		return worstUp;
	}

	public void setWorstUp(Double worstUp) {
		this.worstUp = worstUp;
	}

	public Double getWorstDown() {
		return worstDown;
	}

	public void setWorstDown(Double worstDown) {
		this.worstDown = worstDown;
	}
	
	
}
