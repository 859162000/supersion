package zxptsystem.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.SystemCodeValue;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
/**
 * 借款人财务报表模板下载
 * @author Transino
 *
 */
public class DownLoadDataWithTemplate extends BaseTShowService {

	private String isDownData;
	private String templatePath;
	private String tempPath;
	private Map<String,String> importRelaMap;
	private Map<String,String> mxTypeCellRela;
	private Map<String,String> bblxMap;
	
	@SuppressWarnings("deprecation")
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		String realPath=ServletActionContext.getServletContext().getRealPath(this.getTemplatePath());
		File[] files = new File(realPath).listFiles();
		if(files==null){
			this.setServiceResult(new MessageResult(false,"下载失败,模板不存在"));
		}else{
			String[] strfiles=new String[files.length];
			List<String> fileList=new ArrayList<String>();
			
			if(files.length<1){
				this.setServiceResult(new MessageResult(false,"下载失败"));
			}else{
				DownloadResult downloadResult;
				HashMap<String,String> pathMap = new HashMap<String,String>();
				for(File f:files){				
					pathMap.put(f.getName().substring(0, 2), f.getPath());
					fileList.add(f.getPath());
				}
				fileList.toArray(strfiles);
				if(!Boolean.parseBoolean(this.getIsDownData())){
					downloadResult = new FileHandler().GetStreamResultFromPath(strfiles, "财务报表模板.zip", 1024*1024*5);
				}else{
					String[] idList=(String[]) RequestManager.getIdList();
					List<Object> list=new ArrayList<Object>();
					DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					if(idList!=null && idList.length>0){
						for (String id : idList) {
							IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
							Object obj = (Object)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),id,null});
							list.add(obj);
						}
					}else{
						list = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					}
					
					DetachedCriteria detachedCriteria1=null;
					
					String tmpPath1=ServletActionContext.getServletContext().getRealPath(this.getTempPath());
					
					File tmpFolder = new File(tmpPath1);
					if(!tmpFolder.isDirectory()){
						tmpFolder.mkdir();
					}
					
