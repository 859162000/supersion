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

import zxptsystem.dto.AutoDTO_QY_JKRGK_JC;

@Entity
@Table(name = "QY_JKRGKXX")
@IEntity(description= "借款人概况信息")
public class AutoDTO_QY_JKRGKXX implements java.io.Serializable{

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

	@IColumn(tagMethodName="getJKRGBTag",description="借款人国别")
	@Column(name = "JKRGB", nullable =true)
	private String JKRGB;

	public static Map<String,String> getJKRGBTag() {
		try {
			return HelpTool.getSystemConstVal("JKRGB");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJKRGB() {
		return JKRGB;
	}

	public void setJKRGB(String in) {
		JKRGB = in;
	}

	@Column(name = "JKRZWMC", length = 200, nullable = true)
	@IColumn(description="借款人中文名称")
	private String JKRZWMC;

	public String getJKRZWMC() {
		return JKRZWMC;
	}

	public void setJKRZWMC(String in) {
		JKRZWMC = in;
	}

	@Column(name = "JKRWWMC", length = 200, nullable = true)
	@IColumn(description="借款人外文名称")
	private String JKRWWMC;

	public String getJKRWWMC() {
		return JKRWWMC;
	}

	public void setJKRWWMC(String in) {
		JKRWWMC = in;
	}

	@Column(name = "ZZJGDM", length = 50, nullable = true)
	@IColumn(description="组织机构代码")
	private String ZZJGDM;

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String in) {
		ZZJGDM = in;
	}

	@Column(name = "JKRCLNF", length = 50, nullable = true)
	@IColumn(description="借款人成立年份")
	private String JKRCLNF;

	public String getJKRCLNF() {
		return JKRCLNF;
	}

	public void setJKRCLNF(String in) {
		JKRCLNF = in;
	}

	@IColumn(tagMethodName="getDJZCLXTag",description="登记注册类型")
	@Column(name = "DJZCLX", nullable =true)
	private String DJZCLX;

	public static Map<String,String> getDJZCLXTag() {
		try {
			return HelpTool.getSystemConstVal("DJZCLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDJZCLX() {
		return DJZCLX;
	}

	public void setDJZCLX(String in) {
		DJZCLX = in;
	}

	@Column(name = "DJZCH", length = 50, nullable = true)
	@IColumn(description="登记注册号")
	private String DJZCH;

	public String getDJZCH() {
		return DJZCH;
	}

	public void setDJZCH(String in) {
		DJZCH = in;
	}

	@Column(name = "ZCDJRQ", length = 50, nullable = true)
	@IColumn(description="注册登记日期")
	private String ZCDJRQ;

	public String getZCDJRQ() {
		return ZCDJRQ;
	}

	public void setZCDJRQ(String in) {
		ZCDJRQ = in;
	}

	@Column(name = "YYZZDQRQ", length = 50, nullable = true)
	@IColumn(description="营业执照到期日期")
	private String YYZZDQRQ;

	public String getYYZZDQRQ() {
		return YYZZDQRQ;
	}

	public void setYYZZDQRQ(String in) {
		YYZZDQRQ = in;
	}

	@Column(name = "GSDJZHM", length = 50, nullable = true)
	@IColumn(description="国税登记证号码")
	private String GSDJZHM;

	public String getGSDJZHM() {
		return GSDJZHM;
	}

	public void setGSDJZHM(String in) {
		GSDJZHM = in;
	}

	@Column(name = "DSDJZHM", length = 50, nullable = true)
	@IColumn(description="地税登记证号码")
	private String DSDJZHM;

	public String getDSDJZHM() {
		return DSDJZHM;
	}

	public void setDSDJZHM(String in) {
		DSDJZHM = in;
	}

	@IColumn(tagMethodName="getJKRXZTag",description="借款人性质")
	@Column(name = "JKRXZ", nullable =true)
	private String JKRXZ;

	public static Map<String,String> getJKRXZTag() {
		try {
			return HelpTool.getSystemConstVal("JKRXZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJKRXZ() {
		return JKRXZ;
	}

	public void setJKRXZ(String in) {
		JKRXZ = in;
	}

	@IColumn(tagMethodName="getHYFLTag",description="行业分类")
	@Column(name = "HYFL", nullable =true)
	private String HYFL;

	public static Map<String,String> getHYFLTag() {
		try {
			return HelpTool.getSystemConstVal("HYFL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHYFL() {
		return HYFL;
	}

	public void setHYFL(String in) {
		HYFL = in;
	}

	@Column(name = "CYRS", length = 50, nullable = true)
	@IColumn(description="从业人数")
	private String CYRS;

	public String getCYRS() {
		return CYRS;
	}

	public void setCYRS(String in) {
		CYRS = in;
	}

	@IColumn(tagMethodName="getXZQHTag",description="行政区划")
	@Column(name = "XZQH", nullable =true)
	private String XZQH;

	public static Map<String,String> getXZQHTag() {
		try {
			return HelpTool.getSystemConstVal("XZQH");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXZQH() {
		return XZQH;
	}

	public void setXZQH(String in) {
		XZQH = in;
	}

	@IColumn(tagMethodName="getJKRTZTag",description="借款人特征")
	@Column(name = "JKRTZ", nullable =true)
	private String JKRTZ;

	public static Map<String,String> getJKRTZTag() {
		try {
			return HelpTool.getSystemConstVal("JKRTZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJKRTZ() {
		return JKRTZ;
	}

	public void setJKRTZ(String in) {
		JKRTZ = in;
	}

	@Column(name = "JKRLXDH", length = 100, nullable = true)
	@IColumn(description="借款人联系电话")
	private String JKRLXDH;

	public String getJKRLXDH() {
		return JKRLXDH;
	}

	public void setJKRLXDH(String in) {
		JKRLXDH = in;
	}

	@Column(name = "JKRZCDZ", length = 200, nullable = true)
	@IColumn(description="借款人注册地址")
	private String JKRZCDZ;

	public String getJKRZCDZ() {
		return JKRZCDZ;
	}

	public void setJKRZCDZ(String in) {
		JKRZCDZ = in;
	}

	@Column(name = "JKRCZHM", length = 100, nullable = true)
	@IColumn(description="借款人传真号码")
	private String JKRCZHM;

	public String getJKRCZHM() {
		return JKRCZHM;
	}

	public void setJKRCZHM(String in) {
		JKRCZHM = in;
	}

	@Column(name = "JKREMAILDZ", length = 100, nullable = true)
	@IColumn(description="借款人Email地址")
	private String JKREMAILDZ;

	public String getJKREMAILDZ() {
		return JKREMAILDZ;
	}

	public void setJKREMAILDZ(String in) {
		JKREMAILDZ = in;
	}

	@Column(name = "JKRWZ", length = 100, nullable = true)
	@IColumn(description="借款人网址")
	private String JKRWZ;

	public String getJKRWZ() {
		return JKRWZ;
	}

	public void setJKRWZ(String in) {
		JKRWZ = in;
	}

	@Column(name = "JKRTXDZ", length = 200, nullable = true)
	@IColumn(description="借款人通讯地址")
	private String JKRTXDZ;

	public String getJKRTXDZ() {
		return JKRTXDZ;
	}

	public void setJKRTXDZ(String in) {
		JKRTXDZ = in;
	}

	@Column(name = "YZBM", length = 50, nullable = true)
	@IColumn(description="邮政编码")
	private String YZBM;

	public String getYZBM() {
		return YZBM;
	}

	public void setYZBM(String in) {
		YZBM = in;
	}

	@Column(name = "ZYCPQK", length = 200, nullable = true)
	@IColumn(description="主要产品情况")
	private String ZYCPQK;

	public String getZYCPQK() {
		return ZYCPQK;
	}

	public void setZYCPQK(String in) {
		ZYCPQK = in;
	}

	@Column(name = "JYCDMJ", length = 50, nullable = true)
	@IColumn(description="经营场地面积")
	private String JYCDMJ;

	public String getJYCDMJ() {
		return JYCDMJ;
	}

	public void setJYCDMJ(String in) {
		JYCDMJ = in;
	}

	@IColumn(tagMethodName="getJYCDSYQTag",description="经营场地所有权")
	@Column(name = "JYCDSYQ", nullable =true)
	private String JYCDSYQ;

	public static Map<String,String> getJYCDSYQTag() {
		try {
			return HelpTool.getSystemConstVal("JYCDSYQ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJYCDSYQ() {
		return JYCDSYQ;
	}

	public void setJYCDSYQ(String in) {
		JYCDSYQ = in;
	}

	@IColumn(tagMethodName="getJTKHBZTag",description="集团客户标志")
	@Column(name = "JTKHBZ", nullable =true)
	private String JTKHBZ;

	public static Map<String,String> getJTKHBZTag() {
		try {
			return HelpTool.getSystemConstVal("JTKHBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJTKHBZ() {
		return JTKHBZ;
	}

	public void setJTKHBZ(String in) {
		JTKHBZ = in;
	}

	@IColumn(tagMethodName="getJCKQBZTag",description="进出口权标志")
	@Column(name = "JCKQBZ", nullable =true)
	private String JCKQBZ;

	public static Map<String,String> getJCKQBZTag() {
		try {
			return HelpTool.getSystemConstVal("JCKQBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJCKQBZ() {
		return JCKQBZ;
	}

	public void setJCKQBZ(String in) {
		JCKQBZ = in;
	}

	@IColumn(tagMethodName="getSSGSBZTag",description="上市公司标志")
	@Column(name = "SSGSBZ", nullable =true)
	private String SSGSBZ;

	public static Map<String,String> getSSGSBZTag() {
		try {
			return HelpTool.getSystemConstVal("SSGSBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getSSGSBZ() {
		return SSGSBZ;
	}

	public void setSSGSBZ(String in) {
		SSGSBZ = in;
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
	private zxptsystem.dto.AutoDTO_QY_JKRGK_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_JKRGK_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_JKRGK_JC in) {
		FOREIGNID = in;
	}

}

