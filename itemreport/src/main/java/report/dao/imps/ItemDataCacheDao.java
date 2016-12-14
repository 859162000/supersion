package report.dao.imps;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;



import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.opensymphony.util.GUID;

import report.dto.ItemData;
import framework.dao.imps.BaseVoidResultDao;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
public class ItemDataCacheDao extends BaseVoidResultDao{

	
	@Override
	public void voidResultExecute() throws Exception {
		
		voidResultExecute(RequestManager.getTOject());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		//voidResultExecute(objects[0]);
		voidResultExecuteSql(objects[0]);
	}

	public void voidResultExecute(Object object) throws UnsupportedEncodingException{
		ArrayList<ItemData> obj = (ArrayList<ItemData>) object;
		
		ItemDataCacheManger cacheManager= ItemDataCacheManger.getInsance();
		//this.getHibernateTemplate().saveOrUpdateAll(obj);
	/*	for (int i = 0; i < obj.size(); i++) {
			this.getHibernateTemplate().saveOrUpdate(obj.get(i));
			if(i%100==0)
			{
				this.getHibernateTemplate().flush();
				this.getHibernateTemplate().clear();
			}
			//cacheManager.addItemData((ItemData)obj.get(i));
		}*/
		cacheManager.AsynAddAll(obj);
	}
	
	public  void voidResultExecuteSql(Object object) throws UnsupportedEncodingException, SQLException{
		ArrayList<ItemData> obj = (ArrayList<ItemData>) object;
		StringBuilder insertSql=new StringBuilder();
		insertSql.append("insert into itemdata(id,dtdate,strcheckstate,strextenddimension1,strextenddimension2,strvalue,value1,currencytype,strinstcode,stritemcode,strpropertycode,strfreq)");
		insertSql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?)");
		
		StringBuilder updateSql=new StringBuilder();
		updateSql.append("update itemdata set dtdate=?,strcheckstate=?,strextenddimension1=?,strextenddimension2=?,strvalue=?,value1=?,currencytype=?,strinstcode=?,stritemcode=?,strpropertycode=?,strfreq=?");
		updateSql.append(" where id=?");
		
		Session session=this.getSession();
	
		session.clear();
		Connection connection=session.connection();
		
		PreparedStatement stmtInsert=connection.prepareStatement(insertSql.toString());
		PreparedStatement stmtUpdate = connection.prepareStatement(updateSql.toString());
		
		PreparedStatement stmtdeleteCheckMsg=connection.prepareStatement("delete from ItemDataCheckResult where id =?");
		PreparedStatement stmtInsertCheckMsg=connection.prepareStatement("insert into ItemDataCheckResult(id,strcheckresult,strsumcheckresult) values(?,?,?)");
		
		int ii=1;
		int iu=1;

		for(ItemData it:obj)
		{ 
			
			if(it.getId()==null ||"".equals(it.getId()))
			{
				String uid= UUID.randomUUID().toString().replace("-", "");
				stmtInsert.setString(1,uid);
				stmtInsert.setDate(2,new java.sql.Date(it.getDtDate().getTime()));
				stmtInsert.setString(3,it.getStrCheckState());
				stmtInsert.setString(4,it.getStrExtendDimension1());
				stmtInsert.setString(5,it.getStrExtendDimension2());
				stmtInsert.setString(6,it.getStrValue());
				stmtInsert.setString(7,it.getValue1());
				stmtInsert.setString(8,it.getCurrencyType().getStrCurrencyCode());
				stmtInsert.setString(9,it.getInstInfo().getStrInstCode());
				stmtInsert.setString(10,it.getItemInfo().getStrItemCode());
				stmtInsert.setString(11,it.getItemProperty().getStrPropertyCode());
				stmtInsert.setString(12,it.getStrFreq());
				stmtInsert.addBatch();
				
				
				if(!StringUtils.isBlank(it.getStrCheckResult()) || !StringUtils.isBlank(it.getStrSumCheckResult()))
				{
					stmtInsertCheckMsg.setString(1, uid);
					stmtInsertCheckMsg.setString(2, (it.getStrCheckResult()==null?"":it.getStrCheckResult()));
					stmtInsertCheckMsg.setString(3, (it.getStrSumCheckResult()==null?"":it.getStrSumCheckResult()));
//					stmtInsertCheckMsg.executeUpdate();
					stmtInsertCheckMsg.addBatch();
				}

				
				if(ii%100==0)
				{
					stmtInsert.executeBatch();
					stmtInsert.clearBatch();
					
					stmtInsertCheckMsg.executeBatch();
					stmtInsertCheckMsg.clearBatch();
				}
				ii++;
				
				
			}
			else
			{
				stmtUpdate.setDate(1, new java.sql.Date(it.getDtDate().getTime()));
				stmtUpdate.setString(2,it.getStrCheckState());
				stmtUpdate.setString(3,it.getStrExtendDimension1());
				stmtUpdate.setString(4,it.getStrExtendDimension2());
				stmtUpdate.setString(5,it.getStrValue());
				stmtUpdate.setString(6,it.getValue1());
				stmtUpdate.setString(7,it.getCurrencyType().getStrCurrencyCode());
				stmtUpdate.setString(8,it.getInstInfo().getStrInstCode());
				stmtUpdate.setString(9,it.getItemInfo().getStrItemCode());
				stmtUpdate.setString(10,it.getItemProperty().getStrPropertyCode());
				stmtUpdate.setString(11,it.getStrFreq());
				stmtUpdate.setString(12,it.getId());
				stmtUpdate.addBatch();
				
				if(iu%100==0)
				{					
					stmtUpdate.executeBatch();
					stmtUpdate.clearBatch();
				}
				iu++;
				
				
				//删除旧校验数据
				stmtdeleteCheckMsg.setString(1, it.getId());
				stmtdeleteCheckMsg.executeUpdate();
				
				
				if(!StringUtils.isBlank(it.getStrCheckResult()) || !StringUtils.isBlank(it.getStrSumCheckResult()))
				{
					//写入新校验数据
					stmtInsertCheckMsg.setString(1, it.getId());
					stmtInsertCheckMsg.setString(2, (it.getStrCheckResult()==null?"":it.getStrCheckResult()));
					stmtInsertCheckMsg.setString(3, (it.getStrSumCheckResult()==null?"":it.getStrSumCheckResult()));
					stmtInsertCheckMsg.executeUpdate();
				}
			}
		}
		
		stmtInsert.executeBatch();
		stmtInsert.clearBatch();
		stmtInsert.close();
		
		stmtUpdate.executeBatch();
		stmtUpdate.clearBatch();
		stmtUpdate.close();
		

		stmtdeleteCheckMsg.close();
		
		stmtInsertCheckMsg.executeBatch();
		stmtInsertCheckMsg.clearBatch();
		stmtInsertCheckMsg.close();
		
		ApplicationManager.getActionExcuteLog().info(session.hashCode());
		/*if(ii>0)
		{
			 stmtInsert.executeBatch();
			 stmtInsertCheckMsg.executeBatch();
		}*/
		 
		//tx.commit();
		
        session.clear(); 

        this.getSessionFactory().evict(ItemData.class);
		ItemDataCacheManger cacheManager= ItemDataCacheManger.getInsance();
		cacheManager.AsynAddAll(obj);
	}

}
