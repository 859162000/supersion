package report.service.imps;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;

import report.dto.CurrencyType;
import report.dto.InstCodeMap;
import report.dto.InstRptRela;
import report.dto.ItemBindInfo;
import report.dto.ReportInstInfo;
import report.dto.RptInfo;
import report.dto.TaskFlow;
import report.dto.TaskRptInst;
import report.helper.GetFilePath;
import extend.dto.SystemCodeValue;
import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FileHelper;
import framework.helper.FrameworkFactory;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

public class IJFileBuilder {

	private static final String ENCODE="US-ASCII";
	private List<TaskRptInst> lstTaskRptInst;
	private Map<String,ReportInstInfo> reportInstInfoMap;
	private Map<String,List<ItemBindInfo>> bindInfoValues;
	private Map<String,StringBuilder> templateFiles;
	private Map<String,String> relationMap;
	private List<File> zipFiles;
	private String dataScope;
	private int rowIndex;
	private boolean isMakeNoBusiness = false;
	private boolean isZeroShow = false;
	
	public IJFileBuilder(String dataScope,List<TaskRptInst> taskRptInstList,Map<String,ReportInstInfo> reportInstInfoMap)
	{
		
		
		this.dataScope=dataScope;
		this.lstTaskRptInst=new ArrayList<TaskRptInst>(taskRptInstList);
		this.reportInstInfoMap=new HashMap<String,ReportInstInfo>(reportInstInfoMap);
		this.bindInfoValues=new HashMap<String,List<ItemBindInfo>>();
		this.templateFiles=new HashMap<String,StringBuilder>();
		this.zipFiles=new ArrayList<File>();
		this.rowIndex=0;
		this.relationMap=new HashMap<String,String>();
	}
	
	private String getRowKey()
	{
		return String.format("I%05d", rowIndex++);
	}

	public void make() {
		try {
			if(lstTaskRptInst.size()>0)
			{
				getReportData();
				buildIJFile();
			}
			
		} catch (Exception e) {
    	   ExceptionLog.CreateLog(e);
       }
	}
	private void builDescFile() throws Exception
	{
	/*	2016年5月31日月报第一批中兴通讯集团财务有限公司深圳市数据说明
		[4400000 广东省]
		XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX (中兴通讯集团财务有限公司广东省的数据说明)
		[4403000 深圳市]
		XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX (中兴通讯集团财务有限公司深圳市的数据说明)

	*/
		
		IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		TaskRptInst tpi=lstTaskRptInst.get(0);
		String strDate=TypeParse.parseString(tpi.getTaskFlow().getDtTaskDate(), "yyyy年MM月dd日");
		Map<String,Map<String,String>> entityMap=ShowContext.getInstance().getShowEntityMap();
		String freq=entityMap.get("reportFreq").get(tpi.getTaskFlow().getStrFreq());
		String lot=entityMap.get("lot").get(tpi.getTaskFlow().getStrTime());
		StringBuilder sbDesc=new StringBuilder();
		InstInfo topInstInfo=getTopInstInfo(tpi.getInstInfo());
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forEntityName("report.dto.InstCodeMap");
		detachedCriteria.add(Restrictions.eq("strInstCode", topInstInfo.getStrInstCode()));
		List<InstCodeMap> InstCodeMapList=(List<InstCodeMap>)findByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		String mainReportInstName = topInstInfo.getStrInstName();
		
		if(InstCodeMapList.size() == 1)
		{
			mainReportInstName = InstCodeMapList.get(0).getReportInstInfo().getStrReportInstName();
		}
		
		sbDesc.append(String.format("%s %s报%s%s数据说明\r\n", strDate,freq,lot,mainReportInstName));
		
		
		
		//按照reportInstInfo.AdminRegionCode（即7位地区编码）排序
		List<Map.Entry<String, ReportInstInfo>> reportInstInfoList = new ArrayList<Map.Entry<String, ReportInstInfo>>(reportInstInfoMap.entrySet());
		
		Collections.sort(reportInstInfoList, new Comparator<Map.Entry<String, ReportInstInfo>>()  
	    {   
			public int compare(Map.Entry<String, ReportInstInfo> o1, Map.Entry<String, ReportInstInfo> o2)  
	        {  
	           if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().getAdminRegionCode().compareTo(o1.getValue().getAdminRegionCode())>0)
	           {  
	        	   return -1;  
	           }
	           else
	           {  
	        	   return 1;  
	           }  
	              
	        }  
	     });  
	
		
		String areaName;
		

