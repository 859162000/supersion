package extend.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


@Entity
@Table(name = "AutoETL_ViewField")
@IEntity(navigationName="视图字段",description="视图字段")
public class AutoETL_ViewField implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="strFieldName", length = 30, nullable=false)
	@IColumn(description="字段名", isNullable = false)
	private String strFieldName;

	@Column(name="strChinaName", length = 50, nullable=false)
	@IColumn(description="中文名",isKeyName=true, isNullable = false)
	private String strChinaName;
	
	@Column(name="nSeq", nullable=false)
	@IColumn(description="显示顺序", isNullable = false)
	private Integer nSeq;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoViewID", nullable = false)
	private AutoETL_View autoETL_View;
	
	@Id
	@Column(name = "autoViewFieldID", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoViewFieldID;

	public String getStrFieldName() {
		return strFieldName;
	}

	public void setStrFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}

	public void setStrChinaName(String strChinaName) {
		this.strChinaName = strChinaName;
	}

	public String getStrChinaName() {
		return strChinaName;
	}

	public void setNSeq(String nSeq) {
		this.nSeq = TypeParse.parseInt(nSeq);
	}

	public Integer getNSeq() {
		return nSeq;
	}

	public void setAutoETL_View(AutoETL_View autoETL_View) {
		this.autoETL_View = autoETL_View;
	}

	public AutoETL_View getAutoETL_View() {
		return autoETL_View;
	}

	public void setAutoViewFieldID(String autoViewFieldID) {
		this.autoViewFieldID = autoViewFieldID;
	}

	public String getAutoViewFieldID() {
		return autoViewFieldID;
	}
}




