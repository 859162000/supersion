package autoETLsystem.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import extend.dto.ReportModel_Table;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForExcel")
@IEntity(navigationName="Excle活动结点",description="Excle活动结点")
public class AutoETL_ActivityNodeForExcel  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@IColumn(tagMethodName="getIOTypeTag",description="输入输出类型", isNullable = false)
	@Column(name = "strIOType",length = 50, nullable = false)
	private String strIOType;
	
	public static Map<String,String> getIOTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("iOType");
	}
	
	@Column(name="strPath",length = 200,nullable=false)
	@IColumn(description="路径", isNullable = false,isSpecialCharCheck=true)
	private String strPath;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTableID", nullable = false)
	@IColumn(description="表", isNullable = false)
	private ReportModel_Table autoETL_Table;
	
	@Column(name = "cacheLine", nullable = false)
	@IColumn(description="缓存行数", isNullable = false)
	private Integer cacheLine;
	
	@Column(name = "showInstanceName")
	@IColumn(description="实例名")
	private String showInstanceName;
	
	@Column(name = "strSheetNames", nullable = false)
	@IColumn(description="Sheet名字(多个以逗号分隔)", isNullable = false)
	private String strSheetNames;
	
	@IColumn(tagMethodName="getKeyTypeTag",description="键值类型", isNullable = false)
	@Column(name = "strKeyType",length = 50, nullable = false)
	private String strKeyType;
	
	public static Map<String,String> getKeyTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("keyType");
	}
	
	@IColumn(tagMethodName="getTitleTypeTag",description="是否显示标题", isNullable = false)
	@Column(name = "strTitleType",length = 50, nullable = false)
	private String strTitleType;
	
	public static Map<String,String> getTitleTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("titleType");
	}
	
	@Column(name = "strLine", nullable = false)
	@IColumn(description="开始行", isNullable = false)
	private Integer startLine;
	
	@Column(name = "strCol", nullable = false)
	@IColumn(description="开始列", isNullable = false)
	private Integer startCol;
	
	@Column(name = "strIgnorSeg")
	@IColumn(description="导入数据舍弃分隔字符")
	private String strIgnorSeg;
	
	@Column(name="strDateFormat",length = 50)
	@IColumn(description="时间类型数据格式")
	private String strDateFormat;
	
	@Column(name="strSystemDateFormat",length = 50)
	@IColumn(description="时间类型系统时间格式")
	private String strSystemDateFormat;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForExcel")
	private Set<AutoETL_ActivityNodeForExcelC> autoETL_ActivityNodeForExcelCs = new HashSet<AutoETL_ActivityNodeForExcelC>(0);
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	public String getStrIOType() {
		return strIOType;
	}

	public void setStrIOType(String strIOType) {
		this.strIOType = strIOType;
	}

	public String getStrPath() {
		return strPath;
	}

	public void setStrPath(String strPath) {
		this.strPath = strPath;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETLActivityNode) {
		autoETL_ActivityNode = autoETLActivityNode;
	}

	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setCacheLine(String cacheLine) {
		this.cacheLine = TypeParse.parseInt(cacheLine);;
	}

	public String getStrKeyType() {
		return strKeyType;
	}

	public void setStrKeyType(String strKeyType) {
		this.strKeyType = strKeyType;
	}

	public String getStrTitleType() {
		return strTitleType;
	}

	public void setStrTitleType(String strTitleType) {
		this.strTitleType = strTitleType;
	}

	public Integer getStartLine() {
		return startLine;
	}

	public void setStartLine(String startLine) {
		this.startLine = TypeParse.parseInt(startLine);
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(String startCol) {
		this.startCol = TypeParse.parseInt(startCol);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setShowInstanceName(String showInstanceName) {
		this.showInstanceName = showInstanceName;
	}

	public String getShowInstanceName() {
		return showInstanceName;
	}

	public void setStrSheetNames(String strSheetNames) {
		this.strSheetNames = strSheetNames;
	}

	public String getStrSheetNames() {
		return strSheetNames;
	}

	public void setStrIgnorSeg(String strIgnorSeg) {
		this.strIgnorSeg = strIgnorSeg;
	}

	public String getStrIgnorSeg() {
		return strIgnorSeg;
	}

	public void setAutoETL_Table(ReportModel_Table autoETL_Table) {
		this.autoETL_Table = autoETL_Table;
	}

	public ReportModel_Table getAutoETL_Table() {
		return autoETL_Table;
	}

	public void setAutoETL_ActivityNodeForExcelCs(
			Set<AutoETL_ActivityNodeForExcelC> autoETL_ActivityNodeForExcelCs) {
		this.autoETL_ActivityNodeForExcelCs = autoETL_ActivityNodeForExcelCs;
	}

	public Set<AutoETL_ActivityNodeForExcelC> getAutoETL_ActivityNodeForExcelCs() {
		return autoETL_ActivityNodeForExcelCs;
	}

	public void setStrDateFormat(String strDateFormat) {
		this.strDateFormat = strDateFormat;
	}

	public String getStrDateFormat() {
		return strDateFormat;
	}

	public void setStrSystemDateFormat(String strSystemDateFormat) {
		this.strSystemDateFormat = strSystemDateFormat;
	}

	public String getStrSystemDateFormat() {
		return strSystemDateFormat;
	}
}
