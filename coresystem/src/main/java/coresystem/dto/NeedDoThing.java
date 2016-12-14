package coresystem.dto;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;

@Entity
@Table(name = "NeedDoThing")
public class NeedDoThing  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="名称",isNullable=false)
	@Column(name = "thingName", length = 280)
	private String thingName;
	
	@IColumn(description="SQL语句",isNullable=false,isSpecialCharCheck=true)
	@Column(name = "countSql", length = 4000)
	private String countSql;
	
	@IColumn(description="连接事件",isSpecialCharCheck=true)
	@Column(name = "strActionName", length = 200)
	private String strActionName;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "needDoThing")
	private Set<UserNeedDoThing> userNeedDoThings = new HashSet<UserNeedDoThing>(0);
	
	@Transient
	@IColumn(isIdListField = true, target="userNeedDoThings", mappedBy = "userInfo")
	private String[] userInfoIdList;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setThingName(String thingName) {
		this.thingName = thingName;
	}

	public String getThingName() {
		return thingName;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setUserNeedDoThings(Set<UserNeedDoThing> userNeedDoThings) {
		this.userNeedDoThings = userNeedDoThings;
	}

	public Set<UserNeedDoThing> getUserNeedDoThings() {
		return userNeedDoThings;
	}

	public void setUserInfoIdList(String[] userInfoIdList) {
		this.userInfoIdList = userInfoIdList;
	}

	public String[] getUserInfoIdList() {
		return userInfoIdList;
	}

	public void setStrActionName(String strActionName) {
		this.strActionName = strActionName;
	}

	public String getStrActionName() {
		return strActionName;
	}
}
