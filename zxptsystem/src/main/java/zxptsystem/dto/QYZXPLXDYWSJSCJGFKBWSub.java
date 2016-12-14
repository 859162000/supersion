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
import extend.helper.HelpTool;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "QYZXPLXDYWSJSCJGFKBWSub")
public class QYZXPLXDYWSJSCJGFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(tagMethodName="getSCYWZLTag",description="删除业务种类")
	@Column(name = "strSCYWZL", length = 200)
	private String strSCYWZL;
	
	public static Map<String,String> getSCYWZLTag(){
		try {
			return HelpTool.getSystemConstVal("SCYWZL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGDM", length = 50)
	private String strJRJGDM;
	
	@IColumn(description="主合同编号")
	@Column(name = "strZHTBH", length = 60)
	private String strZHTBH;
	
	@IColumn(tagMethodName="getDeleteResultTag",description="删除结果")
	@Column(name = "strSCJG", length = 200)
	private String strSCJG;
	
	public static Map<String,String> getDeleteResultTag(){
		return ShowContext.getInstance().getShowEntityMap().get("DeleteResult");
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="所属机构")
	private InstInfo instInfo;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strQYZXPLXDYWSJSCJGFKBWId", nullable = false)
	private QYZXPLXDYWSJSCJGFKBW qYZXPLXDYWSJSCJGFKBW;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrJRJGDM() {
		return strJRJGDM;
	}

	public void setStrJRJGDM(String strJRJGDM) {
		this.strJRJGDM = strJRJGDM;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setQYZXPLXDYWSJSCJGFKBW(QYZXPLXDYWSJSCJGFKBW qYZXPLXDYWSJSCJGFKBW) {
		this.qYZXPLXDYWSJSCJGFKBW = qYZXPLXDYWSJSCJGFKBW;
	}

	public QYZXPLXDYWSJSCJGFKBW getQYZXPLXDYWSJSCJGFKBW() {
		return qYZXPLXDYWSJSCJGFKBW;
	}

	public void setStrSCYWZL(String strSCYWZL) {
		this.strSCYWZL = strSCYWZL;
	}

	public String getStrSCYWZL() {
		return strSCYWZL;
	}

	public void setStrZHTBH(String strZHTBH) {
		this.strZHTBH = strZHTBH;
	}

	public String getStrZHTBH() {
		return strZHTBH;
	}

	public void setStrSCJG(String strSCJG) {
		this.strSCJG = strSCJG;
	}

	public String getStrSCJG() {
		return strSCJG;
	}

}
