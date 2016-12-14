package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass  
public abstract class Test2 implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "field3", length = 50)
	public String field3;

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField3() {
		return field3;
	}

}
