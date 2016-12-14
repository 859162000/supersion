package szzxpt.service.procese;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zxptsystem.dto.AutoDTO_GR_DBXX;
import zxptsystem.dto.AutoDTO_GR_GRSFXX;
import zxptsystem.dto.AutoDTO_GR_GRXX_JC;
import zxptsystem.dto.AutoDTO_GR_JZDZ;
import zxptsystem.dto.AutoDTO_GR_ZYXX;
import zxptsystem.dto.AutoDTO_JG_GGJZYGXR;
import zxptsystem.dto.AutoDTO_JG_ZYGD;
import zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC;
import zxptsystem.dto.AutoDTO_QY_CZZBGC;
import zxptsystem.dto.AutoDTO_QY_FRDBJZQYCY;
import zxptsystem.dto.AutoDTO_QY_GJGLRQK;
import zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRDYHTXX;
import zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX;
import zxptsystem.dto.UserModel;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;

public class SaveOrUpdateUserModelProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception{
		
		String tName = RequestManager.getTName();
		Object object=RequestManager.getTOject();
		List<Field> fieldList=ReflectOperation.getColumnFieldList(tName);
		//Set<String> fieldnameList=new LinkedHashSet<String>();
//		for(Field field : fieldList){
//			fieldnameList.add(field.getName());
//			if(field.getName().indexOf("ZJHM")>=0){
//				fieldnameList.add(field.getName());
//			}
//		}
		
		Map<String,String> nameList=new LinkedHashMap<String,String>();
		nameList.put(AutoDTO_GR_DBXX.class.getName(),"ZJLX,ZJHM,XM");
		nameList.put(AutoDTO_GR_GRXX_JC.class.getName(),"ZJLX,ZJHM,XM");
		nameList.put(AutoDTO_QY_FRDBJZQYCY.class.getName(),"ZJLX,ZJHM,JZCYXM");
		nameList.put(AutoDTO_QY_CZZBGC.class.getName(),"ZJLX,ZJHM,CZF");
		nameList.put(AutoDTO_QY_GJGLRQK.class.getName(),"ZJLX,ZJHM,GGRYXM");
		nameList.put(AutoDTO_QY_ZRRBZHTXX.class.getName(),"ZJLX,ZJHM,BZRMC");
		nameList.put(AutoDTO_QY_ZRRDYHTXX.class.getName(),"ZJLX,ZJHM,DYRMC");
		nameList.put(AutoDTO_QY_ZRRZYHTXX.class.getName(),"ZJLX,ZJHM,CZRMC");
		nameList.put(AutoDTO_JG_GGJZYGXR.class.getName(),"GGZJLX,GGZJHM,GGXM");
		nameList.put(AutoDTO_JG_ZYGD.class.getName(),"GDZJLX,GDZJHM,GDMC");
		nameList.put(AutoDTO_JZ_JZCYXX_JC.class.getName(),"ZYGXRZJLX,ZJHM_1,ZYGXRXM,JZCYZJLX,ZJHM_2,JZCYXM");
		nameList.put(AutoDTO_GR_GRSFXX.class.getName(),"Other");
		nameList.put(AutoDTO_GR_JZDZ.class.getName(),"Other");
		nameList.put(AutoDTO_GR_ZYXX.class.getName(),"Other");
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		List<Field> userModelList=ReflectOperation.getColumnFieldList(UserModel.class);
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
		if(nameList.containsKey(tName)){
			String[] fieldName=nameList.get(tName).split(",");
			if(fieldName.length==1 && tName.equals(AutoDTO_GR_GRSFXX.class.getName())){
				Object FOREIGNID=ReflectOperation.getFieldValue(object, "FOREIGNID");
				Object valueZJHM=ReflectOperation.getFieldValue(FOREIGNID, "ZJHM");
				Object valueZJLX=ReflectOperation.getFieldValue(FOREIGNID, "ZJLX");
				UserModel userModel  = (UserModel)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserModel.class.getName(),valueZJHM,null}); 
				boolean isExist=true;
				if(userModel==null){
					isExist=false;
					 userModel =new UserModel();
					 ReflectOperation.setFieldValue(userModel, "ZJHM",valueZJHM);
				}
				if(valueZJLX!=null){
					 ReflectOperation.setFieldValue(userModel, "ZJLX",valueZJLX);
				}
				for(Field field :fieldList){
					for(Field userfield :userModelList){
						if(field.getName().equals(userfield.getName())){
							Object value=ReflectOperation.getFieldValue(object, field.getName());
							if(value !=null){
								ReflectOperation.setFieldValue(userModel, field.getName(),value);
							}
							break;
						}
					}
				}
				if(valueZJLX!=null && valueZJLX.equals("0") && valueZJHM!=null && (String.valueOf(valueZJHM.toString().length()).equals("15") || String.valueOf(valueZJHM.toString().length()).equals("18"))){
					Object valueSex=null;
					Object valueBirth=null;
					if(String.valueOf(valueZJHM.toString().length()).equals("15")){
						valueSex=(TypeParse.parseInt(valueZJHM.toString().substring(14))%2);
						if(valueSex.equals("0")){
							valueSex="2";
						}
						valueBirth="19"+valueZJHM.toString().substring(6, 12);
					}
					else{
						valueSex=(TypeParse.parseInt(valueZJHM.toString().substring(16,17))%2);
						if(valueSex.equals("0")){
							valueSex="2";
						}
						valueBirth=valueZJHM.toString().substring(6, 14);
					}
					ReflectOperation.setFieldValue(userModel, "XB",valueSex);
					ReflectOperation.setFieldValue(userModel, "CSRQ",valueBirth);
				}
				if(!isExist){
					singleObjectSaveDao.paramVoidResultExecute(new Object[]{userModel,null});
				}
				else{
					singleObjectUpdateDao.paramVoidResultExecute(new Object[] {userModel, null });
				}
				//POXM、POZJLX、POZJHM、POGZDW、POLXDH
				Object valuePOZJHM=ReflectOperation.getFieldValue(object, "POZJHM");
				Object valuePOZJLX=ReflectOperation.getFieldValue(object, "POZJLX");
				Object valuePOXM=ReflectOperation.getFieldValue(object, "POXM");
				Object valuePOGZDW=ReflectOperation.getFieldValue(object, "POGZDW");
				Object valuePOLXDH=ReflectOperation.getFieldValue(object, "POLXDH");
				
