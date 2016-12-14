package zxptsystem.helper.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import coresystem.dto.UserInfo;
import org.xml.sax.SAXException;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.dto.QYZXGrantUserInfo;
import zxptsystem.helper.download.Entity.Entity;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import java.lang.NullPointerException;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.security.SecurityContext;
import zxptsystem.helper.download.SubmitInformation;

public class OnLineQuery {
	private static final String USER_AGENT_VALUE = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
	private static String cookie = null;
	private static WebConversation wc = new WebConversation();
	public Map<String,String> OnLineQuery(String id){
		String loginUser="";
		String orgCode="";
		String userid="";
		String password="";
		String ip="";
		QYZXCreditReportInfo obj=null;
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		try {
			loginUser = ((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
			QYZXGrantUserInfo info = (QYZXGrantUserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{QYZXGrantUserInfo.class.getName(),loginUser,null});
			
			if(null==info){
				throw new NullPointerException("没有设置人行征信中心的ip，用户名，密码，机构代码等信息！");
			}
			orgCode=info.getStrInstCode();
			userid=info.getUserId();
			password=info.getUserPwd();
			ip=info.getStrPBOCUrl();
			obj=(QYZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{QYZXCreditReportInfo.class.getName(),id,null});
		} catch (Exception e) {
			e.printStackTrace();
		}		
				
		SubmitInformation submitInformation=new SubmitInformation();
		
		Entity loginEntity=new Entity();//登陆成功
		Entity jkrEntity=new Entity();//输入查询客户条件页  中证吗 等
		Entity jkrjbxxEntity=new Entity();//中间页 其他页面需要此页成功抓取  抓取此页需要参数从上页返回值中截取		
		
		loginEntity=submitInformation.LoginEntity(userid,password,orgCode,ip);//组织登陆post信息
		try {
			String dlcg=new String(postloginthree(loginEntity).getBytes(),"GBK");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List jkr= null;
		Map<String,String> map=new HashMap<String,String>();
		jkrEntity=submitInformation.jkrEntity(obj,userid,password,orgCode,ip);//输入中证码查询页面
		
		try {
			jkr=getPage(jkrEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		WebResponse jkrWebResponse=(WebResponse)jkr.get(1);
		
		
		WebTable JTable;
		TableRow[] tableRows;
		String crccode="";//抓取重要页面重要变化参数参数
		String strInCode="";
		try {
			JTable = jkrWebResponse.getTableStartingWith("机构中文名称/英文名称");
			tableRows = JTable.getRows();
			if(tableRows.length>2){
				//停止查询返回有两条借款人信息
			}
			
			for(int i=1;i<tableRows.length;i++){
				List crccodeParameter=new ArrayList();
				String onclickvalue=JTable.getTableCell(i, 0).getAttribute("onclick");
				strInCode=JTable.getTableCell(i, 3).getText();//获取中证吗
				onclickvalue=onclickvalue.substring(onclickvalue.indexOf("(")+1, onclickvalue.indexOf(")"));
				crccodeParameter=getParameter(onclickvalue,"'",3);
				crccode=(String) crccodeParameter.get(0);
			}
			List jkrjbxx=null;//要从中取出 "绍兴县国泰印染有限公司"
			jkrjbxxEntity=submitInformation.jkrjbxxEntity(strInCode,userid,password,orgCode,ip,crccode);//查询页面
			jkrjbxx=getPage(jkrjbxxEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		map.put("strInCode", strInCode);
		map.put("crcCode", crccode);
		map.put("ip", ip);
		map.put("orgCode", orgCode);
		map.put("userId", userid);
		map.put("pwd", password); 
		return map;		
	}
	
	
	public List getPage(Entity postParameter,String pagePath) throws MalformedURLException, IOException, SAXException{
		List page=null;//取回页面
		WebResponse pageWebResponse=null;
		for(int i=0;i<10;i++){//一次抓取会循环抓页面
			page=postthreewr(postParameter);//调取请求程序传入参数 返回页面
			String pageString=(String)page.get(0);
			if(!(pageString.indexOf("failure")>-1)&&!(pageString.indexOf("新闻公告")>-1)){
				File pageFile=new File(pagePath);
				buildFile(pageFile,pageString);
				break;
			}
		}
		return page;
	}
	
	
	public void buildFile(File xx,String  str) throws IOException{
		xx.createNewFile();
		FileOutputStream txtfiledxx=new FileOutputStream(xx);
		PrintStream xxx=new PrintStream(txtfiledxx);
		xxx.println(str);
		txtfiledxx.close();
		xxx.close();
	}
	public List getPage(Entity postParameter) throws MalformedURLException, IOException, SAXException{
		List page=null;//取回页面
		WebResponse pageWebResponse=null;
		for(int i=0;i<10;i++){//一次抓取会循环抓页面
			page=postthreewr(postParameter);//调取请求程序传入参数 返回页面
			String pageString=(String)page.get(0);
			if(!(pageString.indexOf("failure")>-1)&&!(pageString.indexOf("新闻公告")>-1)){
				//File pageFile=new File(pagePath);
				//buildFile(pageFile,pageString);
				break;
			}
		}
		return page;
	}
	
	public static List postthreewr(Entity entity) throws MalformedURLException, IOException, SAXException{
		WebRequest request ;
		//WebConversation wc = new WebConversation();
		request = new PostMethodWebRequest(entity.getUrl());
		request.setHeaderField("Cookie", cookie);
		//request.setHeaderField("Referer", "http://9.96.47.2/shwebroot/OweBalanceQueryAction.do");
		Iterator<Entry<String,String>> iter = entity.getResult().entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			request.setParameter(key, value);
			
		}
		
		WebResponse wr = wc.getResponse(request);
		
		String out= new String(wr.getText().getBytes(wr.getCharacterSet()),"gbk");
		List rtList=new ArrayList();
		//return out;
		rtList.add(0, out);
		rtList.add(1, wr);
		return rtList;
	}
	
	//取''分隔内容
		public List getParameter(String str,String flg,int num){
			List Parameter=new ArrayList();
			int int1=str.indexOf(flg, 0);
			for(int i=0;i<num;i++){
				if(int1==0){
					int beginIndex=str.indexOf("'", 0)+1;
					int endIndex=str.indexOf("'", int1+1);
					String ParameterValue=str.substring(beginIndex, endIndex);
					Parameter.add(i, ParameterValue);
					int1=str.indexOf("'", int1+1);
				}else{
					int beginIndex=str.indexOf("'", int1+1)+1;
					int endIndex=str.indexOf("'", str.indexOf("'", int1+1)+1);
					String ParameterValue=str.substring(beginIndex, endIndex);
					Parameter.add(i, ParameterValue);
					int1=str.indexOf("'", str.indexOf("'", int1+1)+1);
				}
			}
			return Parameter;
		}
		
	public static String postloginthree(Entity entity) throws Exception{
		//WebRequest request ;WebConversation
		
		WebRequest request = new PostMethodWebRequest(entity.getUrl());
		//WebClient wcl = new WebClient(); 
		HttpUnitOptions.setExceptionsThrownOnScriptError(false);

		Iterator<Entry<String,String>> iter = entity.getResult().entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			request.setParameter(key, value);
		}
		WebResponse wr = wc.getResponse(request);
		
		String setCookie = wr.getHeaderField("Set-Cookie");   
		cookie = setCookie;
		System.out.println("打印Cookie"+setCookie);
		
		
		String out= new String(wr.getText().getBytes(wr.getCharacterSet()),"gbk");

		return out;
	}
}
