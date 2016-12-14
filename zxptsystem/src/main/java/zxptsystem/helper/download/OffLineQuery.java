package zxptsystem.helper.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.xml.sax.SAXException;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.helper.download.Entity.Entity;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import coresystem.dto.UserInfo;
import zxptsystem.helper.download.SubmitInformation;
import zxptsystem.service.imps.CreditReportLogHelper;



public class OffLineQuery {
	private static String cookie = null;	
	private static WebConversation wc = new WebConversation();	
	
	public void OffLineQuery(String downloadPath,QYZXCreditReportInfo obj,String orgCode,String userid,String password,String ip,String sessionFactory,UserInfo user) throws Exception{
		SimpleDateFormat dftwo = new SimpleDateFormat("yyyy-MM-dd");
				
		SubmitInformation submitInformation=new SubmitInformation();
 		
		Entity loginEntity=new Entity();//登陆成功
		Entity jkrEntity=new Entity();//输入查询客户条件页  中证吗 等
		Entity jkrjbxxEntity=new Entity();//中间页 其他页面需要此页成功抓取  抓取此页需要参数从上页返回值中截取
		Entity dqzhxxEntity=new Entity();//当前综合信息
		
		Entity blfzxxEntity=new Entity();//不良负债信息
		Entity dbxxEntity=new Entity();//担保信息
		Entity qxxxEntity=new Entity();//欠息信息
		Entity dkxxEntity=new Entity();//垫款信息
		Entity gksxEntity=new Entity();//公开授信
		Entity bljlEntity=new Entity();//剥离记录
		Entity yybzEntity=new Entity();//异议标注
		//余额页面
		Entity yehzEntity=new Entity();//余额汇总信息
		Entity dkyexxxxEntity=new Entity();//贷款余额详细信息
		Entity myrzEntity=new Entity();//贸易融资
		Entity pjtxEntity=new Entity();//票据贴现（未结清金额） 
		Entity cdhpEntity=new Entity();//承兑汇票（未结清金额） 
		Entity dbdkyexxEntity=new Entity();//单笔贷款信息
		Entity dbmyrzEntity=new Entity();//单笔贸易融资信息
		Entity dbpjtxEntity=new Entity();//单笔票据贴现
		Entity dbcdhpEntity=new Entity();//单笔承兑汇票
		Entity dkhtEntity=new Entity();//单笔贷款中的合同
		Entity myrzhtEntity=new Entity();//单笔贸易融资中的合同
		Entity pjtxhtEntity=new Entity();//单笔票据贴现中的合同
		Entity cdhphtEntity=new Entity();//单笔承兑汇票中的合同
		Entity ldkEntity=new Entity();
		Entity dbldkEntity=new Entity();
		Entity ldkhtEntity=new Entity();
		//余额页面
		//发生额页面
		Entity fsehzEntity=new Entity();//发生额汇总
		Entity dkfsexxxxEntity=new Entity();//贷款发生额信息
		Entity dbdkfsexxEntity=new Entity();//单笔贷款发生额
		Entity dkfsehtEntity=new Entity();//单笔贷款发生额中的合同
		Entity myrzfseEntity=new Entity();//贸易融资信息
		Entity dbmyrzfseEntity=new Entity();//单笔发生额贸易融资信息
		Entity myrzfsehtEntity=new Entity();//单笔发生额贸易融资中的合同
		Entity pjtxfseEntity=new Entity();//票据贴现（未结清金额）
		Entity dbpjtxfseEntity=new Entity();//单笔票据贴现发生额
		Entity pjtxfsehtEntity=new Entity();//单笔发生额票据贴现中的合同
		Entity cdhpfseEntity=new Entity();//承兑汇票（未结清金额）发生额 
		Entity dbcdhpfseEntity=new Entity();//单笔承兑汇票发生额
		Entity cdhpfsehtEntity=new Entity();//单笔承兑汇票中的合同 发生额
		Entity ldkfseEntity=new Entity();
		Entity dbldkfseEntity=new Entity();
		Entity ldkfsehtEntity=new Entity();
		//发生额页面
		//不良负债
		Entity blfzhzEntity=new Entity();//不良负债汇总
		Entity dkblfzxxxxEntity=new Entity();//不良负债 贷款明细页面
		Entity dbdkblfzxxEntity=new Entity();//不良负债 单笔贷款
		Entity dkblfzhtEntity=new Entity();//不良负债 单笔贷款合同
		Entity myrzblfzEntity=new Entity();//不良负债 贸易融资明细
		Entity dbmyrzblfzEntity=new Entity();//不良负债 单笔贸易融资
		Entity myrzblfzhtEntity=new Entity();//不良负债 单笔贸易融资合同
		Entity pjtxblfzEntity=new Entity();//不良负债 票据贴现明细
		Entity dbpjtxblfzEntity=new Entity();//不良负债 单笔票据贴现
		Entity pjtxblfzhtEntity=new Entity();//不良负债 单笔票据贴现合同
		Entity cdhpblfzEntity=new Entity();//不良负债 承兑汇票明细
		Entity dbcdhpblfzEntity=new Entity();//不良负债 单笔承兑汇票
		Entity cdhpblfzhtEntity=new Entity();//不良负债 单笔承兑汇票合同
		//不良负债
		//对外担保
		Entity dwdbhzEntity=new Entity();//对外担保汇总信息
		Entity bzdwdbxxxxEntity=new Entity();//保证对外担保明细页面
		Entity dbbzdwdbxxEntity=new Entity();//单笔保证对外担保
		//对外担保
		//被担保
		Entity bdbhzEntity=new Entity();//被担保汇总信息
		Entity bzbdbxxxxEntity=new Entity();//保证被担保明细页
		Entity dbbzbdbxxEntity=new Entity();//单笔被保证担保
		Entity dybdbxxxxEntity=new Entity();//抵押被担保明细
		Entity dbdybdbxxEntity=new Entity();//单笔被抵押担保
		//被担保
		//欠息信息
		Entity qxxxhzEntity=new Entity();//欠息信息汇总
		Entity bnwqxxxxxEntity=new Entity();//表内欠息信息页面
		Entity qxxxxgrEntity=new Entity();//欠息信息修改日详细信息
		//欠息信息
		             
		loginEntity=submitInformation.LoginEntity(userid,password,orgCode,ip);//组织登陆post信息
		String dlcg;
		
		dlcg = new String(postloginthree(loginEntity).getBytes(),"GBK");
		Thread.sleep(1000);
		
		File ef = new File(downloadPath);
		if (!ef.exists()) {
			ef.mkdirs();
		}
		
		File filedlcg=new File(downloadPath+File.separator+"dlcg.html");
		buildFile(filedlcg,dlcg);
		String logId=CreditReportLogHelper.LogQyInfo(obj.getId(), "Intranet_QY_GeneralReport", userid,sessionFactory,user);
		List jkr=null;
		//输入多项 查询参数
		jkrEntity=submitInformation.jkrEntity( obj,userid,password,orgCode,ip);//输入中证码查询页面
		jkr=getPage(jkrEntity,downloadPath+File.separator+"jkr.html");
		WebResponse jkrWebResponse=(WebResponse)jkr.get(1);
		WebTable JTable=jkrWebResponse.getTableStartingWith("机构中文名称/英文名称");
		TableRow[] tableRows = JTable.getRows();
		if(tableRows.length>2){
			//停止查询返回有两条借款人信息
		}
		String crccode="";//抓取重要页面重要变化参数参数
		String strInCode="";
		for(int i=1;i<tableRows.length;i++){
			List crccodeParameter=new ArrayList();
			String onclickvalue=JTable.getTableCell(i, 0).getAttribute("onclick");
			strInCode=JTable.getTableCell(i, 3).getText();//获取中证码
			onclickvalue=onclickvalue.substring(onclickvalue.indexOf("(")+1, onclickvalue.indexOf(")"));
			crccodeParameter=getParameter(onclickvalue,"'",3);
			crccode=(String) crccodeParameter.get(0);
		}
		
		//中间页 其他页面需要此页成功抓取  抓取此页需要参数从上页返回值中截取
		List jkrjbxx=null;//要从中取出 "绍兴县国泰印染有限公司"
		jkrjbxxEntity=submitInformation.jkrjbxxEntity(strInCode,userid,password,orgCode,ip,crccode);//查询页面
		jkrjbxx=getPage(jkrjbxxEntity);
		WebResponse jkrjbxxWebResponse = (WebResponse) jkrjbxx.get(1);
		File pageFile=new File(downloadPath+File.separator+"Intranet_DetailReport.html");
		String pageString=(String)jkrjbxx.get(0);
		String dqzhxxPath="dqzhxx.html";
		String yehzPath="yehz.html";
		String fsehzPath="fsehz.html";
		String blfzhzPath="blfzhz.html";
		String dwdbhzPath="dwdbhz.html";
		String bdbhzPath="bdbhz.html";
		String qxxxhzPath="qxxxhz.html";
		
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"当前综合信息\" onClick=\"clickGeneral()\">", "<a href='"+dqzhxxPath+"'>当前综合信息</a>" );
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"余额信息\" onClick=\"clickDebit()\">", "<a href='"+yehzPath+"'>余额信息</a>" );
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"发生额信息\" onClick=\"clickDebit_His()\">", "<a href='"+fsehzPath+"'>发生额信息</a>" );
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"不良负债信息\" onClick=\"clickBadOwes()\">", "<a href='"+blfzhzPath+"'>不良负债信息</a>" );
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"担保信息\"  onClick=\"clickAssure()\">", "<a href='"+dwdbhzPath+"'>对外担保信息</a><img src=\"/shwebroot/images/line.gif\" width=\"5\" height=\"15\" align=\"absmiddle\"><a href='"+bdbhzPath+"'>被担保信息</a>" );
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"欠息信息\" onClick=\"clickOwe()\">", "<a href='"+qxxxhzPath+"'>欠息信息</a>" );
		pageString=pageString.replace("<img src=\"/shwebroot/images/line.gif\" width=\"5\" height=\"15\" align=\"absmiddle\">", "");
		buildFile(pageFile,pageString);
		
		WebForm form = jkrjbxxWebResponse.getForms()[0];
		String borrowernamecn=form.getParameterValues("borrowernamecn")[0];//抓取客户名称
		borrowernamecn=URLEncoder.encode(borrowernamecn,"gbk");
		
		List dqzhxx=null;
		dqzhxxEntity=submitInformation.dqzhxxEntity(strInCode,ip,borrowernamecn,crccode);//当前综合信息
		dqzhxx=getPage(dqzhxxEntity);
		WebResponse dqzhxxWebResponse=(WebResponse)dqzhxx.get(1);
		pageFile=new File(downloadPath+File.separator+"dqzhxx.html");
		pageString=(String)dqzhxx.get(0);
		pageString=deleteTop(pageString);
		buildFile(pageFile,pageString);
		
		//开始余额页面处理
		List yehz=null;//从中拿到有业务的金额
		yehzEntity=submitInformation.yehzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode);
		yehz=getPage(yehzEntity);
		WebResponse yehzWebResponse=(WebResponse)yehz.get(1);
		pageFile=new File(downloadPath+File.separator+"yehz.html");
		pageString=(String)yehz.get(0);
		pageString=deleteTop(pageString);
		
		WebLink[] Link=yehzWebResponse.getLinks();
		
		for(int j=0;j<Link.length;j++){//拿到余额汇总页面链接 循环
			List Parameter=new ArrayList();
			String onclickvalue=(Link[j].getAttribute("onclick")).substring(Link[j].getAttribute("onclick").indexOf("(")+1, Link[j].getAttribute("onclick").indexOf(")"));
			Parameter=getParameter(onclickvalue,"'",4);
			if(Parameter.get(3).equals("0")||Parameter.get(3)==null||Parameter.get(3)==""){
				continue;
			}
			switch(Integer.valueOf((String)Parameter.get(0))){
			case 1:
				String dkyexxxxPath="dkyexxxx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('1','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+dkyexxxxPath+"'>贷款余额" );
				pageString=pageString.replace("<font color=\"#880000\">贷款余额&nbsp;</font>", "");
				
				String dkyemxPath =downloadPath+File.separator+"dkyemx"+File.separator;
				File dkyemxFile = new File(dkyemxPath);
				if (!dkyemxFile.exists()) {
					dkyemxFile.mkdirs();
				}
				List dkyexxxx=null;//
				dkyexxxxEntity=submitInformation.dkyexxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				dkyexxxx=getPage(dkyexxxxEntity);//,
				WebResponse dkyexxxxwr=(WebResponse)dkyexxxx.get(1);
				File dkyexxxxPageFile=new File(downloadPath+File.separator+"dkyexxxx.html");
				String dkyexxxxPageString=(String)dkyexxxx.get(0);
				dkyexxxxPageString=deleteTop(dkyexxxxPageString);
				
				WebLink[] dkyeLink=dkyexxxxwr.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<dkyeLink.length;k++){//开始抓取每笔贷款
					String url=dkyeLink[k].getAttribute("href");
					
					
					String dbdkxxPath="dkyemx"+File.separator+"dbdkxx"+(dkyeLink[k].getText()).replace("/", "")+".html";
					dkyexxxxPageString=dkyexxxxPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbdkxxPath+"'>"+dkyeLink[k].getText()+"");
					dkyexxxxPageString=dkyexxxxPageString.replace("<font color=\"880000\">"+dkyeLink[k].getText()+"</font>", "");
					String dkyehtPath =downloadPath+File.separator+"dkyemx"+File.separator+"dkht"+(dkyeLink[k].getText()).replace("/", "");
					File dkyethFile = new File(dkyehtPath);
					if (!dkyethFile.exists()) {
						dkyethFile.mkdirs();
					}
					
					List dbdkxx=null;//单笔贷款 从中拿到合同信息
					dbdkyexxEntity=submitInformation.dbdkxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbdkxx=getPage(dbdkyexxEntity);
					WebResponse dbdkyexxWebResponse=(WebResponse)dbdkxx.get(1);
					File dbdkxxPageFile=new File(downloadPath+File.separator+"dkyemx"+File.separator+"dbdkxx"+(dkyeLink[k].getText()).replace("/", "")+".html");
					String dbdkxxPageString=(String)dbdkxx.get(0);
					dbdkxxPageString=deleteTop(dbdkxxPageString);
					
					WebLink[] dkyehtLink=dbdkyexxWebResponse.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<dkyehtLink.length;o++){//循环抓取贷款下合同页面
						String dkyehturl=dkyehtLink[o].getAttribute("href");
						List dkyeht=null;//单笔合同 从中拿到
						dkhtEntity=submitInformation.dkhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,dkyehturl);
						String dkhtPath="dkht"+(dkyeLink[k].getText()).replace("/", "")+File.separator+"dkht"+(dkyehtLink[o].getText()).replace("/", "")+".html";
						dbdkxxPageString=dbdkxxPageString.replace("<a href='"+dkyehturl.replace("∑", "&sum")+"'>", "<a href='"+dkhtPath+"'>"+dkyehtLink[o].getText()+"");
						dbdkxxPageString=dbdkxxPageString.replace("<font color=\"880000\">"+dkyehtLink[o].getText()+"</font>", "");
						dkyeht=getPage(dkhtEntity,downloadPath+File.separator+"dkyemx"+File.separator+"dkht"+(dkyeLink[k].getText()).replace("/", "")+File.separator+"dkht"+(dkyehtLink[o].getText()).replace("/", "")+".html");
						WebResponse dkyehtwr=(WebResponse)dkyeht.get(1);
					}
					buildFile(dbdkxxPageFile,dbdkxxPageString);
				}
				buildFile(dkyexxxxPageFile,dkyexxxxPageString);
				;break;
			case 2://保理
				break;
			case 3://贸易融资
				String myrzxxPath="myrz.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('3','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+myrzxxPath+"'>贸易融资余额" );
				pageString=pageString.replace("<font color=\"#880000\">贸易融资余额&nbsp;</font>", "");
				String myrzPath =downloadPath+File.separator+"myrzmx"+File.separator;
				File myrzFile = new File(myrzPath);
				if (!myrzFile.exists()) {
					myrzFile.mkdirs();
				}
				List myrz=null;//
				myrzEntity=submitInformation.myrzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				myrz=getPage(myrzEntity);
				WebResponse myrzWebResponse=(WebResponse)myrz.get(1);
				File myrzPageFile=new File(downloadPath+File.separator+"myrz.html");
				String myrzPageString=(String)myrz.get(0);
				myrzPageString=deleteTop(myrzPageString);
				WebLink[] myrzLink=myrzWebResponse.getLinks();//拿到贸易融资详细页面 所有单笔贸易融资连接
				for(int k=0;k<myrzLink.length;k++){//开始抓取每笔贷款
					String url=myrzLink[k].getAttribute("href");
					
					String dbmyrzPath="myrzmx"+File.separator+"dbmyrz"+(myrzLink[k].getText()).replace("/", "")+".html";
					myrzPageString=myrzPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbmyrzPath+"'>"+myrzLink[k].getText()+"");
					myrzPageString=myrzPageString.replace("<font color=\"#800000\">&nbsp;"+myrzLink[k].getText(), "");
					String myrzhtPath =downloadPath+File.separator+"myrzmx"+File.separator+"myrzht"+(myrzLink[k].getText()).replace("/", "");
					File myrzhtFile = new File(myrzhtPath);
					if (!myrzhtFile.exists()) {
						myrzhtFile.mkdirs();
					}
					
					List dbmyrz=null;//单笔贷款 从中拿到合同信息
					dbmyrzEntity=submitInformation.dbmyrzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbmyrz=getPage(dbmyrzEntity);
					WebResponse dbmyrzWebResponse=(WebResponse)dbmyrz.get(1);
					File dbmyrzPageFile=new File(downloadPath+File.separator+"myrzmx"+File.separator+"dbmyrz"+(myrzLink[k].getText()).replace("/", "")+".html");
					String dbmyrzPageString=(String)dbmyrz.get(0);
					dbmyrzPageString=deleteTop(dbmyrzPageString);
					
					WebLink[] myrzhtLink=dbmyrzWebResponse.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<myrzhtLink.length;o++){//循环抓取贷款下合同页面
						String myrzhturl=myrzhtLink[o].getAttribute("href");
						List myrzht=null;//单笔合同 从中拿到
						myrzhtEntity=submitInformation.myrzhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,myrzhturl);
						String myrzhtxxPath="myrzht"+(myrzLink[k].getText()).replace("/", "")+File.separator+"myrzht"+(myrzhtLink[o].getText()).replace("/", "")+".html";
						dbmyrzPageString=dbmyrzPageString.replace("<a href='"+myrzhturl.replace("∑", "&sum")+"'>", "<a href='"+myrzhtxxPath+"'>"+myrzhtLink[o].getText()+"");
						dbmyrzPageString=dbmyrzPageString.replace("<font color=\"880000\">", "");
						dbmyrzPageString=dbmyrzPageString.replace(myrzhtLink[o].getText()+"</font>", "");
						myrzht=getPage(myrzhtEntity,downloadPath+File.separator+"myrzmx"+File.separator+"myrzht"+(myrzLink[k].getText()).replace("/", "")+File.separator+"myrzht"+(myrzhtLink[o].getText()).replace("/", "")+".html");
						WebResponse myrzhtwr=(WebResponse)myrzht.get(1);
					}
					buildFile(dbmyrzPageFile,dbmyrzPageString);
				}
				buildFile(myrzPageFile,myrzPageString);
				;break;
			case 4://票据贴现（未结清金额）
				String pjtxxx="pjtx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('4','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+pjtxxx+"'>票据贴现（未结清金额）" );
				pageString=pageString.replace("<font color=\"#880000\">票据贴现（未结清金额）&nbsp;</font>", "");
				String pjtxPath =downloadPath+File.separator+"pjtxmx"+File.separator;
				File pjtxFile = new File(pjtxPath);
				if (!pjtxFile.exists()) {
					pjtxFile.mkdirs();
				}
				List pjtx=null;//
				pjtxEntity=submitInformation.pjtxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				pjtx=getPage(pjtxEntity);
				WebResponse pjtxWebResponse=(WebResponse)pjtx.get(1);
				File pjtxPageFile=new File(downloadPath+File.separator+"pjtx.html");
				String pjtxPageString=(String)pjtx.get(0);
				pjtxPageString=deleteTop(pjtxPageString);
				
				WebLink[] pjtxLink=pjtxWebResponse.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<pjtxLink.length;k++){//开始抓取每笔贷款
					String url=pjtxLink[k].getAttribute("href");
					String dbpjtxPath="pjtxmx"+File.separator+"dbpjtx"+(pjtxLink[k].getText()).replace("/", "")+".html";
					pjtxPageString=pjtxPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbpjtxPath+"'>"+pjtxLink[k].getText()+"");
					pjtxPageString=pjtxPageString.replace("<font color=\"#800000\">&nbsp;", "");
					pjtxPageString=pjtxPageString.replace("	"+pjtxLink[k].getText(), "");
					pjtxPageString=pjtxPageString.replace("</font>", "");
					String pjtxhtPath =downloadPath+File.separator+"pjtxmx"+File.separator+"pjtxht"+(pjtxLink[k].getText()).replace("/", "");
					File pjtxhtFile = new File(pjtxhtPath);
					if (!pjtxhtFile.exists()) {
						pjtxhtFile.mkdirs();
					}
					
					List dbpjtx=null;//单笔贷款 从中拿到合同信息
					dbpjtxEntity=submitInformation.dbpjtxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbpjtx=getPage(dbpjtxEntity);
					WebResponse dbpjtxWebResponse=(WebResponse)dbpjtx.get(1);
					File dbpjtxPageFile=new File(downloadPath+File.separator+"pjtxmx"+File.separator+"dbpjtx"+(pjtxLink[k].getText()).replace("/", "")+".html");
					String dbpjtxPageString=(String)dbpjtx.get(0);
					dbpjtxPageString=deleteTop(dbpjtxPageString);
					
					WebLink[] pjtxhtLink=dbpjtxWebResponse.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<pjtxhtLink.length;o++){//循环抓取贷款下合同页面
						String pjtxhturl=pjtxhtLink[o].getAttribute("href");
						List pjtxht=null;//单笔合同 从中拿到
						pjtxhtEntity=submitInformation.pjtxhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,pjtxhturl);
						String pjtxhtxxPath="pjtxht"+(pjtxLink[k].getText()).replace("/", "")+File.separator+"pjtxht"+(pjtxhtLink[o].getText()).replace("/", "")+".html";
						dbpjtxPageString=dbpjtxPageString.replace("<a href='"+pjtxhturl.replace("∑", "&sum")+"'>", "<a href='"+pjtxhtxxPath+"'>"+pjtxhtLink[o].getText()+"");
						dbpjtxPageString=dbpjtxPageString.replace("<font color=\"#800000\">&nbsp;"+pjtxhtLink[o].getText(), "");
						pjtxht=getPage(pjtxhtEntity,downloadPath+File.separator+"pjtxmx"+File.separator+"pjtxht"+(pjtxLink[k].getText()).replace("/", "")+File.separator+"pjtxht"+(pjtxhtLink[o].getText()).replace("/", "")+".html");
						WebResponse pjtxhtWebResponse=(WebResponse)pjtxht.get(1);
					}
					buildFile(dbpjtxPageFile,dbpjtxPageString);
				}
				buildFile(pjtxPageFile,pjtxPageString);
				;break;
			case 5://承兑汇票（未结清金额）
				String cdhpxxPath="cdhp.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('5','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+cdhpxxPath+"'>承兑汇票（未结清金额）" );
				pageString=pageString.replace("<font color=\"#880000\">承兑汇票（未结清金额）&nbsp;</font>", "");
				String cdhpPath =downloadPath+File.separator+"cdhpmx"+File.separator;
				File cdhpFile = new File(cdhpPath);
				if (!cdhpFile.exists()) {
					cdhpFile.mkdirs();
				}
				List cdhp=null;//
				cdhpEntity=submitInformation.cdhpEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				cdhp=getPage(cdhpEntity);
				WebResponse cdhpWebResponse=(WebResponse)cdhp.get(1);
				File cdhpPageFile=new File(downloadPath+File.separator+"cdhp.html");
				String cdhpPageString=(String)cdhp.get(0);
				cdhpPageString=deleteTop(cdhpPageString);
				
				WebLink[] cdhpLink=cdhpWebResponse.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<cdhpLink.length;k++){//开始抓取每笔贷款
					String url=cdhpLink[k].getAttribute("href");
					String dbcdhpPath="cdhpmx"+File.separator+"dbcdhp"+(cdhpLink[k].getText()).replace("/", "")+".html";
					cdhpPageString=cdhpPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbcdhpPath+"'>"+cdhpLink[k].getText()+"");
					cdhpPageString=cdhpPageString.replace("<font color=\"#800000\">"+cdhpLink[k].getText()+"</font>", "");
					String cdhphtPath =downloadPath+File.separator+"cdhpmx"+File.separator+"cdhpht"+(cdhpLink[k].getText()).replace("/", "");
					File cdhphtFile = new File(cdhphtPath);
					if (!cdhphtFile.exists()) {
						cdhphtFile.mkdirs();
					}
					
					List dbcdhp=null;//单笔贷款 从中拿到合同信息
					dbcdhpEntity=submitInformation.dbcdhpEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbcdhp=getPage(dbcdhpEntity);
					WebResponse dbcdhpWebResponse=(WebResponse)dbcdhp.get(1);
					File dbcdhpPageFile=new File(downloadPath+File.separator+"cdhpmx"+File.separator+"dbcdhp"+(cdhpLink[k].getText()).replace("/", "")+".html");
					String dbcdhpPageString=(String)dbcdhp.get(0);
					dbcdhpPageString=deleteTop(dbcdhpPageString);
					
					WebLink[] cdhphtLink=dbcdhpWebResponse.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<cdhphtLink.length;o++){//循环抓取贷款下合同页面
						String cdhphturl=cdhphtLink[o].getAttribute("href");
						List cdhpht=null;//单笔合同 从中拿到
						cdhphtEntity=submitInformation.cdhphtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,cdhphturl);
						String cdhphtxxPath="cdhpht"+(cdhpLink[k].getText()).replace("/", "")+File.separator+"cdhpht"+(cdhphtLink[o].getText()).replace("/", "")+".html";
						dbcdhpPageString=dbcdhpPageString.replace("<a href='"+cdhphturl.replace("∑", "&sum")+"'>", "<a href='"+cdhphtxxPath+"'>"+cdhphtLink[o].getText()+"");
						dbcdhpPageString=dbcdhpPageString.replace("<font color=\"880000\">"+cdhphtLink[o].getText()+"</font>", "");
						cdhpht=getPage(cdhphtEntity,downloadPath+File.separator+"cdhpmx"+File.separator+"cdhpht"+(cdhpLink[k].getText()).replace("/", "")+File.separator+"cdhpht"+(cdhphtLink[o].getText()).replace("/", "")+".html");
						WebResponse cdhphtwr=(WebResponse)cdhpht.get(1);
					}
					buildFile(dbcdhpPageFile,dbcdhpPageString);
				}
				buildFile(cdhpPageFile,cdhpPageString);
				;break;
			case 6://信用证余额	
				break;
			case 7://保函余额
				break;
			case 8://资产保全剥离余额
				break;
			case 9://类贷款余额
				String ldkxxPath="ldk.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('9','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+ldkxxPath+"'>类贷款余额" );
				pageString=pageString.replace("<font color=\"#880000\">类贷款余额&nbsp;</font>", "");
				String ldkPath =downloadPath+File.separator+"ldkmx"+File.separator;
				File ldkFile = new File(ldkPath);
				if (!ldkFile.exists()) {
					ldkFile.mkdirs();
				}
				
				List ldk=null;//
				ldkEntity=submitInformation.ldkEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				ldk=getPage(ldkEntity);
				WebResponse ldkWebResponse=(WebResponse)ldk.get(1);
				File ldkPageFile=new File(downloadPath+File.separator+"ldk.html");
				String ldkPageString=(String)ldk.get(0);
				ldkPageString=deleteTop(ldkPageString);
				
				WebLink[] ldkLink=ldkWebResponse.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<ldkLink.length;k++){//开始抓取每笔贷款
					String url=ldkLink[k].getAttribute("href");
					String dbldkPath="ldkmx"+File.separator+"dbldk"+(ldkLink[k].getText()).replace("/", "")+".html";
					ldkPageString=ldkPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbldkPath+"'>"+ldkLink[k].getText()+"");
					ldkPageString=ldkPageString.replace("<font color=\"880000\">"+ldkLink[k].getText()+"</font>", "");
					String ldkhtPath =downloadPath+File.separator+"ldkmx"+File.separator+"ldkht"+(ldkLink[k].getText()).replace("/", "");
					File ldkhtFile = new File(ldkhtPath);
					if (!ldkhtFile.exists()) {
						ldkhtFile.mkdirs();
					}
					
					List dbldk=null;//单笔贷款 从中拿到合同信息
					dbldkEntity=submitInformation.dbldkEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbldk=getPage(dbldkEntity);
					WebResponse dbldkWebResponse=(WebResponse)dbldk.get(1);
					File dbldkPageFile=new File(downloadPath+File.separator+"ldkmx"+File.separator+"dbldk"+(ldkLink[k].getText()).replace("/", "")+".html");
					String dbldkPageString=(String)dbldk.get(0);
					dbldkPageString=deleteTop(dbldkPageString);
					WebLink[] ldkhtLink=dbldkWebResponse.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<ldkhtLink.length;o++){//循环抓取贷款下合同页面
						String ldkhturl=ldkhtLink[o].getAttribute("href");
						List ldkht=null;//单笔合同 从中拿到
						ldkhtEntity=submitInformation.ldkhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,ldkhturl);
						String ldkhtxxPath="ldkht"+(ldkLink[k].getText()).replace("/", "")+File.separator+"ldkht"+(ldkhtLink[o].getText()).replace("/", "")+".html";
						dbldkPageString=dbldkPageString.replace("<a href='"+ldkhturl.replace("∑", "&sum")+"'>", "<a href='"+ldkhtxxPath+"'>"+ldkhtLink[o].getText()+"");
						dbldkPageString=dbldkPageString.replace("<font color=\"880000\">"+ldkhtLink[o].getText()+"</font>", "");
						ldkht=getPage(ldkhtEntity,downloadPath+File.separator+"ldkmx"+File.separator+"ldkht"+(ldkLink[k].getText()).replace("/", "")+File.separator+"ldkht"+(ldkhtLink[o].getText()).replace("/", "")+".html");
						WebResponse ldkhtwr=(WebResponse)ldkht.get(1);
					}
					buildFile(dbldkPageFile,dbldkPageString);
				}
				buildFile(ldkPageFile,ldkPageString);
				
				;break;
			}	
		}
		buildFile(pageFile,pageString);
		
		//开始发生额页面处理
		List fsehz=null;//从中拿到有业务的金额
		fsehzEntity=submitInformation.fsehzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode);
		fsehz=getPage(fsehzEntity);
		WebResponse fsehzwr=(WebResponse)fsehz.get(1);
		pageFile=new File(downloadPath+File.separator+"fsehz.html");
		pageString=(String)fsehz.get(0);
		pageString=deleteTop(pageString);
		
		WebLink[] fseLink=fsehzwr.getLinks();//发生额汇总页面全部链接
		for(int j=0;j<fseLink.length;j++){//拿到发生额汇总页面链接 循环
			List Parameter=new ArrayList();
			String onclickvalue=(fseLink[j].getAttribute("onclick")).substring(fseLink[j].getAttribute("onclick").indexOf("(")+1, fseLink[j].getAttribute("onclick").indexOf(")"));
			Parameter=getParameter(onclickvalue,"'",4);
			if(Parameter.get(3).equals("0")||Parameter.get(3)==null||Parameter.get(3)==""){
				continue;
			}
			switch(Integer.valueOf((String)Parameter.get(0))){
			case 1:
				String dkfsexxxxPath="dkfsexxxx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('1','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+dkfsexxxxPath+"'>贷款发生额" );
				pageString=pageString.replace("<font color=\"#880000\">贷款发生额&nbsp;</font>", "");
				String dkfsemxPath =downloadPath+File.separator+"dkfsemx"+File.separator;
				File dkfsemxFile = new File(dkfsemxPath);
				if (!dkfsemxFile.exists()) {
					dkfsemxFile.mkdirs();
				}
				List dkfsexxxx=null;//
				dkfsexxxxEntity=submitInformation.dkfsexxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				dkfsexxxx=getPage(dkfsexxxxEntity);
				WebResponse dkfsexxxxWebResponse=(WebResponse)dkfsexxxx.get(1);
				File dkfsexxxxPageFile=new File(downloadPath+File.separator+"dkfsexxxx.html");
				String dkfsexxxxPageString=(String)dkfsexxxx.get(0);
				dkfsexxxxPageString=deleteTop(dkfsexxxxPageString);
				WebLink[] dkfseLink=dkfsexxxxWebResponse.getLinks();//拿到贷款发生额详细页面 所有单笔贷款连接
				for(int k=0;k<dkfseLink.length;k++){//开始抓取每笔贷款
					String url=dkfseLink[k].getAttribute("href");
					String dbdkfsexxPath="dkfsemx"+File.separator+"dbdkfsexx"+(dkfseLink[k].getText()).replace("/", "")+".html";
					dkfsexxxxPageString=dkfsexxxxPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbdkfsexxPath+"'>"+dkfseLink[k].getText());
					dkfsexxxxPageString=dkfsexxxxPageString.replace("<font color=\"880000\">"+dkfseLink[k].getText()+"</font>", "");
					String dkfsehtPath =downloadPath+File.separator+"dkfsemx"+File.separator+"dkfseht"+(dkfseLink[k].getText()).replace("/", "");
					File dkfsethFile = new File(dkfsehtPath);
					if (!dkfsethFile.exists()) {
						dkfsethFile.mkdirs();
					}
					List dbdkfsexx=null;//单笔贷款 从中拿到合同信息
					dbdkfsexxEntity=submitInformation.dbdkfsexxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbdkfsexx=getPage(dbdkfsexxEntity);
					WebResponse dbdkfsexxwr=(WebResponse)dbdkfsexx.get(1);
					File dbdkfsexxPageFile=new File(downloadPath+File.separator+"dkfsemx"+File.separator+"dbdkfsexx"+(dkfseLink[k].getText()).replace("/", "")+".html");
					String dbdkfsexxPageString=(String)dbdkfsexx.get(0);
					dbdkfsexxPageString=deleteTop(dbdkfsexxPageString);
					
					
					WebLink[] dkfsehtLink=dbdkfsexxwr.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<dkfsehtLink.length;o++){//循环抓取贷款下合同页面
						String dkfsehturl=dkfsehtLink[o].getAttribute("href");
						List dkfseht=null;//单笔合同 从中拿到
						dkfsehtEntity=submitInformation.dkfsehtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,dkfsehturl);

						String dkfsehtxxPath="dkfseht"+(dkfseLink[k].getText()).replace("/", "")+File.separator+"dkfseht"+(dkfsehtLink[o].getText()).replace("/", "")+".html";
						dbdkfsexxPageString=dbdkfsexxPageString.replace("<a href='"+dkfsehturl.replace("∑", "&sum")+"'>", "<a href='"+dkfsehtxxPath+"'>"+dkfsehtLink[o].getText());
						dbdkfsexxPageString=dbdkfsexxPageString.replace("<font color=\"880000\">"+dkfsehtLink[o].getText()+"</font>", "");
						
						dkfseht=getPage(dkfsehtEntity,downloadPath+File.separator+"dkfsemx"+File.separator+"dkfseht"+(dkfseLink[k].getText()).replace("/", "")+File.separator+"dkfseht"+(dkfsehtLink[o].getText()).replace("/", "")+".html");
						WebResponse dkfsehtwr=(WebResponse)dkfseht.get(1);
						
					}
					buildFile(dbdkfsexxPageFile,dbdkfsexxPageString);
				}
				buildFile(dkfsexxxxPageFile,dkfsexxxxPageString);
				;break;
			case 2://保理
				break;
			case 3://贸易融资
				String myrzfsexxPath="myrzfse.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('3','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+myrzfsexxPath+"'>贸易融资发生额" );
				pageString=pageString.replace("<font color=\"#880000\">贸易融资发生额&nbsp;</font>", "");
				
				String myrzfsePath =downloadPath+File.separator+"myrzfsemx"+File.separator;
				File myrzfseFile = new File(myrzfsePath);
				if (!myrzfseFile.exists()) {
					myrzfseFile.mkdirs();
				}
				List myrzfse=null;//
				myrzfseEntity=submitInformation.myrzfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				myrzfse=getPage(myrzfseEntity);
				WebResponse myrzfsewr=(WebResponse)myrzfse.get(1);
				File myrzfsePageFile=new File(downloadPath+File.separator+"myrzfse.html");
				String myrzfsePageString=(String)myrzfse.get(0);
				myrzfsePageString=deleteTop(myrzfsePageString);
				
				
				WebLink[] myrzfseLink=myrzfsewr.getLinks();//拿到贸易融资发生额详细页面 所有单笔贸易融资连接
				for(int k=0;k<myrzfseLink.length;k++){//开始抓取每笔贸易融资发生额
					String url=myrzfseLink[k].getAttribute("href");
					
					String dbmyrzfsePath="myrzfsemx"+File.separator+"dbmyrzfse"+(myrzfseLink[k].getText()).replace("/", "")+".html";
					myrzfsePageString=myrzfsePageString.replace("<a href='"+url+"'>", "<a href='"+dbmyrzfsePath+"'>"+myrzfseLink[k].getText());
					myrzfsePageString=myrzfsePageString.replace("<font color=\"#800000\">&nbsp;"+myrzfseLink[k].getText(), "");
					
					String myrzfsehtPath =downloadPath+File.separator+"myrzfsemx"+File.separator+"myrzfseht"+(myrzfseLink[k].getText()).replace("/", "");
					File myrzfsehtFile = new File(myrzfsehtPath);
					if (!myrzfsehtFile.exists()) {
						myrzfsehtFile.mkdirs();
					}
					
					List dbmyrzfse=null;//单笔贸易融资发生额 从中拿到合同信息
					dbmyrzfseEntity=submitInformation.dbmyrzfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbmyrzfse=getPage(dbmyrzfseEntity);
					WebResponse dbmyrzfsewr=(WebResponse)dbmyrzfse.get(1);
					File dbmyrzfsePageFile=new File(downloadPath+File.separator+"myrzfsemx"+File.separator+"dbmyrzfse"+(myrzfseLink[k].getText()).replace("/", "")+".html");
					String dbmyrzfsePageString=(String)dbmyrzfse.get(0);
					dbmyrzfsePageString=deleteTop(dbmyrzfsePageString);
					
					WebLink[] myrzfsehtLink=dbmyrzfsewr.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<myrzfsehtLink.length;o++){//循环抓取贷款下合同页面
						String myrzfsehturl=myrzfsehtLink[o].getAttribute("href");
						List myrzfseht=null;//单笔合同 从中拿到
						myrzfsehtEntity=submitInformation.myrzfsehtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,myrzfsehturl);
						String myrzfsehtxxPath="myrzfseht"+(myrzfseLink[k].getText()).replace("/", "")+File.separator+"myrzfseht"+(myrzfsehtLink[o].getText()).replace("/", "")+".html";
						dbmyrzfsePageString=dbmyrzfsePageString.replace("<a href='"+myrzfsehturl.replace("∑", "&sum")+"'>", "<a href='"+myrzfsehtxxPath+"'>"+myrzfsehtLink[o].getText());
						dbmyrzfsePageString=dbmyrzfsePageString.replace("<font color=\"880000\">", "");
						dbmyrzfsePageString=dbmyrzfsePageString.replace(myrzfsehtLink[o].getText()+"</font>", "");
						myrzfseht=getPage(myrzfsehtEntity,downloadPath+File.separator+"myrzfsemx"+File.separator+"myrzfseht"+(myrzfseLink[k].getText()).replace("/", "")+File.separator+"myrzfseht"+(myrzfsehtLink[o].getText()).replace("/", "")+".html");
						WebResponse myrzfsehtwr=(WebResponse)myrzfseht.get(1);
					}
					buildFile(dbmyrzfsePageFile,dbmyrzfsePageString);
				}
				buildFile(myrzfsePageFile,myrzfsePageString);
				;break;
			case 4://票据贴现（未结清金额）
				String pjtxfsexx="pjtxfse.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('4','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+pjtxfsexx+"'>票据贴现发生额" );
				pageString=pageString.replace("<font color=\"#880000\">票据贴现发生额&nbsp;</font>", "");
				String pjtxfsePath =downloadPath+File.separator+"pjtxfsemx"+File.separator;
				File pjtxfseFile = new File(pjtxfsePath);
				if (!pjtxfseFile.exists()) {
					pjtxfseFile.mkdirs();
				}
				List pjtxfse=null;//
				pjtxfseEntity=submitInformation.pjtxfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				pjtxfse=getPage(pjtxfseEntity);
				WebResponse pjtxfsewr=(WebResponse)pjtxfse.get(1);
				File pjtxfsePageFile=new File(downloadPath+File.separator+"pjtxfse.html");
				String pjtxfsePageString=(String)pjtxfse.get(0);
				pjtxfsePageString=deleteTop(pjtxfsePageString);
				
				WebLink[] pjtxfseLink=pjtxfsewr.getLinks();//拿到票据贴现详细页面 所有单笔票据连接
				for(int k=0;k<pjtxfseLink.length;k++){//开始抓取每笔票据
					String url=pjtxfseLink[k].getAttribute("href");
					
					String dbpjtxfsePath="pjtxfsemx"+File.separator+"dbpjtxfse"+(pjtxfseLink[k].getText()).replace("/", "")+".html";
					pjtxfsePageString=pjtxfsePageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbpjtxfsePath+"'>"+pjtxfseLink[k].getText());
					pjtxfsePageString=pjtxfsePageString.replace("<font color=\"#800000\">&nbsp;", "");
					pjtxfsePageString=pjtxfsePageString.replace("	"+pjtxfseLink[k].getText(), "");
					
					String pjtxfsehtPath =downloadPath+File.separator+"pjtxfsemx"+File.separator+"pjtxfseht"+(pjtxfseLink[k].getText()).replace("/", "");
					File pjtxfsehtFile = new File(pjtxfsehtPath);
					if (!pjtxfsehtFile.exists()) {
						pjtxfsehtFile.mkdirs();
					}
					
					List dbpjtxfse=null;//单笔票据 从中拿到合同信息
					dbpjtxfseEntity=submitInformation.dbpjtxfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbpjtxfse=getPage(dbpjtxfseEntity);
					WebResponse dbpjtxfsewr=(WebResponse)dbpjtxfse.get(1);
					File dbpjtxfsePageFile=new File(downloadPath+File.separator+"pjtxfsemx"+File.separator+"dbpjtxfse"+(pjtxfseLink[k].getText()).replace("/", "")+".html");
					String dbpjtxfsePageString=(String)dbpjtxfse.get(0);
					dbpjtxfsePageString=deleteTop(dbpjtxfsePageString);
					
					WebLink[] pjtxfsehtLink=dbpjtxfsewr.getLinks();//拿到本次循环得到的票据的所有合同连接
					for(int o=0;o<pjtxfsehtLink.length;o++){//循环抓取票据下合同页面
						
						String pjtxfsehturl=pjtxfsehtLink[o].getAttribute("href");
						List pjtxfseht=null;//单笔合同 从中拿到
						pjtxfsehtEntity=submitInformation.pjtxfsehtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,pjtxfsehturl);
						
						String pjtxfsehtxxPath="pjtxfseht"+(pjtxfseLink[k].getText()).replace("/", "")+File.separator+"pjtxfseht"+(pjtxfsehtLink[o].getText()).replace("/", "")+".html";
						dbpjtxfsePageString=dbpjtxfsePageString.replace("<a href='"+pjtxfsehturl.replace("∑", "&sum")+"'>", "<a href='"+pjtxfsehtxxPath+"'>"+pjtxfsehtLink[o].getText());
						dbpjtxfsePageString=dbpjtxfsePageString.replace("<font color=\"#800000\">&nbsp;"+pjtxfsehtLink[o].getText(), "");
						
						pjtxfseht=getPage(pjtxfsehtEntity,downloadPath+File.separator+"pjtxfsemx"+File.separator+"pjtxfseht"+(pjtxfseLink[k].getText()).replace("/", "")+File.separator+"pjtxfseht"+(pjtxfsehtLink[o].getText()).replace("/", "")+".html");
						WebResponse pjtxfsehtwr=(WebResponse)pjtxfseht.get(1);
						
					}
					buildFile(dbpjtxfsePageFile,dbpjtxfsePageString);
				}
				buildFile(pjtxfsePageFile,pjtxfsePageString);
				;break;
			case 5://承兑汇票（未结清金额）
				String cdhpfsexxPath="cdhpfse.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('5','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+cdhpfsexxPath+"'>承兑汇票发生额" );
				pageString=pageString.replace("<font color=\"#880000\">承兑汇票发生额&nbsp;</font>", "");
				
				
				String cdhpfsePath =downloadPath+File.separator+"cdhpfsemx"+File.separator;
				File cdhpfseFile = new File(cdhpfsePath);
				if (!cdhpfseFile.exists()) {
					cdhpfseFile.mkdirs();
				}
				List cdhpfse=null;//
				cdhpfseEntity=submitInformation.cdhpfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				cdhpfse=getPage(cdhpfseEntity);
				WebResponse cdhpfsewr=(WebResponse)cdhpfse.get(1);
				File cdhpfsePageFile=new File(downloadPath+File.separator+"cdhpfse.html");
				String cdhpfsePageString=(String)cdhpfse.get(0);
				cdhpfsePageString=deleteTop(cdhpfsePageString);
				
				WebLink[] cdhpfseLink=cdhpfsewr.getLinks();//拿到承兑汇票详细页面 所有单笔承兑汇票连接
				for(int k=0;k<cdhpfseLink.length;k++){//开始抓取每笔承兑汇票
					String url=cdhpfseLink[k].getAttribute("href");
					
					String dbcdhpfsePath="cdhpfsemx"+File.separator+"dbcdhpfse"+(cdhpfseLink[k].getText()).replace("/", "")+".html";
					cdhpfsePageString=cdhpfsePageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbcdhpfsePath+"'>"+cdhpfseLink[k].getText());
					cdhpfsePageString=cdhpfsePageString.replace("<font color=\"#800000\">"+cdhpfseLink[k].getText()+"</font>", "");
					
					String cdhpfsehtPath =downloadPath+File.separator+"cdhpfsemx"+File.separator+"cdhpfseht"+(cdhpfseLink[k].getText()).replace("/", "");
					File cdhpfsehtFile = new File(cdhpfsehtPath);
					if (!cdhpfsehtFile.exists()) {
						cdhpfsehtFile.mkdirs();
					}
					
					List dbcdhpfse=null;//单笔承兑汇票 从中拿到合同信息
					dbcdhpfseEntity=submitInformation.dbcdhpfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbcdhpfse=getPage(dbcdhpfseEntity);
					WebResponse dbcdhpfsewr=(WebResponse)dbcdhpfse.get(1);
					File dbcdhpfsePageFile=new File(downloadPath+File.separator+"cdhpfsemx"+File.separator+"dbcdhpfse"+(cdhpfseLink[k].getText()).replace("/", "")+".html");
					String dbcdhpfsePageString=(String)dbcdhpfse.get(0);
					dbcdhpfsePageString=deleteTop(dbcdhpfsePageString);
					
					WebLink[] cdhpfsehtLink=dbcdhpfsewr.getLinks();//拿到本次循环得到的承兑汇票的所有合同连接
					for(int o=0;o<cdhpfsehtLink.length;o++){//循环抓取承兑汇票下合同页面
						
						String cdhpfsehturl=cdhpfsehtLink[o].getAttribute("href");
						List cdhpfseht=null;//单笔合同 从中拿到
						cdhpfsehtEntity=submitInformation.cdhpfsehtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,cdhpfsehturl);
						String cdhpfsehtxxPath="cdhpfseht"+(cdhpfseLink[k].getText()).replace("/", "")+File.separator+"cdhpfseht"+(cdhpfsehtLink[o].getText()).replace("/", "")+".html";
						dbcdhpfsePageString=dbcdhpfsePageString.replace("<a href='"+cdhpfsehturl.replace("∑", "&sum")+"'>", "<a href='"+cdhpfsehtxxPath+"'>"+cdhpfsehtLink[o].getText());
						dbcdhpfsePageString=dbcdhpfsePageString.replace("<font color=\"880000\">"+cdhpfsehtLink[o].getText()+"</font>", "");
						cdhpfseht=getPage(cdhpfsehtEntity,downloadPath+File.separator+"cdhpfsemx"+File.separator+"cdhpfseht"+(cdhpfseLink[k].getText()).replace("/", "")+File.separator+"cdhpfseht"+(cdhpfsehtLink[o].getText()).replace("/", "")+".html");
						WebResponse cdhpfsehtwr=(WebResponse)cdhpfseht.get(1);
					}
					buildFile(dbcdhpfsePageFile,dbcdhpfsePageString);
				}
				buildFile(cdhpfsePageFile,cdhpfsePageString);
				;break;
			case 6://信用证余额	
				break;
			case 7://保函余额
				break;
			case 8://资产保全剥离余额
				break;
			case 9://类贷款发生额
				String ldkfsexxPath="ldkfse.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('9','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+ldkfsexxPath+"'>类贷款余额" );
				pageString=pageString.replace("<font color=\"#880000\">类贷款发生额&nbsp;</font>", "");
				String ldkfsePath =downloadPath+File.separator+"ldkfsemx"+File.separator;
				File ldkfseFile = new File(ldkfsePath);
				if (!ldkfseFile.exists()) {
					ldkfseFile.mkdirs();
				}
				
				List ldkfse=null;//
				ldkfseEntity=submitInformation.ldkfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				
				ldkfse=getPage(ldkfseEntity);
				WebResponse ldkfseWebResponse=(WebResponse)ldkfse.get(1);
				File ldkfsePageFile=new File(downloadPath+File.separator+"ldkfse.html");
				String ldkfsePageString=(String)ldkfse.get(0);
				ldkfsePageString=deleteTop(ldkfsePageString);
				
				WebLink[] ldkfseLink=ldkfseWebResponse.getLinks();//拿到类贷款详细页面 所有单笔贷款连接
				for(int k=0;k<ldkfseLink.length;k++){//开始抓取每笔类贷款
					String url=ldkfseLink[k].getAttribute("href");
					
					String dbldkfsePath="ldkfsemx"+File.separator+"dbldkfse"+(ldkfseLink[k].getText()).replace("/", "")+".html";
					ldkfsePageString=ldkfsePageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbldkfsePath+"'>"+ldkfseLink[k].getText()+"");
					ldkfsePageString=ldkfsePageString.replace("<font color=\"880000\">"+ldkfseLink[k].getText()+"</font>", "");
					String ldkfsehtPath =downloadPath+File.separator+"ldkfsemx"+File.separator+"ldkfseht"+(ldkfseLink[k].getText()).replace("/", "");
					File ldkfsehtFile = new File(ldkfsehtPath);
					if (!ldkfsehtFile.exists()) {
						ldkfsehtFile.mkdirs();
					}
					
					List dbldkfse=null;//单笔类贷款 从中拿到合同信息
					dbldkfseEntity=submitInformation.dbldkfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					
					dbldkfse=getPage(dbldkfseEntity);
					WebResponse dbldkfseWebResponse=(WebResponse)dbldkfse.get(1);
					File dbldkfsePageFile=new File(downloadPath+File.separator+"ldkfsemx"+File.separator+"dbldkfse"+(ldkfseLink[k].getText()).replace("/", "")+".html");
					String dbldkfsePageString=(String)dbldkfse.get(0);
					dbldkfsePageString=deleteTop(dbldkfsePageString);
					
					WebLink[] ldkfsehtLink=dbldkfseWebResponse.getLinks();//拿到本次循环得到的类贷款的所有合同连接
					for(int o=0;o<ldkfsehtLink.length;o++){//循环抓取类贷款下合同页面
						String ldkfsehturl=ldkfsehtLink[o].getAttribute("href");
						List ldkfseht=null;//单笔合同 从中拿到
						ldkfsehtEntity=submitInformation.ldkfsehtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,ldkfsehturl);
						
						String ldkfsehtxxPath="ldkfseht"+(ldkfseLink[k].getText()).replace("/", "")+File.separator+"ldkfseht"+(ldkfsehtLink[o].getText()).replace("/", "")+".html";
						dbldkfsePageString=dbldkfsePageString.replace("<a href='"+ldkfsehturl.replace("∑", "&sum")+"'>", "<a href='"+ldkfsehtxxPath+"'>"+ldkfsehtLink[o].getText()+"");
						dbldkfsePageString=dbldkfsePageString.replace("<font color=\"880000\">"+ldkfsehtLink[o].getText()+"</font>", "");
						ldkfseht=getPage(ldkfsehtEntity,downloadPath+File.separator+"ldkfsemx"+File.separator+"ldkfseht"+(ldkfseLink[k].getText()).replace("/", "")+File.separator+"ldkfseht"+(ldkfsehtLink[o].getText()).replace("/", "")+".html");
						WebResponse ldkfsehtwr=(WebResponse)ldkfseht.get(1);
					}
					buildFile(dbldkfsePageFile,dbldkfsePageString);
				}
				buildFile(ldkfsePageFile,ldkfsePageString);
				;break;
			}	
		}		
		buildFile(pageFile,pageString);
	/*	*/		
		
		//开始不良负债页面处理
		List blfzhz=null;//从中拿到有业务的金额
		blfzhzEntity=submitInformation.blfzhzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode);
		blfzhz=getPage(blfzhzEntity);
		WebResponse blfzhzwr=(WebResponse)blfzhz.get(1);
		pageFile=new File(downloadPath+File.separator+"blfzhz.html");
		pageString=(String)blfzhz.get(0);
		pageString=deleteTop(pageString);
		
		WebLink[] blfzLink=blfzhzwr.getLinks();//不良负债汇总页面全部链接
		for(int j=0;j<blfzLink.length;j++){//拿到不良负债汇总页面链接 循环
			List Parameter=new ArrayList();
			String onclickvalue=(blfzLink[j].getAttribute("onclick")).substring(blfzLink[j].getAttribute("onclick").indexOf("(")+1, blfzLink[j].getAttribute("onclick").indexOf(")"));
			Parameter=getParameter(onclickvalue,"'",4);
			if(Parameter.get(3).equals("0")||Parameter.get(3)==null||Parameter.get(3)==""){
				continue;
			}
			switch(Integer.valueOf((String)Parameter.get(0))){
			case 1:
				String dkblfzxxxxPath="dkblfzxxxx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('1','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+dkblfzxxxxPath+"'>贷款余额" );
				pageString=pageString.replace("<font color=\"#880000\">贷款余额&nbsp;</font>", "");
				
				String dkblfzmxPath =downloadPath+File.separator+"dkblfzmx"+File.separator;
				File dkblfzmxFile = new File(dkblfzmxPath);
				if (!dkblfzmxFile.exists()) {
					dkblfzmxFile.mkdirs();
				}
				
				List dkblfzxxxx=null;//
				dkblfzxxxxEntity=submitInformation.dkblfzxxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				dkblfzxxxx=getPage(dkblfzxxxxEntity);
				WebResponse dkblfzxxxxwr=(WebResponse)dkblfzxxxx.get(1);
				File dkblfzxxxxPageFile=new File(downloadPath+File.separator+"dkblfzxxxx.html");
				String dkblfzxxxxPageString=(String)dkblfzxxxx.get(0);
				dkblfzxxxxPageString=deleteTop(dkblfzxxxxPageString);
				
				
				WebLink[] dkblfzLink=dkblfzxxxxwr.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<dkblfzLink.length;k++){//开始抓取每笔贷款
					String url=dkblfzLink[k].getAttribute("href");
					
					String dbdkblfzxxPath="dkblfzmx"+File.separator+"dbdkblfzxx"+(dkblfzLink[k].getText()).replace("/", "")+".html";
					dkblfzxxxxPageString=dkblfzxxxxPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbdkblfzxxPath+"'>"+dkblfzLink[k].getText());
					dkblfzxxxxPageString=dkblfzxxxxPageString.replace("<font color=\"880000\">"+dkblfzLink[k].getText()+"</font>", "");
					
					String dkblfzhtPath =downloadPath+File.separator+"dkblfzmx"+File.separator+"dkblfzht"+(dkblfzLink[k].getText()).replace("/", "");
					File dkblfzhtFile = new File(dkblfzhtPath);
					if (!dkblfzhtFile.exists()) {
						dkblfzhtFile.mkdirs();
					}
					List dbdkblfzxx=null;//单笔贷款 从中拿到合同信息
					dbdkblfzxxEntity=submitInformation.dbdkblfzxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbdkblfzxx=getPage(dbdkblfzxxEntity);
					WebResponse dbdkblfzxxwr=(WebResponse)dbdkblfzxx.get(1);
					File dbdkblfzxxPageFile=new File(downloadPath+File.separator+"dkblfzmx"+File.separator+"dbdkblfzxx"+(dkblfzLink[k].getText()).replace("/", "")+".html");
					String dbdkblfzxxPageString=(String)dbdkblfzxx.get(0);
					dbdkblfzxxPageString=deleteTop(dbdkblfzxxPageString);
					
					WebLink[] dkblfzhtLink=dbdkblfzxxwr.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<dkblfzhtLink.length;o++){//循环抓取贷款下合同页面
						String dkblfzhturl=dkblfzhtLink[o].getAttribute("href");
						List dkblfzht=null;//单笔合同 从中拿到
						dkblfzhtEntity=submitInformation.dkblfzhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,dkblfzhturl);
						String dkhtPath="dkblfzht"+(dkblfzLink[k].getText()).replace("/", "")+File.separator+"dkblfzht"+(dkblfzhtLink[o].getText()).replace("/", "")+".html";
						dbdkblfzxxPageString=dbdkblfzxxPageString.replace("<a href='"+dkblfzhturl.replace("∑", "&sum")+"'>", "<a href='"+dkhtPath+"'>"+dkblfzhtLink[o].getText());
						dbdkblfzxxPageString=dbdkblfzxxPageString.replace("<font color=\"880000\">"+dkblfzhtLink[o].getText()+"</font>", "");
						dkblfzht=getPage(dkblfzhtEntity,downloadPath+File.separator+"dkblfzmx"+File.separator+"dkblfzht"+(dkblfzLink[k].getText()).replace("/", "")+File.separator+"dkblfzht"+(dkblfzhtLink[o].getText()).replace("/", "")+".html");
						WebResponse dkblfzhtwr=(WebResponse)dkblfzht.get(1);
					}
					buildFile(dbdkblfzxxPageFile,dbdkblfzxxPageString);
				}
				buildFile(dkblfzxxxxPageFile,dkblfzxxxxPageString);
				;break;
			case 2://保理
				break;
			case 3://贸易融资
				String myrzblfzxxPath="myrzblfz.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('3','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+myrzblfzxxPath+"'>贸易融资余额" );
				pageString=pageString.replace("<font color=\"#880000\">贸易融资余额&nbsp;</font>", "");
				
				String myrzblfzPath =downloadPath+File.separator+"myrzblfzmx"+File.separator;
				File myrzblfzFile = new File(myrzblfzPath);
				if (!myrzblfzFile.exists()) {
					myrzblfzFile.mkdirs();
				}
				List myrzblfz=null;
				myrzblfzEntity=submitInformation.myrzblfzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				myrzblfz=getPage(myrzblfzEntity);
				WebResponse myrzblfzwr=(WebResponse)myrzblfz.get(1);
				File myrzblfzPageFile=new File(downloadPath+File.separator+"myrzblfz.html");
				String myrzblfzPageString=(String)myrzblfz.get(0);
				myrzblfzPageString=deleteTop(myrzblfzPageString);
				
				WebLink[] myrzblfzLink=myrzblfzwr.getLinks();//拿到贸易融资详细页面 所有单笔贸易融资连接
				for(int k=0;k<myrzblfzLink.length;k++){//开始抓取每笔贸易融资发生额
					String url=myrzblfzLink[k].getAttribute("href");
					String dbmyrzblfzPath="myrzblfzmx"+File.separator+"dbmyrzblfz"+(myrzblfzLink[k].getText()).replace("/", "")+".html";
					myrzblfzPageString=myrzblfzPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbmyrzblfzPath+"'>"+myrzblfzLink[k].getText());
					myrzblfzPageString=myrzblfzPageString.replace("<font color=\"#800000\">&nbsp;"+myrzblfzLink[k].getText(), "");
					String myrzblfzhtPath =downloadPath+File.separator+"myrzblfzmx"+File.separator+"myrzblfzht"+myrzblfzLink[k].getText();
					File myrzblfzhtFile = new File(myrzblfzhtPath);
					if (!myrzblfzhtFile.exists()) {
						myrzblfzhtFile.mkdirs();
					}
					
					List dbmyrzblfz=null;//单笔贸易融资发生额 从中拿到合同信息
					dbmyrzblfzEntity=submitInformation.dbmyrzfseEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbmyrzblfz=getPage(dbmyrzblfzEntity);
					WebResponse dbmyrzblfzwr=(WebResponse)dbmyrzblfz.get(1);
					File dbmyrzblfzPageFile=new File(downloadPath+File.separator+"myrzblfzmx"+File.separator+"dbmyrzblfz"+(myrzblfzLink[k].getText()).replace("/", "")+".html");
					String dbmyrzblfzPageString=(String)dbmyrzblfz.get(0);
					dbmyrzblfzPageString=deleteTop(dbmyrzblfzPageString);
					
					WebLink[] myrzblfzhtLink=dbmyrzblfzwr.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<myrzblfzhtLink.length;o++){//循环抓取贷款下合同页面
						
						String myrzblfzhturl=myrzblfzhtLink[o].getAttribute("href");
						List myrzblfzht=null;//单笔合同 从中拿到
						myrzblfzhtEntity=submitInformation.myrzfsehtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,myrzblfzhturl);

						String myrzblfzhtxxPath="myrzblfzht"+(myrzblfzLink[k].getText()).replace("/", "")+File.separator+"myrzblfzht"+(myrzblfzhtLink[o].getText()).replace("/", "")+".html";
						dbmyrzblfzPageString=dbmyrzblfzPageString.replace("<a href='"+myrzblfzhturl.replace("∑", "&sum")+"'>", "<a href='"+myrzblfzhtxxPath+"'>"+myrzblfzhtLink[o].getText());
						dbmyrzblfzPageString=dbmyrzblfzPageString.replace("<font color=\"880000\">", "");
						dbmyrzblfzPageString=dbmyrzblfzPageString.replace(myrzblfzhtLink[o].getText()+"</font>", "");
						
						myrzblfzht=getPage(myrzblfzhtEntity,downloadPath+File.separator+"myrzblfzmx"+File.separator+"myrzblfzht"+(myrzblfzLink[k].getText()).replace("/", "")+File.separator+"myrzblfzht"+(myrzblfzhtLink[o].getText()).replace("/", "")+".html");
						WebResponse myrzblfzhtwr=(WebResponse)myrzblfzht.get(1);
					}
					buildFile(dbmyrzblfzPageFile,dbmyrzblfzPageString);
				}
				buildFile(myrzblfzPageFile,myrzblfzPageString);
				;break;
			case 4://票据贴现（未结清金额）
				String pjtxblfzxx="pjtxblfz.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('4','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+pjtxblfzxx+"'>票据贴现（未结清金额）" );
				pageString=pageString.replace("<font color=\"#880000\">票据贴现（未结清金额）&nbsp;</font>", "");
				
				
				
				String pjtxblfzPath =downloadPath+File.separator+"pjtxblfzmx"+File.separator;
				File pjtxblfzFile = new File(pjtxblfzPath);
				if (!pjtxblfzFile.exists()) {
					pjtxblfzFile.mkdirs();
				}
				
				List pjtxblfz=null;//
				pjtxblfzEntity=submitInformation.pjtxblfzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				pjtxblfz=getPage(pjtxblfzEntity);
				WebResponse pjtxblfzwr=(WebResponse)pjtxblfz.get(1);
				File pjtxblfzPageFile=new File(downloadPath+File.separator+"pjtxblfz.html");
				String pjtxblfzPageString=(String)pjtxblfz.get(0);
				pjtxblfzPageString=deleteTop(pjtxblfzPageString);
				
				WebLink[] pjtxblfzLink=pjtxblfzwr.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<pjtxblfzLink.length;k++){//开始抓取每笔贷款
					String url=pjtxblfzLink[k].getAttribute("href");
					String dbpjtxblfzPath="pjtxblfzmx"+File.separator+"dbpjtxblfz"+(pjtxblfzLink[k].getText()).replace("/", "")+".html";
					pjtxblfzPageString=pjtxblfzPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbpjtxblfzPath+"'>"+pjtxblfzLink[k].getText());
					pjtxblfzPageString=pjtxblfzPageString.replace("<font color=\"#800000\">&nbsp;", "");
					pjtxblfzPageString=pjtxblfzPageString.replace("	"+pjtxblfzLink[k].getText(), "");
					pjtxblfzPageString=pjtxblfzPageString.replace("</font>", "");
					String pjtxblfzhtPath =downloadPath+File.separator+"pjtxblfzmx"+File.separator+"pjtxblfzht"+(pjtxblfzLink[k].getText()).replace("/", "");
					File pjtxblfzhtFile = new File(pjtxblfzhtPath);
					if (!pjtxblfzhtFile.exists()) {
						pjtxblfzhtFile.mkdirs();
					}
					List dbpjtxblfz=null;//单笔贷款 从中拿到合同信息
					dbpjtxblfzEntity=submitInformation.dbpjtxblfzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbpjtxblfz=getPage(dbpjtxblfzEntity);
					WebResponse dbpjtxblfzwr=(WebResponse)dbpjtxblfz.get(1);
					File dbpjtxblfzPageFile=new File(downloadPath+File.separator+"pjtxblfzmx"+File.separator+"dbpjtxblfz"+(pjtxblfzLink[k].getText()).replace("/", "")+".html");
					String dbpjtxblfzPageString=(String)dbpjtxblfz.get(0);
					dbpjtxblfzPageString=deleteTop(dbpjtxblfzPageString);
					
					WebLink[] pjtxblfzhtLink=dbpjtxblfzwr.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<pjtxblfzhtLink.length;o++){//循环抓取贷款下合同页面
						
						String pjtxblfzhturl=pjtxblfzhtLink[o].getAttribute("href");
						List pjtxblfzht=null;//单笔合同 从中拿到
						pjtxblfzhtEntity=submitInformation.pjtxblfzhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,pjtxblfzhturl);
						
						String pjtxblfzhtxxPath="pjtxblfzht"+(pjtxblfzLink[k].getText()).replace("/", "")+File.separator+"pjtxblfzht"+(pjtxblfzhtLink[o].getText()).replace("/", "")+".html";
						dbpjtxblfzPageString=dbpjtxblfzPageString.replace("<a href='"+pjtxblfzhturl.replace("∑", "&sum")+"'>", "<a href='"+pjtxblfzhtxxPath+"'>"+pjtxblfzhtLink[o].getText()+"</a>");
						dbpjtxblfzPageString=dbpjtxblfzPageString.replace("<font color=\"#800000\">&nbsp;"+pjtxblfzhtLink[o].getText(),"");
						
						pjtxblfzht=getPage(pjtxblfzhtEntity,downloadPath+File.separator+"pjtxblfzmx"+File.separator+"pjtxblfzht"+(pjtxblfzLink[k].getText()).replace("/", "")+File.separator+"pjtxblfzht"+(pjtxblfzhtLink[o].getText()).replace("/", "")+".html");
						WebResponse pjtxblfzhtwr=(WebResponse)pjtxblfzht.get(1);
						
					}
					buildFile(dbpjtxblfzPageFile,dbpjtxblfzPageString);
				}
				buildFile(pjtxblfzPageFile,pjtxblfzPageString);
				;break;
			case 5://承兑汇票（未结清金额）
				String cdhpblfzxxPath="cdhpblfz.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript: premiseSubmit('5','"+Parameter.get(1).toString()+"', '"+Parameter.get(2).toString()+"', '"+Parameter.get(3).toString()+"'); \">","<a href='"+cdhpblfzxxPath+"'>承兑汇票（未结清金额）" );
				pageString=pageString.replace("<font color=\"#880000\">承兑汇票（未结清金额）&nbsp;</font>", "");
				
				
				String cdhpblfzPath =downloadPath+File.separator+"cdhpblfzmx"+File.separator;
				File cdhpblfzFile = new File(cdhpblfzPath);
				if (!cdhpblfzFile.exists()) {
					cdhpblfzFile.mkdirs();
				}
				
				List cdhpblfz=null;//
				cdhpblfzEntity=submitInformation.cdhpblfzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(1).toString(),Parameter.get(2).toString(),Parameter.get(3).toString());
				cdhpblfz=getPage(cdhpblfzEntity);
				WebResponse cdhpblfzwr=(WebResponse)cdhpblfz.get(1);
				File cdhpblfzPageFile=new File(downloadPath+File.separator+"cdhpblfz.html");
				String cdhpblfzPageString=(String)cdhpblfz.get(0);
				cdhpblfzPageString=deleteTop(cdhpblfzPageString);
			
				WebLink[] cdhpblfzLink=cdhpblfzwr.getLinks();//拿到贷款详细页面 所有单笔贷款连接
				for(int k=0;k<cdhpblfzLink.length;k++){//开始抓取每笔贷款
					String url=cdhpblfzLink[k].getAttribute("href");
					String dbcdhpblfzPath="cdhpblfzmx"+File.separator+"dbcdhpblfz"+(cdhpblfzLink[k].getText()).replace("/", "")+".html";
					cdhpblfzPageString=cdhpblfzPageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+dbcdhpblfzPath+"'>"+cdhpblfzLink[k].getText());
					cdhpblfzPageString=cdhpblfzPageString.replace("<font color=\"#800000\">"+cdhpblfzLink[k].getText()+"</font>", "");
					String cdhpblfzhtPath =downloadPath+File.separator+"cdhpblfzmx"+File.separator+"cdhpblfzht"+(cdhpblfzLink[k].getText()).replace("/", "");
					File cdhpblfzhtFile = new File(cdhpblfzhtPath);
					if (!cdhpblfzhtFile.exists()) {
						cdhpblfzhtFile.mkdirs();
					}
					
					List dbcdhpblfz=null;//单笔贷款 从中拿到合同信息
					dbcdhpblfzEntity=submitInformation.dbcdhpblfzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					dbcdhpblfz=getPage(dbcdhpblfzEntity);
					WebResponse dbcdhpblfzwr=(WebResponse)dbcdhpblfz.get(1);
					File dbcdhpblfzPageFile=new File(downloadPath+File.separator+"cdhpblfzmx"+File.separator+"dbcdhpblfz"+(cdhpblfzLink[k].getText()).replace("/", "")+".html");
					String dbcdhpblfzPageString=(String)dbcdhpblfz.get(0);
					dbcdhpblfzPageString=deleteTop(dbcdhpblfzPageString);
					
					WebLink[] cdhpblfzhtLink=dbcdhpblfzwr.getLinks();//拿到本次循环得到的贷款的所有合同连接
					for(int o=0;o<cdhpblfzhtLink.length;o++){//循环抓取贷款下合同页面
						
						String cdhpblfzhturl=cdhpblfzhtLink[o].getAttribute("href");
						List cdhpblfzht=null;//单笔合同 从中拿到
						cdhpblfzhtEntity=submitInformation.cdhpblfzhtEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,cdhpblfzhturl);

						String cdhpblfzhtxxPath="cdhpblfzht"+(cdhpblfzLink[k].getText()).replace("/", "")+File.separator+"cdhpblfzht"+(cdhpblfzhtLink[o].getText()).replace("/", "")+".html";
						dbcdhpblfzPageString=dbcdhpblfzPageString.replace("<a href='"+cdhpblfzhturl.replace("∑", "&sum")+"'>", "<a href='"+cdhpblfzhtxxPath+"'>"+cdhpblfzhtLink[o].getText());
						dbcdhpblfzPageString=dbcdhpblfzPageString.replace("<font color=\"880000\">"+cdhpblfzhtLink[o].getText()+"</font>", "");
						cdhpblfzht=getPage(cdhpblfzhtEntity,downloadPath+File.separator+"cdhpblfzmx"+File.separator+"cdhpblfzht"+(cdhpblfzLink[k].getText()).replace("/", "")+File.separator+"cdhpblfzht"+(cdhpblfzhtLink[o].getText()).replace("/", "")+".html");
						WebResponse cdhpblfzhtwr=(WebResponse)cdhpblfzht.get(1);
					}
					buildFile(dbcdhpblfzPageFile,dbcdhpblfzPageString);
				}
				buildFile(cdhpblfzPageFile,cdhpblfzPageString);
				;break;
			case 6://信用证余额	
				break;
			case 7://保函余额
				break;
			case 8://资产保全剥离余额
				break;
			case 9://类贷款余额
				break;
			}	
		}
		buildFile(pageFile,pageString);
		//开始担保页面处理
		//开始对外担保处理
		List dwdbhz=null;//从中拿到有业务的金额
		dwdbhzEntity=submitInformation.dwdbhzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode);
		dwdbhz=getPage(dwdbhzEntity);
		WebResponse dwdbhzwr=(WebResponse)dwdbhz.get(1);
		pageFile=new File(downloadPath+File.separator+"dwdbhz.html");
		pageString=(String)dwdbhz.get(0);
		pageString=deleteTop(pageString);
		WebLink[] dwdbLink=dwdbhzwr.getLinks();//对外担保汇总页面全部链接
		for(int j=0;j<dwdbLink.length;j++){//拿到对外担保汇总页面链接 循环
			List Parameter=new ArrayList();
			String onclickvalue=(dwdbLink[j].getAttribute("onclick")).substring(dwdbLink[j].getAttribute("onclick").indexOf("(")+1, dwdbLink[j].getAttribute("onclick").indexOf(")"));
			Parameter=getParameter(onclickvalue,"'",2);
			switch(Integer.valueOf((String)Parameter.get(1))){
			case 1://保证
				String bzdwdbxxxxPath="bzdwdbxxxx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript:premise('outassure','1'); \">","<a href='"+bzdwdbxxxxPath+"'>保证" );
				pageString=pageString.replace("<font color=\"#880000\">保证 </font>", "");
				String bzdwdbmxPath =downloadPath+File.separator+"bzdwdbmx"+File.separator;
				File bzdwdbmxFile = new File(bzdwdbmxPath);
				if (!bzdwdbmxFile.exists()) {
					bzdwdbmxFile.mkdirs();
				}
				
				List bzdwdbxxxx=null;//
				bzdwdbxxxxEntity=submitInformation.bzdwdbxxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(0).toString(),Parameter.get(1).toString());
				bzdwdbxxxx=getPage(bzdwdbxxxxEntity);
				WebResponse bzdwdbxxxxwr=(WebResponse)bzdwdbxxxx.get(1);
				File bzdwdbxxxxPageFile=new File(downloadPath+File.separator+"bzdwdbxxxx.html");
				String bzdwdbxxxxPageString=(String)bzdwdbxxxx.get(0);
				bzdwdbxxxxPageString=deleteTop(bzdwdbxxxxPageString);
				
				WebLink[] bzdwdbLink=bzdwdbxxxxwr.getLinks();//拿到保证对外担保详细页面 所有单笔保证
				for(int k=0;k<bzdwdbLink.length;k++){//开始抓取每笔担保
					String url=bzdwdbLink[k].getAttribute("href");
					List dbbzdwdbxx=null;//担保贷款 从中拿到合同信息
					dbbzdwdbxxEntity=submitInformation.dbbzdwdbxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					String dbbzdwdbxxPath="dbbzdwdbxx"+(bzdwdbLink[k].getText()).replace("/", "")+".html";
					bzdwdbxxxxPageString=bzdwdbxxxxPageString.replace("<A href='"+url+"'>", "<a href='"+dbbzdwdbxxPath+"'>"+bzdwdbLink[k].getText());
					bzdwdbxxxxPageString=bzdwdbxxxxPageString.replace( "<font color=\"880000\">"+bzdwdbLink[k].getText()+"</font>", "");
					dbbzdwdbxx=getPage(dbbzdwdbxxEntity,downloadPath+File.separator+"bzdwdbmx"+File.separator+"dbbzdwdbxx"+(bzdwdbLink[k].getText()).replace("/", "")+".html");
					WebResponse dbbzdwdbxxwr=(WebResponse)dbbzdwdbxx.get(1);
				}
				buildFile(bzdwdbxxxxPageFile,bzdwdbxxxxPageString);
				;break;
			case 2://抵押
				break;
			
			case 3://质押
			}
		}
		buildFile(pageFile,pageString);
		//开始被担保页面处理
		List bdbhz=null;//从中拿到有业务的金额
		bdbhzEntity=submitInformation.bdbhzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode);
		bdbhz=getPage(bdbhzEntity);
		WebResponse bdbhzwr=(WebResponse)bdbhz.get(1);
		pageFile=new File(downloadPath+File.separator+"bdbhz.html");
		pageString=(String)bdbhz.get(0);
		pageString=deleteTop(pageString);
		
		WebLink[] bdbLink=bdbhzwr.getLinks();//被担保汇总页面全部链接
		for(int j=0;j<bdbLink.length;j++){//拿到被担保汇总页面链接 循环
			List Parameter=new ArrayList();
			String onclickvalue=(bdbLink[j].getAttribute("onclick")).substring(bdbLink[j].getAttribute("onclick").indexOf("(")+1, bdbLink[j].getAttribute("onclick").indexOf(")"));
			Parameter=getParameter(onclickvalue,"'",2);
			switch(Integer.valueOf((String)Parameter.get(1))){
			case 1://保证
				String bzbdbxxxxPath="bzbdbxxxx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript:premise('byassure','1'); \">","<a href='"+bzbdbxxxxPath+"'>保证" );
				pageString=pageString.replace("<font color=\"#880000\">保证 </font>","");
				String bzbdbmxPath =downloadPath+File.separator+"bzbdbmx"+File.separator;
				File bzbdbmxFile = new File(bzbdbmxPath);
				if (!bzbdbmxFile.exists()) {
					bzbdbmxFile.mkdirs();
				}
				List bzbdbxxxx=null;//
				bzbdbxxxxEntity=submitInformation.bzbdbxxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(0).toString(),Parameter.get(1).toString());
				bzbdbxxxx=getPage(bzbdbxxxxEntity);
				WebResponse bzbdbxxxxwr=(WebResponse)bzbdbxxxx.get(1);
				File bzbdbxxxxPageFile=new File(downloadPath+File.separator+"bzbdbxxxx.html");
				String bzbdbxxxxPageString=(String)bzbdbxxxx.get(0);
				bzbdbxxxxPageString=deleteTop(bzbdbxxxxPageString);
				
				WebLink[] bzbdbLink=bzbdbxxxxwr.getLinks();//拿到保证对外担保详细页面 所有单笔保证
				for(int k=0;k<bzbdbLink.length;k++){//开始抓取每笔担保
					String url=bzbdbLink[k].getAttribute("href");
					List dbbzbdbxx=null;//单笔担保 从中拿到合同信息
					dbbzbdbxxEntity=submitInformation.dbbzbdbxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					String dbbzbdbxxPath="bzbdbmx"+File.separator+"dbbzbdbxx"+(bzbdbLink[k].getText()).replace("/", "")+".html";
					bzbdbxxxxPageString=bzbdbxxxxPageString.replace("<A href='"+url+"'>", "<a href='"+dbbzbdbxxPath+"'>"+bzbdbLink[k].getText());
					bzbdbxxxxPageString=bzbdbxxxxPageString.replace("<font color=\"880000\">"+bzbdbLink[k].getText()+"</font>", "");
					dbbzbdbxx=getPage(dbbzbdbxxEntity,downloadPath+File.separator+"bzbdbmx"+File.separator+"dbbzbdbxx"+(bzbdbLink[k].getText()).replace("/", "")+".html");
					WebResponse dbbzbdbxxwr=(WebResponse)dbbzbdbxx.get(1);
					
				}
				buildFile(bzbdbxxxxPageFile,bzbdbxxxxPageString);
				;break;
			case 2://抵押
				String dybdbxxxxPath="dybdbxxxx.html";
				pageString=pageString.replace("<a href=\"#\" class=\"a.link\" onclick=\"javascript:premise('byassure','2'); \">","<a href='"+dybdbxxxxPath+"'>抵押" );
				pageString=pageString.replace("<font color=\"#880000\">抵押 </font>","");
				
				String dybdbmxPath =downloadPath+File.separator+"dybdbmx"+File.separator;
				File dybdbmxFile = new File(dybdbmxPath);
				if (!dybdbmxFile.exists()) {
					dybdbmxFile.mkdirs();
				}
				
				List dybdbxxxx=null;//
				dybdbxxxxEntity=submitInformation.dybdbxxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode,Parameter.get(0).toString(),Parameter.get(1).toString());
				dybdbxxxx=getPage(dybdbxxxxEntity);
				WebResponse dybdbxxxxwr=(WebResponse)dybdbxxxx.get(1);
				File dybdbxxxxPageFile=new File(downloadPath+File.separator+"dybdbxxxx.html");
				String dybdbxxxxPageString=(String)dybdbxxxx.get(0);
				dybdbxxxxPageString=deleteTop(dybdbxxxxPageString);
				
				WebLink[] dybdbLink=dybdbxxxxwr.getLinks();//拿到保证对外担保详细页面 所有单笔保证
				for(int k=0;k<dybdbLink.length;k++){//开始抓取每笔担保
					String url=dybdbLink[k].getAttribute("href");
					
					List dbdybdbxx=null;//单笔担保 从中拿到合同信息
					dbdybdbxxEntity=submitInformation.dbdybdbxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
					
					String dbdybdbxxPath="dybdbmx"+File.separator+"dbdybdbxx"+(dybdbLink[k].getText()).replace("/", "")+".html";
					dybdbxxxxPageString=dybdbxxxxPageString.replace("<A href='"+url+"'>", "<a href='"+dbdybdbxxPath+"'>"+dybdbLink[k].getText()+"</a>");
					dybdbxxxxPageString=dybdbxxxxPageString.replace("<font color=\"880000\">"+dybdbLink[k].getText()+"</font>", "");
					
					dbdybdbxx=getPage(dbdybdbxxEntity,downloadPath+File.separator+"dybdbmx"+File.separator+"dbdybdbxx"+(dybdbLink[k].getText()).replace("/", "")+".html");
					WebResponse dbdybdbxxwr=(WebResponse)dbdybdbxx.get(1);
				}
				buildFile(dybdbxxxxPageFile,dybdbxxxxPageString);
				;break;
			
			case 3://质押
			}
		}
		buildFile(pageFile,pageString);
		//开始处理欠息信息页面
		List qxxxhz=null;//从中拿到有业务的金额
		qxxxhzEntity=submitInformation.qxxxhzEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,borrowernamecn,crccode);
		qxxxhz=getPage(qxxxhzEntity);
		WebResponse qxxxhzwr=(WebResponse)qxxxhz.get(1);
		pageFile=new File(downloadPath+File.separator+"qxxxhz.html");
		pageString=(String)qxxxhz.get(0);
		pageString=deleteTop(pageString);
		
		WebLink[] qxxxLink=qxxxhzwr.getLinks();//欠息信息页面链接
		for(int j=0;j<qxxxLink.length;j++){//拿到欠息信息汇总页面链接 循环
			String qxxxmxPath="";
			if(qxxxLink[j].getText().equals("表内")){
					 qxxxmxPath =downloadPath+File.separator+"bnqxxxmx"+File.separator;
			}else{
				 qxxxmxPath =downloadPath+File.separator+"bwqxxxmx"+File.separator;
			}
			File qxxxmxFile = new File(qxxxmxPath);
			if (!qxxxmxFile.exists()) {
				qxxxmxFile.mkdirs();
			}
			String url=qxxxLink[j].getAttribute("href");
			List bnwqxxxxx=null;//表内欠息信息详细页面
			bnwqxxxxxEntity=submitInformation.bnwqxxxxxEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,url);
			bnwqxxxxx=getPage(bnwqxxxxxEntity);
			WebResponse bnwqxxxxxwr=(WebResponse)bnwqxxxxx.get(1);
			File bnqxxxxxPageFile=null;
			if(qxxxLink[j].getText().equals("表内")){
				String bnqxxxxxPath="bnqxxxxx.html";
				pageString=pageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+bnqxxxxxPath+"'>表内");
				pageString=pageString.replace("<font color=\"#800000\">表内&nbsp;</font>", "");
						
				bnqxxxxxPageFile=new File(downloadPath+File.separator+"bnqxxxxx.html");
			}else{
				String bwqxxxxxPath=downloadPath+File.separator+"bwqxxxxx.html";
				pageString=pageString.replace("<a href='"+url.replace("∑", "&sum")+"'>", "<a href='"+bwqxxxxxPath+"'>表外");
				pageString=pageString.replace("<font color=\"#800000\">表外&nbsp;</font>", "");
				
				bnqxxxxxPageFile=new File(downloadPath+File.separator+"bwqxxxxx.html");
			}
			String bnwqxxxxxPageString=(String)bnwqxxxxx.get(0);
			bnwqxxxxxPageString=deleteTop(bnwqxxxxxPageString);
			
			String bnwqxxxxxStr="";
			WebLink[] qxxxxgrLink=bnwqxxxxxwr.getLinks();//欠息信息修改日
			for(int k=0;k<qxxxxgrLink.length;k++){//开始抓取每笔修改日欠息信息
				String qxxxxgrurl=qxxxxgrLink[k].getAttribute("href");
				List qxxxxgr=null;//欠息信息修改日 中信息
				String qxxxxgrPath=null;
				qxxxxgrEntity=submitInformation.qxxxxgrEntity(dftwo.format(obj.getTimeCreateDate()), strInCode, ip,qxxxxgrurl);
				if(qxxxLink[j].getText().equals("表内")){
					qxxxxgrPath="bnqxxxmx"+File.separator+"qxxxxgr"+(qxxxxgrLink[k].getText()).replace("/", "")+".html";
					bnwqxxxxxPageString=bnwqxxxxxPageString.replace("<a href='"+qxxxxgrurl+"'>", "<a href='"+qxxxxgrPath+"'>"+qxxxxgrLink[k].getText());
					bnwqxxxxxPageString=bnwqxxxxxPageString.replace("<font color=\"#800000\">"+qxxxxgrLink[k].getText()+"&nbsp;</font>", "");
					 
					
					qxxxxgr=getPage(qxxxxgrEntity,downloadPath+File.separator+"bnqxxxmx"+File.separator+"qxxxxgr"+(qxxxxgrLink[k].getText()).replace("/", "")+".html");
				}else{
					qxxxxgrPath="bwqxxxmx"+File.separator+"qxxxxgr"+(qxxxxgrLink[k].getText()).replace("/", "")+".html";
					bnwqxxxxxPageString=bnwqxxxxxPageString.replace("<a href='"+qxxxxgrurl+"'>", "<a href='"+qxxxxgrPath+"'>"+qxxxxgrLink[k].getText());
					bnwqxxxxxPageString=bnwqxxxxxPageString.replace("<font color=\"#800000\">"+qxxxxgrLink[k].getText()+"&nbsp;</font>", "");
					qxxxxgr=getPage(qxxxxgrEntity,downloadPath+File.separator+"bwqxxxmx"+File.separator+"qxxxxgr"+(qxxxxgrLink[k].getText()).replace("/", "")+".html");
				}
				
				WebResponse qxxxxgrwr=(WebResponse)qxxxxgr.get(1);
			}
			buildFile(bnqxxxxxPageFile,bnwqxxxxxPageString);
		}
		buildFile(pageFile,pageString);
		CreditReportLogHelper.updateQyLogEndDateById(logId,sessionFactory);
	}
	
	public String deleteTop(String pageString){
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"当前综合信息\" onClick=\"clickGeneral()\">", "");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"余额信息\" onClick=\"clickDebit()\">", "");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"发生额信息\" onClick=\"clickDebit_His()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"不良负债信息\" onClick=\"clickBadOwes()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"担保信息\"  onClick=\"clickAssure()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"欠息信息\" onClick=\"clickOwe()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"垫款信息\" onClick=\"clickPk()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"公开授信\" onClick=\"clickCreditInfo()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"剥离记录\" onClick=\"clickBCInfo()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"异议标注\" onClick=\"clickDissent()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"公共信息\" onClick=\"clickJieSuan()\">","");
		pageString=pageString.replace("<input name=\"Submit42223\" type=\"button\" class=\"input-button\" value=\"财务报表信息\" onClick=\"clickFinalReptInfo()\">","");
		pageString=pageString.replace("<img src=\"/shwebroot/images/line.gif\" width=\"5\" height=\"15\" align=\"absmiddle\">", "");
		return pageString;
	} 
	/*相同下载逻辑*/
	/****获得页面的WebResponse和String
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws MalformedURLException ****/
	//抓取页面 报错继续抓取 一共10次 直接创建文件 不修改页面内容时使用
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
	//抓取页面 报错继续抓取 一共10次 不创建文件 页面内容有修改需求时使用 修改后创建文件
	public List getPage(Entity postParameter) throws MalformedURLException, IOException, SAXException{
		List page=null;//取回页面
		WebResponse pageWebResponse=null;
		for(int i=0;i<10;i++){//一次抓取会循环抓页面
			page=postthreewr(postParameter);//调取请求程序传入参数 返回页面
			String pageString=(String)page.get(0);
			if(!(pageString.indexOf("failure")>-1)&&!(pageString.indexOf("新闻公告")>-1)){
				break;
			}
		}
		return page;
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
	//创建文件
	public void buildFile(File file,String content) throws IOException{
		file.createNewFile();
		FileOutputStream txtfiledxx=new FileOutputStream(file);
		Writer out = new OutputStreamWriter(txtfiledxx, "gbk");
		out.write(content);
		out.close();
		txtfiledxx.close();
	}
	//计算字符串中 指定字符出现次数
	public static int getCount(String str,String sub)
	{
		int index = 0;
		int count = 0;
		while((index = str.indexOf(sub,index))!=-1)
		{
	
			index = index + sub.length();
			count++;
		}
		return count;
	}
	//抓取页面 返回WebResponse与页面字符输出
	public static List postthreewr(Entity entity) throws MalformedURLException, IOException, SAXException{
		WebRequest request ;
		request = new PostMethodWebRequest(entity.getUrl());
		request.setHeaderField("Cookie", cookie);
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
		rtList.add(0, out);
		rtList.add(1, wr);
		return rtList;
	}
	
	public static String postthree(Entity entity) throws MalformedURLException, IOException, SAXException{
		WebRequest request ;
		request = new PostMethodWebRequest(entity.getUrl());
		request.setHeaderField("Cookie", cookie);
		Iterator<Entry<String,String>> iter = entity.getResult().entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,String> entry=(Map.Entry<String,String>)iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			request.setParameter(key, value);
			
		}
		WebResponse wr = wc.getResponse(request);
		String out= new String(wr.getText().getBytes(wr.getCharacterSet()),"gbk");
		return out;
	}
	
	public static String postloginthree(Entity entity) throws MalformedURLException, IOException, SAXException{
		WebRequest request = new PostMethodWebRequest(entity.getUrl());
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
		String out= new String(wr.getText().getBytes(wr.getCharacterSet()),"gbk");
		return out;
	}
}