				userModel  = (UserModel)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserModel.class.getName(),valuePOZJHM,null}); 
				isExist=true;
				if(userModel==null){
					isExist=false;
					 userModel =new UserModel();
					 ReflectOperation.setFieldValue(userModel, "ZJHM",valuePOZJHM);
				}
				if(valuePOZJLX!=null){
					 ReflectOperation.setFieldValue(userModel, "ZJLX",valuePOZJLX);
				}
				if(valuePOXM!=null){
					 ReflectOperation.setFieldValue(userModel, "XM",valuePOXM);
				}
				if(valuePOGZDW !=null){
					 ReflectOperation.setFieldValue(userModel, "DWMC",valuePOGZDW);
				}
				if(valuePOLXDH !=null){
					ReflectOperation.setFieldValue(userModel, "SJHM",valuePOGZDW);
				}
				if(valuePOZJLX!=null && valuePOZJLX.equals("0") && valuePOZJHM!=null && (String.valueOf(valuePOZJHM.toString().length()).equals("15") || String.valueOf(valuePOZJHM.toString().length()).equals("18"))){
					Object valueSex=null;
					Object valueBirth=null;
					if(String.valueOf(valuePOZJHM.toString().length()).equals("15")){
						valueSex=(TypeParse.parseInt(valuePOZJHM.toString().substring(14))%2);
						if(valueSex.equals("0")){
							valueSex="2";
						}
						valueBirth="19"+valuePOZJHM.toString().substring(6, 12);
					}
					else{
						valueSex=(TypeParse.parseInt(valuePOZJHM.toString().substring(16,17))%2);
						if(valueSex.equals("0")){
							valueSex="2";
						}
						valueBirth=valuePOZJHM.toString().substring(6, 14);
					}
					ReflectOperation.setFieldValue(userModel, "XB",valueSex);
					ReflectOperation.setFieldValue(userModel, "CSRQ",valueBirth);
				}
				if(!isExist){
					singleObjectSaveDao.paramVoidResultExecute(new Object[]{userModel,null});
				}
				else{
					singleObjectUpdateDao.paramVoidResultExecute(new Object[] {userModel, null });
				}
			}
			else if(fieldName.length==1){
				Object FOREIGNID=ReflectOperation.getFieldValue(object, "FOREIGNID");
				Object valueZJHM=ReflectOperation.getFieldValue(FOREIGNID, "ZJHM");
				Object valueZJLX=ReflectOperation.getFieldValue(FOREIGNID, "ZJLX");
				UserModel userModel  = (UserModel)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserModel.class.getName(),valueZJHM,null}); 
				boolean isExist=true;
				if(userModel==null){
					isExist=false;
					 userModel =new UserModel();
					 ReflectOperation.setFieldValue(userModel, "ZJHM",valueZJHM);
				}
				if(valueZJLX!=null){
					 ReflectOperation.setFieldValue(userModel, "ZJLX",valueZJLX);
				}
				for(Field field :fieldList){
					for(Field userfield :userModelList){
						if(field.getName().equals(userfield.getName())){
							Object value=ReflectOperation.getFieldValue(object, field.getName());
							if(value !=null){
								ReflectOperation.setFieldValue(userModel, field.getName(),value);
							}
							break;
						}
					}
				}
				if(valueZJLX!=null && valueZJLX.equals("0") && valueZJHM!=null && (String.valueOf(valueZJHM.toString().length()).equals("15") || String.valueOf(valueZJHM.toString().length()).equals("18"))){
					Object valueSex=null;
					Object valueBirth=null;
					if(String.valueOf(valueZJHM.toString().length()).equals("15")){
						valueSex=(TypeParse.parseInt(valueZJHM.toString().substring(14))%2);
						if(valueSex.equals("0")){
							valueSex="2";
						}
						valueBirth="19"+valueZJHM.toString().substring(6, 12);
					}
					else{
						valueSex=(TypeParse.parseInt(valueZJHM.toString().substring(16,17))%2);
						if(valueSex.equals("0")){
							valueSex="2";
						}
						valueBirth=valueZJHM.toString().substring(6, 14);
					}
					ReflectOperation.setFieldValue(userModel, "XB",valueSex);
					ReflectOperation.setFieldValue(userModel, "CSRQ",valueBirth);
				}
				if(!isExist){
					singleObjectSaveDao.paramVoidResultExecute(new Object[]{userModel,null});
				}
				else{
					singleObjectUpdateDao.paramVoidResultExecute(new Object[] {userModel, null });
				}
			}
			else{

				for(int i=0;i<fieldName.length;i=i+3){
					Object valueZJHM=ReflectOperation.getFieldValue(object, fieldName[i+1]);
					if(valueZJHM !=null){
						UserModel userModel  = (UserModel)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserModel.class.getName(),valueZJHM,null}); 
						boolean isExist=true;
						if(userModel==null){
							isExist=false;
							userModel =new UserModel();
							ReflectOperation.setFieldValue(userModel,"ZJHM",valueZJHM);
						}

						Object valueZJLX=ReflectOperation.getFieldValue(object, fieldName[i]);
						Object valueXM=ReflectOperation.getFieldValue(object, fieldName[i+2]);
						if(valueZJLX!=null){
							ReflectOperation.setFieldValue(userModel, "ZJLX",valueZJLX);
						}
						if(valueXM!=null){
							ReflectOperation.setFieldValue(userModel, "XM",valueXM);
						}
						if(valueZJLX!=null && valueZJLX.equals("0") && valueZJHM!=null && (String.valueOf(valueZJHM.toString().length()).equals("15") || String.valueOf(valueZJHM.toString().length()).equals("18"))){
							Object valueSex=null;
							Object valueBirth=null;
							if(String.valueOf(valueZJHM.toString().length()).equals("15")){
								valueSex=(TypeParse.parseInt(valueZJHM.toString().substring(14))%2);
								if(valueSex.equals("0")){
									valueSex="2";
								}
								valueBirth="19"+valueZJHM.toString().substring(6, 12);
							}
							else{
								valueSex=(TypeParse.parseInt(valueZJHM.toString().substring(16,17))%2);
								if(valueSex.equals("0")){
									valueSex="2";
								}
								valueBirth=valueZJHM.toString().substring(6, 14);
							}
							ReflectOperation.setFieldValue(userModel, "XB",valueSex);
							ReflectOperation.setFieldValue(userModel, "CSRQ",valueBirth);
						}
						if(!isExist){
							singleObjectSaveDao.paramVoidResultExecute(new Object[]{userModel,null});
						}
						else{
							singleObjectUpdateDao.paramVoidResultExecute(new Object[] {userModel, null });
						}
					}
					
					
				}
				
			}
		}
		
		
		return serviceResult;
	}
}
