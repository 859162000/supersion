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

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXSJSCCJFKBWSub")
@IEntity(description= "个人征信数据删除采集反馈报文体")
public class GRZXSJSCCJFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strGRZXSJSCCJFKBWId", nullable = false)
	private GRZXSJSCCJFKBW gRZXSJSCCJFKBW;
	
	@IColumn(tagMethodName="getGRZXSJSCCWDMTag",description="错误代码")
	@Column(name = "strGRZXSJSCCWDM", length = 50)
	private String strGRZXSJSCCWDM;
	
	public static Map<String,String> getGRZXSJSCCWDMTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GRZXSJSCCWDM");
	}
	
	@IColumn(description="未删除记录的年月")
	@Column(name = "strWSCJLDJSNY", length = 50)
	private String strWSCJLDJSNY;
	
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

	public void setGRZXSJSCCJFKBW(GRZXSJSCCJFKBW gRZXSJSCCJFKBW) {
		this.gRZXSJSCCJFKBW = gRZXSJSCCJFKBW;
	}

	public GRZXSJSCCJFKBW getGRZXSJSCCJFKBW() {
		return gRZXSJSCCJFKBW;
	}

	public void setStrGRZXSJSCCWDM(String strGRZXSJSCCWDM) {
		this.strGRZXSJSCCWDM = strGRZXSJSCCWDM;
	}

	public String getStrGRZXSJSCCWDM() {
		return strGRZXSJSCCWDM;
	}

	public void setStrWSCJLDJSNY(String strWSCJLDJSNY) {
		this.strWSCJLDJSNY = strWSCJLDJSNY;
	}

	public String getStrWSCJLDJSNY() {
		return strWSCJLDJSNY;
	}

	@IColumn(description="修改数据")
	@Column(name = "strLinkCCXXJL", length = 200)
	private String strLinkCCXXJL;
}
