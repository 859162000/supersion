package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "JGXXCJFKBWSub")
public class JGXXCJFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="出错记录位置")
	@Column(name = "strCCJLWZ", length = 200)
	private String strCCJLWZ;
	
	@IColumn(description="出错信息")
	@Column(name = "strCCXX", length = 200)
	private String strCCXX;
	
	@Transient
	@IColumn(description="出错信息",isListShow = true)
	private String strShowCCXX;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="所属机构")
	private InstInfo instInfo;
	
	@IColumn(isNullable = false,isSingleTagHidden=true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strJGXXCJFKBWId", nullable = false)
	private JGXXCJFKBW JGXXCJFKBW;
	
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

	public void setStrShowCCXX(String strShowCCXX) {
		this.strShowCCXX = strShowCCXX;
	}
	
	public String getStrShowCCXX() {
        String strCCXXName = "";
		
        String tempCCXX = strCCXX;
        
        while(tempCCXX!=null && tempCCXX.length() > 0){
        	
        	String code = tempCCXX.substring(0,4);
        	
        	if(!StringUtils.isBlank(strCCXXName)){
    			strCCXXName += " ";
    		}
        	
        	if(ShowContext.getInstance().getShowEntityMap().get("JG_CCXX").containsKey(code)){
        		
        		strCCXXName += ShowContext.getInstance().getShowEntityMap().get("JG_CCXX").get(code);
        	}
        	else{
        		strCCXXName += code;
        	}
        	
        	tempCCXX= tempCCXX.substring(4);
        }

		return strCCXXName;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setStrCCJLWZ(String strCCJLWZ) {
		this.strCCJLWZ = strCCJLWZ;
	}

	public String getStrCCJLWZ() {
		return strCCJLWZ;
	}

	public void setJGXXCJFKBW(JGXXCJFKBW jGXXCJFKBW) {
		JGXXCJFKBW = jGXXCJFKBW;
	}

	public JGXXCJFKBW getJGXXCJFKBW() {
		return JGXXCJFKBW;
	}
	
	public String getStrCCXX() {
		return strCCXX;
	}

	public void setStrCCXX(String strCCXX) {
		this.strCCXX = strCCXX;
	}

	@IColumn(description="修改数据")
	@Column(name = "strLinkCCXXJL", length = 200)
	private String strLinkCCXXJL;
}
