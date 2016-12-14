package report.service.imps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.TypedValue;

import report.dto.CurrencyType;
import report.dto.RptInfo;

import coresystem.dto.InstInfo;

import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.imps.BaseService;

/**
 * 机构报表新增界面初始化service
 * 此处将会查询出相关的机构信息、报表信息、主题信息以及币种信息
 * @author liutao
 *
 */
public class InsertInstRptRelaDataInitService extends BaseService {

	private Map<String,String> operationMap;
	
	public Map<String, String> getOperationMap() {
		return operationMap;
	}

	public void setOperationMap(Map<String, String> operationMap) {
		this.operationMap = operationMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws IOException{
		// JSONObject jso = JSONArray.fromObject(head);
		// 定义Map，用于保存最后筛选之后的数据
		Map<String, Object> resultMap = new HashMap<String, Object>();;
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		Map<String, String> dataMap = null;
		// 创建数据查询对象
		DetachedCriteria detachedCriteria = null;
		// 创建数据查询对象接口类
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		// 获取当前action操作的DTO对象的名称
		String tName = TActionRule.getTName(RequestManager.getActionName());
		// 判断当前要查询的数据是否是机构信息数据
		if(tName != null && tName.equals("coresystem.dto.InstInfo")){
			// 构建查询对象
			try {
				detachedCriteria = DetachedCriteria.forClass(Class.forName("coresystem.dto.InstInfo"));
				// 查询机构信息
				List<InstInfo> insetInfos = (List<InstInfo>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
				// 获取机构编码以及机构名称
				if(insetInfos != null && insetInfos.size() > 0){
					for(InstInfo ii : insetInfos){
						dataMap = new HashMap<String, String>();
						dataMap.put("id", ii.getStrInstCode());
						dataMap.put("name", ii.getStrInstCode()+" - "+ii.getStrInstName());
						resultList.add(dataMap);
						dataMap = null;
					}
				}else{
					resultMap.put("instInfo", "null");
				}
			} catch (Exception e) {
				resultMap.put("instInfo", "error");
				e.printStackTrace();
			}
		}
		
		// 判断当前要查询的数据是否是报表信息数据
		if(tName != null && tName.equals("report.dto.RptInfo")){
			// 构建查询对象
			try {
				detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptInfo"));
				detachedCriteria.addOrder(Order.asc("strBCode"));
				// 查询机构信息
				List<RptInfo> rptInfos = (List<RptInfo>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
				// 获取机构编码以及机构名称
				if(rptInfos != null && rptInfos.size() > 0){
					for(RptInfo ri : rptInfos){
						dataMap = new HashMap<String, String>();
						dataMap.put("id", ri.getStrRptCode());
						dataMap.put("name", ri.getDescName());
						resultList.add(dataMap);
						dataMap=null;
					}
				}else{
					resultMap.put("rptInfo", "null");
				}
			} catch (Exception e) {
				resultMap.put("rptInfo", "error");
				e.printStackTrace();
			}
		}
		
		// 判断当前要查询的数据是否是主题信息数据
		if(tName != null && tName.equals("extend.dto.Suit")){
			String suitId = RequestManager.getLevelIdValue();
			try {
				if(!StringUtils.isBlank(suitId)){
					Suit sp = new Suit();
					sp.setStrSuitCode(suitId);
					detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptInfo"));
					detachedCriteria.add(Restrictions.eq("suit", sp));
					detachedCriteria.addOrder(Order.asc("strBCode"));
					// 查询机构信息
					List<RptInfo> rptInfos = (List<RptInfo>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
					// 获取报表编码以及报表名称
					if(rptInfos != null && rptInfos.size() > 0){
						for(RptInfo ri : rptInfos){
							dataMap = new HashMap<String, String>();
							dataMap.put("id", ri.getStrRptCode());
							dataMap.put("name", ri.getDescName());
							resultList.add(dataMap);
							dataMap=null;
						}
						tName = "report.dto.RptInfo";
					}else{
						resultMap.put("rptInfo", "null");
					}
					sp = null;
				}else{
					detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.Suit"));
					// 查询主题信息
					List<Suit> suits = (List<Suit>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
					// 获取主题编码以及主题名称
					if(suits != null && suits.size() > 0){
						for(Suit s : suits){
							dataMap = new HashMap<String, String>();
							dataMap.put("id", s.getStrSuitCode());
							dataMap.put("name", s.getStrSuitName());
							resultList.add(dataMap);
							dataMap=null;
						}
					}else{
						resultMap.put("suit", "null");
					}	
				}
			} catch (Exception e) {
				if(suitId != null && suitId != ""){
					resultMap.put("rptInfo", "error");
				}else{
					resultMap.put("suit", "error");
				}
				e.printStackTrace();
			}
		}
		
		// 判断当前要查询的数据是否是币种信息数据
		if(tName != null && tName.equals("report.dto.CurrencyType")){
			// 构建查询对象
			try {
				detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.CurrencyType"));
				// 查询机构信息
				List<CurrencyType> cts = (List<CurrencyType>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
				// 获取机构编码以及机构名称
				if(cts != null && cts.size() > 0){
					for(CurrencyType ct : cts){
						dataMap = new HashMap<String, String>();
						dataMap.put("id", ct.getStrCurrencyCode());
						dataMap.put("name", ct.getStrCurrencyCode()+" - "+ct.getStrCurrencyName());
						resultList.add(dataMap);
						dataMap=null;
					}
				}else{
					resultMap.put("currencyType", "null");
				}
			} catch (Exception e) {
				resultMap.put("currencyType", "error");
				e.printStackTrace();
			}
		}
		this.returnValue(this.getKey(tName), resultMap, resultList);
	}// 整个方法执行完成
	
	private void returnValue(String key, Map<String, Object> resultMap, 
			List<Map<String, String>> resultList) throws IOException{

		if(resultMap.size() > 0){
			this.setServiceResult(resultMap);
		}else{
			resultMap.put(key, resultList);
			this.setServiceResult(resultMap);
		}
	}
	
	private String getKey(String tName){
		int index = tName.lastIndexOf(".");
		return tName.substring(index+1, index+2).toLowerCase()+tName.substring(index+2);
	}

}
