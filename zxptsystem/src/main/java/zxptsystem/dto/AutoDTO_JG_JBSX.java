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

import zxptsystem.dto.AutoDTO_JG_JGJBXX_JC;

@Entity
@Table(name = "JG_JBSX")
@IEntity(description= "基本属性段")
public class AutoDTO_JG_JBSX implements java.io.Serializable{

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

	@Column(name = "JGZWMC", length = 200, nullable = true)
	@IColumn(description="机构中文名称")
	private String JGZWMC;

	public String getJGZWMC() {
		return JGZWMC;
	}

	public void setJGZWMC(String in) {
		JGZWMC = in;
	}

	@Column(name = "JGYWMC", length = 200, nullable = true)
	@IColumn(description="机构英文名称")
	private String JGYWMC;

	public String getJGYWMC() {
		return JGYWMC;
	}

	public void setJGYWMC(String in) {
		JGYWMC = in;
	}

	@IColumn(description="注册（登记）地址")
	@Column(name = "ZCDJDZ", nullable =true)
	private String ZCDJDZ;

	public String getZCDJDZ() {
		return ZCDJDZ;
	}

	public void setZCDJDZ(String in) {
		ZCDJDZ = in;
	}

	@IColumn(tagMethodName="getJKRGBTag",description="国别")
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

	@IColumn(tagMethodName="getXZQHTag",description="注册（登记）地行政区划")
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

	@Column(name = "CLRQ", length = 50, nullable = true)
	@IColumn(description="成立日期")
	private String CLRQ;

	public String getCLRQ() {
		return CLRQ;
	}

	public void setCLRQ(String in) {
		CLRQ = in;
	}

	@Column(name = "ZSDQR", length = 50, nullable = true)
	@IColumn(description="证书到期日")
	private String ZSDQR;

	public String getZSDQR() {
		return ZSDQR;
	}

	public void setZSDQR(String in) {
		ZSDQR = in;
	}

	@Column(name = "JYYWFW", length = 800, nullable = true)
	@IColumn(description="经营（业务）范围")
	private String JYYWFW;

	public String getJYYWFW() {
		return JYYWFW;
	}

	public void setJYYWFW(String in) {
		JYYWFW = in;
	}

	@IColumn(tagMethodName="getCURRENCYTag",description="注册资本币种")
	@Column(name = "CURRENCY", nullable =true)
	private String CURRENCY;

	public static Map<String,String> getCURRENCYTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String in) {
		CURRENCY = in;
	}

	@Column(name = "ZCZB", length = 50, nullable = true)
	@IColumn(description="注册资本（万元）")
	private String ZCZB;

	public String getZCZB() {
		return ZCZB;
	}

	public void setZCZB(String in) {
		ZCZB = in;
	}

	@IColumn(tagMethodName="getZZJGLXTag",description="组织机构类别")
	@Column(name = "ZZJGLX", nullable =true)
	private String ZZJGLX;

	public static Map<String,String> getZZJGLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZZJGLB");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZZJGLX() {
		return ZZJGLX;
	}

	public void setZZJGLX(String in) {
		ZZJGLX = in;
	}

	@IColumn(tagMethodName="getZZJGLBXFTag",description="组织机构类别细分")
	@Column(name = "ZZJGLBXF", nullable =true)
	private String ZZJGLBXF;

	public static Map<String,String> getZZJGLBXFTag() {
		try {
			return HelpTool.getSystemConstVal("ZZJGLBXF");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZZJGLBXF() {
		return ZZJGLBXF;
	}

	public void setZZJGLBXF(String in) {
		ZZJGLBXF = in;
	}

	@IColumn(tagMethodName="getJJHYFLTag",description="经济行业分类")
	@Column(name = "JJHYFL", nullable =true)
	private String JJHYFL;

	public static Map<String,String> getJJHYFLTag() {
		try {
			return HelpTool.getSystemConstVal("HYFL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJJHYFL() {
		return JJHYFL;
	}

	public void setJJHYFL(String in) {
		JJHYFL = in;
	}

	@IColumn(tagMethodName="getJJLXTag",description="经济类型")
	@Column(name = "JJLX", nullable =true)
	private String JJLX;

	public static Map<String,String> getJJLXTag() {
		try {
			return HelpTool.getSystemConstVal("JJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getJJLX() {
		return JJLX;
	}

	public void setJJLX(String in) {
		JJLX = in;
	}

	@Column(name = "QTXXGXRQ", length = 50, nullable = true)
	@IColumn(description="信息更新日期")
	private String QTXXGXRQ;

	public String getQTXXGXRQ() {
		return QTXXGXRQ;
	}

	public void setQTXXGXRQ(String in) {
		QTXXGXRQ = in;
	}

	@Column(name = "YLZD", length = 100, nullable = true)
	@IColumn(description="预留字段")
	private String YLZD;

	public String getYLZD() {
		return YLZD;
	}

	public void setYLZD(String in) {
		YLZD = in;
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
	private zxptsystem.dto.AutoDTO_JG_JGJBXX_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_JG_JGJBXX_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_JG_JGJBXX_JC in) {
		FOREIGNID = in;
	}

}

