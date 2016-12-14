package ncr.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.spi.ServiceRegistry;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ncr.helper.InterfaceDownBean;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;


import report.dto.DownloadResource;
import report.dto.ReportInstSubInfo;
import report.helper.GetFilePath;
import framework.helper.SmallTools;
import report.service.imps.DownloadResourceService;
import sun.print.resources.serviceui;

import extend.dto.Suit;
import framework.security.SecurityContext;
import framework.services.imps.BaseDTService;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.imps.FileHandler;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowInstance;
import framework.show.ShowList;


@SuppressWarnings("unused")
public class InterfaceDownNewCustomService extends BaseDTService{
	
	private String dataBaseType;
	private String suitCode;
	public void setSuitCode(String suitCode) {
		this.suitCode = suitCode;
	}

	public String getSuitCode() {
		return suitCode;
	}

	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
    	String time = RequestManager.getReportCheckParam().get("Year")+"-"+RequestManager.getReportCheckParam().get("Month");
		String reportInstinfo = RequestManager.getReportCheckParam().get("reportInstinfo");
		String[] downList = InterfaceDown();

		List<String> nbjgh=findInstInfo(reportInstinfo, "strInstCode", "1");
		
		String date = RequestManager.getReportCheckParam().get("startDate");
		SimpleDateFormat simpleName = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "NcrtReport_"+simpleName.format(new Date())+".zip";
		
