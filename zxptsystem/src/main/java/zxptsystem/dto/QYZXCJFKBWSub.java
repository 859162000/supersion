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

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "QYZXCJFKBWSub")
public class QYZXCJFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strQYZXCJFKBWId", nullable = false)
	private QYZXCJFKBW qYZXCJFKBW;
	
	@IColumn(description="信息记录长度")
	@Column(name = "strXXJLCD", length = 50)
	private String strXXJLCD;
	
	@IColumn(description="信息记录跟踪编号")
	@Column(name = "strXXJLGZBH", length = 50)
	private String strXXJLGZBH;
	
	@IColumn(description="金融机构代码")
	@Column(name = "strJRJGDM", length = 50)
	private String strJRJGDM;
	
	@IColumn(description="业务发生日期")
	@Column(name = "strYWFSRQ", length = 50)
	private String strYWFSRQ;
	
	@IColumn(tagMethodName="getXXJLCZLXTag",description="信息记录操作类型")
	@Column(name = "strXXJLCZLX", length = 200)
	private String strXXJLCZLX;
	
	public static Map<String,String> getXXJLCZLXTag(){
		return ShowContext.getInstance().getShowEntityMap().get("XXJLCZLX");
	}
	
	@IColumn(description="出错记录位置")
	@Column(name = "strCCJLWZ", length = 200)
	private String strCCJLWZ;
	
	@IColumn(description="出错信息长度")
	@Column(name = "strCCXXCD", length = 200)
	private String strCCXXCD;
	
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QYZXCJFKBW getQYZXCJFKBW() {
		return qYZXCJFKBW;
	}

	public void setQYZXCJFKBW(QYZXCJFKBW qYZXCJFKBW) {
		this.qYZXCJFKBW = qYZXCJFKBW;
	}

	public String getStrXXJLCD() {
		return strXXJLCD;
	}

	public void setStrXXJLCD(String strXXJLCD) {
		this.strXXJLCD = strXXJLCD;
	}

	public String getStrXXJLGZBH() {
		return strXXJLGZBH;
	}

	public void setStrXXJLGZBH(String strXXJLGZBH) {
		this.strXXJLGZBH = strXXJLGZBH;
	}

	public String getStrJRJGDM() {
		return strJRJGDM;
	}

	public void setStrJRJGDM(String strJRJGDM) {
		this.strJRJGDM = strJRJGDM;
	}

	public String getStrYWFSRQ() {
		return strYWFSRQ;
	}

	public void setStrYWFSRQ(String strYWFSRQ) {
		this.strYWFSRQ = strYWFSRQ;
	}

	public String getStrXXJLCZLX() {
		return strXXJLCZLX;
	}

	public void setStrXXJLCZLX(String strXXJLCZLX) {
		this.strXXJLCZLX = strXXJLCZLX;
	}

	public String getStrCCJLWZ() {
		return strCCJLWZ;
	}

	public void setStrCCJLWZ(String strCCJLWZ) {
		this.strCCJLWZ = strCCJLWZ;
	}

	public String getStrCCXXCD() {
		return strCCXXCD;
	}

	public void setStrCCXXCD(String strCCXXCD) {
		this.strCCXXCD = strCCXXCD;
	}

	public String getStrCCXX() {
		return strCCXX;
	}

	public void setStrCCXX(String strCCXX) {
		this.strCCXX = strCCXX;
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
        
        while(tempCCXX.length() > 0){
        	
        	String code = tempCCXX.substring(0,4);
        	
        	if(!StringUtils.isBlank(strCCXXName)){
    			strCCXXName += " ";
    		}
        	
        	if(ShowContext.getInstance().getShowEntityMap().get("CCXX").containsKey(code)){
        		
        		strCCXXName += ShowContext.getInstance().getShowEntityMap().get("CCXX").get(code);
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

	@IColumn(description="修改数据")
	@Column(name = "strLinkCCXXJL", length = 200)
	private String strLinkCCXXJL;
}
