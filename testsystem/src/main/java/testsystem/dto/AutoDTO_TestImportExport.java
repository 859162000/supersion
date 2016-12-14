package testsystem.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@IEntity(description="²âÊÔµ¼Èëµ¼³ö")
@Entity
@Table(name = "TestImportExport")
public class AutoDTO_TestImportExport implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	@Column(name = "bookName")
	@IColumn(description="ÊéÃû", isNullable=false)
	private String bookName;
	
	@Column(name = "bookColor")
	@IColumn(tagMethodName="getBookColorTag",description="ÊéÃæÑÕÉ«")
	private String bookColor;
	
	@Column(name = "money")
	@IColumn(description="½ð¶î")
	private String money;

	@Column(name = "numbers")
	@IColumn(description="ÊýÁ¿")
	private String numbers;
	
	@Column(name = "dtDate")
	@IColumn(description="ÂòÊéÊ±¼ä")
	private String dtDate;
	
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getBookColor() {
		return bookColor;
	}

	public void setBookColor(String bookColor) {
		this.bookColor = bookColor;
	}

	public static Map<String,String> getBookColorTag(){
		return ShowContext.getInstance().getShowEntityMap().get("bookColor");
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
