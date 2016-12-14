package report.dto;

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


import extend.dto.ReportModel_Table;
import extend.helper.HelpTool;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "CheckToReportModel_Field")
@IEntity(navigationName="校验字段",description="校验字段")
public class CheckToReportModel_Field implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -350013012526220415L;

	@Column(name="strFieldName", length = 128, nullable=false)
	@IColumn(description="字段名", isNullable = false)
	private String strFieldName;

	@Column(name="strChinaName", length = 50, nullable = false)
	@IColumn(description="中文名",isKeyName=true, isNullable = false)
	private String strChinaName;
	
	@Column(name="nSeq", nullable=false)
	@IColumn(description="显示顺序", isNullable = false)
	private Integer nSeq;

	@IColumn(tagMethodName="getFieldTypeTag", description="字段类型", isNullable = false)
	@Column(name = "strFieldType", nullable = false)
	private String strFieldType;
	
	public static Map<String,String> getFieldTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("fieldType");
	}
	
	@Column(name="nLength")
	@IColumn(description="长度")
	private Integer nLength;
	
	@Column(name="nPrecise")
	@IColumn(description="精度")
	private Integer nPrecise;
	
	@IColumn(tagMethodName="getEmptyTypeTag",description="数据库是否为空", isNullable = false)
	@Column(name = "strEmptyType", nullable = false)
	private String strEmptyType;
	
	public static Map<String,String> getEmptyTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("emptyType");
	}
	
	@IColumn(tagMethodName="getEmptyTypeTag",description="逻辑是否为空", isNullable = false)
	@Column(name = "strLogicEmptyType", nullable = false)
	private String strLogicEmptyType;
	
	@IColumn(tagMethodName="getIsKeyTag",description="是否为主键", isNullable = false)
	@Column(name = "IsKey", nullable = false)
	private String IsKey;
	
	public static Map<String,String> getIsKeyTag(){
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Column(name="strConstList",length = 200)
	@IColumn(tagMethodName="getConstTag", description="常量列表")
	private String strConstList;
	
	// 可用常量为系统*Entity.xml中配置的所有常量及其中ForeignTable下的所有外键数据库表
	public static Map<String,String> getConstTag(){
		return ShowContext.getInstance().getShowEntityMap().get("foreignEntity");
	}
	
	@Column(name="strDBConstList",length = 200)
	@IColumn(tagMethodName="getDBConstTag", description="数据库常量")
	private String strDBConstList;
	
	// 可用常量为SystemCodeSet表中的常量
	public static Map<String,String> getDBConstTag() {
		try {
			return HelpTool.getSystemConstSet();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name="isEnable")
	@IColumn(description="是否可用", tagMethodName="getIsVisibleTag")
	private String isEnable;
	
	public static Map<String,String> getIsVisibleTag() {
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}
	/*
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "reportModel_Field")
	private Set<ReportModel_FieldCondition> reportModel_FieldConditions = new HashSet<ReportModel_FieldCondition>(0);
	*/
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AutoTableID", nullable = false)
	private ReportModel_Table reportModel_Table;
	
	@Id
	@Column(name = "autoFieldID", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoFieldID;


	public String getStrFieldType() {
		return strFieldType;
	}

	public void setStrFieldType(String strFieldType) {
		this.strFieldType = strFieldType;
	}

	public String getStrEmptyType() {
		return strEmptyType;
	}

	public void setStrEmptyType(String strEmptyType) {
		this.strEmptyType = strEmptyType;
	}

	public String getStrFieldName() {
		return strFieldName;
	}

	public void setStrFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public String getIsKey() {
		return IsKey;
	}

	public void setIsKey(String isKey) {
		IsKey = isKey;
	}

	public void setAutoFieldID(String autoFieldID) {
		this.autoFieldID = autoFieldID;
	}

	public String getAutoFieldID() {
		return autoFieldID;
	}

	public void setStrConstList(String strConstList) {
		this.strConstList = strConstList;
	}

	public String getStrConstList() {
		return strConstList;
	}

	public void setReportModel_Table(ReportModel_Table reportModel_Table) {
		this.reportModel_Table = reportModel_Table;
	}

	public ReportModel_Table getReportModel_Table() {
		return reportModel_Table;
	}

	public void setStrChinaName(String strChinaName) {
		this.strChinaName = strChinaName;
	}

	public String getStrChinaName() {
		return strChinaName;
	}

	public void setNLength(String nLength) {
		this.nLength = TypeParse.parseInt(nLength);
	}

	public Integer getNLength() {
		return nLength;
	}

	public void setNPrecise(String nPrecise) {
		this.nPrecise = TypeParse.parseInt(nPrecise);
	}

	public Integer getNPrecise() {
		return nPrecise;
	}

	public void setNSeq(String nSeq) {
		this.nSeq = TypeParse.parseInt(nSeq);
	}

	public Integer getNSeq() {
		return nSeq;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setStrDBConstList(String strDBConstList) {
		this.strDBConstList = strDBConstList;
	}

	public String getStrDBConstList() {
		return strDBConstList;
	}

	public void setStrLogicEmptyType(String strLogicEmptyType) {
		this.strLogicEmptyType = strLogicEmptyType;
	}

	public String getStrLogicEmptyType() {
		return strLogicEmptyType;
	}
}




