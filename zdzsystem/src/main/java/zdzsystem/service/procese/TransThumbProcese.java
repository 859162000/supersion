package zdzsystem.service.procese;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGWZZJRN;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGZZZJNR;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQWSNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGWZZJNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGZZZJNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZWSNR;
import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowList;
import framework.show.ShowOperation;
/**
 * 处理文书、工作证、公务证下载显示
 * @author xiajieli
 *
 */
public class TransThumbProcese implements IProcese {

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		if(null != serviceResult){
			if (serviceResult instanceof ShowList) {
				ShowList showList = (ShowList)serviceResult;
				List<List<ShowOperation>> lists=showList.getLinkedList();
				String currentTName=RequestManager.getTName();
				
				if(currentTName.equals("zdzsystem.dto.AutoDTO_ZDZ_CXQQNR")){//查询请求内容
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_CXQQNR.class);
					List<AutoDTO_ZDZ_CXQQNR> CXQQNRList = (List<AutoDTO_ZDZ_CXQQNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for(List<ShowOperation> list :lists){
						for (AutoDTO_ZDZ_CXQQNR autoDTOZDZCXQQNR : CXQQNRList) {
							
							String WSBH="";//文书编号
							String GZZBH="";//承办法官工作证编号
							String GWZBH="";//承办法官公务证编号
							
							if(ReflectOperation.getFieldValue(autoDTOZDZCXQQNR, "WSBH")!=null){
								WSBH=ReflectOperation.getFieldValue(autoDTOZDZCXQQNR, "WSBH").toString();
							}
							
							if(ReflectOperation.getFieldValue(autoDTOZDZCXQQNR, "GZZBH")!=null){
								GZZBH=ReflectOperation.getFieldValue(autoDTOZDZCXQQNR, "GZZBH").toString();
							}
							
							if(ReflectOperation.getFieldValue(autoDTOZDZCXQQNR, "GWZBH")!=null){
								GWZBH=ReflectOperation.getFieldValue(autoDTOZDZCXQQNR, "GWZBH").toString();
							}
							
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_CXQQWSNR.class);//文书
							detachedCriteria.add(Restrictions.eq("WSBH",WSBH));
							List<AutoDTO_ZDZ_CXQQWSNR> CXQQWSNRList = (List<AutoDTO_ZDZ_CXQQWSNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_CXQQFGGZZZJNR.class);//工作证
							detachedCriteria.add(Restrictions.eq("GZZBM",GZZBH));
							List<AutoDTO_ZDZ_CXQQFGGZZZJNR> CXQQFGGZZZJNRList = (List<AutoDTO_ZDZ_CXQQFGGZZZJNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_CXQQFGGWZZJRN.class);//公务证
							detachedCriteria.add(Restrictions.eq("GWZBM",GWZBH));
							List<AutoDTO_ZDZ_CXQQFGGWZZJRN> CXQQFGGWZZJRNList = (List<AutoDTO_ZDZ_CXQQFGGWZZJRN>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							String valueWS="";
							String valueGZZ="";
							String valueGWZ="";
							
							List<String> primaryKeyWSList=new ArrayList<String>();
							List<String> primaryKeyGZZList=new ArrayList<String>();
							List<String> primaryKeyGWZList=new ArrayList<String>();
							if(CXQQWSNRList.size()>0){//查询——文书
								List<String> primaryKeyWSTempList=new ArrayList<String>();
								for (AutoDTO_ZDZ_CXQQWSNR CXQQWSNR : CXQQWSNRList) {
									if(!primaryKeyWSTempList.contains(CXQQWSNR.getXH()+"*****"+CXQQWSNR.getWSBH())){
										primaryKeyWSTempList.add(CXQQWSNR.getXH()+"*****"+CXQQWSNR.getWSBH());
										primaryKeyWSList.add(ReflectOperation.getPrimaryKeyValue(CXQQWSNR).toString());
										
										String XH="";
										String WJMC="";
										String WJLX="";
										String WSLB="";
										String MD5="";
										
										if(ReflectOperation.getFieldValue(CXQQWSNR, "XH")!=null){
											XH=ReflectOperation.getFieldValue(CXQQWSNR, "XH").toString();
										}
										
										if(ReflectOperation.getFieldValue(CXQQWSNR, "WJMC")!=null){
											WJMC=ReflectOperation.getFieldValue(CXQQWSNR, "WJMC").toString();
										}
										if(ReflectOperation.getFieldValue(CXQQWSNR, "WJLX")!=null){
											WJLX=ReflectOperation.getFieldValue(CXQQWSNR, "WJLX").toString();
										}
										if(ReflectOperation.getFieldValue(CXQQWSNR, "WSLB")!=null){
											WSLB=ReflectOperation.getFieldValue(CXQQWSNR, "WSLB").toString();
										}
										if(ReflectOperation.getFieldValue(CXQQWSNR, "MD5")!=null){
											MD5=ReflectOperation.getFieldValue(CXQQWSNR, "MD5").toString();
										}
										if(!valueWS.contains("序号："+XH +"，名称：" + WJMC + "，类型："+ WJLX +"，类别："+ WSLB + "，MD5："+MD5 +" || ")){
											valueWS +="序号："+XH +"，名称：" + WJMC + "，类型："+ WJLX +"，类别："+ WSLB + "，MD5："+MD5 +" || ";
										}
										
									}
								}
								
								if(list.get(1).getOperationType().equals("GetPic")){
									String url="";
									for (String str : primaryKeyWSList) {
										url+="framework/ShowImg-"+"zdzsystem.dto.AutoDTO_ZDZ_CXQQWSNR"+".action?id="+str+"&type="+"@";
									}
									if(url.endsWith("@")){
										list.get(1).setImageUrl(url.substring(0, url.length()-1));
									}
									else{
										list.get(1).setImageUrl(url);
									}
									if(valueWS.endsWith(" || ")){
										valueWS=valueWS.substring(0, valueWS.length()-3);
									}
									list.get(1).setOperationName(valueWS);
								}
								else if(list.get(1).getOperationType().equals("Get")){
									list.get(1).setOperationName(valueWS);
								}
							}
							
							if(CXQQFGGZZZJNRList.size()>0){//查询——工作证
								List<String> primaryKeyGZZTempList=new ArrayList<String>();
								for (AutoDTO_ZDZ_CXQQFGGZZZJNR CXQQFGGZZZJNR : CXQQFGGZZZJNRList) {
									
									if(!primaryKeyGZZTempList.contains(CXQQFGGZZZJNR.getXH()+"*****"+CXQQFGGZZZJNR.getGZZBM())){
										primaryKeyGZZTempList.add(CXQQFGGZZZJNR.getXH()+"*****"+CXQQFGGZZZJNR.getGZZBM());
										primaryKeyGZZList.add(ReflectOperation.getPrimaryKeyValue(CXQQFGGZZZJNR).toString());
									}
									
									String XH="";
									String ZJMC="";
									String GZZBM="";
									String GZZWJGS="";
									
									if(ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "XH")!=null){
										XH=ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "XH").toString();
									}
									if(ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "ZJMC")!=null){
										ZJMC=ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "ZJMC").toString();
									}
									if(ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "GZZBM")!=null){
										GZZBM=ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "GZZBM").toString();
									}
									if(ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "GZZWJGS")!=null){
										GZZWJGS=ReflectOperation.getFieldValue(CXQQFGGZZZJNR, "GZZWJGS").toString();
									}
									if(!valueGZZ.contains("序号"+XH+"，名称："+ZJMC+"，编码："+GZZBM+"，格式："+GZZWJGS +" || ")){
										valueGZZ+="序号"+XH+"，名称："+ZJMC+"，编码："+GZZBM+"，格式："+GZZWJGS +" || ";
									}
									
								}
								
								if(list.get(2).getOperationType().equals("GetPic")){
									String url="";
									for (String str : primaryKeyGZZList) {
										url+="framework/ShowImg-"+"zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGZZZJNR"+".action?id="+str+"&type="+"@";
									}
									if(url.endsWith("@")){
										list.get(2).setImageUrl(url.substring(0, url.length()-1));
									}
									else{
										list.get(2).setImageUrl(url);
									}
									
									if(valueGZZ.endsWith(" || ")){
										valueGZZ=valueGZZ.substring(0, valueGZZ.length()-3);
									}
									list.get(2).setOperationName(valueGZZ);
								}
							}
							
							if(CXQQFGGWZZJRNList.size()>0){//查询——公务证
								List<String> primaryKeyGWZTempList=new ArrayList<String>();
								for (AutoDTO_ZDZ_CXQQFGGWZZJRN CXQQFGGWZZJRN : CXQQFGGWZZJRNList) {
									if(!primaryKeyGWZTempList.contains(CXQQFGGWZZJRN.getXH()+"*****"+CXQQFGGWZZJRN.getGWZBM())){
										primaryKeyGWZTempList.add(CXQQFGGWZZJRN.getXH()+"*****"+CXQQFGGWZZJRN.getGWZBM());
										primaryKeyGWZList.add(ReflectOperation.getPrimaryKeyValue(CXQQFGGWZZJRN).toString());
									}
									
									String XH="";
									String ZJMC="";
									String GWZBM="";
									String GWZWJGS="";
									
									if(ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "XH")!=null){
										XH=ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "XH").toString();
									}
									if(ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "ZJMC")!=null){
										ZJMC=ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "ZJMC").toString();
									}
									if(ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "GWZBM")!=null){
										GWZBM=ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "GWZBM").toString();
									}
									if(ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "GWZWJGS")!=null){
										GWZWJGS=ReflectOperation.getFieldValue(CXQQFGGWZZJRN, "GWZWJGS").toString();
									}
									if(!valueGWZ.contains("序号："+XH+"，名称"+ZJMC+"，编码："+GWZBM+"，格式："+GWZWJGS +" || ")){
										valueGWZ+="序号："+XH+"，名称"+ZJMC+"，编码："+GWZBM+"，格式："+GWZWJGS +" || ";
									}
									
								}
								
								if(list.get(3).getOperationType().equals("GetPic")){
									String url="";
									for (String str : primaryKeyGWZList) {
										url+="framework/ShowImg-"+"zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGWZZJRN"+".action?id="+str+"&type="+"@";
									}
									if(url.endsWith("@")){
										list.get(3).setImageUrl(url.substring(0, url.length()-1));
									}
									else{
										list.get(3).setImageUrl(url);
									}
									if(valueWS.endsWith(" || ")){
										valueWS=valueWS.substring(0, valueWS.length()-3);
									}
									if(valueGWZ.endsWith(" || ")){
										valueGWZ=valueGWZ.substring(0, valueGWZ.length()-3);
									}
									list.get(3).setOperationName(valueGWZ);
								}
							}
								
						}
					}
					
				}
				else if(currentTName.equals("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR")){//控制请求具体内容
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_KZQQJTNR.class);
					List<AutoDTO_ZDZ_KZQQJTNR> KZQQJTNRList = (List<AutoDTO_ZDZ_KZQQJTNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for(List<ShowOperation> list :lists){
						for (AutoDTO_ZDZ_KZQQJTNR autoDTOZDZKZQQJTNR : KZQQJTNRList) {
							
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_FYSFKZWSNR.class);//文书
							detachedCriteria.add(Restrictions.eq("BDHM",autoDTOZDZKZQQJTNR));
							List<AutoDTO_ZDZ_FYSFKZWSNR> FYSFKZWSNRList = (List<AutoDTO_ZDZ_FYSFKZWSNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_FYSFKZFGGZZZJNR.class);//工作证
							detachedCriteria.add(Restrictions.eq("BDHM",autoDTOZDZKZQQJTNR));
							List<AutoDTO_ZDZ_FYSFKZFGGZZZJNR> FYSFKZFGGZZZJNRList = (List<AutoDTO_ZDZ_FYSFKZFGGZZZJNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_FYSFKZFGGWZZJNR.class);//公务证
							detachedCriteria.add(Restrictions.eq("BDHM",autoDTOZDZKZQQJTNR));
							List<AutoDTO_ZDZ_FYSFKZFGGWZZJNR> FYSFKZFGGWZZJNRList = (List<AutoDTO_ZDZ_FYSFKZFGGWZZJNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							String valueWS="";
							String valueGZZ="";
							String valueGWZ="";
							
							List<String> primaryKeyWSList=new ArrayList<String>();
							List<String> primaryKeyGZZList=new ArrayList<String>();
							List<String> primaryKeyGWZList=new ArrayList<String>();
							if(FYSFKZWSNRList.size()>0){//控制——文书
								for (AutoDTO_ZDZ_FYSFKZWSNR FYSFKZWSNR : FYSFKZWSNRList) {
									List<String> primaryKeyWSTempList=new ArrayList<String>();
									if(!primaryKeyWSTempList.contains(FYSFKZWSNR.getXH()+"*****"+FYSFKZWSNR.getBDHM())){
										primaryKeyWSTempList.add(FYSFKZWSNR.getXH()+"*****"+FYSFKZWSNR.getBDHM());
										primaryKeyWSList.add(ReflectOperation.getPrimaryKeyValue(FYSFKZWSNR).toString());
									}
									
									String XH="";
									String WJMC="";
									String WJLX="";
									String WSLB="";
									String MD5="";
									if(ReflectOperation.getFieldValue(FYSFKZWSNR, "XH")!=null){
										XH=ReflectOperation.getFieldValue(FYSFKZWSNR, "XH").toString();
									}
									
									if(ReflectOperation.getFieldValue(FYSFKZWSNR, "WJMC")!=null){
										WJMC=ReflectOperation.getFieldValue(FYSFKZWSNR, "WJMC").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZWSNR, "WJLX")!=null){
										WJLX=ReflectOperation.getFieldValue(FYSFKZWSNR, "WJLX").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZWSNR, "WSLB")!=null){
										WSLB=ReflectOperation.getFieldValue(FYSFKZWSNR, "WSLB").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZWSNR, "MD5")!=null){
										MD5=ReflectOperation.getFieldValue(FYSFKZWSNR, "MD5").toString();
									}
									if(!valueWS.contains("序号："+XH +"，名称：" + WJMC + "，类型："+ WJLX +"，类别："+ WSLB + "，MD5："+MD5  +" || ")){
										valueWS +="序号："+XH +"，名称：" + WJMC + "，类型："+ WJLX +"，类别："+ WSLB + "，MD5："+MD5  +" || ";
									}
									
								}
								
								if(list.get(1).getOperationType().equals("GetPic")){
									String url="";
									for (String str : primaryKeyWSList) {
										url+="framework/ShowImg-"+"zdzsystem.dto.AutoDTO_ZDZ_FYSFKZWSNR"+".action?id="+str+"&type="+"@";
									}
									if(url.endsWith("@")){
										list.get(1).setImageUrl(url.substring(0, url.length()-1));
									}
									else{
										list.get(1).setImageUrl(url);
									}
									if(valueWS.endsWith(" || ")){
										valueWS=valueWS.substring(0, valueWS.length()-3);
									}
									list.get(1).setOperationName(valueWS);
								}
								else if(list.get(1).getOperationType().equals("Get")){
									list.get(1).setOperationName(valueWS);
								}
							}
							
							if(FYSFKZFGGZZZJNRList.size()>0){//控制——工作证
								List<String> primaryKeyGZZTempList=new ArrayList<String>();

								for (AutoDTO_ZDZ_FYSFKZFGGZZZJNR FYSFKZFGGZZZJNR : FYSFKZFGGZZZJNRList) {
									if(!primaryKeyGZZTempList.contains(FYSFKZFGGZZZJNR.getXH()+"*****"+FYSFKZFGGZZZJNR.getBDHM())){
										primaryKeyGZZTempList.add(FYSFKZFGGZZZJNR.getXH()+"*****"+FYSFKZFGGZZZJNR.getBDHM());
										primaryKeyGZZList.add(ReflectOperation.getPrimaryKeyValue(FYSFKZFGGZZZJNR).toString());
									}
									
									String XH="";
									String GZZBM="";
									String GZZWJGS="";
									
									if(ReflectOperation.getFieldValue(FYSFKZFGGZZZJNR, "XH")!=null){
										XH=ReflectOperation.getFieldValue(FYSFKZFGGZZZJNR, "XH").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZFGGZZZJNR, "GZZBM")!=null){
										GZZBM=ReflectOperation.getFieldValue(FYSFKZFGGZZZJNR, "GZZBM").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZFGGZZZJNR, "GZZWJGS")!=null){
										GZZWJGS=ReflectOperation.getFieldValue(FYSFKZFGGZZZJNR, "GZZWJGS").toString();
									}
									if(!valueGZZ.contains("序号"+XH+"，编码："+GZZBM+"，格式："+GZZWJGS +" || ")){
										valueGZZ+="序号"+XH+"，编码："+GZZBM+"，格式："+GZZWJGS +" || ";
									}
									
								}
								
								if(list.get(2).getOperationType().equals("GetPic")){
									String url="";
									for (String str : primaryKeyGZZList) {
										url+="framework/ShowImg-"+"zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGZZZJNR"+".action?id="+str+"&type="+"@";
									}
									if(url.endsWith("@")){
										list.get(2).setImageUrl(url.substring(0, url.length()-1));
									}
									else{
										list.get(2).setImageUrl(url);
									}
									if(valueGZZ.endsWith(" || ")){
										valueGZZ=valueGZZ.substring(0, valueGZZ.length()-3);
									}
									list.get(2).setOperationName(valueGZZ);
									
								}
							}
							
							if(FYSFKZFGGWZZJNRList.size()>0){//控制——公务证
								List<String> primaryKeyGWZTempList=new ArrayList<String>();

								for (AutoDTO_ZDZ_FYSFKZFGGWZZJNR FYSFKZFGGWZZJNR : FYSFKZFGGWZZJNRList) {
									if(!primaryKeyGWZTempList.contains(FYSFKZFGGWZZJNR.getXH()+"*****"+FYSFKZFGGWZZJNR.getBDHM())){
										primaryKeyGWZTempList.add(FYSFKZFGGWZZJNR.getXH()+"*****"+FYSFKZFGGWZZJNR.getBDHM());
										primaryKeyGWZList.add(ReflectOperation.getPrimaryKeyValue(FYSFKZFGGWZZJNR).toString());
									}
									
									String XH="";
									String GWZBM="";
									String GWZWJGS="";
									
									if(ReflectOperation.getFieldValue(FYSFKZFGGWZZJNR, "XH")!=null){
										XH=ReflectOperation.getFieldValue(FYSFKZFGGWZZJNR, "XH").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZFGGWZZJNR, "GWZBM")!=null){
										GWZBM=ReflectOperation.getFieldValue(FYSFKZFGGWZZJNR, "GWZBM").toString();
									}
									if(ReflectOperation.getFieldValue(FYSFKZFGGWZZJNR, "GWZWJGS")!=null){
										GWZWJGS=ReflectOperation.getFieldValue(FYSFKZFGGWZZJNR, "GWZWJGS").toString();
									}
									if(!valueGWZ.contains("序号"+XH+"，编码："+GWZBM+"，格式："+GWZWJGS +" || ")){
										valueGWZ+="序号"+XH+"，编码："+GWZBM+"，格式："+GWZWJGS +" || ";
									}
									
								}
								
								if(list.get(3).getOperationType().equals("GetPic")){
									String url="";
									for (String str : primaryKeyGWZList) {
										url+="framework/ShowImg-"+"zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGWZZJNR"+".action?id="+str+"&type="+"@";
									}
									if(url.endsWith("@")){
										list.get(3).setImageUrl(url.substring(0, url.length()-1));
									}
									else{
										list.get(3).setImageUrl(url);
									}
									if(valueGWZ.endsWith(" || ")){
										valueGWZ=valueGWZ.substring(0, valueGWZ.length()-3);
									}
									list.get(3).setOperationName(valueGWZ);
								}
							}
								
						}
					}
				}
					
			}
		}
		return serviceResult;
		
	}
	
}
