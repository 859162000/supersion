package zxptsystem.service.procese;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowFieldValue;
import framework.show.ShowHeaderName;
import framework.show.ShowList;
import framework.show.ShowValue;
import zxptsystem.dto.View_JG_JGJBXX;

public class DynamicModifyShowListProcess implements IProcese {
 /**
  * 动态修改ShowList 增加 借款人名称
  * 2016年01月13日
  * 程序中注视均为记录api用,熟悉可忽略
  *  @author whe
  * */
	
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		String primarykey="";
		String strSql = "";
		Object object=null;       
		String id = "qyzx_strVersion";
		String viewName ="";
		String jkrmc= "";      
		String 	Tname="";
		String	instInfoName= "";
		String dkkbm ="";
		String colname ="";
		
		//收取ShowList数据 表里数据
		ShowList showList=(ShowList) serviceResult;
		
		//showList.getValueTable() 得到表中数据,无数据就退出不执行该process
				List<List<ShowValue>> showValueTable = showList.getValueTable();
				if(showValueTable.size()<1){
					return serviceResult;
				}
		//跟据entity.xml 该节点值 无配置 则不需要porcese处理 
				if(ShowContext.getInstance().getShowEntityMap().get("dynamicShowJkrmc").get(RequestManager.getTName())==null	){
					return serviceResult;
				}else {
					Tname =ShowContext.getInstance().getShowEntityMap().get("dynamicShowJkrmc").get(RequestManager.getTName());
				}
				
		//获取ShowHeaderName数据 数据对应的	
		List<ShowHeaderName> shownamelist = showList.getShowNameList();
		
		//在showHeaderName 增加一个showName 写死的,因为无法配置出来
			ShowHeaderName showHeaderName = new ShowHeaderName();
			showHeaderName.setShowName("借款人名称");
		//	showHeaderName.setWidth(100);
			 shownamelist.add(0, showHeaderName);
			
		//根据ID 查询表,不支持VIEW
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		//判断接口版本,取DKKBM对应的JKRZWMC不同,
		SystemParam qyzxVersionObj = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", id , null});
		if (qyzxVersionObj != null && qyzxVersionObj.getStrParamValue().equals("2.2")) {
			  viewName = "View_JG_JGJBXX";
			  colname = "JGZWMC";
		}else {
			  viewName = "View_QY_JKRGKXX";
			  colname ="JKRZWMC";
		}
		//根据SQL语句查询数据
		IParamObjectResultExecute createSqlQueryListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryListMapDao");		
		//循环表中数据每行都要增加对应的JKRZWMC
				for (List<ShowValue> showValuesList : showValueTable) {
					
					ShowValue showValue = new  ShowValue();
					//因为每行数据具体位置根据showinstansname配置,所以为了避免错误 先取主键AUTOID
					 primarykey = showValuesList.get(0).getValue().toString();	
					 //获取getTname 对应表的数据
					 object =singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(), primarykey, null});
				
					//根据主键查询表里数据
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(Tname));
					if(Tname.equals(RequestManager.getTName())){
						//基础表 
						detachedCriteria.add(Restrictions.eq("autoID",primarykey ));
					}else {
						// 基础表对应到名细表要用 foreignid  传入object 其他都是String 直接可传变量
						detachedCriteria.add(Restrictions.eq("FOREIGNID",object ));
					}
					// 基础表对应到名细表表数据获取
					List<Object> valueList = (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
					//表中无数据就无需处理直接返回
					if(valueList.size()>0){
					//得到表中数据,具体可能不知道object类型所以用映射,知道具体类型可不用	
						if(ReflectOperation.getFieldValue(valueList.get(0),"DKKBM")!=null){
							dkkbm  = ReflectOperation.getFieldValue(valueList.get(0),"DKKBM").toString();	
						}
						if(ReflectOperation.getFieldValue(object,"instInfo")!=null){
							instInfoName = (String)ReflectOperation.getFieldValue(ReflectOperation.getFieldValue(object, "instInfo"), "strInstCode");
						}
					}
				strSql = "SELECT "+colname+" FROM  "+viewName+"  WHERE DKKBM = "+"'"+dkkbm.toString()+"'"+" AND instInfo = "+"'"+instInfoName+"'";
	       	     //执行SQL语句查询返回结果
	      		List<Map<String,String>> objectSList= (List<Map<String,String>>)createSqlQueryListMapDao.paramObjectResultExecute(new Object[]{strSql, null});
	      		//判断对应的业务主键是否存在JKRZWMC 
	      		//重载重复数据暂时处理,
	       		if(objectSList.size()>0){
	       			if(objectSList.get(0).get(colname)!=null){
	       			//此处也可以用映射,涉及Object 根据是否为NULL 转换成String 否则  showValue.setValue(jkrmc.toString()) 报错 所以直接获取
	       				jkrmc  = objectSList.get(0).get(colname);
	       			}
	       		}
				 showValue.setValue(jkrmc);
				showValuesList.add(2, showValue);
				//重置,因为中间涉及到数据是否为空处理,可能导致同样全局变量一直被赋值使用
				jkrmc ="";
				instInfoName="";
				dkkbm="";
				}
		
		return serviceResult;
	}
}
