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
@Table(name = "AutoETL_ActivityNodeForFile")
@IEntity(navigationName="文件活动结点",description="文件活动结点")
public class AutoETL_ActivityNodeForFile implements java.io.Serializable{
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
	
	public static Map<String,String> getOrderTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("orderType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTableID", nullable = false)
	@IColumn(description="表", isNullable = false)
	private ReportModel_Table autoETL_Table;
	
	@Column(name="strDataSegType",length = 50,nullable=false)
	@IColumn(description="分隔符", isNullable = false,isSpecialCharCheck=true)
	private String strDataSegType;
	
	@Column(name = "cacheLine", nullable = false)
	@IColumn(description="缓存行数", isNullable = false)
	private Integer cacheLine;
	
	@Column(name="strDateFormat",length = 50)
	@IColumn(description="时间类型数据格式")
	private String strDateFormat;
	
	@Column(name="strSystemDateFormat",length = 50)
	@IColumn(description="时间类型系统时间格式")
	private String strSystemDateFormat;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForFile")
	private Set<AutoETL_ActivityNodeForFileC> autoETL_ActivityNodeForFileCs = new HashSet<AutoETL_ActivityNodeForFileC>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForFile")
	private Set<AutoETL_ActivityNodeForFileF> autoETL_ActivityNodeForFileFs = new HashSet<AutoETL_ActivityNodeForFileF>(0);
	
	
	@Id
	@Column(name = "autoActivityNodeForFileID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForFileID;

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

	public String getStrDataSegType() {
		return strDataSegType;
	}


	public void setStrDataSegType(String strDataSegType) {
		this.strDataSegType = strDataSegType;
	}

	public String getStrDiscription() {
		return strDiscription;
	}


	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}


	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}


	public void setAutoActivityNodeForFileID(String autoActivityNodeForFileID) {
		this.autoActivityNodeForFileID = autoActivityNodeForFileID;
	}


	public String getAutoActivityNodeForFileID() {
		return autoActivityNodeForFileID;
	}

	public void setCacheLine(String cacheLine) {
		this.cacheLine = TypeParse.parseInt(cacheLine);
	}


	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setAutoETL_Table(ReportModel_Table autoETL_Table) {
		this.autoETL_Table = autoETL_Table;
	}


	public ReportModel_Table getAutoETL_Table() {
		return autoETL_Table;
	}


	public void setAutoETL_ActivityNodeForFileCs(
			Set<AutoETL_ActivityNodeForFileC> autoETL_ActivityNodeForFileCs) {
		this.autoETL_ActivityNodeForFileCs = autoETL_ActivityNodeForFileCs;
	}


	public Set<AutoETL_ActivityNodeForFileC> getAutoETL_ActivityNodeForFileCs() {
		return autoETL_ActivityNodeForFileCs;
	}


	public void setAutoETL_ActivityNodeForFileFs(
			Set<AutoETL_ActivityNodeForFileF> autoETL_ActivityNodeForFileFs) {
		this.autoETL_ActivityNodeForFileFs = autoETL_ActivityNodeForFileFs;
	}


	public Set<AutoETL_ActivityNodeForFileF> getAutoETL_ActivityNodeForFileFs() {
		return autoETL_ActivityNodeForFileFs;
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

