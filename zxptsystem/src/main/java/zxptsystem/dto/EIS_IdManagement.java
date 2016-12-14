package zxptsystem.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "EIS_IdManagement")
@IEntity(description= "客户证照附件信息")
public class EIS_IdManagement implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strTableName", length = 50)
	@IColumn(description="表名")
	private String strTableName;
	
	@Column(name = "curIndex",nullable = false)
	@IColumn(description="当前索引")
	private int curIndex;

	public String getStrTableName() {
		return strTableName;
	}

	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}

	public int getCurIndex() {
		return curIndex;
	}

	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}
}
