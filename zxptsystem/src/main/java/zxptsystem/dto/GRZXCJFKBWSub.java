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
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXCJFKBWSub")
@IEntity(description= "个人征信采集反馈报文体")
public class GRZXCJFKBWSub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strGRZXCJFKBWId", nullable = false)
	private GRZXCJFKBW gRZXCJFKBW;
	
	@IColumn(description="出错报文文件名")
	@Column(name = "strCCBWWJM", length = 200)
	private String strCCBWWJM;
	
	@IColumn(description="出错原因信息")
	@Column(name = "strCCYYXX_1", length = 50)
	private String strCCYYXX_1;
	
	@IColumn(description="出错原因信息")
	@Column(name = "strCCYYXX_2", length = 50)
	private String strCCYYXX_2;
	
	@IColumn(description="出错原因信息")
	@Column(name = "strCCYYXX_3", length = 50)
	private String strCCYYXX_3;
	
	@IColumn(description="出错原因信息")
	@Column(name = "strCCYYXX_4", length = 50)
	private String strCCYYXX_4;
	
	@IColumn(description="出错原因信息")
	@Column(name = "strCCYYXX_5", length = 50)
	private String strCCYYXX_5;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="所属机构")
	private InstInfo instInfo;
	
	@Transient
	@IColumn(description="出错信息",isListShow = true)
	private String strShowCCXX;
	
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

	public GRZXCJFKBW getGRZXCJFKBW() {
		return gRZXCJFKBW;
	}

	public void setGRZXCJFKBW(GRZXCJFKBW gRZXCJFKBW) {
		this.gRZXCJFKBW = gRZXCJFKBW;
	}

	public String getStrCCBWWJM() {
		return strCCBWWJM;
	}

	public void setStrCCBWWJM(String strCCBWWJM) {
		this.strCCBWWJM = strCCBWWJM;
	}

	public String getStrCCYYXX_1() {
		return strCCYYXX_1;
	}

	public void setStrCCYYXX_1(String strCCYYXX_1) {
		this.strCCYYXX_1 = strCCYYXX_1;
	}

	public String getStrCCYYXX_2() {
		return strCCYYXX_2;
	}

	public void setStrCCYYXX_2(String strCCYYXX_2) {
		this.strCCYYXX_2 = strCCYYXX_2;
	}

	public String getStrCCYYXX_3() {
		return strCCYYXX_3;
	}

	public void setStrCCYYXX_3(String strCCYYXX_3) {
		this.strCCYYXX_3 = strCCYYXX_3;
	}

	public String getStrCCYYXX_4() {
		return strCCYYXX_4;
	}

	public void setStrCCYYXX_4(String strCCYYXX_4) {
		this.strCCYYXX_4 = strCCYYXX_4;
	}

	public String getStrCCYYXX_5() {
		return strCCYYXX_5;
	}

	public void setStrCCYYXX_5(String strCCYYXX_5) {
		this.strCCYYXX_5 = strCCYYXX_5;
	}

	public String getStrShowCCXX() {
        String strCCXXName = "";
		
        String[] tempCCYYXX = {strCCYYXX_1,strCCYYXX_2,strCCYYXX_3,strCCYYXX_4,strCCYYXX_5};
        
        for(String ccxx :tempCCYYXX){
        	if(ccxx.length() > 0){
        		if(!StringUtils.isBlank(strCCXXName)){
        			strCCXXName += " ";
        		}
            	if(ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").containsKey(ccxx)){
            		strCCXXName += ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get(ccxx);
            	}else{
            		strCCXXName += ccxx;
            	}
        	}
        	
        	
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
