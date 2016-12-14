package zxptsystem.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;
import zxptsystem.dto.AutoDTO_QYZXPLXDYWSJSC;
import zxptsystem.dto.QYZXPLXDYWSJSCJGFKBW;
import zxptsystem.dto.QYZXPLXDYWSJSCJGFKBWSub;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;

/**
 * 企业征信批量信贷业务数据删除采集反馈报文
 * @author Transino
 *
 */
public class QYZXPLXDYWSJSCJGFKBWUploadFileService implements IObjectResultExecute{

	private Map<String,String> statusMap;
	
	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		try{
			File uploadFile1 = (File) ActionContext.getContext().getSession().get("uploadFile1");
			if(uploadFile1 == null){
				return new MessageResult(false,"解析文件不能为空");
			}
			String encName=(String)ActionContext.getContext().getSession().get("encName");
			if(!StringUtils.isBlank(encName) && !encName.endsWith(".enc")){
				return new MessageResult(false,"请导入后缀为enc格式的文件");
			}
			String ErrorMessage=(String)ActionContext.getContext().getSession().get("ErrorMessage");
			if(!StringUtils.isBlank(ErrorMessage)){
				return new MessageResult(false,ErrorMessage);
			}
			
			String FileName= (String) ActionContext.getContext().getSession().get("FileName");
		    String outputFileName=(String) ActionContext.getContext().getSession().get("outputFileName");
		    
		    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(outputFileName),"GBK"));
			
			String line=null; 
			QYZXPLXDYWSJSCJGFKBW qYZXPLXDYWSJSCJGFKBW = new QYZXPLXDYWSJSCJGFKBW();
			List<QYZXPLXDYWSJSCJGFKBWSub> qYZXPLXDYWSJSCJGFKBWSubList = new ArrayList<QYZXPLXDYWSJSCJGFKBWSub>();
			
			Set<String> fkbwTypeSet=new HashSet<String>();
			String strJRJGDM="";

			CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
			List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(strJRJGDM);
			
			String BWCLJG="";//报文处理结果
			String reportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //反馈报文导入时间
			while((line=bufferedReader.readLine())!=null) { 
				if(!StringUtils.isBlank(line)){
					if(line.startsWith("A")){
						qYZXPLXDYWSJSCJGFKBW = new QYZXPLXDYWSJSCJGFKBW();
						qYZXPLXDYWSJSCJGFKBW.setStrBWCCWJM(FileName.substring(0, FileName.length()-4));
						qYZXPLXDYWSJSCJGFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
						if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
							qYZXPLXDYWSJSCJGFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
						}
						
						qYZXPLXDYWSJSCJGFKBW.setStrVersionCode(subStringByByte(line,1,3));
						qYZXPLXDYWSJSCJGFKBW.setStrJRJGCode(subStringByByte(line,4,11));
						qYZXPLXDYWSJSCJGFKBW.setStrBWSCSJ(subStringByByte(line,15,14));
						qYZXPLXDYWSJSCJGFKBW.setStrBWLX(subStringByByte(line,29,2));
						BWCLJG=subStringByByte(line,31,2);
						qYZXPLXDYWSJSCJGFKBW.setStrBWCLJG(BWCLJG);
						
						fkbwTypeSet.add(qYZXPLXDYWSJSCJGFKBW.getStrBWLX());
						
						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{qYZXPLXDYWSJSCJGFKBW,null});
					}
					else if(line.startsWith("Z")){
						if(qYZXPLXDYWSJSCJGFKBWSubList.size() > 0){
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{qYZXPLXDYWSJSCJGFKBWSubList,null});
							qYZXPLXDYWSJSCJGFKBWSubList = new ArrayList<QYZXPLXDYWSJSCJGFKBWSub>();
						}
					}
					else{
						QYZXPLXDYWSJSCJGFKBWSub qYZXPLXDYWSJSCJGFKBWSub = new QYZXPLXDYWSJSCJGFKBWSub();
						qYZXPLXDYWSJSCJGFKBWSub.setQYZXPLXDYWSJSCJGFKBW(qYZXPLXDYWSJSCJGFKBW);
						
						String strSCYWZL=subStringByByte(line,0,2);
						qYZXPLXDYWSJSCJGFKBWSub.setStrSCYWZL(strSCYWZL);
						strJRJGDM=subStringByByte(line,2,11);
						qYZXPLXDYWSJSCJGFKBWSub.setStrJRJGDM(strJRJGDM);
						String strZHTBH = subStringByByte(line,13,60);
						qYZXPLXDYWSJSCJGFKBWSub.setStrZHTBH(strZHTBH);
						String strSCJG =subStringByByte(line,73,1);
						qYZXPLXDYWSJSCJGFKBWSub.setStrSCJG(strSCJG);
						
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria=null;
						detachedCriteria = DetachedCriteria.forClass(AutoDTO_QYZXPLXDYWSJSC.class);
						detachedCriteria.add(Restrictions.eq("strDeleteBusiType", strSCYWZL));
						detachedCriteria.add(Restrictions.eq("strJRJGCode", strJRJGDM));
						detachedCriteria.add(Restrictions.eq("strZHTBH", strZHTBH));
						List<AutoDTO_QYZXPLXDYWSJSC> qYZXPLXDYWSJSCList = (List<AutoDTO_QYZXPLXDYWSJSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						if(strSCJG.equals("1")){
							//逻辑删除数据
							for (AutoDTO_QYZXPLXDYWSJSC qYZXPLXDYWSJSC : qYZXPLXDYWSJSCList) {
								ReflectOperation.setFieldValue(qYZXPLXDYWSJSC, "RPTFeedbackType", "2");
							}
							if(strSCYWZL.equals("01")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKYW_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("DKHTHM", strZHTBH));
								
							}else if(strSCYWZL.equals("02")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_BLYW_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("BLXYBH", strZHTBH));
							}
							else if(strSCYWZL.equals("03")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_PJTX_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("PJNBBH", strZHTBH));
							}
							else if(strSCYWZL.equals("04")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_MYRZ_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("RZXYBH", strZHTBH));
							}
							else if(strSCYWZL.equals("05")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_XYZYW_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("XYZHM", strZHTBH));
								
							}else if(strSCYWZL.equals("06")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_BHYW_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("BHHM", strZHTBH));
								
							}else if(strSCYWZL.equals("07")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_YHCDHP_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("HPHM", strZHTBH));
								
							}else if(strSCYWZL.equals("08")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_GKSX_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("SXXYHM", strZHTBH));
								
							}else if(strSCYWZL.equals("09")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKXX_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("DKYWHM", strZHTBH));
								
							}else if(strSCYWZL.equals("10")){
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_QXXX_JC"));
								detachedCriteria.add(Restrictions.eq("JRJGDM", strJRJGDM));
								detachedCriteria.add(Restrictions.eq("DKKBM", strZHTBH));
							}	
							List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							for (Object object_JC : Object_JCList) {
								ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType", "5");
								List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
								for (Field field : listObjectMx) {
									detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
									List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (Object object : objectMXList) {
										ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
									}
								}
							}
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
						
							//统计删除的明细表任务层的状态
							commonStatus.commonUpdateReportStatus(Object_JCList,instInfoSubList);
						}else{
							for (AutoDTO_QYZXPLXDYWSJSC qYZXPLXDYWSJSC : qYZXPLXDYWSJSCList) {
								ReflectOperation.setFieldValue(qYZXPLXDYWSJSC, "RPTFeedbackType", "3");
								//回执失败，联动改变其它状态
								if(statusMap!=null){
									for (Map.Entry<String, String>  entry: statusMap.entrySet()) {
										ReflectOperation.setFieldValue(qYZXPLXDYWSJSC, entry.getKey(), entry.getValue());
									}
								}
								
							}
						}
						IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{qYZXPLXDYWSJSCList,null});
						
						new RejectReportService().GetSendedRejectReportFeedback(qYZXPLXDYWSJSCList,FileName,"21"); //处理已报送报文驳回的回执状态

						//统计批量信贷业务数据删除表任务层回执状态
						commonStatus.commonUpdateReportStatus(qYZXPLXDYWSJSCList, instInfoSubList);
						
						qYZXPLXDYWSJSCJGFKBWSubList.add(qYZXPLXDYWSJSCJGFKBWSub);
					}
				}
			}
			
			/**更新企业征信报送流转概况表*/
			GenerAndAnaInsertService updateData=new GenerAndAnaInsertService();
			String strFKBWSFTSSBCW="是";//反馈报文是否提示上报错误
			if(!StringUtils.isBlank(BWCLJG) && BWCLJG.equals("90")){
				strFKBWSFTSSBCW="否";
			}
			updateData.UpdateE_RPT(FileName, "是", reportDate, outputFileName, strFKBWSFTSSBCW,0);
			
			return new MessageResult("报文解析成功");
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			if(StringUtils.isBlank(ex.getMessage())){
				return new MessageResult(false,"报文解析文件异常");
			}
			else{
				return new MessageResult(false,"报文解析文件异常:" + ex.getMessage());
			}
		}
	}

	public static void deCompress(String fileGzipIn, String fileOut) throws IOException{
		FileInputStream gzipIn = new FileInputStream(fileGzipIn);
		FileOutputStream out = new FileOutputStream(fileOut);
		
		GZIPInputStream in = new GZIPInputStream(gzipIn);
		byte buf[] = new byte[BUFFER];
		int num;
		while ((num = in.read(buf, 0, BUFFER)) != -1)
			out.write(buf, 0, num);
		in.close();
		
		out.close();
		gzipIn.close();
	}
	
	private String subStringByByte(String str,int start, int subLen) throws UnsupportedEncodingException {
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes("GBK");
            if (a.length <= subLen) {
                result = str;
            } else if (subLen > 0) {
                result = new String(a, start, subLen,"GBK");
            }
        }
        return result;
    }
	
	public void setStatusMap(Map<String,String> statusMap) {
		this.statusMap = statusMap;
	}

	public Map<String,String> getStatusMap() {
		return statusMap;
	}

	private static final int BUFFER = 2048;
	
}
