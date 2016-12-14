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

import zxptsystem.dto.AutoDTO_QY_XYZYW_JC;

@Entity
@Table(name = "QY_XYZYWXX")
@IEntity(description= "信用证业务信息段")
public class AutoDTO_QY_XYZYWXX implements java.io.Serializable{

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

	@Column(name = "JKRMC", length = 200, nullable = true)
	@IColumn(description="借款人名称")
	private String JKRMC;

	public String getJKRMC() {
		return JKRMC;
	}

	public void setJKRMC(String in) {
		JKRMC = in;
	}

	@Column(name = "DKKBM", length = 50, nullable = true)
	@IColumn(description="贷款卡编码")
	private String DKKBM;

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String in) {
		DKKBM = in;
	}

	@IColumn(tagMethodName="getBZTag",description="币种")
	@Column(name = "BZ", nullable =true)
	private String BZ;

	public static Map<String,String> getBZTag() {
		try {
			return HelpTool.getSystemConstVal("BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String in) {
		BZ = in;
	}

	@Column(name = "KZJE", length = 50, nullable = true)
	@IColumn(description="开证金额")
	private String KZJE;

	public String getKZJE() {
		return KZJE;
	}

	public void setKZJE(String in) {
		KZJE = in;
	}

	@Column(name = "KZRQ", length = 50, nullable = true)
	@IColumn(description="开证日期")
	private String KZRQ;

	public String getKZRQ() {
		return KZRQ;
	}

	public void setKZRQ(String in) {
		KZRQ = in;
	}

	@Column(name = "XYZYXQ", length = 50, nullable = true)
	@IColumn(description="信用证有效期")
	private String XYZYXQ;

	public String getXYZYXQ() {
		return XYZYXQ;
	}

	public void setXYZYXQ(String in) {
		XYZYXQ = in;
	}

	@IColumn(tagMethodName="getXYZFKQXTag",description="信用证付款期限")
	@Column(name = "XYZFKQX", nullable =true)
	private String XYZFKQX;

	public static Map<String,String> getXYZFKQXTag() {
		try {
			return HelpTool.getSystemConstVal("XYZFKQX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXYZFKQX() {
		return XYZFKQX;
	}

	public void setXYZFKQX(String in) {
		XYZFKQX = in;
	}

	@Column(name = "BZJBL", length = 50, nullable = true)
	@IColumn(description="保证金比例")
	private String BZJBL;

	public String getBZJBL() {
		return BZJBL;
	}

	public void setBZJBL(String in) {
		BZJBL = in;
	}

	@IColumn(tagMethodName="getDBBZTag",description="担保标志")
	@Column(name = "DBBZ", nullable =true)
	private String DBBZ;

	public static Map<String,String> getDBBZTag() {
		try {
			return HelpTool.getSystemConstVal("DBBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDBBZ() {
		return DBBZ;
	}

	public void setDBBZ(String in) {
		DBBZ = in;
	}

	@IColumn(tagMethodName="getDKBZTag",description="垫款标志")
	@Column(name = "DKBZ", nullable =true)
	private String DKBZ;

	public static Map<String,String> getDKBZTag() {
		try {
			return HelpTool.getSystemConstVal("DKBZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDKBZ() {
		return DKBZ;
	}

	public void setDKBZ(String in) {
		DKBZ = in;
	}

	@IColumn(tagMethodName="getXYZZTTag",description="信用证状态")
	@Column(name = "XYZZT", nullable =true)
	private String XYZZT;

	public static Map<String,String> getXYZZTTag() {
		try {
			return HelpTool.getSystemConstVal("XYZZT");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXYZZT() {
		return XYZZT;
	}

	public void setXYZZT(String in) {
		XYZZT = in;
	}

	@Column(name = "XYZZXRQ", length = 50, nullable = true)
	@IColumn(description="信用证注销日期")
	private String XYZZXRQ;

	public String getXYZZXRQ() {
		return XYZZXRQ;
	}

	public void setXYZZXRQ(String in) {
		XYZZXRQ = in;
	}

	@Column(name = "XYZYE", length = 50, nullable = true)
	@IColumn(description="信用证余额")
	private String XYZYE;

	public String getXYZYE() {
		return XYZYE;
	}

	public void setXYZYE(String in) {
		XYZYE = in;
	}

	@Column(name = "YEBGRQ", length = 50, nullable = true)
	@IColumn(description="余额报告日期")
	private String YEBGRQ;

	public String getYEBGRQ() {
		return YEBGRQ;
	}

	public void setYEBGRQ(String in) {
		YEBGRQ = in;
	}

	@IColumn(tagMethodName="getWJFLTag",description="五级分类")
	@Column(name = "WJFL", nullable =true)
	private String WJFL;

	public static Map<String,String> getWJFLTag() {
		try {
			return HelpTool.getSystemConstVal("WJFL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getWJFL() {
		return WJFL;
	}

	public void setWJFL(String in) {
		WJFL = in;
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
	private zxptsystem.dto.AutoDTO_QY_XYZYW_JC FOREIGNID;

	public zxptsystem.dto.AutoDTO_QY_XYZYW_JC getFOREIGNID() {
		return FOREIGNID;
	}

	public void setFOREIGNID(zxptsystem.dto.AutoDTO_QY_XYZYW_JC in) {
		FOREIGNID = in;
	}

}

