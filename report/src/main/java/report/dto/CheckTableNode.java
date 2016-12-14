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
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "CheckTableNode")
@IEntity(navigationName="校验表结点",description="校验表结点")
public class CheckTableNode implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -8514915383998628736L;

	@Id
	@Column(name = "checkTableNodeID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkTableNodeID;
	

	@IColumn(tagMethodName="getCheckTableNodeTypeTag",description="结点类型")
	@Column(name = "checkTableNodeType", nullable = false)
	private String checkTableNodeType;
	
	public static Map<String,String> getCheckTableNodeTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("checkTableNodeType");
	}
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTableNode")
	private Set<CheckUniqueField> checkUniqueFields = new HashSet<CheckUniqueField>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTableNode")
	private Set<CheckBasic> checkBasics = new HashSet<CheckBasic>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTableNode")
	private Set<CheckCaseWhen> checkCaseWhens = new HashSet<CheckCaseWhen>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTableNode")
	private Set<CheckFieldEffective> checkFieldEffectives = new HashSet<CheckFieldEffective>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTableNode")
	private Set<CheckFieldLine> checkFieldLines = new HashSet<CheckFieldLine>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkTableNode")
	private Set<CheckFieldOrList> checkFieldOrLists = new HashSet<CheckFieldOrList>(0);

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTable checkTable;
	
	public String getCheckTableNodeID() {
		return checkTableNodeID;
	}

	public void setCheckTableNodeID(String checkTableNodeID) {
		this.checkTableNodeID = checkTableNodeID;
	}

	public void setCheckTableNodeType(String checkTableNodeType) {
		this.checkTableNodeType = checkTableNodeType;
	}

	public String getCheckTableNodeType() {
		return checkTableNodeType;
	}

	public void setCheckTable(CheckTable checkTable) {
		this.checkTable = checkTable;
	}

	public CheckTable getCheckTable() {
		return checkTable;
	}

	public void setCheckUniqueFields(Set<CheckUniqueField> checkUniqueFields) {
		this.checkUniqueFields = checkUniqueFields;
	}

	public Set<CheckUniqueField> getCheckUniqueFields() {
		return checkUniqueFields;
	}

	public void setCheckFieldOrLists(Set<CheckFieldOrList> checkFieldOrLists) {
		this.checkFieldOrLists = checkFieldOrLists;
	}

	public Set<CheckFieldOrList> getCheckFieldOrLists() {
		return checkFieldOrLists;
	}

	public void setCheckBasics(Set<CheckBasic> checkBasics) {
		this.checkBasics = checkBasics;
	}

	public Set<CheckBasic> getCheckBasics() {
		return checkBasics;
	}

	public void setCheckCaseWhens(Set<CheckCaseWhen> checkCaseWhens) {
		this.checkCaseWhens = checkCaseWhens;
	}

	public Set<CheckCaseWhen> getCheckCaseWhens() {
		return checkCaseWhens;
	}

	public void setCheckFieldEffectives(Set<CheckFieldEffective> checkFieldEffectives) {
		this.checkFieldEffectives = checkFieldEffectives;
	}

	public Set<CheckFieldEffective> getCheckFieldEffectives() {
		return checkFieldEffectives;
	}

	public void setCheckFieldLines(Set<CheckFieldLine> checkFieldLines) {
		this.checkFieldLines = checkFieldLines;
	}

	public Set<CheckFieldLine> getCheckFieldLines() {
		return checkFieldLines;
	}
}
