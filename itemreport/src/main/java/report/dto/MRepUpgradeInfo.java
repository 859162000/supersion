package report.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：MRepUpgradeInfo</P>
 * *********************************<br>
 * <P>类描述：制度升级信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-18 上午11:05:25<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-18 上午11:05:25<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="MRepUpgradeInfo")
public class MRepUpgradeInfo implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -1131538602460729714L;

	@Id
	@Column(name = "uuid", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String uuid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	@IColumn(description="升级时间")
	private Date updateDate;
	
	@Column(name = "strStatus", length = 5)
	@IColumn(description="状态",tagMethodName="getPropertyStatus")
	private String strStatus;
	
	public static Map<String,String> getPropertyStatus(){
		return ShowContext.getInstance().getShowEntityMap().get("MUpdateRptHis_status");
	}
	
	@Column(name = "packName", length = 80)
	@IColumn(description="制度包")
	private String packName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "makeDate")
	@IColumn(description="生成时间")
	private Date makeDate;
	
	@Column(name = "descript")
	@IColumn(description="结果")
	private String descript;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}
