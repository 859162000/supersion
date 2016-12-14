package zxptsystem.service.add;

import java.lang.reflect.Field;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class XXJLGZBHSaveAdd implements IAdd{

	public void Add() throws Exception {
		if(RequestManager.getActionName().contains("AutoDTO_QY_")){
			Object tObjct = RequestManager.getTOject();
			if(tObjct != null){
				Field xxjlgzbh = ReflectOperation.getReflectField(tObjct.getClass(), "XXJLGZBH");
				if(xxjlgzbh != null){
					ReflectOperation.setFieldValue(tObjct, "XXJLGZBH", "00000000000000000000");
				}
			}
		}
	}

}
