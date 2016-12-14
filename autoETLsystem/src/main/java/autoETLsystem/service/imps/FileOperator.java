package autoETLsystem.service.imps;

import java.util.List;
import java.util.Map;



public abstract class FileOperator {
	
	protected Map<String,Object> context;
	public abstract List<String> GetFileList(String dirPath) throws Exception;
	public abstract void Del(String dirPath,List<String> delFileList) throws Exception;
	public void setContext(Map<String,Object> context)
	{
		this.context=context;
	}
	
	
}
