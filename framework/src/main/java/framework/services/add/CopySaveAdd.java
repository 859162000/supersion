package framework.services.add;

import framework.helper.ReflectOperation;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;

public class CopySaveAdd extends BaseConstructor implements IAdd{

	public CopySaveAdd(){
		super();
	}
	
	public CopySaveAdd(Object baseObject){
		super(baseObject);
	}
	
	public void Add() throws Exception {
		ReflectOperation.setFieldValue(this.getBaseObject(), ReflectOperation.getPrimaryKeyField(this.getBaseObject().getClass()).getName(),"");
	}

}
