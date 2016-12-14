package ncr.service.add;

import java.util.Map;

import framework.services.add.DefaultValueAdd;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.LogicParamManager;

public class NCRDropExtendAdd   extends DefaultValueAdd{
	
	public NCRDropExtendAdd(){
		super();
	}
	
	public NCRDropExtendAdd(Object baseObject){
		super(baseObject);
	}
    
	private Map<String,String> defaultValueMap;
	
	@Override
	public void Add() throws Exception {
		// TODO Auto-generated method stub
		this.defaultValueMap = LogicParamManager.getDefaultValueMap();
		System.out.println(123);
		super.Add();
		
	}

}
