package report.dto;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
@SuppressWarnings("unchecked")
@Entity
@Table(name = "MergeSummary")
public class MergeSummary implements java.io.Serializable {

	/**  **/
	private static final long serialVersionUID = 7505981319421957869L;

	@Id
	@Column(name="MergeSummary",length = 50,nullable=false)
	@IColumn(description="实例名称", isNullable = false)
	private String strMergeSummaryName;
	
	@Column(name="strDescription",length = 200)
	@IColumn(description="描述")
	private String strDescription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strMergeInstanceid", nullable = false)
	@IColumn(isNullable = false)
	private MergeInstance mergeInstance;
	
	
	@Transient
	@IColumn(description="可选汇总指标报表",tagMethodName="getReportTag")
	private String[] rptInfosIdList;
	
	public void setRptInfosIdList(String[] rptids)
	{
		this.rptInfosIdList=rptids;
	}
	public String[] getRptInfosIdList()
	{
		return this.rptInfosIdList;
	}
	
	public static Map<String,String> getReportTag(){
		Map<String, String> simpleMap = new LinkedHashMap<String, String>();
		try {
			Field primaryKeyField = ReflectOperation.getPrimaryKeyField(RptInfo.class);
			Field keyNameField =  ReflectOperation.getKeyNameField(RptInfo.class);

			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RptInfo.class);
			Date curDate=new Date();
			detachedCriteria.add(Restrictions.le("startDate",curDate));
			detachedCriteria.add(Restrictions.ge("endDate",curDate));
			IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute) FrameworkFactory
					.CreateBean("singleObjectFindByCriteriaDao");
			
			List<Object> objectList = (List<Object>) defaultLogicDaoDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

			if (keyNameField != null) {
				for (Object object : objectList) {
					Object objFieldVal = ReflectOperation.getFieldValue(object,
							keyNameField.getName());
					if (objFieldVal != null)
						simpleMap.put(ReflectOperation.getFieldValue(object,
								primaryKeyField.getName()).toString(),
								objFieldVal.toString());
				}
			} else {
				for (Object object : objectList) {
					if (object != null)
						simpleMap.put(ReflectOperation.getFieldValue(object,
								primaryKeyField.getName()).toString(),
								ReflectOperation.getFieldValue(object,
										primaryKeyField.getName()).toString());
				}
			}
		} catch (Exception e) {
			ExceptionLog.CreateLog(e);
		}
		return simpleMap;


	}
	public String getStrMergeSummaryName() {
		return strMergeSummaryName;
	}

	public void setStrMergeSummaryName(String strMergeSummaryName) {
		this.strMergeSummaryName = strMergeSummaryName;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public MergeInstance getMergeInstance() {
		return mergeInstance;
	}

	public void setMergeInstance(MergeInstance mergeInstance) {
		this.mergeInstance = mergeInstance;
	}

}