					for(Object obj:list){
						String BBNF="";
						if(ReflectOperation.getFieldValue(obj, "BBNF")!=null){
							BBNF=ReflectOperation.getFieldValue(obj, "BBNF").toString();
						}
						String XXJLLX="";
						if(ReflectOperation.getFieldValue(obj, "XXJLLX")!=null){
							XXJLLX= ReflectOperation.getFieldValue(obj, "XXJLLX").toString();
						}
						String BBLX="";
						if(ReflectOperation.getFieldValue(obj, "BBLX")!=null){
							BBLX=ReflectOperation.getFieldValue(obj, "BBLX").toString();
						}
						String JKRMC="";
						if(ReflectOperation.getFieldValue(obj, "JKRMC")!=null){
							JKRMC=ReflectOperation.getFieldValue(obj, "JKRMC").toString();	
						}
						String BBLXXF="";
						if(ReflectOperation.getFieldValue(obj, "BBLXXF")!=null){
							BBLXXF=ReflectOperation.getFieldValue(obj, "BBLXXF").toString();
						}
						
						detachedCriteria = DetachedCriteria.forClass(SystemCodeValue.class);
						detachedCriteria.add(Restrictions.eq("codeSet.strCodeID", "CWBB_XXJLLX"));
						detachedCriteria.add(Restrictions.eq("strCode", XXJLLX));
						List<SystemCodeValue> XXJLLXList = (List<SystemCodeValue>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						String strXXJLLX = "";
						if(XXJLLXList != null && XXJLLXList.size() > 0){
							if(XXJLLXList.get(0).getStrValue().split("-")[1]!=null){
								strXXJLLX=XXJLLXList.get(0).getStrValue().split("-")[1].toString();
							}
						}
						
						detachedCriteria = DetachedCriteria.forClass(SystemCodeValue.class);
						detachedCriteria.add(Restrictions.eq("codeSet.strCodeID", "BBLXXF"));
						detachedCriteria.add(Restrictions.eq("strCode", BBLXXF));
						List<SystemCodeValue> BBLXXFList = (List<SystemCodeValue>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
						String strBBLX=bblxMap.get(BBLX);
						
						String strExcleName= BBNF +"_"+ strXXJLLX +"_"+ strBBLX +"_"+ JKRMC;
						
						java.lang.reflect.Field foreignField = ReflectOperation.getFieldByName(Class.forName(mxTypeCellRela.get(XXJLLX)), "FOREIGNID");
						detachedCriteria1 = DetachedCriteria.forClass(Class.forName(mxTypeCellRela.get(XXJLLX)));
						Object foreignObj = foreignField.getType().newInstance();
						
						String autoId="";
						if(ReflectOperation.getFieldValue(obj, "autoID")!=null){
							autoId=ReflectOperation.getFieldValue(obj, "autoID").toString();
						}
						
						ReflectOperation.setFieldValue(foreignObj, "autoID", autoId);
						detachedCriteria1.add(Restrictions.eq("FOREIGNID", foreignObj));
						List<Object> list1 = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria1,null});
											
						for(Object obj1:list1){
							HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(pathMap.get(XXJLLX))));
							HSSFSheet sheet = wb.getSheetAt(0);
							String[] baseValueMap = this.getImportRelaMap().get(foreignField.getType().getName()).split(",");//XXJLLX:3-3-DOWNLIST,JRJGDM:4-3-STRING
							int rowIndex=0;
							int cellIndex=0;
							String type="";
							String fieldStr="";
							for(String str:baseValueMap){
								fieldStr=str.split(":")[0];
								rowIndex = Integer.parseInt(str.split(":")[1].split("-")[0]);
								cellIndex = Integer.parseInt(str.split(":")[1].split("-")[1]);
								type=str.split(":")[1].split("-")[2];
								
								Object parentObj = ReflectOperation.getFieldValue(obj1, "FOREIGNID");
								String strValue="";
								if(ReflectOperation.getFieldValue(parentObj, fieldStr)!=null){
									strValue=ReflectOperation.getFieldValue(parentObj, fieldStr).toString();
								}
								
								if(type.equals("DOWNLIST")){
									if(fieldStr.equals("XXJLLX")){
										if(XXJLLXList != null && XXJLLXList.size() > 0){
											strValue=XXJLLXList.get(0).getStrValue();
										}
									}else if(fieldStr.equals("BBLX")){
										strValue=strValue+"-"+bblxMap.get(BBLX);
									}else if(fieldStr.equals("BBLXXF")){
										if(BBLXXFList != null && BBLXXFList.size() > 0){
											strValue =BBLXXFList.get(0).getStrValue();
										}
									}
								} 
								sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(strValue);
							}
							String[] mxValueMap = this.getImportRelaMap().get(obj1.getClass().getName()).split(",");//HBZJ:15-3,DQTZ:16-3
							for(String str:mxValueMap){
								fieldStr=str.split(":")[0];								
								rowIndex = Integer.parseInt(str.split(":")[1].split("-")[0]);
								cellIndex = Integer.parseInt(str.split(":")[1].split("-")[1]);
								
								String tempFieldStr="";
								if(ReflectOperation.getFieldValue(obj1, fieldStr)!=null){
									tempFieldStr=ReflectOperation.getFieldValue(obj1, fieldStr).toString();
								}
								
								if(fieldStr.equalsIgnoreCase("extend2")){
									sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(tempFieldStr);
								}else{
									double v=0;
									try{
										v=Double.parseDouble(tempFieldStr);
									}catch(Exception e){
										v=0;
									}
									sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(v);
								}
							}
							
							FileOutputStream out = new FileOutputStream(tmpFolder +File.separator +strExcleName +".xls");  
							wb.write(out);
							out.close();
						}
					}
					File[] tmpfiles=tmpFolder.listFiles();
					String[] tmpStrs =new String[tmpfiles.length];
					for(int index=0;index<tmpfiles.length;index++){
						tmpStrs[index]=tmpfiles[index].getPath();
					}
					downloadResult=new FileHandler().GetStreamResultFromPath(tmpStrs, "财务报表数据.zip", 1024*1024*5);
					for(File tmpfile:tmpfiles){
						tmpfile.delete();
					}
					tmpFolder.delete();
				}
				this.setServiceResult(downloadResult);
			}
		}
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getTemplatePath() {
		return templatePath;
	}

	public void setIsDownData(String isDownData) {
		this.isDownData = isDownData;
	}

	public String getIsDownData() {
		return isDownData;
	}
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	public String getTempPath() {
		return tempPath;
	}
	public void setImportRelaMap(Map<String,String> importRelaMap) {
		this.importRelaMap = importRelaMap;
	}
	public Map<String,String> getImportRelaMap() {
		return importRelaMap;
	}
	public void setMxTypeCellRela(Map<String,String> mxTypeCellRela) {
		this.mxTypeCellRela = mxTypeCellRela;
	}
	public Map<String,String> getMxTypeCellRela() {
		return mxTypeCellRela;
	}
	public Map<String, String> getBblxMap() {
		return bblxMap;
	}
	public void setBblxMap(Map<String, String> bblxMap) {
		this.bblxMap = bblxMap;
	}

	
}
