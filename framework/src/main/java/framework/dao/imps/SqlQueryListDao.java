package framework.dao.imps;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.SQLQuery;

import framework.services.interfaces.LogicParamManager;

/**
 * 项目名称：framework<br>
 * *********************************<br>
 * <P>类名称：SqlQueryListDao</P>
 * *********************************<br>
 * <P>类描述：SQL LIST结果查询</P>
 * 创建人：王川<br>
 * 创建时间：2016-6-16 下午4:46:56<br>
 * 修改人：王川<br>
 * 修改时间：2016-6-16 下午4:46:56<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class SqlQueryListDao extends BaseObjectResultDao {

	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(new Object[]{LogicParamManager.getSqlQuery()});
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		if(objects==null){
			return null;
		}
		else if(objects.length==1){
			return excuteQuery(objects[0].toString(),null);
		}
		else{
			return excuteQuery(objects[0].toString(),objects[1]);
		}
	}
	
	public Object excuteQuery(String sql,Object params){
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if(params != null){
			Object[] objParams=null;
			if(params instanceof Object[])
			{
				objParams=(Object[]) params;
			}
			else
			{
				objParams=new Object[]{params};
			}
			int i=0;
			for(Object param:objParams)
			{
				if(param==null){
					param = "";
				}
				if(param instanceof String){
					sqlQuery.setString(i, (String) param);
				}
				if(param instanceof Integer){
					sqlQuery.setInteger(i, (Integer) param);
				}
				if(param instanceof Float){
					sqlQuery.setFloat(i, (Float) param);
				}
				if(param instanceof Double){
					sqlQuery.setDouble(i, (Double) param);
				}
				if(param instanceof BigDecimal){
					sqlQuery.setBigDecimal(i, (BigDecimal) param);
				}
				if(param instanceof Boolean){
					sqlQuery.setBoolean(i, (Boolean) param);
				}
				if(param instanceof Date)
				{
					sqlQuery.setDate(i, (Date)param);
				}
				i++;
			}
		}
		return sqlQuery.list();
	}

}
