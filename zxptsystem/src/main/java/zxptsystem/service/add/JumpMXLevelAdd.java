package zxptsystem.service.add;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import zxptsystem.dto.AutoDTO_QY_BHYW_JC;
import zxptsystem.dto.AutoDTO_QY_BLXDZCCL_JC;
import zxptsystem.dto.AutoDTO_QY_BLYW_JC;
import zxptsystem.dto.AutoDTO_QY_DBXX_JC;
import zxptsystem.dto.AutoDTO_QY_DKXX_JC;
import zxptsystem.dto.AutoDTO_QY_DKYWXX;
import zxptsystem.dto.AutoDTO_QY_DKYW_JC;
import zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX;
import zxptsystem.dto.AutoDTO_QY_HKXX;
import zxptsystem.dto.AutoDTO_QY_JJXX;
import zxptsystem.dto.AutoDTO_QY_MYRZ_JC;
import zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX;
import zxptsystem.dto.AutoDTO_QY_RZYWHKXX;
import zxptsystem.dto.AutoDTO_QY_RZYWXX;
import zxptsystem.dto.AutoDTO_QY_XYZYW_JC;
import zxptsystem.dto.AutoDTO_QY_YHCDHP_JC;
import zxptsystem.dto.View_JG_JGJBXX;
import zxptsystem.dto.View_QY_JKRGKXX;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IAdd;

public class JumpMXLevelAdd implements IAdd{

