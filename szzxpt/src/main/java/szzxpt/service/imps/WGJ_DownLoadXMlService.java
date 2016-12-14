package szzxpt.service.imps;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import szzxpt.dto.AutoDTO_WGJ_DownLoadRecord;

import coresystem.dto.InstInfo;
import coresystem.service.imps.AutoOrderService;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;
public class WGJ_DownLoadXMlService {
	
	private int maxRowSize;
	private String dtoName; // 对应dto 名字
	private String entityName;// 对应EntityName
	private Map<String, String> oneToManyEntityMap;
	private String applicationType;// 应用类型
	private String controlType;
	private String dataType;// 接口文件类型
	private String repeatNode;//重复节点（父节点-子节点）

	@SuppressWarnings("unchecked")
	public List<String> GenerateServiceTempXML(List<Object> dataList,String path,String intOrder,String strInstCode,String strUNO) throws Exception {
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoName));
		
		Map<String, String> downloadJudgeStatusMap=(Map<String, String>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("downloadJudgeStatusMap");
		for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
			if(entry.getValue().indexOf(",") > -1){
				String[] strValues = entry.getValue().split(",");
				detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
			}
			else{
				detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}
		}

		List<InstInfo> instList = (List<InstInfo>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("instInfoSubList");
		
		detachedCriteria.add(Restrictions.in("instInfo", instList));

		List<Object> objList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		Map<String, String> map = ShowContext.getInstance().getShowEntityMap().get(entityName);

		List<Object> objChildList=new ArrayList<Object>() ;
		int k=0;
		List<String> fileList = new ArrayList<String>();
		
		SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
		String dtDate = f.format((new Date()).getTime());
		int t = 0;
		for(int i=0;i<objList.size();i++){
			k++;
			defaultValue(objList.get(i));
			objChildList.add(objList.get(i));
			if(k > maxRowSize - 1){
				if(t > 0){
					AutoOrderService autoOrderService = new AutoOrderService();
					intOrder = autoOrderService.getAutoOrder(dtDate,"00");
				}
				fileList.add(initDataDocument(dataList,path,intOrder, objChildList,map,instList,strInstCode, strUNO));
				objChildList=new ArrayList<Object>() ;
				k=0;
				t++;
			}
		}
		if(objChildList.size()>0){
			if(t > 0){
				AutoOrderService autoOrderService = new AutoOrderService();
				intOrder = autoOrderService.getAutoOrder(dtDate,"00");
			}
			fileList.add(initDataDocument(dataList,path,intOrder,objChildList,map,instList,strInstCode, strUNO));
		}
		else{
			if(t == 0){
				fileList.add(initDataDocument(dataList,path,intOrder,objChildList,map,instList,strInstCode, strUNO));
			}
		}
		return fileList;
	}
	
	String initDataDocument(List<Object> dataList,String path,String intOrder,List<Object> objList,Map<String, String> map,List<InstInfo> instList,String strInstCode,String strUNO) throws Exception{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("MSG");
		document.appendChild(root);
		Element apptype = document.createElement("APPTYPE");
		apptype.setTextContent(applicationType);
		root.appendChild(apptype);
		Element currentfile = document.createElement("CURRENTFILE");
		currentfile.setTextContent(applicationType+dataType);
		root.appendChild(currentfile);
		Element inout = document.createElement("INOUT");
		inout.setTextContent("IN");
		root.appendChild(inout);
		Element totalRecords = document.createElement("TOTALRECORDS");
		totalRecords.setTextContent(String.valueOf(objList.size()));
		root.appendChild(totalRecords);

		Element recordsNode = document.createElement("RECORDS");
		root.appendChild(recordsNode);
		
		String strDtoName=null;
		SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
		String dtDate = f.format((new Date()).getTime());   //用的地方较多，独立出来

		for (Object obj : objList) {
			if(StringUtils.isBlank(strDtoName))
			{
				strDtoName=obj.getClass().getName();
			}
			
			Element recNode = document.createElement("REC");
			
			Field[] fieldList =ReflectOperation.getReflectFields(Class.forName(dtoName));
			
			map.entrySet();
			for (Entry<String, String> entryObject : map.entrySet()) {
				for (int i = 0; i < fieldList.length; i++) {
					if (fieldList[i].getName().equals(entryObject.getKey())) {
						Element elNode = document.createElement(map.get(fieldList[i].getName()).split(",")[0]);
					
						elNode.setTextContent(formatValue(obj,fieldList[i].getName(), map.get(fieldList[i].getName())));
						recNode.appendChild(elNode);

						// 判断当前字段是否是子循环节点
						if (oneToManyEntityMap != null && oneToManyEntityMap.size() > 0) {
							for (Entry<String, String> entry : oneToManyEntityMap.entrySet()) {
								String childDtoName = entry.getKey();
								String childKeyValue = entry.getValue();
								String childEntName = childKeyValue.split("%")[1]; // 子表需要下载的字段用命名
								String childField = childKeyValue.split("%")[0]; // 需要在其后插入子表的主表的字段
								String firstTagName = childKeyValue.split("%")[2]; // 子表的一级节点名
								String SecondTagName = childKeyValue.split("%")[3]; // 子表的二级节点名
								String childDTOForeignField = childKeyValue.split("%")[4]; // 子表DTO中的外键属性名
								
								if (map.get(fieldList[i].getName()).split(",")[0].equals(childField)) {
									initChildDocument(obj, document, childDtoName, childEntName, recNode, firstTagName, 
											SecondTagName, childDTOForeignField);
								}
							}
						}
					}
				}
			}
			
			recordsNode.appendChild(recNode);
			ReflectOperation.setFieldValue(obj, "RPTSendType", "2");
			ReflectOperation.setFieldValue(obj, "XZXH", dtDate+intOrder);
			dataList.add(obj);
		}
		if(!StringUtils.isBlank(strDtoName))
		{
			AutoDTO_WGJ_DownLoadRecord autoDTO_WGJ_DownLoadRecord =new AutoDTO_WGJ_DownLoadRecord();
			autoDTO_WGJ_DownLoadRecord.setStrDownLoadNo( dtDate+intOrder);
			autoDTO_WGJ_DownLoadRecord.setStrDtoName(strDtoName);
			autoDTO_WGJ_DownLoadRecord.setStrUNO(strUNO);
			dataList.add(autoDTO_WGJ_DownLoadRecord);
		}
		return after(path,document,intOrder,strInstCode);
	}
	@SuppressWarnings("unchecked")
	private void initChildDocument(Object motherDto,Document document, String childDtoName, String childEntName, Element recNode,
			String firstTagName, String SecondTagName, String childDTOForeignField) throws Exception
	{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(childDtoName));
		detachedCriteria.add(Restrictions.eq(childDTOForeignField, motherDto));

		List<Object> objList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		Map<String, String> map = ShowContext.getInstance().getShowEntityMap().get(childEntName);
		
		Element stockinfos = document.createElement(firstTagName);
		
		for (Object obj : objList) {
			
			Element stockinfo = document.createElement(SecondTagName);
			
			Field[] fieldList =ReflectOperation.getReflectFields(Class.forName(childDtoName));
			for (int i = 0; i < fieldList.length; i++) {
				if (map.containsKey(fieldList[i].getName())) {
					
					Element elNode = document.createElement(map.get(fieldList[i].getName()).split(",")[0]);
					
					elNode.setTextContent(formatValue(obj, fieldList[i].getName(),map.get(fieldList[i].getName())));
					stockinfo.appendChild(elNode);
				}
			}
			stockinfos.appendChild(stockinfo);
			ReflectOperation.setFieldValue(obj, "STRDATASTATE", "7");
		}
		recNode.appendChild(stockinfos);
	}
	
	public String after(String path,Document document,String intOrder,String strInstCode) throws Exception {
		
		String xmlContent = buildXMLContentStringWithDefaultCharatersEscaped(document);
		
		// 数据类型 + 12 位机构号 + 6 位日期YYMMDD + 2 位序号，文件扩展名为XML
		// 数据类型的编制规则：应用类型代码+1 位或2 位接口文件类型代码

		SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
		String dtDate = f.format((new Date()).getTime());
		String fileName = applicationType + dataType  +strInstCode+ dtDate + intOrder + ".XML";
		
		String filePath = path + "\\" + fileName;
		escapeXMLContentsAndCreateDiskFile(filePath, xmlContent);
		
		return fileName;

	}
	
	 private void escapeXMLContentsAndCreateDiskFile(String filePath, String xmlContent) throws IOException  {
		 int index = xmlContent.indexOf(">");
         String prefix = xmlContent.substring(0, index + 1);
         String suffix = xmlContent.substring(index + 1);
         final String SIGNLE_QUOTE = "'";
         final String SINGLE_QUOTE_ESCAPE = "&apos;";
         final String DOUBLE_QUOTE = "\"";
         final String DOUBLE_QUOTE_ESCAPE = "&quot;";
         if(-1 != suffix.indexOf(SIGNLE_QUOTE)){
                suffix = suffix.replaceAll(SIGNLE_QUOTE, SINGLE_QUOTE_ESCAPE);
         }
         if(-1 != suffix.indexOf(DOUBLE_QUOTE)){
                suffix = suffix.replaceAll(DOUBLE_QUOTE, DOUBLE_QUOTE_ESCAPE);
         }

         FileWriter writer = null;

         try {
                writer = new FileWriter(filePath);
                writer.append(prefix);
                writer.append(suffix);
                writer.flush();
         } catch (IOException ioe) {
                throw ioe;
         } finally {
                try {
                       if (null != writer) {
                              writer.close();
                       }
                } catch (IOException e) {
                }
         }

     }

	
	  private String buildXMLContentStringWithDefaultCharatersEscaped(Document doc) throws TransformerConfigurationException, TransformerException{
           TransformerFactory tf = TransformerFactory.newInstance();
           DOMSource source = new DOMSource(doc);
           Transformer transformer = null;
           StringWriter writer = null;
           try {
               transformer = tf.newTransformer();
               transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
               transformer.setOutputProperty(OutputKeys.ENCODING, "gb18030");
               transformer.setOutputProperty(OutputKeys.INDENT,"yes");
               writer = new StringWriter();
               StreamResult strResult = new StreamResult(writer);
               transformer.transform(source, strResult);
               return strResult.getWriter().toString();
           } finally {
                try {
                    if(null != writer){
                       writer.close();
                    }
                } 
                catch (IOException e) {
                }
            }
      }

   
	public String filterValue(String strValue)
	{
		strValue=strValue.replace("&", "&amp;");
		strValue=strValue.replace("<", "&lt;");
		strValue=strValue.replace(">", "&gt;");
		strValue=strValue.replace("\"", "&quot;");
		strValue=strValue.replace("'", "&apos;");
		return strValue;
	}
	
	// 根据配置设置数据格式（小数点位数）
	public String formatValue(Object obj, String strFieldName,String xmlField) throws Exception
	{
		String result=null;
		String [] str=xmlField.split(",");
		Object value=ReflectOperation.getFieldValue(obj,strFieldName);
		int strWs=0;
		if(str.length>1)
		{
			strWs=Integer.parseInt(str[1]);
			if(value==null)
			{
				result="";
			}else
			{
				if(StringUtils.isBlank(value.toString()))
				{
					result="";
				}
				else
				{
				result=value.toString();
				BigDecimal bg= new BigDecimal(result);
				result=bg.setScale(strWs,BigDecimal.ROUND_HALF_UP).toString();
				}
			}
		}
		else
		{
			if(value==null)
			{
				result="";	
			}else
			{
				result=value.toString();
			}
		}
		return result;
	}
	
	public void defaultValue(Object obj) throws Exception
	{
		if(applicationType.equals("FAL"))
		{
			//数据自编码----FAL 对外金融资产负债及交易数据
			//机构代码（12 位，不足12 位补a）+报表代码（5 位，不足5 位补a）+报送期（6 位）+顺序号（7 位，从0000001 开始增加）

	     
			Object value = ReflectOperation.getFieldValue(obj,"SNOCODE");
			
			
			if(value!=null)
			{
				if(value.toString().trim().length()>0)
				{
					return;
				}
				
			}
			
			String generateNo = "";
			
			
			
			String strInstCode  = ShowContext.getInstance().getShowEntityMap().get("HInstTbInstRela").get(ReflectOperation.getPrimaryKeyValue(ReflectOperation.getFieldValue(obj, "instInfo")).toString());
			
			Map<String,String> rptMap = ShowContext.getInstance().getShowEntityMap().get("dtoRptRela");
		
			String strRptCode = rptMap.get(dtoName);
					
			String reportDate = ReflectOperation.getFieldValue(obj, "BUOCMONTH").toString();
			
			AutoOrderService autoOrderService = new AutoOrderService();
			
			String intOrder=autoOrderService.getAutoOrder(strInstCode + strRptCode + reportDate, "0000000");
			
			generateNo=strInstCode + strRptCode + reportDate+intOrder;
			ReflectOperation.setFieldValue(obj,"SNOCODE",generateNo);
		}
		else if(applicationType.equals("CWD"))
		{
			//业务办理参号  ---- CWD 个人外币现钞存取数据
			//指由境内银行按国家外汇管理局要求编制的号码，共计26 位。
			//开户行12位金融机构标识码+6 位交易日期（YYMMDD）+1 位业务代码（存、取）+7位流水号

	      //  Object object = RequestManager.getTOject();
			
			Object value = ReflectOperation.getFieldValue(obj,"RPTNO");
			if(value!=null)
			{
				if(value.toString().trim().length()>0)
				{
					return;
				}
			}
			
			String rptNo = "";
			
			String strInstCode  = ShowContext.getInstance().getShowEntityMap().get("HInstTbInstRela").get(ReflectOperation.getPrimaryKeyValue(ReflectOperation.getFieldValue(obj, "instInfo")).toString());
			
			Map<String,String> rptMap = ShowContext.getInstance().getShowEntityMap().get("dtoRptNORela");
			
			String strRptNo = rptMap.get(dtoName);  //业务代码
					
			SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
			
			Object valueDate=ReflectOperation.getFieldValue(obj, "valueDate");
			String dtDate = f.format(valueDate);
			
			AutoOrderService autoOrderService = new AutoOrderService();
			
			String intOrder=autoOrderService.getAutoOrder(strInstCode +dtDate+ strRptNo, "0000000");
			
			rptNo=strInstCode +dtDate+ strRptNo +intOrder;
			
			ReflectOperation.setFieldValue(obj,"RPTNO",rptNo);
		}
	}
	
	public String getDtoName() {
		return dtoName;
	}

	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlType() {
		return controlType;
	}

	public int getMaxRowSize() {
		return maxRowSize;
	}

	public void setMaxRowSize(int maxRowSize) {
		this.maxRowSize = maxRowSize;
	}

	public Map<String, String> getOneToManyEntityMap() {
		return oneToManyEntityMap;
	}

	public void setOneToManyEntityMap(Map<String, String> oneToManyEntityMap) {
		this.oneToManyEntityMap = oneToManyEntityMap;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setRepeatNode(String repeatNode) {
		this.repeatNode = repeatNode;
	}

	public String getRepeatNode() {
		return repeatNode;
	}
}