		NcrThread ncrThread = new NcrThread();
		ncrThread.setDownList(downList);
		ncrThread.setNbjgh(nbjgh);
		ncrThread.setTime(time);
		ncrThread.setDate(date);
		ncrThread.setSuitCode(suitCode);
		ncrThread.setActionContext(ActionContext.getContext());
		ncrThread.setFileName(fileName);
		Thread thr = new Thread(ncrThread);
		thr.start();
	}
	
	class NcrThread implements Runnable{
		String[] downList;
		List<String> nbjgh;
		String time;
		String date;
		String suitCode;
		ActionContext actionContext;
		String fileName;

		public void run() {
			try {
				ServletActionContext.setContext(actionContext);

				new DownloadResourceService().addStart(fileName, suitCode); // 新增离线下载记录
				
				IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
				dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
			
		        ApplicationContext DownFile = new ClassPathXmlApplicationContext(new String[]{"ncr/configs/bcs.interface.down.xml"});	//获得bean容器
				InterfaceDownBean DownNewCustom = (InterfaceDownBean)DownFile.getBean("NewInterfaceDown");
											//取下载文件列表
				
				/**	循环下载列表，生成zip文件	 **/
		        List<File> fileList = new ArrayList<File>();					//用于文件打包、下载
		        for (int i = 0; i < downList.length; i++) {
		        	XMLWriter writer = null;									// 声明写XML的对象      
		        	OutputFormat format = OutputFormat.createPrettyPrint();
		         	format.setEncoding("UTF-8");  								// 设置XML文件的编码格式 UTF-8
		         	
		         	Map<String, String> Tablemap =byXmlGetName(downList,i,DownNewCustom);	//从配置文件 取得小表名字的map集合 
		         	Map<String, Object> map = xmlTableName(Tablemap, time, nbjgh); //传入小表名字的map集合 ，返小表数据集合
		         	if(map == null){
		         		System.out.println("新客户风险下载失败!异常信息为：配置文件是否错误;在选择时间内，是否存在数据;选择的报送机构是否存在内部机构");
		         		return;
		         	}
		         	String tablename = splitMe(Tablemap.get("1"),0,"[*]");
		         	fileList.add(createXml(map,tablename,DownNewCustom,date)); // 传入小表数据集合，生成xml文件，返回所有文件路径、文件名
		        }

				GetFilePath getFilePath = new GetFilePath();

				SmallTools.compressedFiles(fileList, getFilePath.getDownloadResourcePath(), fileName); // 将文件打包
				SmallTools.delFileList(fileList);	//清空所有临时子文件

				new DownloadResourceService().addEnd(fileName, suitCode); // 修改结束日期
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public List<String> getNbjgh() {
			return nbjgh;
		}


		public void setNbjgh(List<String> nbjgh) {
			this.nbjgh = nbjgh;
		}
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getSuitCode() {
			return suitCode;
		}

		public void setSuitCode(String suitCode) {
			this.suitCode = suitCode;
		}
		
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String[] getDownList() {
			return downList;
		}
		public void setDownList(String[] downList) {
			this.downList = downList;
		}
		public ActionContext getActionContext() {
			return actionContext;
		}
		public void setActionContext(ActionContext actionContext) {
			this.actionContext = actionContext;
		}
	}
	
	/**
	 * 取下载文件列表 
	 */
	public String[] InterfaceDown(){
		String strCheckbox = RequestManager.getReportCheckParam().get("strCheckbox");	//选中下载文件
    	String[] DownList;												//下载列表
    	
    	/**		判断是否存在*分隔符	*/
        Pattern p=Pattern.compile(",");
		Matcher m=p.matcher(strCheckbox);
        if(m.find()){
        	DownList = strCheckbox.split(",");									//表值切割，形成下载列表数组
        }else{
        	DownList=new String[1];
        	DownList[0] = strCheckbox;
        }
        return DownList;
	}
	
	/**
	 * 循环大表地址，返小表名字的map集合 
	 */
	public Map<String, String> byXmlGetName(String[] DownList,int i,InterfaceDownBean DownNewCustom){
		Map<String,String> TableName = null;						//此变量为bean中取得所有表的变量
		Map<String,String> BigTableName = null;
		if(DownList[i].trim().equals("tableOne")){
			BigTableName = DownNewCustom.getDGJTYKH();			//解析xml配置文件取表名
		}
		else if(DownList[i].trim().equals("tableTwo")){
			BigTableName = DownNewCustom.getJTKH();
		}
		else if(DownList[i].trim().equals("tableThree")){
			BigTableName = DownNewCustom.getDYFRKH();
		}
		else if(DownList[i].trim().equals("tableFour")){
			BigTableName = DownNewCustom.getDGKHDB();
		}
		else if(DownList[i].trim().equals("tableFive")){
			BigTableName = DownNewCustom.getGRDKWY();
		}
		else if(DownList[i].trim().equals("tableSix")){
			BigTableName = DownNewCustom.getGRWYDKDB();
		}
		return BigTableName;
	}
	
	/**
	 * 循环小表名字map集合，返小表数据集合
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> xmlTableName(Map<String,String> BigTableName, String time, List<String> nbjgh) throws Exception{

		String strNbjgh = "";
		if(nbjgh!= null && nbjgh.size()>0){
			strNbjgh =  " and INSTINFO in('";
	    	for (int i = 0; i < nbjgh.size(); i++) {
	    		if(i<nbjgh.size()-1){
	    			strNbjgh += nbjgh.get(i)+"','";
	    		}else{
	    			strNbjgh += nbjgh.get(i)+"')";
	    		}
			}
		}
		
		ArrayList list = new ArrayList();
		Map Tmap = new HashMap();
		int TableSize = BigTableName.size();
		int TempSize=1;
		
		
		while(TempSize<=TableSize){

				String selectState="";
				String TableName = splitMe(BigTableName.get(String.valueOf(TempSize)), 0, "[*]");
				
				/**	根据数据结果拼接字符串*/
				Object FieldsObject = queryReportModel_Fields(TableName);
				if(FieldsObject == null){
					this.setServiceResult(new MessageResult(false,"reportModel_table表中，不存在"+TableName+"表的记录信息"));
					System.out.println("reportModel_table表中，不存在"+TableName+"表的记录信息");
					return null;
				}
				else if(((ArrayList)FieldsObject).size() == 0){
					this.setServiceResult(new MessageResult(false,"reportModel_Field表中，不存在"+TableName+"表相应的字段"));
					System.out.println("reportModel_Field表中，不存在"+TableName+"表相应的字段");
					return null;
				}
				String TName = "ncr.dto.AutoDTO_"+TableName;
				ShowInstance showInstance = ReflectOperation.getShowInstance(TName, "1");
//				Class<?> type = Class.forName(TName);
				
				Map<String, String> autoFieldMap = ShowContext.getInstance().getShowEntityMap().get("autoField");	//取配置文件，固定字段
				for(Object lineObj : (ArrayList<Object>) FieldsObject) {	// 每行记录
					
					Map<String, Object> map = (Map<String, Object>)lineObj;
					if(null==map.get("STRFIELDNAME")){
						map.put("STRFIELDNAME",map.get("StrFieldName"));
					}
					String StrAutoTablename = map.get("STRFIELDNAME").toString();

					if(StrAutoTablename.equals(TableName)) {
							continue;
					}

					boolean isFixedField = false;
					for(Map.Entry<String, String> entry: autoFieldMap.entrySet()) {
						String strField = entry.getValue();
						if(strField.indexOf(',') > -1) {
							if(strField.substring(0, strField.indexOf(',')).toUpperCase().equals(StrAutoTablename.toUpperCase())){
								isFixedField = true;
								break; //skip
							}
						}
					}
					
					if(!isFixedField && !"autoID".equals(map.get("STRFIELDNAME").toString().toUpperCase())){

						for(ShowField showField : showInstance.getShowFieldList()){ // 生成各字段值
//							Field field = ReflectOperation.getReflectField(type,showField.getFieldName());
							if(showField.getFieldName().equals(StrAutoTablename) && showField.getReportUnitCode()!=0){

								if(showField.getReportUnitCode()==1){ //元
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/1, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/1)";
									}
								}
								else if(showField.getReportUnitCode()==2){ //十元
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/10, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/10)";
									}
								}
								else if(showField.getReportUnitCode()==3){ //百元
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/100, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/100)";
									}
								}
								else if(showField.getReportUnitCode()==4){ // 千元
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/1000, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/1000)";
									}
								}
								else if(showField.getReportUnitCode()==5){ //万元
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/10000, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/10000)";
									}
								}
								else if(showField.getReportUnitCode()==6){ //十万
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/100000, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/100000)";
									}
								}
								else if(showField.getReportUnitCode()==7){ //百万
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/1000000, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/1000000)";
									}
								}
								else if(showField.getReportUnitCode()==8){ //千万
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/10000000, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/10000000)";
									}
								}
								else if(showField.getReportUnitCode()==9){ //亿
									if(dataBaseType.equals("oracle")){
										StrAutoTablename = "to_char("+StrAutoTablename+"/100000000, 'FM9999999999990.00')";
									}else{
										StrAutoTablename = "convert(decimal(18,2),"+StrAutoTablename+"/100000000)";
									}
								}
							}
						}
						selectState += ","+StrAutoTablename;
					}
				}
				/**		表查询语句  */
				String strPrefix = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix");
				
				if(strPrefix == null){
					strPrefix = "";
				}

				
				String DTDATE = "DTDATE";
				if(dataBaseType == "oracle"){
					DTDATE = "to_char(DTDATE,'yyyy-MM')";
				}else{
					DTDATE = "convert(varchar,DTDATE,'yyyy-MM')";
				}
				selectState="select "+selectState.substring(1, selectState.length())+" from " + strPrefix + TableName+" where "+DTDATE+"='"+time+"'"+strNbjgh;
				
				String beanID = "createSqlQueryNcrDao";												//指定需调用DAO		.append("prefix")	
				IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);	// 调用DAO,执行SQL,取得数据
				
				Object retObj = null;
				try {
					retObj = dao.paramObjectResultExecute(new Object[]{selectState, null});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Tmap.put(String.valueOf(TempSize),retObj);
			TempSize++;
		}
		return Tmap;
	}
	
	/**
	 * 根据表名，从字段存储表中查询所有表字段
	 */
	@SuppressWarnings("unchecked")
	public Object queryReportModel_Fields(String autoTableId){
   	 
		StringBuffer sql1 = new StringBuffer();
        sql1.append("select autotableid from reportmodel_table q");
        if (autoTableId != null && !"".equals(autoTableId.trim())) {
        	sql1.append(" where q.strtablename='").append(autoTableId).append("' ");
        }
        
		String beanID1 = "createSqlQueryListMapDao";												//指定需调用DAO		.append("prefix")	
		IParamObjectResultExecute dao1 = (IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID1);	// 调用DAO,执行SQL,取得数据
		
		Object retObj1 = null;
		try {
			retObj1 = dao1.paramObjectResultExecute(new Object[]{sql1.toString(), null});
		} catch (Exception e) {
			e.printStackTrace();
		}	
		String StrAutoTableId="";
		if(((ArrayList<Object>) retObj1).size() == 0){return null;}
		for(Object lineObj : (ArrayList<Object>) retObj1) {			// 每行记录
			Map<String, Object> map = (Map<String, Object>)lineObj;
			if(null==map.get("AUTOTABLEID")){
				map.put("AUTOTABLEID", map.get("AUTOTABLEID".toLowerCase()));
			}
			StrAutoTableId = map.get("AUTOTABLEID").toString();
		}
	
		StringBuffer sql = new StringBuffer();
        sql.append("select StrFieldName from reportmodel_field q");
        if (autoTableId != null && !"".equals(autoTableId.trim())) {
        	sql.append(" where q.autoTableId='").append(StrAutoTableId).append("' ");
        }
        sql.append(" order by q.nseq");
        
		String beanID = "createSqlQueryListMapDao";												//指定需调用DAO		.append("AUTODTO_")	
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);	// 调用DAO,执行SQL,取得数据
		
		Object retObj = null;
		try {
			retObj = dao.paramObjectResultExecute(new Object[]{sql.toString(), null});			// 调用DAO查询到retObj对象(数组)	
		} catch (Exception e) {
			e.printStackTrace();
		}												
            return retObj;
    }
	
	/**
	 * 创建xml文件
	 */
	@SuppressWarnings({ "unchecked" })
	public File createXml(Map m,String xmlName,InterfaceDownBean DownNewCustom, String Date){

		List<Map> list1 = new ArrayList();
		List<Map> list12= new ArrayList();
		List<Map> list13= new ArrayList();
		List<Map> list14= new ArrayList();
		List<Map> list15= new ArrayList();
		List<Map> list16= new ArrayList();
		List<Map> list17= new ArrayList();

		Map<String,String> NewAssociativeField = DownNewCustom.getNewAssociativeField();
		String AssociativeField1171 = tableRelation(NewAssociativeField, "172", 0); // 是第一张小表的字段，用于和第七张小表的关联字段
		String AssociativeField110 = tableRelation(NewAssociativeField, "110", 0);
		String AssociativeField111 = tableRelation(NewAssociativeField, "111", 0);
		String AssociativeField121 = tableRelation(NewAssociativeField, "121", 1);
		String AssociativeField122 = tableRelation(NewAssociativeField, "122", 1);
		String AssociativeField131 = tableRelation(NewAssociativeField, "131", 1);
		String AssociativeField132 = tableRelation(NewAssociativeField, "132", 1);
		String AssociativeField141 = tableRelation(NewAssociativeField, "141", 1);
		String AssociativeField142 = tableRelation(NewAssociativeField, "142", 1);
		String AssociativeField151 = tableRelation(NewAssociativeField, "151", 1);
		String AssociativeField152 = tableRelation(NewAssociativeField, "152", 1);
		String AssociativeField161 = tableRelation(NewAssociativeField, "161", 1);
		String AssociativeField162 = tableRelation(NewAssociativeField, "162", 1);
		String AssociativeField171 = tableRelation(NewAssociativeField, "171", 1);
		String AssociativeField172 = tableRelation(NewAssociativeField, "172", 1);
		
		String AssociativeField21 = NewAssociativeField.get("21").split("[*]")[0];
		String AssociativeField22 = NewAssociativeField.get("22").split("[*]")[1];
		String AssociativeField23 = NewAssociativeField.get("23").split("[*]")[1];
		String AssociativeField24 = NewAssociativeField.get("24").split("[*]")[1];
		String AssociativeField25 = NewAssociativeField.get("25").split("[*]")[1];
		
		String AssociativeField31 = NewAssociativeField.get("31").split("[*]")[0];
		String AssociativeField32 = NewAssociativeField.get("32").split("[*]")[1];
		String AssociativeField33 = NewAssociativeField.get("33").split("[*]")[1];

		String AssociativeField41 = NewAssociativeField.get("41").split("[*]")[0];
		String AssociativeField51 = NewAssociativeField.get("51").split("[*]")[0];
		String AssociativeField61 = NewAssociativeField.get("61").split("[*]")[0];
		
		String executivesDetail_G = DownNewCustom.getJTKH().get("2").split("[*]")[0];
		int executivesDetail_G2 = Integer.parseInt(DownNewCustom.getJTKH().get("2").split("[*]")[1]);
		int executivesDetail_G3 = Integer.parseInt(DownNewCustom.getJTKH().get("2").split("[*]")[2]);
		String executivesDetail_F = DownNewCustom.getDYFRKH().get("2").split("[*]")[0];
		int executivesDetail_F2 = Integer.parseInt(DownNewCustom.getDYFRKH().get("2").split("[*]")[1]);
		int executivesDetail_F3 = Integer.parseInt(DownNewCustom.getDYFRKH().get("2").split("[*]")[2]);
		String personalLoan = DownNewCustom.getGRDKWY().get("1").split("[*]")[0];
		int personalLoan2 = Integer.parseInt(DownNewCustom.getGRDKWY().get("1").split("[*]")[1]);
		
		
		if(xmlName.equals(DownNewCustom.getDGJTYKH().get("1"))){
			list1=(List<Map>) m.get("1");
			list12=(List<Map>) m.get("2");
			list13=(List<Map>) m.get("3");
			list14=(List<Map>) m.get("4");
			list15=(List<Map>) m.get("5");
			list16=(List<Map>) m.get("6");
			list17=(List<Map>) m.get("7");
		}
		else if(xmlName.equals(DownNewCustom.getJTKH().get("1"))){
			list1=(List<Map>) m.get("1");
			list12=(List<Map>) m.get("2");
			list13=(List<Map>) m.get("3");
			list14=(List<Map>) m.get("4");
			list15=(List<Map>) m.get("5");
		}
		else if(xmlName.equals(DownNewCustom.getDYFRKH().get("1"))){
			list1=(List<Map>) m.get("1");
			list12=(List<Map>) m.get("2");
			list13=(List<Map>) m.get("3");
		}
		else if(xmlName.equals(DownNewCustom.getDGKHDB().get("1"))){
			list1=(List<Map>) m.get("1");
		}
		else if(xmlName.equals(personalLoan)){
						list1=(List<Map>) m.get("1");
		}
		else if(xmlName.equals(DownNewCustom.getGRWYDKDB().get("1"))){
			list1=(List<Map>) m.get("1");
		}
		XMLWriter writer = null;					// 声明写XML的对象      
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");  				// 设置XML文件的编码格式 UTF-8
        GetFilePath getFilePath = new GetFilePath();
        
	    String CreatePath =getFilePath.getTmpFilePath()+"/"+xmlName+".xml";
        File file = new File(CreatePath);			//获得文件   
        if (file.exists()){
           file.delete();     
        }
        File formworkPath = new File(getFilePath.getModelFilePath("ncrFilePath")+"/"+xmlName+".xml");
        Document document = DocumentHelper.createDocument();  				// 创建xml文件    
		SAXReader sax=new SAXReader();
   	    Document doc = null;
		try {
			doc = (Document) sax.read(formworkPath);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		Element rootElement=doc.getRootElement();							//得到根元素(一级目录)
		Element root = document.addElement(rootElement.getName()+"");		//添加根节点   
		 
		List attr = rootElement.attributes();								//得到根节点属性
		for (int j = 0; j < attr.size(); j++) {
			org.dom4j.Attribute item = (org.dom4j.Attribute) attr.get(j);  //添加根节点属性
			if("reportdate".equals(item.getName())){
				root.addAttribute(item.getName(), Date);
			}else{
				root.addAttribute(item.getName(), item.getValue());
			}
		}
		Element ele=(Element) rootElement.elements().get(0);
		String ele2 = ele.getName();
		
		if (list1!=null && list1.size()>0) {
			for (int index = 0; index < list1.size(); index++) {
				Map map = list1.get(index);
				List uslist1=rootElement.elements(); 								//得到根元素下的子元素(二级元素)
				Iterator it1=uslist1.iterator();

				List<Map> list2= new ArrayList();
				List<Map> list3= new ArrayList();
				List<Map> list4= new ArrayList();
				List<Map> list5= new ArrayList();
				List<Map> list6= new ArrayList();
				List<Map> list7= new ArrayList();
				while(it1.hasNext())	     
				{
					Element tempele1=(Element)it1.next();							//取得子元素(二级)
					Element element1 = root.addElement(tempele1.getName()+""); 		//添加次节点(二级)
					List attr1 = tempele1.attributes();								//得到根节点属性
					for (int j = 0; j < attr1.size(); j++) {
						org.dom4j.Attribute item1 = (org.dom4j.Attribute) attr1.get(j); //添加根节点属性
						element1.addAttribute(item1.getName(), item1.getValue());
					}
					
					/**
					 * 下列判断取表间关联数据
					 */
					if(xmlName.equals(DownNewCustom.getDGJTYKH().get("1"))){
	   					String creditCode;
	   					String customCode;
	   					String customName;
	   					if(map.get(AssociativeField110)!=null && !"".equals(map.get(AssociativeField110))){
	   						creditCode = map.get(AssociativeField110).toString();
	   					}else{
	   						creditCode = "";
	   					}
	   					if(map.get(AssociativeField111)!=null && !"".equals(map.get(AssociativeField111))){
	   						customCode = map.get(AssociativeField111).toString();
	   					}else{
	   						customCode = "";
	   					}
	   					if(map.get(AssociativeField1171)!=null && !"".equals(map.get(AssociativeField1171))){
	   						customName = map.get(AssociativeField1171).toString();
	   					}else{
	   						customName = "";
	   					}
	   					
	   					if(list12!=null){
	   						for(Map map2:list12){
	   							if(map2.get(AssociativeField121)!=null && map2.get(AssociativeField121).equals(creditCode)){
	   								if(!AssociativeField132.equals("")){
			   							if(map2.get(AssociativeField122)!=null && map2.get(AssociativeField122).equals(customCode)){
			   								list2.add(map2);
			   							}
		   							}else{
		   								list2.add(map2);
		   							}
	   							}
	   						}
   						}
	   					if(list13!=null){
	   						for(Map map2:list13){
	   							if(map2.get(AssociativeField131)!=null && map2.get(AssociativeField131).equals(creditCode)){
		   							if(!AssociativeField132.equals("")){
			   							if(map2.get(AssociativeField132)!=null && map2.get(AssociativeField132).equals(customCode)){
			   								list3.add(map2);
			   							}
		   							}else{
		   								list3.add(map2);
		   							}
	   							}
	   						}
   						}
	   					if(list14!=null){
	   						for(Map map2:list14){
	   							if(map2.get(AssociativeField141)!=null && map2.get(AssociativeField141).equals(creditCode)){
	   								if(!AssociativeField132.equals("")){
			   							if(map2.get(AssociativeField142)!=null && map2.get(AssociativeField142).equals(customCode)){
			   								list4.add(map2);
			   							}
		   							}else{
		   								list4.add(map2);
		   							}
	   							}
	   						}
   						}
	   					if(list15!=null){
	   						for(Map map2:list15){
	   							if(map2.get(AssociativeField151)!=null && map2.get(AssociativeField151).equals(creditCode)){
	   								if(!AssociativeField132.equals("")){
			   							if(map2.get(AssociativeField152)!=null && map2.get(AssociativeField152).equals(customCode)){
			   								list5.add(map2);
			   							}
		   							}else{
		   								list5.add(map2);
		   							}
	   							}
	   						}
   						}
	   					if(list16!=null){
	   						for(Map map2:list16){
	   							if(map2.get(AssociativeField161)!=null && map2.get(AssociativeField161).equals(creditCode)){
	   								if(!AssociativeField132.equals("")){
			   							if(map2.get(AssociativeField162)!=null && map2.get(AssociativeField162).equals(customCode)){
			   								list6.add(map2);
			   							}
		   							}else{
		   								list6.add(map2);
		   							}
	   							}
	   						}
   						}
	   					if(list17!=null){
	   						for(Map map2:list17){
	   							if(map2.get(AssociativeField171)!=null && map2.get(AssociativeField171).equals(customCode)){
	   								//判断是否配置第二个条件
	   								if(!AssociativeField132.equals("")){
		   								//需要满足第二个条件
			   							if(map2.get(AssociativeField172)!=null && map2.get(AssociativeField172).equals(customName)){
			   								list7.add(map2);
			   							}
		   							}else{
		   								list7.add(map2);
		   							}
	   							}
	   						}
   						}
	   				}
	   				else if(xmlName.equals(DownNewCustom.getJTKH().get("1"))){

	   					String customCode;
	   					if(map.get(AssociativeField21)!=null&&!"".equals(map.get(AssociativeField21))){
	   						customCode = map.get(AssociativeField21).toString();
	   					}else{
	   						customCode = "";
	   					}
	   					
	   					if(list12!=null){
	   						for(Map map2:list12){
	   							if(map2.get(AssociativeField22)!=null && map2.get(AssociativeField22).equals(customCode)){
	   								list2.add(map2);
	   							}
	   						}
   						}
	   					if(list13!=null){
	   						for(Map map2:list13){
	   							if(map2.get(AssociativeField23)!=null && map2.get(AssociativeField23).equals(customCode)){
	   								list3.add(map2);
	   							}
	   						}
   						}
	   					if(list14!=null){
	   						for(Map map2:list14){
	   							if(map2.get(AssociativeField24)!=null && map2.get(AssociativeField24).equals(customCode)){
	   								list4.add(map2);
	   							}
	   						}
   						}
	   					if(list15!=null){
	   						for(Map map2:list15){
	   							if(map2.get(AssociativeField25)!=null && map2.get(AssociativeField25).equals(customCode)){
	   								list5.add(map2);
	   							}
	   						}
   						}
	   				}
	   				else if(xmlName.equals(DownNewCustom.getDYFRKH().get("1"))){
	   					String customCode;
	   					if(map.get(AssociativeField31)!=null&&!"".equals(map.get(AssociativeField31))){
	   						customCode = map.get(AssociativeField31).toString();
	   					}else{
	   						customCode = "";
	   					}
	   					
	   					if(list12!=null){
	   						for(Map map2:list12){
	   							if(map2.get(AssociativeField32)!=null && map2.get(AssociativeField32).equals(customCode)){
	   								list2.add(map2);
	   							}
	   						}
   						}
	   					if(list13!=null){
	   						for(Map map2:list13){
	   							if(map2.get(AssociativeField33)!=null && map2.get(AssociativeField33).equals(customCode)){
	   								list3.add(map2);
	   							}
	   						}
   						}
	   				}
					
					int list1FieldNum = 1;		
			   		int recordNum=2;
					//得到根元素下的子元素(三级)
					List uslist2=tempele1.elements(); 
					Iterator it2=uslist2.iterator();
					while(it2.hasNext())	     
					{
				   		Element tempele2=(Element)it2.next();								//取得子元素(三级)
		   				Element element2 = element1.addElement(tempele2.getName()+""); 		//添加次节点(三级)
		   				List attr2 = tempele2.attributes();									//得到根节点属性
		   				for (int j = 0; j < attr2.size(); j++) {
		   					org.dom4j.Attribute item2 = (org.dom4j.Attribute) attr2.get(j); //添加根节点属性
		   					element2.addAttribute(item2.getName(), item2.getValue());
						}
		   				
		   				List uslist3=tempele2.elements();
		   				if(uslist3.size()>0){
		   					
		   					List<Map> list = new ArrayList();
		   					if(recordNum==2){
		   						list= list2;
		   					}
		   					else if(recordNum==3){
		   						list= list3;
		   					}
		   					else if(recordNum==4){
		   						list= list4;
		   					}
		   					else if(recordNum==5){
		   						list= list5;
		   					}
		   					else if(recordNum==6){
		   						list= list6;
		   					}
		   					else if(recordNum==7){
		   						list= list7;
		   					}
		   					if (list!=null && list.size()>0) {
			                    for(Map map4 : list){
			   						int list2FieldNum=1;
				   					Iterator it3=uslist3.iterator();
				   					while(it3.hasNext()){
				   						Element tempele3=(Element)it3.next();								//取得子元素(四级)
						   				Element element3 = element2.addElement(tempele3.getName()+""); 		//添加次节点(四级)
						   				List attr3 = tempele3.attributes();									//得到根节点属性
						   				for (int j = 0; j < attr3.size(); j++) {
						   					org.dom4j.Attribute item3 = (org.dom4j.Attribute) attr3.get(j); //添加根节点属性
						   					element3.addAttribute(item3.getName(), item3.getValue());
										}
						   				int Asso= 0;
						   				List uslist4=tempele3.elements();
						   				Iterator it4=uslist4.iterator();
						   				while(it4.hasNext()){
							   				Element tempele4=(Element)it4.next();								//取得子元素(五级)
							   				Element element4 = element3.addElement(tempele4.getName()+""); 		//添加次节点(五级)
							   				List attr4 = tempele4.attributes();									//得到根节点属性
							   				for (int j = 0; j < attr4.size(); j++) {
							   					org.dom4j.Attribute item4 = (org.dom4j.Attribute) attr4.get(j); //添加根节点属性
							   					element4.addAttribute(item4.getName(), item4.getValue());
											}
							   				List uslist5=tempele4.elements();
						   					//判断他在单条语句中是否含有循环
							   				if(uslist5.size()>0){
							   					int i=0;
							   					int num=0;
							   					if(xmlName.equals(DownNewCustom.getJTKH().get("1"))){
							   						if(Asso==0){
							   							num = executivesDetail_G2;
							   						}else{
							   							num = executivesDetail_G3;
							   						}
							   					}else{
							   						if(Asso==0){
							   							num = executivesDetail_F2;
							   						}else{
							   							num = executivesDetail_F3;
							   						}
							   					}
							   					while(i<num){
							   						Iterator it5=uslist5.iterator();
								   					while(it5.hasNext()){
								   						Element tempele5=(Element)it5.next();								//取得子元素(四级)
										   				Element element5 = element4.addElement(tempele5.getName()+""); 		//添加次节点(四级)
										   				List attr5 = tempele5.attributes();									//得到根节点属性
										   				for (int j = 0; j < attr5.size(); j++) {
										   					org.dom4j.Attribute item5 = (org.dom4j.Attribute) attr5.get(j); //添加根节点属性
										   					element3.addAttribute(item5.getName(), item5.getValue());
														}
										   				List uslist6=tempele5.elements();
										   				Iterator it6=uslist6.iterator();
										   				while(it6.hasNext()){
											   				Element tempele6=(Element)it6.next();								//取得子元素(五级)
											   				Element element6 = element5.addElement(tempele6.getName()+""); 		//添加次节点(五级)
											   				List attr6 = tempele6.attributes();									//得到根节点属性
											   				for (int j = 0; j < attr6.size(); j++) {
											   					org.dom4j.Attribute item6 = (org.dom4j.Attribute) attr6.get(j); //添加根节点属性
											   					element6.addAttribute(item6.getName(), item6.getValue());
															}
										   					if(map4.get(String.valueOf(list2FieldNum))==null||"".equals(map4.get(String.valueOf(list2FieldNum)))){
										   						element6.setText("");
										   					}else{
												   				element6.setText(map4.get(String.valueOf(list2FieldNum))+"");
										   					}
											   				list2FieldNum++;	
										   				}
								   					}
								   					i++;
							   					}
							   					Asso++;
							   				}else{
							   					if(map4.get(String.valueOf(list2FieldNum))==null||"".equals(map4.get(String.valueOf(list2FieldNum)))){
							   						element4.setText("");
							   					}else{
							   						element4.setText(map4.get(String.valueOf(list2FieldNum))+"");
							   					}
								   				list2FieldNum++;
							   				}
						   				}
				   					}
			                    }
		   					}
		   					//<!-- 新客户风险<5>：个人贷款违约情况统计表	 -->
		   					else if(xmlName.equals(personalLoan)){
		   						int i=0;
		   						while(i<personalLoan2){
			   						Iterator it3=uslist3.iterator();
				   					while(it3.hasNext()){
				   						Element tempele3=(Element)it3.next();								//取得子元素(四级)
						   				Element element3 = element2.addElement(tempele3.getName()+""); 		//添加次节点(四级)
						   				List attr3 = tempele3.attributes();									//得到根节点属性
						   				for (int j = 0; j < attr3.size(); j++) {
						   					org.dom4j.Attribute item3 = (org.dom4j.Attribute) attr3.get(j); //添加根节点属性
						   					element2.addAttribute(item3.getName(), item3.getValue());
										}
						   				List uslist4=tempele3.elements();
						   				Iterator it4=uslist4.iterator();
						   				while(it4.hasNext()){
							   				Element tempele4=(Element)it4.next();								//取得子元素(五级)
							   				Element element4 = element3.addElement(tempele4.getName()+""); 		//添加次节点(五级)
							   				List attr4 = tempele4.attributes();									//得到根节点属性
							   				for (int j = 0; j < attr4.size(); j++) {
							   					org.dom4j.Attribute item4 = (org.dom4j.Attribute) attr4.get(j); //添加根节点属性
							   					element4.addAttribute(item4.getName(), item4.getValue());
											}
							   				if(map.get(String.valueOf(list1FieldNum))==null||"".equals(map.get(String.valueOf(list1FieldNum)))){
						   						element4.setText("");
						   					}else{
								   				element4.setText(map.get(String.valueOf(list1FieldNum))+"");
						   					}
							   				list1FieldNum++;
						   				}
				   					}
				   					i++;
		   						}
		   					}
			   				recordNum++;
		   				}else{
		   					if(map.get(String.valueOf(list1FieldNum))==null || "".equals(map.get(String.valueOf(list1FieldNum)))){
		   						element2.setText("");
		   					}else{
		   						element2.setText(map.get(String.valueOf(list1FieldNum))+"");
		   					}
			   				list1FieldNum++;
		   				}
			   		}
				}
				if(index%1000==0 || index==list1.size()-1){
					System.out.println(file.getName()+"已读取："+index+"行");
				}
				
			}
		}
		try{
			//正式创建xml文件
			writer = new XMLWriter(new FileOutputStream(file), format);  
			writer.write(document);     
			writer.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return file;
		
	}
	/**
	 * 判断是否存在分隔符，返回你需要分割好的字段
	 */
	public String splitMe(String Str,int Num,String split){
		Pattern p=Pattern.compile(split);
 		Matcher m=p.matcher(Str);
         if(m.find()){
        	 Str = Str.split(split)[Num];									//表值切割，形成下载列表数组
         }
		return Str;
	}

	public String stringToInt(String str[], int index){
		if(str.length<1){
			return "-1";
		}
		else{
			return str[index];
		}
	}
	public String tableRelation(Map<String,String> table, String Num, int index){
		try {
			if(table.get(Num) == null){
				return "";
			}else{
				String str = table.get(Num);
				return stringToInt(str.split("\\*"), index);
			}
		} catch (Exception e) {
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> findInstInfo(String reportInstInfo, String returnFieldName, String strContrastType){
		try{
		
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstSubInfo.class);
			Object objReportInstInfo = Class.forName("report.dto.ReportInstInfo").newInstance();
			ReflectOperation.setFieldValue(objReportInstInfo, "strReportInstCode", reportInstInfo);
			detachedCriteria.add(Restrictions.eq("reportInstInfo", objReportInstInfo));
			List<ReportInstSubInfo> objectList = (List<ReportInstSubInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			
			List<String> strInstInfo = new ArrayList<String>();
			for (int i = 0; i < objectList.size(); i++) {
				strInstInfo.add(objectList.get(i).getInstInfo().getStrInstCode());
			}
			return strInstInfo;
		}catch (Exception e) {
			return null;
		}
	
	}
}
