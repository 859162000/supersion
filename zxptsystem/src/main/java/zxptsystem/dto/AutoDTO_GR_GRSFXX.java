package zxptsystem.dto;

import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import framework.interfaces.IColumn;
import framework.show.ShowContext;
import framework.helper.TypeParse;
import extend.helper.HelpTool;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.EmbeddedId;
import javax.persistence.TemporalType;

import java.util.HashSet;
import framework.interfaces.IEntity;
import java.util.Set;

import zxptsystem.dto.AutoDTO_GR_GRXX_JC;

@Entity
@Table(name = "GR_GRSFXX")
@IEntity(description= "身份信息段")
public class AutoDTO_GR_GRSFXX implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden=true)
	@Column(name = "RPTFeedbackType", nullable =true)
	private String RPTFeedbackType;

	public static Map<String,String> getRPTFeedbackTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String in) {
		RPTFeedbackType = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden=true)
	@Column(name = "RPTSendType", nullable =true)
	private String RPTSendType;

	public static Map<String,String> getRPTSendTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSendType");
	}

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5")
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4")
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3")
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2")
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1")
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态",isSingleTagHidden=true)
	@Column(name = "RPTCheckType", nullable =true)
	private String RPTCheckType;

	public static Map<String,String> getRPTCheckTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTCheckType");
	}

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	@IColumn(tagMethodName="getXBTag",description="性别")
	@Column(name = "XB", nullable =true)
	private String XB;

	public static Map<String,String> getXBTag() {
		try {
			return HelpTool.getSystemConstVal("XB");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXB() {
		return XB;
	}

	public void setXB(String in) {
		XB = in;
	}

	@Column(name = "CSRQ", length = 50, nullable = true)
	@IColumn(description="出生日期")
	private String CSRQ;

	public String getCSRQ() {
		return CSRQ;
	}

	public void setCSRQ(String in) {
		CSRQ = in;
	}

	@IColumn(tagMethodName="getHYZKTag",description="婚姻状况")
	@Column(name = "HYZK", nullable =true)
	private String HYZK;

	public static Map<String,String> getHYZKTag() {
		try {
			return HelpTool.getSystemConstVal("HYZK");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHYZK() {
		return HYZK;
	}

	public void setHYZK(String in) {
		HYZK = in;
	}

	@IColumn(tagMethodName="getZGXLTag",description="最高学历")
	@Column(name = "ZGXL", nullable =true)
	private String ZGXL;

	public static Map<String,String> getZGXLTag() {
		try {
			return HelpTool.getSystemConstVal("GGRYZGXL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZGXL() {
		return ZGXL;
	}

	public void setZGXL(String in) {
		ZGXL = in;
	}

	@IColumn(tagMethodName="getZGXWTag",description="最高学位")
	@Column(name = "ZGXW", nullable =true)
	private String ZGXW;

	public static Map<String,String> getZGXWTag() {
		try {
			return HelpTool.getSystemConstVal("ZGXW");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZGXW() {
		return ZGXW;
	}

	public void setZGXW(String in) {
		ZGXW = in;
	}

	@Column(name = "ZZDH", length = 50, nullable = true)
	@IColumn(description="住宅电话")
	private String ZZDH;

	public String getZZDH() {
		return ZZDH;
	}

	public void setZZDH(String in) {
		ZZDH = in;
	}

	@Column(name = "SJHM", length = 50, nullable = true)
	@IColumn(description="手机号码")
	private String SJHM;

	public String getSJHM() {
		return SJHM;
	}

	public void setSJHM(String in) {
		SJHM = in;
	}

	@Column(name = "DWDH", length = 50, nullable = true)
	@IColumn(description="单位电话")
	private String DWDH;

	public String getDWDH() {
		return DWDH;
	}

	public void setDWDH(String in) {
		DWDH = in;
	}

	@Column(name = "DZYX", length = 100, nullable = true)
	@IColumn(description="电子邮箱")
	private String DZYX;

	public String getDZYX() {
		return DZYX;
	}

	public void setDZYX(String in) {
		DZYX = in;
	}

	@Column(name = "TXDZ", length = 200, nullable = true)
	@IColumn(description="通讯地址")
	private String TXDZ;

	public String getTXDZ() {
		return TXDZ;
	}

	public void setTXDZ(String in) {
		TXDZ = in;
	}

	@Column(name = "TXDZYZBM", length = 50, nullable = true)
	@IColumn(description="通讯地址邮政编码")
	private String TXDZYZBM;

	public String getTXDZYZBM() {
		return TXDZYZBM;
	}

	public void setTXDZYZBM(String in) {
		TXDZYZBM = in;
	}

	@Column(name = "HJDZ", length = 200, nullable = true)
	@IColumn(description="户籍地址")
	private String HJDZ;

	public String getHJDZ() {
		return HJDZ;
	}

	public void setHJDZ(String in) {
		HJDZ = in;
	}

	@Column(name = "POXM", length = 100, nullable = true)
	@IColumn(description="配偶姓名")
	private String POXM;

	public String getPOXM() {
		return POXM;
	}

	public void setPOXM(String in) {
		POXM = in;
	}

	@IColumn(tagMethodName="getPOZJLXTag",description="配偶证件类型")
	@Column(name = "POZJLX", nullable =true)
	private String POZJLX;

	public static Map<String,String> getPOZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPOZJLX() {
		return POZJLX;
	}

	public void setPOZJLX(String in) {
		POZJLX = in;
	}

	@Column(name = "POZJHM", length = 50, nullable = true)
	@IColumn(description="配偶证件号码")
	private String POZJHM;

	public String getPOZJHM() {
		return POZJHM;
	}

	public void setPOZJHM(String in) {
		POZJHM = in;
	}

	@Column(name = "POGZDW", length = 200, nullable = true)
	@IColumn(description="配偶工作单位")
	private String POGZDW;

	public String getPOGZDW() {
		return POGZDW;
	}

	public void setPOGZDW(String in) {
		POGZDW = in;
	}

	@Column(name = "POLXDH", length = 50, nullable = true)
	@IColumn(description="配偶联系电话")
	private String POLXDH;

	public String getPOLXDH() {
		return POLXDH;
	}

	public void setPOLXDH(String in) {
		POLXDH = in;
	}

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FOREIGNID", nullable = false)
	private zxptsystem.dto.AutoDTO_GR_GRXX_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_GR_GRXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_GR_GRXX_JC in) {
		FOREIGNID = in;
	}

}

