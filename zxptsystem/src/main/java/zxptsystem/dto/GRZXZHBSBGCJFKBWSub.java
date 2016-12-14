package zxptsystem.dto;

import java.util.Map;

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

import coresystem.dto.InstInfo;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXZHBSBGCJFKBWSub")
@IEntity(description= "个人征信账户标识变更采集反馈报文体")
public class GRZXZHBSBGCJFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strGRZXZHBSBGCJFKBWId", nullable = false)
	private GRZXZHBSBGCJFKBW gRZXZHBSBGCJFKBW;
	
	@IColumn(tagMethodName="getGRZXZHBSBGCWDMTag",description="错误代码")
	@Column(name = "strGRZXZHBSBGCWDM", length = 50)
	private String strGRZXZHBSBGCWDM;
	
	public static Map<String,String> getGRZXZHBSBGCWDMTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GRZXZHBSBGCWDM");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="所属机构")
	private InstInfo instInfo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrLinkCCXXJL() {
		return strLinkCCXXJL;
	}

	public void setStrLinkCCXXJL(String strLinkCCXXJL) {
		this.strLinkCCXXJL = strLinkCCXXJL;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setStrGRZXZHBSBGCWDM(String strGRZXZHBSBGCWDM) {
		this.strGRZXZHBSBGCWDM = strGRZXZHBSBGCWDM;
	}

	public String getStrGRZXZHBSBGCWDM() {
		return strGRZXZHBSBGCWDM;
	}

	public void setGRZXZHBSBGCJFKBW(GRZXZHBSBGCJFKBW gRZXZHBSBGCJFKBW) {
		this.gRZXZHBSBGCJFKBW = gRZXZHBSBGCJFKBW;
	}

	public GRZXZHBSBGCJFKBW getGRZXZHBSBGCJFKBW() {
		return gRZXZHBSBGCJFKBW;
	}

	@IColumn(description="修改数据")
	@Column(name = "strLinkCCXXJL", length = 200)
	private String strLinkCCXXJL;
}
