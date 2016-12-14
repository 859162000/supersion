package zxptsystem.helper.download;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.helper.download.Entity.Entity;
import zxptsystem.service.imps.CreditReportLogHelper;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.MessageResult;

public class OffLineGRGeneralReportQuery {	
	
	public MessageResult ImplementQuery(GRZXCreditReportInfo obj,String userid,String password,String ip,String sessionFactory,UserInfo user,String logId,String grzxQueryAcount) throws Exception{
		HttpUnitOptions.setExceptionsThrownOnScriptError(false);
		return offLineGRGeneralReportQuery(obj, userid, password, ip,sessionFactory,user,logId,grzxQueryAcount);//调取请求程序传入参数 返回页面
	}
	private MessageResult offLineGRGeneralReportQuery(GRZXCreditReportInfo obj,String userid
			,String password,String ip
			,String sessionFactory,UserInfo user
			,String logId,String grzxQueryAcount)  throws Exception{		
		SubmitInformation submitInformation=new SubmitInformation();
		Entity loginEntity = submitInformation.grLoginEntity(userid,password,ip);
		MessageResult messageResult = new MessageResult();
		String cookie =null;
		WebConversation wc = new WebConversation();
		Timestamp dtBeginDate,dtEndDate;
		String url="";
		int acount = Integer.valueOf(grzxQueryAcount.substring(8)) ;
		ApplicationManager.getActionExcuteLog().info("开始准备"+obj.getId()+"登录准备！人行账号："+userid+",线程ID："+Thread.currentThread().getId());
		cookie = CookieContainer.getInstance().Data.get(userid) ;
		if(null != cookie){
			acount = Integer.valueOf(grzxQueryAcount.substring(8)) - 1 ;
			//尝试用现有cookies去登录
			dtBeginDate = new Timestamp(System.currentTimeMillis());
			ApplicationManager.getActionExcuteLog().info("尝试用现有cookies去抓取数据,人行账号："+userid+",cookies:"+cookie+",线程ID:"+Thread.currentThread().getId());
			Entity grbwEntity =submitInformation.grbwEntity(obj,ip);
			messageResult = postthree(grbwEntity,cookie,wc);
			dtEndDate = new Timestamp(System.currentTimeMillis());
			
			for(String str:messageResult.getMessageList()){
				if(str.startsWith("URL\\")){
					url=str.replace("URL\\", "");
				}
			}
			if(messageResult.isSuccess()){
				CreditReportLogHelper.LogGrDetailInfo(logId,userid,password,url,dtBeginDate,dtEndDate,"成功","","现有cookies登录",sessionFactory);
				ApplicationManager.getActionExcuteLog().info("现有cookies抓取数据成功,人行账号："+userid+",线程Id:"+Thread.currentThread().getId());
				Thread.sleep(1000);
				obj.getStrCustomerID().setGrzxQueryAcount(grzxQueryAcount.substring(0,8) + acount) ;
				IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
				singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj.getStrCustomerID(), sessionFactory});
				return messageResult;
			}else{
				CookieContainer.getInstance().Data.remove(userid) ;
				CreditReportLogHelper.LogGrDetailInfo(logId,userid,password,url,dtBeginDate,dtEndDate,"失败","","现有cookies登录",sessionFactory);
				ApplicationManager.getActionExcuteLog().info("现有cookies抓取数据失败,人行账号："+userid+",线程Id:"+Thread.currentThread().getId());
			}
		}
		wc = new WebConversation();
		dtBeginDate=new Timestamp(System.currentTimeMillis());
		messageResult = postloginthree(loginEntity,wc);//login
		dtEndDate=new Timestamp(System.currentTimeMillis());
		for(String str:messageResult.getMessageList()){
			if(str.startsWith("URL\\")){
				url=str.replace("URL\\", "");
			}
		}
		if(messageResult.isSuccess()){
			acount = Integer.valueOf(grzxQueryAcount.substring(8)) - 1 ;
			cookie = messageResult.getMessage();
			Thread.sleep(1000);
			Entity grbwEntity =submitInformation.grbwEntity(obj,ip);
			dtBeginDate =new Timestamp(System.currentTimeMillis());
			messageResult = postthree(grbwEntity,cookie,wc);
			dtEndDate=new Timestamp(System.currentTimeMillis());
			if(!messageResult.isSuccess()){
				CreditReportLogHelper.LogGrDetailInfo(logId,loginEntity.getResult().get("userid").toString()
						,loginEntity.getResult().get("password").toString(),
						url,dtBeginDate,dtEndDate,"失败","","第1次抓取失败",sessionFactory);
				ApplicationManager.getActionExcuteLog().info("第1次抓取失败！人行账号："+userid+",线程ID："+Thread.currentThread().getId());
				for(int i=0;i<1;i++){
					Thread.sleep(1000);
					ApplicationManager.getActionExcuteLog().info("尝试第" +(i+2)+ "次抓取！人行账号："+userid+",线程ID："+Thread.currentThread().getId());
					dtBeginDate =new Timestamp(System.currentTimeMillis());
					messageResult = postthree(grbwEntity,cookie,wc);
					dtEndDate=new Timestamp(System.currentTimeMillis());
					if(messageResult.isSuccess()){
						CreditReportLogHelper.LogGrDetailInfo(logId,loginEntity.getResult().get("userid").toString()
								,loginEntity.getResult().get("password").toString(),
								url,dtBeginDate,dtEndDate,"成功","","第"+(i+2)+"次抓取成功",sessionFactory);
						ApplicationManager.getActionExcuteLog().info("第2次抓取成功！人行账号："+userid+",线程ID："+Thread.currentThread().getId());
						break;
					}else{
						CreditReportLogHelper.LogGrDetailInfo(logId,loginEntity.getResult().get("userid").toString()
								,loginEntity.getResult().get("password").toString(),
								url,dtBeginDate,dtEndDate,"失败","","第"+(i+2)+"次抓取失败",sessionFactory);
						ApplicationManager.getActionExcuteLog().info("第2次抓取失败！人行账号："+userid+",线程ID："+Thread.currentThread().getId());
					}
				}
			}else{
				CookieContainer.getInstance().Data.put(userid, cookie);//更新cookie
				CreditReportLogHelper.LogGrDetailInfo(logId,loginEntity.getResult().get("userid").toString()
						,loginEntity.getResult().get("password").toString(),
						url,dtBeginDate,dtEndDate,"成功","","第1次抓取成功",sessionFactory);
				ApplicationManager.getActionExcuteLog().info("第1次抓取成功！人行账号："+userid+",线程ID："+Thread.currentThread().getId());
			}
		}else{
			CreditReportLogHelper.LogGrDetailInfo(logId,loginEntity.getResult().get("userid").toString()
					,loginEntity.getResult().get("password").toString(),
					url,dtBeginDate,dtEndDate,"失败","","登录失败:"+messageResult.getMessage(),sessionFactory);
		}
		obj.getStrCustomerID().setGrzxQueryAcount(grzxQueryAcount.substring(0,8) + acount) ;
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj.getStrCustomerID(), sessionFactory});
		return messageResult;
	}
	
	public  MessageResult postthree(Entity entity,String cookie,WebConversation wc) throws Exception{
		MessageResult messageResult = new MessageResult();
		
		WebRequest request = new PostMethodWebRequest(entity.getUrl());
		request.setHeaderField("Cookie", cookie);
		Iterator<Entry<String,String>> iter = entity.getResult().entrySet().iterator();
		ApplicationManager.getActionExcuteLog().info("准备抓取数据,"+entity.getUrl()+",cookie:"+cookie+",线程ID："+Thread.currentThread().getId());
		while(iter.hasNext()){
			Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			request.setParameter(key, value);	
			ApplicationManager.getActionExcuteLog().info("	"+key+"		"+value+",线程ID："+Thread.currentThread().getId());
		}
		WebResponse wr = wc.getResponse(request);
		com.meterware.httpunit.WebForm[] forms = wr.getForms();
		messageResult = checkResponse(forms,wr.getCharacterSet());
		messageResult.getMessageList().add("URL\\"+request.getQueryString());
		if(messageResult.isSuccess()){
			messageResult.setMessage(new String(wr.getText().getBytes(wr.getCharacterSet()),"gbk"));  
		}	
		return messageResult;
	}
	
	public  MessageResult postloginthree(Entity entity,WebConversation wc) throws Exception{
		MessageResult messageResult = new MessageResult();
		
		WebRequest request = new PostMethodWebRequest(entity.getUrl());		
		Iterator<Entry<String,String>> iter = entity.getResult().entrySet().iterator();
		ApplicationManager.getActionExcuteLog().info(entity.getUrl()+",线程ID："+Thread.currentThread().getId());
		while(iter.hasNext()){
			Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			request.setParameter(key, value);
			if(key.equals("userid")){
				ApplicationManager.getActionExcuteLog().info("	"+key+"	"+value+",线程ID："+Thread.currentThread().getId());
			}
		}
		WebResponse wr = wc.getResponse(request);
		
		com.meterware.httpunit.WebForm[] forms = wr.getForms();
		messageResult = checkResponse(forms,wr.getCharacterSet());
		if(messageResult.isSuccess()){
			messageResult.setMessage(wr.getHeaderField("Set-Cookie"));
			ApplicationManager.getActionExcuteLog().error("登录人行成功！新Cookies:"+messageResult.getMessage()+",线程ID："+Thread.currentThread().getId());			
		}else{
			ApplicationManager.getActionExcuteLog().error("登录人行失败，不再抓取数据！线程ID："+Thread.currentThread().getId());
			messageResult.getMessageList().add("URL\\"+request.getQueryString());			
		}
		return messageResult;
	}

	private MessageResult checkResponse(com.meterware.httpunit.WebForm[] forms,String charset) throws Exception{
		MessageResult messageResult = new MessageResult();
		if(null!=forms && forms.length==1 && forms[0].getAction().startsWith("logon.do")){
			messageResult.setSuccess(false);
			messageResult.setMessage(new String(forms[0].getText().getBytes(charset),"gbk"));
			return messageResult;
		}
		return messageResult;
	}
}
