package zxptsystem.helper.download;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import zxptsystem.dto.QYZXCreditReportInfo;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import coresystem.dto.UserInfo;
import framework.services.interfaces.MessageResult;
import zxptsystem.service.imps.CreditReportLogHelper;

public class OffLineGeneralReportQuery {

	public MessageResult OffLineQuery(QYZXCreditReportInfo obj,String orgCode,String userid,String password,String ip,String sessionFactory,UserInfo user,boolean isOfflineDownLoad) throws Exception{
		HashMap<String,String> map = new HashMap<String,String>();
        map.put("orgCode", orgCode);
        map.put("userid", userid);
        map.put("password", password);
        String loginUrl = ip+"/logon.do";
        HttpUnitOptions.setExceptionsThrownOnScriptError(false);
		
		WebConversation wc = new WebConversation();
		
        MessageResult message = doLogin(wc,loginUrl,map,"POST");//login
        if(message.isSuccess()){
        	String logId=CreditReportLogHelper.LogQyInfo(obj.getId(), "Intranet_QY_GeneralReport", userid,sessionFactory,user);
        	message = queryForQyzx(ip+"/",obj,wc,isOfflineDownLoad,ip);
        	
        	CreditReportLogHelper.updateQyLogEndDateById(logId,sessionFactory);
        }
        
        return message;
	}
		
