package autoETLsystem.dto;

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
@Table(name = "AutoETL_ActivityNodeForSqlCR")
@IEntity(navigationName="SQL传输数据映射关系",description="SQL传输数据映射关系")
public class AutoETL_ActivityNodeForSqlCR  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="strWhenValue",length = 2000)
	@IColumn(description="When值")
	private String strWhenValue;
	
	@Column(name="strThenValue",length = 4000, nullable = false)
	@IColumn(description="Then值", isNullable = false)
	private String strThenValue;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_ActivityNodeForSqlCID", nullable = false)
	private AutoETL_ActivityNodeForSqlC autoETL_ActivityNodeForSqlC;

	public void setStrWhenValue(String strWhenValue) {
		this.strWhenValue = strWhenValue;
	}

	public String getStrWhenValue() {
		return strWhenValue;
	}

	public void setStrThenValue(String strThenValue) {
		this.strThenValue = strThenValue;
	}

	public String getStrThenValue() {
		return strThenValue;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAutoETL_ActivityNodeForSqlC(
			AutoETL_ActivityNodeForSqlC autoETL_ActivityNodeForSqlC) {
		this.autoETL_ActivityNodeForSqlC = autoETL_ActivityNodeForSqlC;
	}

	public AutoETL_ActivityNodeForSqlC getAutoETL_ActivityNodeForSqlC() {
		return autoETL_ActivityNodeForSqlC;
	}
	
}
