package report.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;

@Entity
@Table(name = "MergeInstance")
public class MergeInstance implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 5605334751852661961L;

	@Id
	@Column(name = "autoMergeID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoMergeID;
	
	@Column(name="strMergeName",length = 50,nullable=false)
	@IColumn(description="汇总关系名称",isKeyName=true, isNullable = false)
	private String strMergeName;
	
	@Column(name="strDescription",length = 200)
	@IColumn(description="描述")
	private String strDescription;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "mergeInst")
	private Set<MergeRule> mergeRules = new HashSet<MergeRule>(0);

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public void setAutoMergeID(String autoMergeID) {
		this.autoMergeID = autoMergeID;
	}

	public String getAutoMergeID() {
		return autoMergeID;
	}

	public void setStrMergeName(String strMergeName) {
		this.strMergeName = strMergeName;
	}

	public String getStrMergeName() {
		return strMergeName;
	}

	public void setMergeRules(Set<MergeRule> mergeRules) {
		this.mergeRules = mergeRules;
	}

	public Set<MergeRule> getMergeRules() {
		return mergeRules;
	}

}