	private MessageResult doLogin(WebConversation wc,String loginurl,HashMap<String,String> map,String method){
		MessageResult message=new MessageResult();
		WebRequest request ;
		if(method.toUpperCase().equals("POST")){
			request = new PostMethodWebRequest(loginurl);
		}
		else {
			request = new GetMethodWebRequest(loginurl);
		}		
		if(map!=null && map.size()>0){
			Iterator<Entry<String,String>> iter = map.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				request.setParameter(key, value);
			}
		}
		try {
			WebResponse wr = wc.getResponse(request);
			com.meterware.httpunit.WebForm[] forms = wr.getForms();
			message = checkResponse(forms,wr.getCharacterSet());			
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
		}					
		return message;
	}
	private MessageResult getResponse(WebConversation wc,String firsturl,HashMap<String,String> map,String method,String secondurl,boolean isOfflineDownLoad,String headIp) throws Exception{
		
		MessageResult message=new MessageResult();
		WebRequest request ;
		if(method.toUpperCase().equals("POST")){
			request = new PostMethodWebRequest(firsturl);
		}
		else {
			request = new GetMethodWebRequest(firsturl);
		}		
		if(map!=null && map.size()>0){
			Iterator<Entry<String,String>> iter = map.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				request.setParameter(key, value);
			}
		}

		WebResponse wr = wc.getResponse(request);
		if(null!=secondurl){
			WebForm form = wr.getForms()[0];
			String[] paramNames = form.getParameterNames();
			request = new PostMethodWebRequest(secondurl);
			for(String param :paramNames){
				if(param.equals("sz_zk")){
					request.setParameter(param+".x", "29");
					request.setParameter(param+".y", "11");
				}else{
					request.setParameter(param, form.getParameterValues(param)[0]);
				}
			}
			wr = wc.getResponse(request);	
		}
		String out= new String(wr.getText().getBytes(wr.getCharacterSet()),"gbk");
		message.setMessage(out);
		if(isOfflineDownLoad){
			OfflineDownLoad(wc,wr,headIp);
		}
		return message;
	}
	
	private void OfflineDownLoad(WebConversation wc,WebResponse wr,String headIp) throws Exception{//wr.getLinks()
		
		String loancardcode =null;
		String searchreasoncode =null;
		String editiontype =null;
		String creditcode =null;		
		String chinaName =null;
		String crccode =null;
		
		InputStream is= wr.getInputStream();
		String line="";
		String attch = "1";
		String mainCredit="0";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
		while((line=br.readLine().trim())!=null){
			line=line.replace("<", "").replace(">", "").replace("\"", "").replace("  ", " ");
			if(line.indexOf("loancardcode")>-1){
				String[] elements = line.split(" ");
				for(String element:elements){
					if(element.indexOf("=")>-1){
						String[] pair = element.split("=",-1);
						if(pair[0].equals("value")){
							loancardcode=pair[1];
						}
					}
				}
			}
			if(line.indexOf("searchReasonCode")>-1){
				String[] elements = line.split(" ");
				for(String element:elements){
					if(element.indexOf("=")>-1){
						String[] pair = element.split("=",-1);
						if(pair[0].equals("value")){
							searchreasoncode=pair[1];
						}
					}
				}
			}
			
			if(line.indexOf("reportcode")>-1){
				String[] elements = line.split(" ");
				for(String element:elements){
					if(element.indexOf("=")>-1){
						String[] pair = element.split("=",-1);
						if(pair[0].equals("value")){
							editiontype=pair[1];
						}
					}
				}
			}
			if(line.indexOf("creditcode")>-1){
				String[] elements = line.split(" ");
				for(String element:elements){
					if(element.indexOf("=")>-1){
						String[] pair = element.split("=",-1);
						if(pair[0].equals("value")){
							creditcode=pair[1];
						}
					}
				}
			}
			if(line.indexOf("borrowernamecn")>-1){
				String[] elements = line.split(" ");
				for(String element:elements){
					if(element.indexOf("=")>-1){
						String[] pair = element.split("=",-1);
						if(pair[0].equals("value")){
							chinaName=pair[1];
						}
					}
				}
			}
			if(line.indexOf("crccode")>-1){
				String[] elements = line.split(" ");
				for(String element:elements){
					if(element.indexOf("=")>-1){
						String[] pair = element.split("=",-1);
						if(pair[0].equals("value")){
							crccode=pair[1];
						}
					}
				}
			}
			if(null!=loancardcode && null!=searchreasoncode && null!=editiontype && null!=creditcode && null!=chinaName && null!=crccode){
	        	break;
	        }
		}		
		
		String url=headIp+"/recordExpand.do";
		
		WebRequest request = new PostMethodWebRequest(url);
		request.setParameter("loancardno", loancardcode);
		request.setParameter("searchreasoncode", searchreasoncode);
		request.setParameter("editiontype", editiontype);
		request.setParameter("creditCode",creditcode);
		request.setParameter("kind", "4");
		
		WebResponse wr1 = wc.getResponse(request);
				
		url=headIp+"/offlineInfoAction.do";
		
		request = new PostMethodWebRequest(url);
		request.setParameter("loancardcode", loancardcode);
		request.setParameter("mancredit", mainCredit);
		request.setParameter("bornatue", "1");
		request.setParameter("searchreason",searchreasoncode);
		request.setParameter("type", editiontype);
		request.setParameter("borrowernamecn", URLEncoder.encode(chinaName,"utf-8"));
		request.setParameter("creditCode", creditcode);
		request.setParameter("attch", attch);
		request.setParameter("crccode", crccode);
		
		wr1 = wc.getResponse(request);
	}
	
	private MessageResult checkResponse(com.meterware.httpunit.WebForm[] forms,String charset) throws Exception{
		MessageResult messageResult = new MessageResult();
		if(null!=forms && forms.length>0 && forms[0].getAction().startsWith("logon.do")){
			messageResult.setSuccess(false);
			messageResult.setMessage(new String(forms[0].getText().getBytes(charset),"gbk"));
			return messageResult;
		}
		return messageResult;
	}

	public MessageResult queryForQyzx(String url,QYZXCreditReportInfo info,WebConversation wc,boolean isOfflineDownLoad,String headIp){		
		MessageResult message;
		try {
			HashMap<String,String> map=new HashMap<String,String>();
			
			if(null==info.getStrCustomerID()){
				map.put("creditcode","");
				map.put("loancardno","");
				map.put("corpno","");	
				map.put("regitypecode","");
				map.put("frgcorpno","");
				map.put("gsregino","");
				map.put("dsregino","");
			}else{
				if(null==info.getStrCustomerID().getStrCUSCreditInstitutionsCode()){
					map.put("creditcode","");
				}else{
					map.put("creditcode",info.getStrCustomerID().getStrCUSCreditInstitutionsCode());
				}
				if(null==info.getStrCustomerID().getStrInCode()){
					map.put("loancardno","");
				}else{
					map.put("loancardno",info.getStrCustomerID().getStrInCode());
				}
				if(null==info.getStrCustomerID().getStrOrganizationCode()){
					map.put("corpno","");		
				}else{
					map.put("corpno",info.getStrCustomerID().getStrOrganizationCode());		
				}
				if(null==info.getStrCustomerID().getStrRegistrationType()){
					map.put("regitypecode","");
				}else{
					map.put("regitypecode",info.getStrCustomerID().getStrRegistrationType());
				}
				if(null==info.getStrCustomerID().getStrRegistrationNo()){
					map.put("frgcorpno","");
				}else{
					map.put("frgcorpno",info.getStrCustomerID().getStrRegistrationNo());
				}
				if(null==info.getStrCustomerID().getStrTaxpayerIdentStateNo()){
					map.put("gsregino","");
				}else{
					map.put("gsregino",info.getStrCustomerID().getStrTaxpayerIdentStateNo());
				}
				if(null==info.getStrCustomerID().getStrTaxpayerIdentLandNo()){
					map.put("dsregino","");
				}else{
					map.put("dsregino",info.getStrCustomerID().getStrTaxpayerIdentLandNo());
				}
			}
			
			if(null==info.getStrQueryVersion()){
				map.put("type","");
			}else{
				map.put("type",info.getStrQueryVersion());
			}
			if(null==info.getStrQueryCause()){
				map.put("searchreason","");
			}else{
				map.put("searchreason",info.getStrQueryCause());
			}
			
			map.put("searchType","1");
			map.put("attribute","0");
			map.put("radioValue","0");
			map.put("offline","null");
			map.put("displayreason","1");

			String gUrl=url+"newconfirmProfessionReport.do";
			String zkUrl=url+"professionReportExpend.do";
			message = getResponse(wc,gUrl,map,"POST",zkUrl,isOfflineDownLoad,headIp); 
			return message;
		}catch (Exception e) {
			e.printStackTrace();
			message= new MessageResult();
			message.setSuccess(false);
			message.setMessage(e.getMessage());
		}        
        return message;
	}
}
