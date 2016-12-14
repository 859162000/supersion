package zdzsystem.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import extend.helper.HelpTool;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;
import zdzsystem.oxm.Kzqqbzxrzhxx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "ZDZ_KZQQJTNR")
@IEntity(description= "控制请求具体内容")
@XStreamAlias("kzqq")
public class AutoDTO_ZDZ_KZQQJTNR implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5", isNullable = false)
	@XStreamOmitField
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4", isNullable = false)
    @XStreamOmitField
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3", isNullable = false)
    @XStreamOmitField
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2", isNullable = false)
    @XStreamOmitField
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1", isNullable = false)
    @XStreamOmitField
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态")
	@Column(name = "RPTFeedbackType", nullable =true)
    @XStreamOmitField
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

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态")
	@Column(name = "RPTSubmitStatus", nullable =true)
    @XStreamOmitField
	private String RPTSubmitStatus;

	public static Map<String,String> getRPTSubmitStatusTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSubmitStatus");
	}

	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String in) {
		RPTSubmitStatus = in;
	}

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态")
	@Column(name = "RPTVerifyType", nullable =true)
    @XStreamOmitField
	private String RPTVerifyType;

	public static Map<String,String> getRPTVerifyTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTVerifyType");
	}

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String in) {
		RPTVerifyType = in;
	}

	@Column(name = "lastUpdateDate", nullable = true)
	@IColumn(description="最后修改时间", isNullable = false)
    @XStreamOmitField
	private Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String in) {
		lastUpdateDate = TypeParse.parseTimestamp(in);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operationUser")
    @XStreamOmitField
	private UserInfo operationUser;

	public UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(UserInfo in) {
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态")
	@Column(name = "RPTSendType", nullable =true)
    @XStreamOmitField
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

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态")
	@Column(name = "RPTCheckType", nullable =true)
    @XStreamOmitField
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instInfo")
    @XStreamOmitField
	private InstInfo instInfo;

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo in) {
		instInfo = in;
	}

	@Column(name = "dtDate", nullable = true)
	@IColumn(description="数据时间", isNullable = false)
	@Temporal(TemporalType.DATE)
    @XStreamOmitField
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Id
	@Column(name = "BDHM", length = 100, nullable = false)
	@IColumn(description="控制请求单号")
    @XStreamAsAttribute
	private String BDHM;

	public String getBDHM() {
		return BDHM;
	}

	public void setBDHM(String in) {
		BDHM = in;
	}

	@Column(name = "LB", length = 50, nullable = true)
	@IColumn(description="类别")
    @XStreamAsAttribute
	private String LB;

	public String getLB() {
		return LB;
	}

	public void setLB(String in) {
		LB = in;
	}

	@IColumn(tagMethodName="getXZTag",description="性质")
	@Column(name = "XZ", nullable =true)
    @XStreamAsAttribute
	private String XZ;

	public static Map<String,String> getXZTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_XZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getXZ() {
		return XZ;
	}

	public void setXZ(String in) {
		XZ = in;
	}

	@Column(name = "XM", length = 200, nullable = true)
	@IColumn(description="被查询人姓名",isKeyName=true)
    @XStreamAsAttribute
	private String XM;

	public String getXM() {
		return XM;
	}

	public void setXM(String in) {
		XM = in;
	}

	@IColumn(tagMethodName="getZJLXTag",description="证件类型")
	@Column(name = "ZJLX", nullable =true)
    @XStreamAsAttribute
	private String ZJLX;

	public static Map<String,String> getZJLXTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_ZJLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String in) {
		ZJLX = in;
	}

	@Column(name = "DSRZJHM", length = 100, nullable = true)
	@IColumn(description="被查询人证件/组织机构号码")
    @XStreamAsAttribute
	private String DSRZJHM;

	public String getDSRZJHM() {
		return DSRZJHM;
	}

	public void setDSRZJHM(String in) {
		DSRZJHM = in;
	}

	@Column(name = "FYMC", length = 100, nullable = true)
	@IColumn(description="执行法院名称")
    @XStreamAsAttribute
	private String FYMC;

	public String getFYMC() {
		return FYMC;
	}

	public void setFYMC(String in) {
		FYMC = in;
	}

	@Column(name = "FYDM", length = 50, nullable = true)
	@IColumn(description="执行法院代码")
    @XStreamAsAttribute
	private String FYDM;

	public String getFYDM() {
		return FYDM;
	}

	public void setFYDM(String in) {
		FYDM = in;
	}

	@Column(name = "CBR", length = 50, nullable = true)
	@IColumn(description="承办法官")
    @XStreamAsAttribute
	private String CBR;

	public String getCBR() {
		return CBR;
	}

	public void setCBR(String in) {
		CBR = in;
	}

	@Column(name = "YHDH", length = 100, nullable = true)
	@IColumn(description="承办法官联系电话")
    @XStreamAsAttribute
	private String YHDH;

	public String getYHDH() {
		return YHDH;
	}

	public void setYHDH(String in) {
		YHDH = in;
	}

	@Column(name = "AH", length = 100, nullable = true)
	@IColumn(description="执行案号")
    @XStreamAsAttribute
	private String AH;

	public String getAH() {
		return AH;
	}

	public void setAH(String in) {
		AH = in;
	}

	@Column(name = "GZZBH", length = 50, nullable = true)
	@IColumn(description="承办法官工作证编号")
    @XStreamAsAttribute
	private String GZZBH;

	public String getGZZBH() {
		return GZZBH;
	}

	public void setGZZBH(String in) {
		GZZBH = in;
	}

	@Column(name = "GWZBH", length = 50, nullable = true)
	@IColumn(description="承办法官公务证编号")
    @XStreamAsAttribute
	private String GWZBH;

	public String getGWZBH() {
		return GWZBH;
	}

	public void setGWZBH(String in) {
		GWZBH = in;
	}

	@Column(name = "CKH", length = 100, nullable = true)
	@IColumn(description="控制通知书名称")
    @XStreamAsAttribute
	private String CKH;

	public String getCKH() {
		return CKH;
	}

	public void setCKH(String in) {
		CKH = in;
	}

	@Column(name = "LXFS", length = 100, nullable = true)
	@IColumn(description="承办人联系地址")
    @XStreamAsAttribute
	private String LXFS;

	public String getLXFS() {
		return LXFS;
	}

	public void setLXFS(String in) {
		LXFS = in;
	}

	@Column(name = "BEIZ", length = 2000, nullable = true)
	@IColumn(description="备注")
    @XStreamAsAttribute
	private String BEIZ;

	public String getBEIZ() {
		return BEIZ;
	}

	public void setBEIZ(String in) {
		BEIZ = in;
	}
	
	@Column(name = "isMatchCustInfo", length = 50, nullable = true)
	@IColumn(description="是否匹配客户信息",tagMethodName="getIsMatchCustInfoTag")
    @XStreamOmitField
	private String isMatchCustInfo;

	public static Map<String,String> getIsMatchCustInfoTag() {
		try {
			return HelpTool.getSystemConstVal("ZDZ_IsMatchCustInfo");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getIsMatchCustInfo() {
		return isMatchCustInfo;
	}

	public void setIsMatchCustInfo(String isMatchCustInfo) {
		this.isMatchCustInfo = isMatchCustInfo;
	}

	@Column(name = "BatchNumber", length = 50, nullable = true)
	@IColumn(description="批次号")
    @XStreamOmitField
	private String BatchNumber;

	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String in) {
		BatchNumber = in;
	}

    @Transient
    @XStreamImplicit
    private List<Kzqqbzxrzhxx> kzzhlist;

    public List<Kzqqbzxrzhxx> getKzzhlist() {
        return kzzhlist;
    }

    public void setKzzhlist(List<Kzqqbzxrzhxx> kzzhlist) {
        this.kzzhlist = kzzhlist;
    }
}

