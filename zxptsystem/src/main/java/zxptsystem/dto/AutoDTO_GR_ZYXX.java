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
@Table(name = "GR_ZYXX")
@IEntity(description= "职业信息段")
public class AutoDTO_GR_ZYXX implements java.io.Serializable{

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

	@IColumn(tagMethodName="getZYTag",description="职业")
	@Column(name = "ZY", nullable =true)
	private String ZY;

	public static Map<String,String> getZYTag() {
		try {
			return HelpTool.getSystemConstVal("ZY");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZY() {
		return ZY;
	}

	public void setZY(String in) {
		ZY = in;
	}

	@Column(name = "DWMC", length = 200, nullable = true)
	@IColumn(description="单位名称")
	private String DWMC;

	public String getDWMC() {
		return DWMC;
	}

	public void setDWMC(String in) {
		DWMC = in;
	}

	@IColumn(tagMethodName="getDWSSHYTag",description="单位所属行业")
	@Column(name = "DWSSHY", nullable =true)
	private String DWSSHY;

	public static Map<String,String> getDWSSHYTag() {
		try {
			return HelpTool.getSystemConstVal("DWSSHY");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDWSSHY() {
		return DWSSHY;
	}

	public void setDWSSHY(String in) {
		DWSSHY = in;
	}

	@Column(name = "DWDZ", length = 200, nullable = true)
	@IColumn(description="单位地址")
	private String DWDZ;

	public String getDWDZ() {
		return DWDZ;
	}

	public void setDWDZ(String in) {
		DWDZ = in;
	}

	@Column(name = "DWDZYZBM", length = 50, nullable = true)
	@IColumn(description="单位地址邮政编码")
	private String DWDZYZBM;

	public String getDWDZYZBM() {
		return DWDZYZBM;
	}

	public void setDWDZYZBM(String in) {
		DWDZYZBM = in;
	}

	@Column(name = "BDWGZQSNF", length = 50, nullable = true)
	@IColumn(description="本单位工作起始年份")
	private String BDWGZQSNF;

	public String getBDWGZQSNF() {
		return BDWGZQSNF;
	}

	public void setBDWGZQSNF(String in) {
		BDWGZQSNF = in;
	}

	@IColumn(tagMethodName="getZWTag",description="职务")
	@Column(name = "ZW", nullable =true)
	private String ZW;

	public static Map<String,String> getZWTag() {
		try {
			return HelpTool.getSystemConstVal("ZW");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZW() {
		return ZW;
	}

	public void setZW(String in) {
		ZW = in;
	}

	@IColumn(tagMethodName="getZCTag",description="职称")
	@Column(name = "ZC", nullable =true)
	private String ZC;

	public static Map<String,String> getZCTag() {
		try {
			return HelpTool.getSystemConstVal("ZC");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZC() {
		return ZC;
	}

	public void setZC(String in) {
		ZC = in;
	}

	@Column(name = "NSR", length = 50, nullable = true)
	@IColumn(description="年收入")
	private String NSR;

	public String getNSR() {
		return NSR;
	}

	public void setNSR(String in) {
		NSR = in;
	}

	@Column(name = "GZZH", length = 100, nullable = true)
	@IColumn(description="工资账号")
	private String GZZH;

	public String getGZZH() {
		return GZZH;
	}

	public void setGZZH(String in) {
		GZZH = in;
	}

	@Column(name = "GZZHKHYH", length = 50, nullable = true)
	@IColumn(description="工资账户开户银行")
	private String GZZHKHYH;

	public String getGZZHKHYH() {
		return GZZHKHYH;
	}

	public void setGZZHKHYH(String in) {
		GZZHKHYH = in;
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

