package zxptsystem.service.procese;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import zxptsystem.dto.condition.GRZXDownload_Condition;
import zxptsystem.dto.condition.JGXXDownload_Condition;
import zxptsystem.dto.condition.QYZXDownload_Condition;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class GetJRJGDataProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {

		if(serviceResult.getClass().equals(ShowList.class)){
			ShowList showList = (ShowList)serviceResult;
			Object o = RequestManager.getTOject();
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			for(ShowFieldCondition showFieldCondition :showList.getShowCondition()){
				if((o instanceof QYZXDownload_Condition && showFieldCondition.getFieldName().equals("strJRJGCode"))
						||(o instanceof JGXXDownload_Condition && showFieldCondition.getFieldName().equals("strJgJRJGCode"))
						||(o instanceof GRZXDownload_Condition && showFieldCondition.getFieldName().equals("strGrJRJGCode"))){
					showFieldCondition.setSingleTag("selectField");
					IParamObjectResultExecute dao= (IParamObjectResultExecute) FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
					String strSql ="SELECT exchange.INSTCODE,inst.strInstName,jrjgdm_qy,jrjgdm_gr,jrjgdm_jg FROM INSTCODE_EXCHANGE exchange INNER JOIN INSTINFO inst ON inst.strInstCode=exchange.INSTCODE";
					
					try {
						ResultSet resultSet = (ResultSet)dao.paramObjectResultExecute(new Object[]{strSql,null});
						if(o instanceof QYZXDownload_Condition){
							while(resultSet.next()){ 
								map.put(resultSet.getString("jrjgdm_qy"), resultSet.getString("strInstName"));
							}
						}
						else if(o instanceof GRZXDownload_Condition){
							while(resultSet.next()){ 
								map.put(resultSet.getString("jrjgdm_gr"), resultSet.getString("strInstName"));
							}
						}else if(o instanceof JGXXDownload_Condition){
							while(resultSet.next()){ 
								map.put(resultSet.getString("jrjgdm_jg"), resultSet.getString("strInstName"));
							}
						}
						
						resultSet.close();
					} catch (Exception e) {
						e.printStackTrace();
					}					
					showFieldCondition.setTag(map);
				}
			}
		}
		return serviceResult;
	}

}