		for(int i=0;i<reportInstInfoList.size();i++)
		{
			ReportInstInfo reportInstInfo = ((Map.Entry<String, ReportInstInfo>)reportInstInfoList.get(i)).getValue();
			
			
			areaName = reportInstInfo.getAdminRegionCode();
			areaName = areaName.substring(0, areaName.length()-1);
			
			detachedCriteria=DetachedCriteria.forEntityName("extend.dto.SystemCodeValue");
			detachedCriteria.add(Restrictions.eq("codeSet.strCodeID", "XZQH"));
			detachedCriteria.add(Restrictions.eq("strCode",areaName));
			
			List<SystemCodeValue> result=(List<SystemCodeValue>)findByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(result.size() == 1)
			{
				areaName = result.get(0).getStrValue().split("-")[1];
			}
			else
			{
				areaName = reportInstInfo.getStrReportInstName();
			}
			
			sbDesc.append(String.format("[%s %s]\r\n",reportInstInfo.getAdminRegionCode(),areaName));
			sbDesc.append(String.format("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX（%s的数据说明）\r\n",reportInstInfo.getStrReportInstName()));
		}
		
		GetFilePath getFilePath = new GetFilePath();
		String fileName=getFileName("D");
	        
	    String filePath =getFilePath.getTmpFilePath()+"/"+fileName;
        File file =FileHelper.writeTxtFile(sbDesc.toString(), filePath, "UTF-8");
        if(file!=null)
        {
        	zipFiles.add(file);
        }
       
	       

	}
	
	private InstInfo getTopInstInfo(InstInfo curInstInfo)
	{
		InstInfo topInstInfo=curInstInfo;
		while(topInstInfo.getHigherInst()!=null)
		{
			topInstInfo=topInstInfo.getHigherInst();
		}
		return topInstInfo;
	}
	
	private StringBuilder getTemplateContent(String strRptpFile) throws Exception
	{
		
		
		// 读取模板文件
		StringBuilder template=templateFiles.get(strRptpFile);
		if(template==null)
		{
			template=new StringBuilder();
			String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
			String strRootPath = new GetFilePath().getWebRootPath();
			
			File file = new File(strRootPath + strTmpRootPath + "/" + strRptpFile + ".htm");
			if(file.exists()){
				// 先去除方括号中的<>中内容
				String strIn = HelpTool.readToString(file, "GB2312");
				template.append(strIn);
			}
			templateFiles.put(strRptpFile, template);
		}
		
		return template;
	}
	private String getFileName(String fileTyepFlag)
	{
		String fileName = "";
		String ext="TXT";
		if("I".equals(fileTyepFlag))
		{
			ext="IDX";
		}
		else if("J".equals(fileTyepFlag))
		{
			ext="DAT";
		}
		TaskFlow tf=lstTaskRptInst.get(0).getTaskFlow();
		for(ReportInstInfo rii:reportInstInfoMap.values())
		{
			fileName=String.format("B%s%s%s%s%s%s1.%s",fileTyepFlag,rii.getOrgTypeCode(),
					rii.getAdminRegionCode(),
					TypeParse.parseString(tf.getDtTaskDate(),"yyyyMMdd"),
					 ShowContext.getInstance().getShowEntityMap().get("freqMap").get(tf.getStrFreq()),
					 ShowContext.getInstance().getShowEntityMap().get("lotMap").get(tf.getStrTime()),
					 ext);
			break;
		}
		
		
		return fileName;
		
	}
	
	private void getReportData()
	{
		try
		{
			for(TaskRptInst task:lstTaskRptInst)
			{
				
				StringBuilder template=getTemplateContent(task.getRptInfo().getStrRptPath());
				if(template.length()>0)
				{
					List<ItemBindInfo> taskBindInfo=new ArrayList<ItemBindInfo>();
					bindInfoValues.put(task.getId(), taskBindInfo);
					task.getRptInfo().setStrIsThousands("0");
					ItemTemplate itemTemplate=new ItemTemplate(task);
					InstInfo instInfo=task.getInstInfo();
					String strInstCode=instInfo.getStrInstCode();
					String strDtDate=TypeParse.parseString(task.getTaskFlow().getDtTaskDate(), "yyyy-MM-dd");
					String strFreq=task.getTaskFlow().getStrFreq();
					int itenKeyLen=ItemTemplate.Start.length();
					int startPos = template.indexOf(ItemTemplate.Start);
					int endPos=-1;
					while(startPos > -1) {
						
						endPos = template.indexOf(ItemTemplate.ContentEnd, startPos + itenKeyLen);
						String strItem = template.substring(startPos, endPos + 1);
						
						//String strSub = getStrIn(strItem);
						//strItem = deleteStrIn(strItem);
						ItemBindInfo itemBindInfo=itemTemplate.getItemBindInfo(strItem,strInstCode,strDtDate,strFreq);
						String val=itemTemplate.getItemVal(itemBindInfo);
						itemBindInfo.value=val;
						taskBindInfo.add(itemBindInfo);
						startPos = template.indexOf(ItemTemplate.Start, endPos);
					}
			
				}
			}
		
		}
		catch(Exception ex)
		{
			ExceptionLog.CreateLog(ex);
		}
			
	}
	private String getZipName(TaskRptInst tri)
	{
		if("1".equals(dataScope))
		{
			return String.format("%s_%s_%s_%s.zip", tri.getTaskFlow().getStrTaskName(),
					tri.getInstInfo().getStrInstName(),
					tri.getRptInfo().getStrBCode(),
					TypeParse.parseString(tri.getTaskFlow().getDtTaskDate(), "yyyyMMdd"));
		}
		else if("2".equals(dataScope))
		{
			return String.format("%s_%s_%s.zip", tri.getTaskFlow().getStrTaskName(),
					tri.getInstInfo().getStrInstName(),
					TypeParse.parseString(tri.getTaskFlow().getDtTaskDate(), "yyyyMMdd"));
		}
		else
		{
			return String.format("%s_%s.zip", tri.getTaskFlow().getStrTaskName(),TypeParse.parseString(tri.getTaskFlow().getDtTaskDate(), "yyyyMMdd"));
		}
		
	}
	private void buildIJFile() throws Exception
	{
		DownloadResourceService dr=new DownloadResourceService();
		GetFilePath getFilePath = new GetFilePath();
		TaskRptInst tri=lstTaskRptInst.get(0);
		String zipFileName=getZipName(tri);
		dr.addStart(zipFileName, tri.getTaskFlow().getSuit().getStrSuitCode()); 
		
		getInstRptRelation();
		
		buildIFile();
		builDescFile();
		

		try {
			SmallTools.compressedFiles(zipFiles, getFilePath.getDownloadResourcePath(), zipFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExceptionLog.CreateLog(e);
		} // 将文件打包
		SmallTools.delFileList(zipFiles);	//清空所有临时子文件

		dr.addEnd(zipFileName, tri.getTaskFlow().getSuit().getStrSuitCode()); // 修改结束日期
		
	}
	private Map<String,String> getCurrency() throws Exception
	{
		Map<String,String> curMap=new HashMap<String,String>();
		IParamObjectResultExecute findAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<CurrencyType> result=(List<CurrencyType>)findAllDao.paramObjectResultExecute(new Object[]{"report.dto.CurrencyType",null});
		for(CurrencyType cy:result)
		{
			curMap.put(cy.getStrCurrencyCode(), cy.getStrAbbrv());
		}
		return curMap;
	}
	
	//获取机构报送的表单及币种关系
	private void getInstRptRelation() throws Exception
	{
		List<InstInfo> lstInstInfo=new ArrayList<InstInfo>();
		List<RptInfo> lstRptInfo=new ArrayList<RptInfo>();
		for(TaskRptInst taksRptInst:lstTaskRptInst)
		{
			
			lstInstInfo.add(taksRptInst.getInstInfo());
			lstRptInfo.add(taksRptInst.getRptInfo());
		}
		IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=DetachedCriteria.forEntityName("report.dto.InstRptRela");
		detachedCriteria.add(Restrictions.in("instInfo",lstInstInfo ));
		detachedCriteria.add(Restrictions.in("rptInfo",lstRptInfo ));
		List<InstRptRela> result=(List<InstRptRela>)findByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		for(InstRptRela instRptRela:result)
		{
			relationMap.put(getInstRptRelaMapKey(instRptRela.getInstInfo().getStrInstCode(),
												instRptRela.getRptInfo().getStrBCode(),
												instRptRela.getCurrencyType().getStrCurrencyCode()
												),"1");
		}
		
	}
	private String getInstRptRelaMapKey(String strInstCode,String strRtpCode,String strCurrencyCode)
	{
		return String.format("%s_%s_%s",strInstCode,strRtpCode,strCurrencyCode);
	}
	
	
	private String getBusinessFlag(String strInstCode,String rptCode,String currency)
	{
		String flag=relationMap.get(getInstRptRelaMapKey(strInstCode,rptCode,currency));
		if(flag==null)
		{
			flag="0";
		}
				
		return flag;
	}
	
	private void buildIFile() throws Exception
	{
		//关键字代码|表单代码|机构类代码|地区代码|数据属性|币种|单位|业务数据标志|数字型类型|标准化机构编码
		StringBuilder iBuilder=new StringBuilder();
		StringBuilder jBuilder=new StringBuilder();
		Map<String,String> entityPropertyMap=ShowContext.getInstance().getShowEntityMap().get("itemPropertyMap");
		Map<String,String> entityUnitMap=ShowContext.getInstance().getShowEntityMap().get("unitMap");
		
		//ij文件生成时，无业务是否生成为0的数据,false不生成，true生成
//		Map<String,String> entityIsMakeNoBusiness=ShowContext.getInstance().getShowEntityMap().get("isMakeNoBusiness");
//		isMakeNoBusiness=Boolean.valueOf(entityIsMakeNoBusiness.get("isMakeNoBusiness"));
		SystemParam systemParam = HelpTool.getSystemParam("IsMakeNoBusiness");
		if(systemParam!=null && systemParam.getStrEnable().equals("1"))
		{
			isMakeNoBusiness=Boolean.valueOf(systemParam.getStrParamValue().trim().toLowerCase());
		}
		
		
		//ij文件生成时，指标值为0的记录是否统计,false不统计，true统计
//		Map<String,String> entityIsZeroShow=ShowContext.getInstance().getShowEntityMap().get("isZeroShow");
//		isZeroShow=Boolean.valueOf(entityIsZeroShow.get("isZeroShow"));
		systemParam = HelpTool.getSystemParam("IsZeroShow");
		if(systemParam!=null && systemParam.getStrEnable().equals("1"))
		{
			isZeroShow=Boolean.valueOf(systemParam.getStrParamValue().trim().toLowerCase());
		}
		
		
		Map<String,String> currencyMap=getCurrency();
		Map<String,String> dataGroup =new HashMap<String,String>();
		Map<String,String> dataCount =new HashMap<String,String>();
		Map<String,String> dataCountFlag =new HashMap<String,String>();
		String key;
		String unit;
		for(TaskRptInst task:lstTaskRptInst)
		{
			dataGroup.clear();
			dataCount.clear();
			dataCountFlag.clear();
			unit=entityUnitMap.get(task.getRptInfo().getIntRptUnit().toString());
			ReportInstInfo rii=reportInstInfoMap.get(task.getInstInfo().getStrInstCode());
			RptInfo ri=task.getRptInfo();
			List<ItemBindInfo> bindInfo=this.bindInfoValues.get(task.getId());
			Collections.sort(bindInfo, new Comparator<ItemBindInfo>(){
				            	public int compare(ItemBindInfo o1,
										ItemBindInfo o2) {
									String group1=getGroupKey(o1);
									String group2=getGroupKey(o2);
									return group1.compareTo(group2);
								}
				        	});
			
		
			
			for(ItemBindInfo ibf:bindInfo)
			{
				
				String curGroupKey=getGroupKey(ibf);
				
				key=dataGroup.get(curGroupKey);
				String businessFlag=getBusinessFlag(task.getInstInfo().getStrInstCode(),ri.getStrBCode(),ibf.currency);
				if(key==null)
				{
					key=getRowKey();
					String currency=currencyMap.get(ibf.currency);
					if(StringUtils.isBlank(currency))
					{
						currency=ibf.currency;
					}
					dataGroup.put(curGroupKey, key);

					
					/*生成I文件的内容。这里格式化字符串时，把倒数第2位直接赋值为1，因为人行大集中的数据都是数字型的。
					 * 把倒数第3位直接赋值为0，这个只是暂时的，后面会根据J文件中对应I文件中此KEY下是否有数据来给此字段重新赋值，
					 * 如果有记录就会赋值为1，否则保持0不变
					 * */
					if(businessFlag.equals("1") || isMakeNoBusiness)
						dataCount.put(key, String.format("%s|%s|%s|%s|%s|%s|%s|0|1|%s\r\n", 
			                key,ri.getStrBCode(),rii.getOrgTypeCode(),rii.getAdminRegionCode(),
			                entityPropertyMap.get(ibf.property),currency,unit,
			                rii.getStrReportInstCode().substring(0, 14)));	//这里因为人行大集中B类机构编码都是14位的，所以可以这样截取。因为系统里ReportInstInfo表的strReportInstCode为主键，部分虚拟机构为避免主键冲突，strReportInstCode字段的值超过14位了，所以需要截取。	
										
				}

				if((isMakeNoBusiness&&"0".equals(businessFlag))||"1".equals(businessFlag))			
				{   
					
					if((isNumeric(ibf.value) && Double.parseDouble(ibf.value) == 0) || ibf.value.isEmpty())
					{
						if(isMakeNoBusiness || (!isMakeNoBusiness&&isZeroShow))
						{
							buildJFile(key,jBuilder,ibf);
							dataCountFlag.put(key, "1");
						}
					}
					else
					{
						buildJFile(key,jBuilder,ibf);
						dataCountFlag.put(key, "1");
					}	
				}

			}
			
			//根据J文件中针对每个KEY写入的记录来重新设定I文件中对应KEY的记录信息中的倒数第3个字段（业务数据标志）的值
			Iterator iter = dataCount.entrySet().iterator();  
			while (iter.hasNext()){
				 Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();  
	              
		            String entryKey = entry.getKey();  
		            String value = entry.getValue();  
		            
		            if(dataCountFlag.containsKey(entryKey))
		            {
		            	String[] result = value.split("\\|");
		            	result[7]="1";
		            	
		            	StringBuffer sb = new StringBuffer();
		            	for(int i = 0; i < result.length; i++){
		            	 sb. append(result[i]);
		            	 sb. append("|");
		            	}
		            	value = sb.toString();
		            	value = value.substring(0, value.length()-1);
		            }
		            
		            iBuilder.append(value);
			}
			
		}
		GetFilePath g=new GetFilePath();
		String fileName=getFileName("I");
		String ifilePath =g.getTmpFilePath()+"/"+fileName;
        File file =FileHelper.writeTxtFile(iBuilder.toString(), ifilePath, ENCODE);
        if(file!=null)
        {
        	zipFiles.add(file);
        }
        fileName=getFileName("J");
		String jfilePath =g.getTmpFilePath()+"/"+fileName;
        file =FileHelper.writeTxtFile(jBuilder.toString(), jfilePath, ENCODE);
        if(file!=null)
        {
        	zipFiles.add(file);
        }
	}
	private String getGroupKey(ItemBindInfo ibf)
	{
		return ibf.currency+"_"+ibf.property;
	}
	private void buildJFile(String rowKey,StringBuilder jBuilder,ItemBindInfo itemBindInfo)
	{
		//关键字代码|指标代码|数字
		jBuilder.append(String.format("%s|%s|%s\r\n", rowKey,itemBindInfo.itemCode,itemBindInfo.value));
	}

	private  boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[-]?[0-9]*[\\.][0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true;
	}

}
