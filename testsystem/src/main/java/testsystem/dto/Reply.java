package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Reply")
public class Reply implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Column(name = "field2")
	private String field2;

	@Id
	@Column(name = "replyId")
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String replyId;

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField2() {
		return field2;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getReplyId() {
		return replyId;
	}
	
	public void setCommunicationInfo(CommunicationInfo communicationInfo) {
		this.communicationInfo = communicationInfo;
	}

	public CommunicationInfo getCommunicationInfo() {
		return communicationInfo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strCommunicationInfoCode", nullable = false)
	private CommunicationInfo communicationInfo;

}

