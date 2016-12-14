package report.dto;

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
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "CheckTable")
@IEntity(navigationName="校验表",description="校验表")
public class CheckTable implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 7456646127748053215L;

	@Id
	@Column(name = "checkTableID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkTableID;
	
	@Column(name="procedureName",length = 50,nullable=true)
	@IColumn(description="存储过程名称",isKeyName=true)
	private String procedureName;
	
	@Column(name="procedureDiscription",length = 50,nullable=true)
	@IColumn(description="存储过程描述")
	private String procedureDiscription;
	
	@Column(name="procedureParam",length = 50,nullable=true)
	@IColumn(description="存储过程参数")
	private String procedureParam;
	
	@Column(name="originTable",length = 50,nullable=true)
	@IColumn(description="原表")
	private String originTable;
	
	@Column(name="errorMsgField",length = 50)
	@IColumn(description="描述字段名",isSingleTagHidden=true)
	private String errorMsgField;
	
	@Column(name="errorStateField",length = 50,nullable=true)
	@IColumn(description="状态字段")
	private String errorStateField;
	
	@Column(name="errorStateValue",length = 50,nullable=true)
	@IColumn(description="错误状态值")
	private String errorStateValue;
	
	@Column(name="successStateValue",length = 50,nullable=true)
	@IColumn(description="成功状态值")
	private String successStateValue;

	@IColumn(tagMethodName="getdefaultLogicDaoBeanIdTag",description="默认DaoBean名")
	@Column(name = "defaultLogicDaoBeanId",length = 50,nullable=true)
	private String defaultLogicDaoBeanId;
	
	public static Map<String,String> getdefaultLogicDaoBeanIdTag(){
		return ShowContext.getInstance().getShowEntityMap().get("defaultLogicDaoBeanId");
	}
	
	@Column(name="periodFieldField",length = 50,nullable=true)
	@IColumn(description="periodFieldField",isSingleTagHidden=true)
	private String periodFieldField;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkNodeID")
	@IColumn(isSingleTagHidden=true)
	private CheckNode checkNode;

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTable")
	private Set<CheckExtendTableParam> checkExtendTableParams = new HashSet<CheckExtendTableParam>(0);
	
	@Transient
	@IColumn(isIdListField = true, target="checkExtendTableParams", mappedBy = "autoETL_Param")
	private String[] autoETL_ParamIdList;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTable")
	private Set<CheckTableNode> checkTableNodes = new HashSet<CheckTableNode>(0);

	public String getCheckTableID() {
		return checkTableID;
	}

	public void setCheckTableID(String checkTableID) {
		this.checkTableID = checkTableID;
	}

	public CheckNode getCheckNode() {
		return checkNode;
	}

	public void setCheckNode(CheckNode checkNode) {
		this.checkNode = checkNode;
	}

	public void setCheckTableNodes(Set<CheckTableNode> checkTableNodes) {
		this.checkTableNodes = checkTableNodes;
	}

	public Set<CheckTableNode> getCheckTableNodes() {
		return checkTableNodes;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

	public String getProcedureDiscription() {
		return procedureDiscription;
	}

	public void setCheckExtendTableParams(Set<CheckExtendTableParam> checkExtendTableParams) {
		this.checkExtendTableParams = checkExtendTableParams;
	}

	public Set<CheckExtendTableParam> getCheckExtendTableParams() {
		return checkExtendTableParams;
	}

	public void setAutoETL_ParamIdList(String[] autoETL_ParamIdList) {
		this.autoETL_ParamIdList = autoETL_ParamIdList;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

	public void setOriginTable(String originTable) {
		this.originTable = originTable;
	}

	public String getOriginTable() {
		return originTable;
	}

	public String getErrorStateField() {
		return errorStateField;
	}

	public void setErrorStateField(String errorStateField) {
		this.errorStateField = errorStateField;
	}

	public String getErrorStateValue() {
		return errorStateValue;
	}

	public void setErrorStateValue(String errorStateValue) {
		this.errorStateValue = errorStateValue;
	}

	public String getSuccessStateValue() {
		return successStateValue;
	}

	public void setSuccessStateValue(String successStateValue) {
		this.successStateValue = successStateValue;
	}

	public String getDefaultLogicDaoBeanId() {
		return defaultLogicDaoBeanId;
	}

	public void setDefaultLogicDaoBeanId(String defaultLogicDaoBeanId) {
		this.defaultLogicDaoBeanId = defaultLogicDaoBeanId;
	}

	public void setErrorMsgField(String errorMsgField) {
		this.errorMsgField = errorMsgField;
	}

	public String getErrorMsgField() {
		return errorMsgField;
	}

	public void setPeriodFieldField(String periodFieldField) {
		this.periodFieldField = periodFieldField;
	}

	public String getPeriodFieldField() {
		return periodFieldField;
	}

	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getProcedureParam() {
		return procedureParam;
	}
}
