package framework.services.add;

import java.lang.reflect.Field;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.SessionManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;

// 添加上一级对象信息作为下一级的检索条件
public class LevelDataAdd  extends BaseConstructor  implements IAdd{
	
	public LevelDataAdd(){
		super();
	}
	
	public LevelDataAdd(Object baseObject){
		super(baseObject);
	}

	public void Add() throws Exception {

		if(SessionManager.getCurrentLevel() != null){
			String currentLevelLevel = SessionManager.getCurrentLevel();
			if(SessionManager.getLevelTName(currentLevelLevel) != null 
					&& SessionManager.getLevelIdValue(currentLevelLevel) != null){
				String previousLevelLevelTName  = SessionManager.getPreviousLevelTName();
				// 根据id构造上一级对象
				Object foreignObject = FrameworkFactory.CreateClass(previousLevelLevelTName);
				Field primaryField = ReflectOperation.getPrimaryKeyField(previousLevelLevelTName);
				ReflectOperation.setFieldValue(foreignObject, primaryField.getName(),
						SessionManager.getLevelIdValue(currentLevelLevel));
				Field[] fields = ReflectOperation.getReflectFields(Class.forName(this.getBaseObject().getClass().getName()));
				for(int j=0;j<fields.length;j++){
					if(fields[j].getType().equals(Class.forName(previousLevelLevelTName))){
						Object tObject = this.getBaseObject();
						ReflectOperation.setFieldValue(tObject, fields[j].getName(), foreignObject);
						break;
					}
				}
			}
		}
		
	}
}