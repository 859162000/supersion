package coresystem.dto;

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
@Table(name = "UserNeedDoThing")
public class UserNeedDoThing  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode", nullable = false)
	private UserInfo userInfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "needDoThingId", nullable = false)
	private NeedDoThing needDoThing;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setNeedDoThing(NeedDoThing needDoThing) {
		this.needDoThing = needDoThing;
	}

	public NeedDoThing getNeedDoThing() {
		return needDoThing;
	}
}
