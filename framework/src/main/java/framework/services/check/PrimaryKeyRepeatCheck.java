package framework.services.check;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class PrimaryKeyRepeatCheck  extends BaseConstructor implements ICheck{

	public PrimaryKeyRepeatCheck(){
		super();
	}
	
	public PrimaryKeyRepeatCheck(Object baseObject){
		super(baseObject);
	}
	
	public MessageResult Check() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		Object tObject = this.getBaseObject();
		Field primaryKeyField = ReflectOperation.getPrimaryKeyField(tObject.getClass());
		ShowInstance showInstance = ReflectOperation.getShowInstance(tObject.getClass(), ShowParamManager.getShowInstanceName());
		String[] strSpecialChar=new String[]{"～","！","◎","＃","￥","％","……","※","（","）","*","——","×","＋","＝","：",
											 ",","“","”","？","《","》","，","。","、","~","!","@","#","$","%","^","&","+",
											 "\\","/",";","[","]","{","}","\"",":","?","<",">","|"
				                             };
		
		//\/:*?"<>|
		String fieldValue=null;
		Object objFieldVal=null;
		Map<Field,ShowField> mapStrField=getStrField(tObject.getClass(),showInstance.getShowFieldList());
		for(Field f:mapStrField.keySet()){
			if(!f.isAnnotationPresent(GeneratedValue.class)
					&&!ReflectOperation.isSpecialCharCheck(f) && !f.isAnnotationPresent(Transient.class)){
				objFieldVal=ReflectOperation.getFieldValue(tObject,f.getName());
				if(objFieldVal!=null)
				{
					fieldValue=objFieldVal.toString();
					if(fieldValue!="")
					{
						String strArr="";
						boolean checkFail=false;
						 for(int i=0;i<strSpecialChar.length;i++){
							 if(fieldValue.indexOf(strSpecialChar[i])>-1){
								 strArr+="\\"+strSpecialChar[i]+" ";//解决界面提示不能输出\问题
								 checkFail=true;
							}
						 }
						 if(checkFail)
						 {
							 messageResult.setSuccess(false);
							 messageResult.getErrorFieldList().add(new MessageResult.ErrorField(f.getName(), MessageResult.COLORRED,mapStrField.get(f).getFieldShowName()+"不能包含特殊字符"+strArr));
						 }
						 
					}
					
				}
		   }
	   }
		
		
		
		
		if(!primaryKeyField.isAnnotationPresent(GeneratedValue.class)){
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			RequestManager.setId(ReflectOperation.getFieldValue(tObject, primaryKeyField.getName()));
			
			String fieldShowName=primaryKeyField.getName();
			for(ShowField sf:showInstance.getShowFieldList())
			{
				if(sf.getFieldName().equals(fieldShowName))
				{
					fieldShowName=sf.getFieldShowName();
				}
			}
			
			
	         if(singleObjectFindByIdDao.paramObjectResultExecute(null) != null){
				 messageResult.setSuccess(false);
				 messageResult.getErrorFieldList().add(new MessageResult.ErrorField(primaryKeyField.getName(), MessageResult.COLORRED,"主键："+fieldShowName+"不能重复"));
			 }
	         fieldValue = (String) ReflectOperation.getFieldValue(tObject,primaryKeyField.getName());
	         byte[] buf = fieldValue.toString().getBytes();
	    		boolean haveChn = false;
	    		for (byte b : buf) {
	    			if(b<0){
	    				haveChn = true;
	    				break;
	    			}
	    		}
    		if(haveChn){
    			 messageResult.setSuccess(false);
				 messageResult.getErrorFieldList().add(new MessageResult.ErrorField(primaryKeyField.getName(), MessageResult.COLORRED,"主键："+fieldShowName+"不能为中文"));
    		}
			
    	}
		return messageResult;
    }
	
	private  Map<Field,ShowField> getStrField(Class<?> type,List<ShowField> lstShowField){
		 Map<Field,ShowField> mapStrField=new HashMap<Field,ShowField>();
		Field[] fields = type.getDeclaredFields();
		for(ShowField sf:lstShowField)
		{
			for(Field f:fields){
				if(f.getType()==String.class 
						&& f.getName().equals(sf.getFieldName())){
					mapStrField.put(f, sf);
					break;
				}
			}
		}
				
		return mapStrField;
		
	}
}