	public void Add() throws Exception {

		String viewDTOName = View_QY_JKRGKXX.class.getName();
		IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		String id = "qyzx_strVersion";
		SystemParam qyzxVersionObj = (SystemParam)byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", id , null});
		if (qyzxVersionObj != null && qyzxVersionObj.getStrParamValue().equals("2.2")) {
			viewDTOName = View_JG_JGJBXX.class.getName();
		}
		
		Object idValue = ServletActionContext.getContext().getSession().get("JumpMXLevel");
		if(idValue!=null){
			String tmpIdValue = idValue.toString();
			Object JKRGKPosition = ServletActionContext.getContext().getSession().get(viewDTOName+".JKRGKPosition");
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			Map<String, String> targetFieldNameAndValue = new HashMap<String, String>();
			String targetTableName = null;
			
			if(tmpIdValue.startsWith(AutoDTO_QY_JJXX.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_DKYW_ZQXX.class.getName())){
					AutoDTO_QY_JJXX autoDTO_QY_JJXX = (AutoDTO_QY_JJXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_JJXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_JJXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("JJBH", autoDTO_QY_JJXX.getJJBH());
					targetTableName = AutoDTO_QY_DKYW_ZQXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_HKXX.class.getName())){
					AutoDTO_QY_JJXX autoDTO_QY_JJXX = (AutoDTO_QY_JJXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_JJXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_JJXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("JJBH", autoDTO_QY_JJXX.getJJBH());
					targetTableName = AutoDTO_QY_HKXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_BLXDZCCL_JC.class.getName())){
					AutoDTO_QY_JJXX autoDTO_QY_JJXX = (AutoDTO_QY_JJXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_JJXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_JJXX.class.getName(), "") ,null});
					if(JKRGKPosition == null || JKRGKPosition.equals("false")){
						targetFieldNameAndValue.put("DKKBM", autoDTO_QY_JJXX.getFOREIGNID().getDKKBM());
					}else{
						targetFieldNameAndValue.put("DKKBM", "");
					}
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_JJXX.getFOREIGNID().getJRJGDM());
					targetFieldNameAndValue.put("YWBH", autoDTO_QY_JJXX.getJJBH());
					targetTableName = AutoDTO_QY_BLXDZCCL_JC.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_HKXX.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_DKYW_ZQXX.class.getName())){
					AutoDTO_QY_HKXX autoDTO_QY_HKXX = (AutoDTO_QY_HKXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_HKXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_HKXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("JJBH", autoDTO_QY_HKXX.getJJBH());
					targetTableName = AutoDTO_QY_DKYW_ZQXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_JJXX.class.getName())){
					AutoDTO_QY_HKXX autoDTO_QY_HKXX = (AutoDTO_QY_HKXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_HKXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_HKXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("JJBH", autoDTO_QY_HKXX.getJJBH());
					targetTableName = AutoDTO_QY_JJXX.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_DKYW_ZQXX.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_HKXX.class.getName())){
					AutoDTO_QY_DKYW_ZQXX autoDTO_QY_DKYW_ZQXX = (AutoDTO_QY_DKYW_ZQXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKYW_ZQXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_DKYW_ZQXX.class.getName(), "") ,null});
					ReflectOperation.setFieldValue(RequestManager.getTOject(), "JJBH",autoDTO_QY_DKYW_ZQXX.getJJBH());
					SessionManager.setTCondition(RequestManager.getTOject(), AutoDTO_QY_HKXX.class.getName());
					targetFieldNameAndValue.put("JJBH", autoDTO_QY_DKYW_ZQXX.getJJBH());
					targetTableName = AutoDTO_QY_HKXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_JJXX.class.getName())){
					AutoDTO_QY_DKYW_ZQXX autoDTO_QY_DKYW_ZQXX = (AutoDTO_QY_DKYW_ZQXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKYW_ZQXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_DKYW_ZQXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("JJBH", autoDTO_QY_DKYW_ZQXX.getJJBH());
					targetTableName = AutoDTO_QY_JJXX.class.getName();
				}
			}

			else if(tmpIdValue.startsWith(AutoDTO_QY_RZYWXX.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_MYRZ_ZQXX.class.getName())){
					AutoDTO_QY_RZYWXX autoDTO_QY_RZYWXX = (AutoDTO_QY_RZYWXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_RZYWXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_RZYWXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("RZYWBH", autoDTO_QY_RZYWXX.getRZYWBH());
					targetTableName = AutoDTO_QY_MYRZ_ZQXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_RZYWHKXX.class.getName())){
					AutoDTO_QY_RZYWXX autoDTO_QY_RZYWXX = (AutoDTO_QY_RZYWXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_RZYWXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_RZYWXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("RZYWBH", autoDTO_QY_RZYWXX.getRZYWBH());
					targetTableName = AutoDTO_QY_RZYWHKXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_BLXDZCCL_JC.class.getName())){
					AutoDTO_QY_RZYWXX autoDTO_QY_RZYWXX = (AutoDTO_QY_RZYWXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_RZYWXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_RZYWXX.class.getName(), "") ,null});
					if(JKRGKPosition == null || JKRGKPosition.equals("false")){
						targetFieldNameAndValue.put("DKKBM", autoDTO_QY_RZYWXX.getFOREIGNID().getDKKBM());
					}else{
						targetFieldNameAndValue.put("DKKBM", "");
					}
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_RZYWXX.getFOREIGNID().getJRJGDM());
					targetFieldNameAndValue.put("YWBH", autoDTO_QY_RZYWXX.getRZYWBH());
					targetTableName = AutoDTO_QY_BLXDZCCL_JC.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_RZYWHKXX.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_MYRZ_ZQXX.class.getName())){
					AutoDTO_QY_RZYWHKXX autoDTO_QY_RZYWHKXX = (AutoDTO_QY_RZYWHKXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_RZYWHKXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_RZYWHKXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("RZYWBH", autoDTO_QY_RZYWHKXX.getRZYWBH());
					targetTableName = AutoDTO_QY_MYRZ_ZQXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_RZYWXX.class.getName())){
					AutoDTO_QY_RZYWHKXX autoDTO_QY_RZYWHKXX = (AutoDTO_QY_RZYWHKXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_RZYWHKXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_RZYWHKXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("RZYWBH", autoDTO_QY_RZYWHKXX.getRZYWBH());
					targetTableName = AutoDTO_QY_RZYWXX.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_MYRZ_ZQXX.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_RZYWXX.class.getName())){
					AutoDTO_QY_MYRZ_ZQXX autoDTO_QY_MYRZ_ZQXX = (AutoDTO_QY_MYRZ_ZQXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_MYRZ_ZQXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_MYRZ_ZQXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("RZYWBH", autoDTO_QY_MYRZ_ZQXX.getRZYWBH());
					targetTableName = AutoDTO_QY_RZYWXX.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_RZYWHKXX.class.getName())){
					AutoDTO_QY_MYRZ_ZQXX autoDTO_QY_MYRZ_ZQXX = (AutoDTO_QY_MYRZ_ZQXX)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_MYRZ_ZQXX.class.getName(), tmpIdValue.replace(AutoDTO_QY_MYRZ_ZQXX.class.getName(), "") ,null});
					targetFieldNameAndValue.put("RZYWBH", autoDTO_QY_MYRZ_ZQXX.getRZYWBH());
					targetTableName =  AutoDTO_QY_RZYWHKXX.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_DBXX_JC.class.getName())){
				AutoDTO_QY_DBXX_JC autoDTO_QY_DBXX_JC = (AutoDTO_QY_DBXX_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DBXX_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_DBXX_JC.class.getName(), "") ,null});
				String XDYWZL = autoDTO_QY_DBXX_JC.getXDYWZL();
				if(XDYWZL.equals("1")){
					targetTableName = AutoDTO_QY_DKYW_JC.class.getName();
					targetFieldNameAndValue.put("DKHTHM", autoDTO_QY_DBXX_JC.getZHTBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DBXX_JC.getJRJGDM());
					if(JKRGKPosition == null || JKRGKPosition.equals("false")){
						targetFieldNameAndValue.put("DKKBM", autoDTO_QY_DBXX_JC.getDKKBM());
					}else{
						targetFieldNameAndValue.put("DKKBM", "");
					}
				}
				else if(XDYWZL.equals("2")){
					targetTableName = AutoDTO_QY_BLYW_JC.class.getName();
					targetFieldNameAndValue.put("BLXYBH", autoDTO_QY_DBXX_JC.getZHTBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DBXX_JC.getJRJGDM());
				}
				else if(XDYWZL.equals("4")){
					targetTableName = AutoDTO_QY_MYRZ_JC.class.getName();
					targetFieldNameAndValue.put("RZXYBH", autoDTO_QY_DBXX_JC.getZHTBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DBXX_JC.getJRJGDM());
					if(JKRGKPosition == null || JKRGKPosition.equals("false")){
						targetFieldNameAndValue.put("DKKBM", autoDTO_QY_DBXX_JC.getDKKBM());
					}else{
						targetFieldNameAndValue.put("DKKBM", "");
					}
				}
				else if(XDYWZL.equals("5")){
					targetTableName = AutoDTO_QY_XYZYW_JC.class.getName();
					targetFieldNameAndValue.put("XYZHM", autoDTO_QY_DBXX_JC.getZHTBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DBXX_JC.getJRJGDM());
				}
				else if(XDYWZL.equals("6")){
					targetTableName = AutoDTO_QY_BHYW_JC.class.getName();
					targetFieldNameAndValue.put("BHHM", autoDTO_QY_DBXX_JC.getZHTBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DBXX_JC.getJRJGDM());
				}
				else if(XDYWZL.equals("7")){
					targetTableName = AutoDTO_QY_YHCDHP_JC.class.getName();
					targetFieldNameAndValue.put("HPHM", autoDTO_QY_DBXX_JC.getZHTBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DBXX_JC.getJRJGDM());
				}
			}

			else if(tmpIdValue.startsWith(AutoDTO_QY_DKXX_JC.class.getName())){
				AutoDTO_QY_DKXX_JC autoDTO_QY_DKXX_JC = (AutoDTO_QY_DKXX_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKXX_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_DKXX_JC.class.getName(), "") ,null});
				if(RequestManager.getTName().equals(AutoDTO_QY_BLXDZCCL_JC.class.getName())){
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DKXX_JC.getJRJGDM());
					targetFieldNameAndValue.put("YWBH", autoDTO_QY_DKXX_JC.getDKYWHM());
					targetTableName = AutoDTO_QY_BLXDZCCL_JC.class.getName();
				}else{
					Set<AutoDTO_QY_DKYWXX> XDYWZLSet = autoDTO_QY_DKXX_JC.getOneToMany_QY_DKYWXX();
					Iterator<AutoDTO_QY_DKYWXX> it = XDYWZLSet.iterator();
					while(it.hasNext()){
						AutoDTO_QY_DKYWXX autoDTO_QY_DKYWXX = (AutoDTO_QY_DKYWXX)it.next();
						targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DKXX_JC.getJRJGDM());
						if(RequestManager.getTName().equals(AutoDTO_QY_BHYW_JC.class.getName())){
							targetTableName = AutoDTO_QY_BHYW_JC.class.getName();
							targetFieldNameAndValue.put("BHHM", autoDTO_QY_DKYWXX.getYYWH());
						}
						else if(RequestManager.getTName().equals(AutoDTO_QY_YHCDHP_JC.class.getName())){
							targetTableName = AutoDTO_QY_YHCDHP_JC.class.getName();
							targetFieldNameAndValue.put("HPHM", autoDTO_QY_DKYWXX.getYYWH());
						}	
						else if(RequestManager.getTName().equals(AutoDTO_QY_XYZYW_JC.class.getName())){
							targetTableName = AutoDTO_QY_XYZYW_JC.class.getName();
							targetFieldNameAndValue.put("XYZHM", autoDTO_QY_DKYWXX.getYYWH());
						}
						else if(RequestManager.getTName().equals(AutoDTO_QY_BLYW_JC.class.getName())){
							targetTableName = AutoDTO_QY_BLYW_JC.class.getName();
							targetFieldNameAndValue.put("BLXYBH", autoDTO_QY_DKYWXX.getYYWH());
						}
						break;
					}
				}
			}

			else if(tmpIdValue.startsWith(AutoDTO_QY_BHYW_JC.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_DKXX_JC.class.getName())){
					AutoDTO_QY_BHYW_JC autoDTO_QY_BHYW_JC = (AutoDTO_QY_BHYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_BHYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_BHYW_JC.class.getName(), "") ,null});
	//					targetFieldNameAndValue.put("AutoDTO_QY_DKYWXX.YYWH", autoDTO_QY_BHYW_JC.getBHHM());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_BHYW_JC.getJRJGDM());
					targetTableName = AutoDTO_QY_DKXX_JC.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_DBXX_JC.class.getName())){
					AutoDTO_QY_BHYW_JC autoDTO_QY_BHYW_JC = (AutoDTO_QY_BHYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_BHYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_BHYW_JC.class.getName(), "") ,null});
					targetFieldNameAndValue.put("ZHTBH", autoDTO_QY_BHYW_JC.getBHHM());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_BHYW_JC.getJRJGDM());
					targetFieldNameAndValue.put("XDYWZL", "6");
					targetTableName = AutoDTO_QY_DBXX_JC.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_YHCDHP_JC.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_DKXX_JC.class.getName())){
					AutoDTO_QY_YHCDHP_JC autoDTO_QY_YHCDHP_JC = (AutoDTO_QY_YHCDHP_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_YHCDHP_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_YHCDHP_JC.class.getName(), "") ,null});
	//					targetFieldNameAndValue.put("AutoDTO_QY_DKYWXX.YYWH", autoDTO_QY_YHCDHP_JC.getHPHM());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_YHCDHP_JC.getJRJGDM());
					targetTableName = AutoDTO_QY_DKXX_JC.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_DBXX_JC.class.getName())){
					AutoDTO_QY_YHCDHP_JC autoDTO_QY_YHCDHP_JC = (AutoDTO_QY_YHCDHP_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_YHCDHP_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_YHCDHP_JC.class.getName(), "") ,null});
					targetFieldNameAndValue.put("ZHTBH", autoDTO_QY_YHCDHP_JC.getHPHM());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_YHCDHP_JC.getJRJGDM());
					targetFieldNameAndValue.put("XDYWZL", "7");
					targetTableName = AutoDTO_QY_DBXX_JC.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_XYZYW_JC.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_DKXX_JC.class.getName())){
					AutoDTO_QY_XYZYW_JC autoDTO_QY_XYZYW_JC = (AutoDTO_QY_XYZYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_XYZYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_XYZYW_JC.class.getName(), "") ,null});
	//					targetFieldNameAndValue.put("AutoDTO_QY_DKYWXX.YYWH", autoDTO_QY_XYZYW_JC.getXYZHM());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_XYZYW_JC.getJRJGDM());
					targetTableName = AutoDTO_QY_DKXX_JC.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_DBXX_JC.class.getName())){
					AutoDTO_QY_XYZYW_JC autoDTO_QY_XYZYW_JC = (AutoDTO_QY_XYZYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_XYZYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_XYZYW_JC.class.getName(), "") ,null});
					targetFieldNameAndValue.put("ZHTBH", autoDTO_QY_XYZYW_JC.getXYZHM());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_XYZYW_JC.getJRJGDM());
					targetFieldNameAndValue.put("XDYWZL", "5");
					targetTableName = AutoDTO_QY_DBXX_JC.class.getName();
				}
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_BLYW_JC.class.getName())){
				if(RequestManager.getTName().equals(AutoDTO_QY_DKXX_JC.class.getName())){
					AutoDTO_QY_BLYW_JC autoDTO_QY_BLYW_JC = (AutoDTO_QY_BLYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_BLYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_BLYW_JC.class.getName(), "") ,null});
	//					targetFieldNameAndValue.put("AutoDTO_QY_DKYWXX.YYWH", autoDTO_QY_BLYW_JC.getBLXYBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_BLYW_JC.getJRJGDM());
					targetTableName = AutoDTO_QY_DKXX_JC.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_DBXX_JC.class.getName())){
					AutoDTO_QY_BLYW_JC autoDTO_QY_BLYW_JC = (AutoDTO_QY_BLYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_BLYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_BLYW_JC.class.getName(), "") ,null});
					targetFieldNameAndValue.put("ZHTBH", autoDTO_QY_BLYW_JC.getBLXYBH());
					targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_BLYW_JC.getJRJGDM());
					targetFieldNameAndValue.put("XDYWZL", "2");
					targetTableName = AutoDTO_QY_DBXX_JC.class.getName();
				}
			}

			else if(tmpIdValue.startsWith(AutoDTO_QY_BLXDZCCL_JC.class.getName())){
				AutoDTO_QY_BLXDZCCL_JC autoDTO_QY_BLXDZCCL_JC = (AutoDTO_QY_BLXDZCCL_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_BLXDZCCL_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_BLXDZCCL_JC.class.getName(), "") ,null});
				targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_BLXDZCCL_JC.getJRJGDM());
				if(RequestManager.getTName().equals(AutoDTO_QY_DKXX_JC.class.getName())){
					targetFieldNameAndValue.put("DKYWHM", autoDTO_QY_BLXDZCCL_JC.getYWBH());
					targetTableName = AutoDTO_QY_DKXX_JC.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_DKYW_JC.class.getName())){
					if(JKRGKPosition == null || JKRGKPosition.equals("false")){
						targetFieldNameAndValue.put("DKKBM", autoDTO_QY_BLXDZCCL_JC.getDKKBM());
					}else{
						targetFieldNameAndValue.put("DKKBM", "");
					}
					targetTableName = AutoDTO_QY_DKYW_JC.class.getName();
				}
				else if(RequestManager.getTName().equals(AutoDTO_QY_MYRZ_JC.class.getName())){
					if(JKRGKPosition == null || JKRGKPosition.equals("false")){
						targetFieldNameAndValue.put("DKKBM", autoDTO_QY_BLXDZCCL_JC.getDKKBM());
					}else{
						targetFieldNameAndValue.put("DKKBM", "");
					}
					targetTableName = AutoDTO_QY_MYRZ_JC.class.getName();
				}
			}

			else if(tmpIdValue.startsWith(AutoDTO_QY_DKYW_JC.class.getName()) && RequestManager.getTName().equals(AutoDTO_QY_DBXX_JC.class.getName())){
				AutoDTO_QY_DKYW_JC autoDTO_QY_DKYW_JC = (AutoDTO_QY_DKYW_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKYW_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_DKYW_JC.class.getName(), "") ,null});
				targetFieldNameAndValue.put("ZHTBH", autoDTO_QY_DKYW_JC.getDKHTHM());
				targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_DKYW_JC.getJRJGDM());
				if(JKRGKPosition == null || JKRGKPosition.equals("false")){
					targetFieldNameAndValue.put("DKKBM", autoDTO_QY_DKYW_JC.getDKKBM());
				}else{
					targetFieldNameAndValue.put("DKKBM", "");
				}
				targetFieldNameAndValue.put("XDYWZL", "1");
				targetTableName = AutoDTO_QY_DBXX_JC.class.getName();
			}
			else if(tmpIdValue.startsWith(AutoDTO_QY_MYRZ_JC.class.getName()) && RequestManager.getTName().equals(AutoDTO_QY_DBXX_JC.class.getName())){
				AutoDTO_QY_MYRZ_JC autoDTO_QY_MYRZ_JC = (AutoDTO_QY_MYRZ_JC)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_MYRZ_JC.class.getName(), tmpIdValue.replace(AutoDTO_QY_MYRZ_JC.class.getName(), "") ,null});
				targetFieldNameAndValue.put("ZHTBH", autoDTO_QY_MYRZ_JC.getRZXYBH());
				targetFieldNameAndValue.put("JRJGDM", autoDTO_QY_MYRZ_JC.getJRJGDM());
				if(JKRGKPosition == null || JKRGKPosition.equals("false")){
					targetFieldNameAndValue.put("DKKBM", autoDTO_QY_MYRZ_JC.getDKKBM());
				}else{
					targetFieldNameAndValue.put("DKKBM", "");
				}
				targetFieldNameAndValue.put("XDYWZL", "4");
				targetTableName = AutoDTO_QY_DBXX_JC.class.getName();
			}
			
			if(targetFieldNameAndValue!=null && targetTableName != null){
				for (String key : targetFieldNameAndValue.keySet()) {
					ReflectOperation.setFieldValue(RequestManager.getTOject(), key, targetFieldNameAndValue.get(key));
				}
				SessionManager.setTCondition(RequestManager.getTOject(), targetTableName);
			}
			ServletActionContext.getContext().getSession().put("JumpMXLevel", null);
		}
	}
}
