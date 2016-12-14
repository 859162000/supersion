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
@Table(name = "CheckFieldOrList")
@IEntity(navigationName="或校验",description="或校验")
public class CheckFieldOrList implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -7152869658862493058L;

	@Id
	@Column(name = "checkFieldOrListID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldOrListID;
	
	@Column(name="discription",length =35,nullable=false)
	@IColumn(description="描述",isKeyName=true)
	private String discription;
	
	@IColumn(tagMethodName="getOrTypeTag",description="类型")
	@Column(name = "type", nullable = true,length=10)
	private String type;
	
	public static Map<String,String> getOrTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("orType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTableNode checkTableNode;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldOrList")
	private Set<CheckOrBasic> checkOrBasics = new HashSet<CheckOrBasic>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldOrList")
	private Set<CheckOrCaseWhen> checkOrCaseWhens = new HashSet<CheckOrCaseWhen>(0);
	
	public String getCheckFieldOrListID() {
		return checkFieldOrListID;
	}

	public void setCheckFieldOrListID(String checkFieldOrListID) {
		this.checkFieldOrListID = checkFieldOrListID;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public void setCheckTableNode(CheckTableNode checkTableNode) {
		this.checkTableNode = checkTableNode;
	}

	public CheckTableNode getCheckTableNode() {
		return checkTableNode;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setCheckOrBasics(Set<CheckOrBasic> checkOrBasics) {
		this.checkOrBasics = checkOrBasics;
	}

	public Set<CheckOrBasic> getCheckOrBasics() {
		return checkOrBasics;
	}

	public void setCheckOrCaseWhens(Set<CheckOrCaseWhen> checkOrCaseWhens) {
		this.checkOrCaseWhens = checkOrCaseWhens;
	}

	public Set<CheckOrCaseWhen> getCheckOrCaseWhens() {
		return checkOrCaseWhens;
	}
}
