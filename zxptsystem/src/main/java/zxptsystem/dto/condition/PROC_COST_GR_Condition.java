package zxptsystem.dto.condition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class PROC_COST_GR_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(target="beginDate",description="开始时间",comparison=Comparison.GE)
	private Date beginDate;
	
	@ICondition(target="endDate",description="结束时间",comparison=Comparison.LE)
	private Date endDate;

	@ICondition(target="category",description="方式")
	private String category;
	
	public String getBeginDate() {
		if(beginDate==null){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(beginDate);
	}

	public void setBeginDate(String beginDate) {
		if(null!=beginDate){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				this.beginDate = simpleDateFormat.parse(beginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}		
	}

	public String getEndDate() {
		if(endDate==null){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(endDate);
	}

	public void setEndDate(String endDate) {
		if(null!=endDate){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				this.endDate = simpleDateFormat.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}		
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		if(null==category || category.trim().equals("")){
			return "I";
		}
		return category;
	}
}
