package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;



@Entity
@Table(name = "CheckUniqueField")
@IEntity(navigationName="校验唯一性",description="校验唯一性")
public class CheckUniqueField implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -7575774805112126563L;

	@Id
	@Column(name = "checkUniqueFieldID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkUniqueFieldID;
	
	@Column(name="name",length =35,nullable=false)
	@IColumn(description="字段名",isKeyName=true)
	private String name;
	
	@Column(name="discription",length =35,nullable=false)
	@IColumn(description="描述")
	private String discription;
	
	@Column(name="originField",length =35,nullable=false)
	@IColumn(description="原字段")
	private String originField;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTableNode checkTableNode;

	public String getCheckUniqueFieldID() {
		return checkUniqueFieldID;
	}

	public void setCheckUniqueFieldID(String checkUniqueFieldID) {
		this.checkUniqueFieldID = checkUniqueFieldID;
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

	public void setCheckTableNode(CheckTableNode checkTableNode) {
		this.checkTableNode = checkTableNode;
	}

	public CheckTableNode getCheckTableNode() {
		return checkTableNode;
	}

	public void setOriginField(String originField) {
		this.originField = originField;
	}

	public String getOriginField() {
		return originField;
	}
}
