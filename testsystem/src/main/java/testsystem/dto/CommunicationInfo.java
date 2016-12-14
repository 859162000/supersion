package testsystem.dto;

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


@Entity
@Table(name = "CommunicationInfo")
public class CommunicationInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Column(name = "field1")
	private String field1;

	@Id
	@Column(name = "communicationInfoId")
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String communicationInfoId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "communicationInfo")
	private Set<Reply> replys = new HashSet<Reply>(0);

	
	public void setCommunicationInfoId(String communicationInfoId) {
		this.communicationInfoId = communicationInfoId;
	}

	public String getCommunicationInfoId() {
		return communicationInfoId;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField1() {
		return field1;
	}

	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}

	public Set<Reply> getReplys() {
		return replys;
	}
	
	
}

