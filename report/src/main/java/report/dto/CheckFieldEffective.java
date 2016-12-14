package report.dto;

import java.util.HashSet;
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

@Entity
@Table(name = "CheckFieldEffective")
@IEntity(navigationName="有效性校验",description="有效性校验")
public class CheckFieldEffective implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -2689381787549225529L;

	@Id
	@Column(name = "checkFieldEffectiveID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldEffectiveID;
	
	@Column(name="name",length =35,nullable=false)
	@IColumn(description="字段名",isKeyName=true)
	private String name;
	
	@Column(name="discription",length =35,nullable=false)
	@IColumn(description="描述")
	private String discription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTableNode checkTableNode;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldEffective")
	private Set<CheckFieldEffectiveValues> checkFieldEffectiveValues = new HashSet<CheckFieldEffectiveValues>(0);

	public String getCheckFieldEffectiveID() {
		return checkFieldEffectiveID;
	}

	public void setCheckFieldEffectiveID(String checkFieldEffectiveID) {
		this.checkFieldEffectiveID = checkFieldEffectiveID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}
	
	public void setCheckFieldEffectiveValues(
			Set<CheckFieldEffectiveValues> checkFieldEffectiveValues) {
		this.checkFieldEffectiveValues = checkFieldEffectiveValues;
	}

	public Set<CheckFieldEffectiveValues> getCheckFieldEffectiveValues() {
		return checkFieldEffectiveValues;
	}

	public void setCheckTableNode(CheckTableNode checkTableNode) {
		this.checkTableNode = checkTableNode;
	}

	public CheckTableNode getCheckTableNode() {
		return checkTableNode;
	}
	
	
}
