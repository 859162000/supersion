package zxptsystem.helper.download;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.helper.download.Entity.Entity;

public class SubmitInformation {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public Entity LoginEntity(String yhm,String mm,String jgh,String rhip){
		Map<String, String> result = new HashMap<String, String>();
		result.put("orgCode", jgh);
		result.put("userid", yhm);
		result.put("password", mm);
		String url=rhip+"/logon.do";
		Entity entity=new Entity();
		entity.setResult(result);
		System.out.println("输出登录"+url);
		entity.setUrl(url);
		return entity;
	}
	public Entity grLoginEntity(String yhm,String mm,String rhip){
		Map<String, String> result = new HashMap<String, String>();
		result.put("userid", yhm);
		result.put("password", mm);
		String url=rhip+"/logon.do?isDissentLogin=null";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity grbwEntity(GRZXCreditReportInfo obj,String rhip){
		
		EIS_PERCustomernBasicInfo customernBasicInfo=obj.getStrCustomerID();
		Map<String, String> result = new HashMap<String, String>();
		String username="";//姓名
		String certype="";//证件类型
		String cercode="";//证件号码
		String queryreason="";//查询原因
		String vertype="";//信用报告版式
		String idauthflag="";//查询类型
		if(!(customernBasicInfo.getStrCustomerName()==null)){
			try {
				username=URLEncoder.encode(customernBasicInfo.getStrCustomerName(),"gbk");
			} catch (UnsupportedEncodingException e) {
				username=customernBasicInfo.getStrCustomerName();
			}			
		}
		if(!(customernBasicInfo.getStrCertType()==null)){
			certype=customernBasicInfo.getStrCertType();
		}
		if(!(customernBasicInfo.getStrCertNo()==null)){
			cercode=customernBasicInfo.getStrCertNo();
		}
		if(!(obj.getStrQueryCause()==null)){
			queryreason=obj.getStrQueryCause();
		}
		if(!(obj.getStrCreditReportType()==null)){
			vertype=obj.getStrCreditReportType();
		}
		if(!(obj.getStrCreditType()==null)){
			idauthflag=obj.getStrCreditType();
		}
		String url=rhip+"/queryAction.do?username="+username+"&certype="+certype+"&cercode="+cercode+"&queryreason="+queryreason+"&vertype="+vertype+"&idauthflag="+idauthflag+"&policetype=0";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		System.out.println(url);
		return entity;
		
	}
	
	public Entity jkrEntity(QYZXCreditReportInfo obj,String yhm,String mm,String jgh,String rhip){
		EIS_ENTCustomernBasicInfo customernBasicInfo=obj.getStrCustomerID();
		Map<String, String> result = new HashMap<String, String>();
//creditcode机构信用代码
		
		String creditcode="";
		String loancardno="";
		String sdeporgcode="";
		String registertype="";
		String registercode="";
		String sdepnationaltaxcode="";
		String sdeplandtaxcode="";
		String queryreason="05";
		
		if(!(customernBasicInfo.getStrCUSCreditInstitutionsCode()==null)){
			creditcode=customernBasicInfo.getStrCUSCreditInstitutionsCode();
		}
		if(!(customernBasicInfo.getStrInCode()==null)){
			loancardno=customernBasicInfo.getStrInCode();
		}
		if(!(customernBasicInfo.getStrOrganizationCode()==null)){
			sdeporgcode=customernBasicInfo.getStrOrganizationCode();
		}
		if(!(customernBasicInfo.getStrRegistrationType()==null)){			
			registertype=customernBasicInfo.getStrRegistrationType();
		}
		if(!(customernBasicInfo.getStrRegistrationNo()==null)){
			registercode=customernBasicInfo.getStrRegistrationNo();
		}
		if(!(customernBasicInfo.getStrTaxpayerIdentStateNo()==null)){
			sdepnationaltaxcode=customernBasicInfo.getStrTaxpayerIdentStateNo();
		}
		if(!(customernBasicInfo.getStrTaxpayerIdentLandNo()==null)){
			sdeplandtaxcode=customernBasicInfo.getStrTaxpayerIdentLandNo();
		}
		if(!(obj.getStrQueryCause()==null)){
			queryreason=obj.getStrQueryCause();
		}
		
		String url=rhip+"/newConfirmEditQuery.do?type=00101&nocaCode=1&creditcode="
				+creditcode
				+"&loancardno="+loancardno
				+"&sdeporgcode="+sdeporgcode
				+"&registertype="+registertype
				+"&registercode="+registercode
				+"&sdepnationaltaxcode="+sdepnationaltaxcode
				+"&sdeplandtaxcode="+sdeplandtaxcode
				+"&queryreason="+queryreason;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity jkrjbxxEntity(String KHH,String yhm,String mm,String jgh,String rhip,String crccode){
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/detail_qt_new.do?loancard="+KHH+"&crccode="+crccode+"&attribute=1&borrnatucode=1&loancard="+KHH+"&curtype=00101&somePage=";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}


	
	
	public Entity yehzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode) throws UnsupportedEncodingException{
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweBalanceQueryAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loansign=on&suchloan=on&keepfinancingsign=on&financingsign=on&discountsign=on&creditsign=on&acceptordersign=on&keepcasesign=on&peeloffsign=on&loancard="+KHH+"&searchtype=balance&owesendtime1=null&rmb=&dollar=&total=&typecode=&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity fsehzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode) throws UnsupportedEncodingException{
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/OweOccurQueryAction.do?systemDate="+df.format(new Date())+"&owesstarttime=1900-01-01&owesendtime="+endDate+"&loansign=on&suchloan=on&keepfinancingsign=on&financingsign=on&discountsign=on&creditsign=on&acceptordersign=on&keepcasesign=on&peeloffsign=on&loancard="+KHH+"&searchtype=occur&owesstarttime1=1900-01-01&owesendtime1="+df.format(new Date())+"&rmb=&dollar=&total=&typecode=&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity blfzhzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode) throws UnsupportedEncodingException{
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/HadowesAction.do?systemDate="+df.format(new Date())+"&owesendtime="+df.format(new Date())+"&loansign=on&suchloan=on&keepfinancingsign=on&financingsign=on&discountsign=on&creditsign=on&acceptordersign=on&keepcasesign=on&loancard="+KHH+"&searchtype=hadowe&owesendtime1=null&rmb=&dollar=&total=&typecode=&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dwdbhzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode) throws UnsupportedEncodingException{
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/AssureSumAction.do?borrowernamecn="+borrowernamecn+"&loancard="+KHH+"&attribute=1&systemDate="+df.format(new Date())+"&assure=curassure&assuretype=outassure&endtime=&guarant=guarant&pledge=pledge&impawn=impawn";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity bdbhzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode) throws UnsupportedEncodingException{
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/AssureSumAction.do?borrowernamecn="+borrowernamecn+"&loancard="+KHH+"&attribute=null&systemDate="+df.format(new Date())+"&assure=curassure&assuretype=byassure&endtime=&guarant=guarant&pledge=pledge&impawn=impawn";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity qxxxhzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode) throws UnsupportedEncodingException{
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweListAction.do?loancard="+KHH+"&borrowernamecn="+borrowernamecn+"&attribute=1&borrnatucode=1&debitflag=&queryendtime="+df.format(new Date());
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}

	public Entity dqzhxxEntity(String KHH,String rhip,String borrowernamecn,String crccode){//endDate
		Map<String, String> result = new HashMap<String, String>();
		
		String url=rhip+"/detail_qt_new.do?borrowernamecn="+borrowernamecn+"&loancard="+KHH+"&attribute=1&borrnatucode=1&crccode="+crccode+"&financecode=&datevalue=&debitflag=";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	
	
	
	public Entity dkyexxxxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweBalanceDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=balance&owesendtime1="+endDate+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=1&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dkfsexxxxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/OweOccurDetailAction.do?systemDate="+df.format(new Date())+"&owesstarttime=1900-01-01&owesendtime="+df.format(new Date())+"&loancard="+KHH+"&searchtype=occur&owesstarttime1=1900-01-01&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=1&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dkblfzxxxxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/HadowesDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+df.format(new Date())+"&loancard="+KHH+"&searchtype=hadowe&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=1&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity bzdwdbxxxxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String asstype,String type){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/AssureAllActionNew.do?loancard="+KHH+"&type="+type+"&assuretype="+asstype+"&borrowernamecn="+borrowernamecn+"&loancard="+KHH+"&attribute=1&systemDate="+df.format(new Date())+"&assure=curassure&assuretype=outassure&endtime=&guarant=guarant&pledge=pledge&impawn=impawn";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	
	public Entity bzbdbxxxxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String asstype,String type){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/AssureAllActionNew.do?loancard="+KHH+"&type="+type+"&assuretype="+asstype+"&borrowernamecn="+borrowernamecn+"&loancard="+KHH+"&attribute=1&systemDate="+df.format(new Date())+"&assure=curassure&assuretype=byassure&endtime=&guarant=guarant&pledge=pledge&impawn=impawn";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity bnwqxxxxxEntity(String endDate,String KHH,String rhip,String qurl){//endDate
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dybdbxxxxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String asstype,String type){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/AssureAllActionNew.do?loancard="+KHH+"&type="+type+"&assuretype="+asstype+"&borrowernamecn="+borrowernamecn+"&loancard="+KHH+"&attribute=null&systemDate="+df.format(new Date())+"&assure=curassure&assuretype=byassure&endtime=&guarant=guarant&pledge=pledge&impawn=impawn";
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity myrzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();
		
		String url=rhip+"/OweBalanceDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=balance&owesendtime1="+endDate+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=3&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity myrzfseEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweOccurDetailAction.do?systemDate="+df.format(new Date())+"&owesstarttime=1900-01-01&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=occur&owesstarttime1=1900-01-01&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=3&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	
	public Entity myrzblfzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/HadowesDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+df.format(new Date())+"&loancard="+KHH+"&searchtype=hadowe&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=3&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity pjtxEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweBalanceDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=balance&owesendtime1="+endDate+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=4&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity pjtxfseEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/OweOccurDetailAction.do?systemDate="+df.format(new Date())+"&owesstarttime=1900-01-01&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=occur&owesstarttime1=1900-01-01&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=4&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity pjtxblfzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/HadowesDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+df.format(new Date())+"&loancard="+KHH+"&searchtype=hadowe&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=4&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity cdhpEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweBalanceDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=balance&owesendtime1="+endDate+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=5&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity ldkEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweBalanceDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=balance&owesendtime1="+endDate+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=9&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity ldkfseEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/OweBalanceDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=balance&owesendtime1="+endDate+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=9&borrowernamecn="+borrowernamecn;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity cdhpfseEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/OweOccurDetailAction.do?systemDate="+df.format(new Date())+"&owesstarttime=1900-01-01&owesendtime="+endDate+"&loancard="+KHH+"&searchtype=occur&owesstarttime1=1900-01-01&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=5&borrowernamecn="+borrowernamecn;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity cdhpblfzEntity(String endDate,String KHH,String rhip,String borrowernamecn,String crccode,String rmb,String dollar,String total){//endDate
		Map<String, String> result = new HashMap<String, String>();
		String url=rhip+"/HadowesDetailAction.do?systemDate="+df.format(new Date())+"&owesendtime="+df.format(new Date())+"&loancard="+KHH+"&searchtype=hadowe&owesendtime1="+df.format(new Date())+"&rmb="+rmb+"&dollar="+dollar+"&total="+total+"&typecode=5&borrowernamecn="+borrowernamecn;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbdkxxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbdkblfzxxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbbzdwdbxxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbbzbdbxxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity qxxxxgrEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbdybdbxxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbdkfsexxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dkhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dkblfzhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dkfsehtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity myrzhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity myrzfsehtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity myrzblfzhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity pjtxhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity pjtxfsehtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity pjtxblfzhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity cdhphtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity ldkhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity ldkfsehtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity cdhpfsehtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity cdhpblfzhtEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbmyrzEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbmyrzfseEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbmyrzblfzEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url="http://"+rhip+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbpjtxEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/"+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbpjtxfseEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/"+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbpjtxblfzEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();

		String url=rhip+"/"+qurl;
		
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbcdhpEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbldkEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbldkfseEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbcdhpfseEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	public Entity dbcdhpblfzEntity(String endDate,String KHH,String rhip,String qurl){
		Map<String, String> result = new HashMap<String, String>();
		String url="http://"+rhip+qurl;
		Entity entity=new Entity();
		entity.setResult(result);
		entity.setUrl(url);
		return entity;
	}
	
}
