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

import coresystem.dto.InstInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：AnalyseModel_Orgs</P>
 * *********************************<br>
 * <P>类描述：分析模型--机构列表</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午10:33:31<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午10:33:31<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_AnalyseModel_Orgs")
@IEntity(navigationName="分析-机构",description="分析-机构")
public class Rep_AnalyseModel_Orgs implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -1635409850867704859L;

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
	
	/** 机构id **/
	@IColumn(description="机构")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="orgId",nullable = false)
	private InstInfo instInfo;
	
	public Rep_AnalyseModel_Orgs(){
		
	}
	
	public Rep_AnalyseModel_Orgs(String modelId,String orgId){
		this.model = new Rep_AnalyseModel();
		this.model.setUuid(modelId);
		this.instInfo = new InstInfo();
		this.instInfo.setStrInstCode(orgId);
	}
	
	public Rep_AnalyseModel_Orgs(Rep_AnalyseModel model,String orgId){
		this.model = model;
		this.instInfo = new InstInfo();
		this.instInfo.setStrInstCode(orgId);
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

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

}
