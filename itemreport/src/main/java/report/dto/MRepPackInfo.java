package report.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：MRepPackInfo</P>
 * *********************************<br>
 * <P>类描述：制度包打包信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-18 上午11:05:25<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-18 上午11:05:25<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="MRepPackInfo")
public class MRepPackInfo implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 7079476803846937405L;

	@Id
	@Column(name = "uuid", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String uuid;
	
	@Column(name = "packName", length = 80)
	@IColumn(description="制度包")
	private String packName;
	
	@Column(name = "descript", length = 200)
	@IColumn(description="描述")
	private String descript;
	
	@Column(name = "author", length = 50)
	@IColumn(description="作者")
	private String author;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "makeDate")
	@IColumn(description="生成时间")
	private Date makeDate;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	
}
