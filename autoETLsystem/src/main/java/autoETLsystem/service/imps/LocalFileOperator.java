package autoETLsystem.service.imps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import autoETLsystem.dto.AutoETL_ActivityNodeForDeleteFile;
import autoETLsystem.dto.AutoETL_ActivityNodeForDeleteFileDetail;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

public class LocalFileOperator extends FileOperator {
	public List<String> GetFileList(String dirPath) throws Exception
	{
		File dir =new File(dirPath);  
		if(!dir .exists() || !dir.isDirectory()){
			throw new Exception("文件夹路径不存在");
		}
		List<String> allFileName = new ArrayList<String>();
		File[] files = dir.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				allFileName.add(files[i].getName());
			}
		}
		return allFileName;
	}

	
	public void Del(String dirPath,List<String> delFileList)
	{
		for (String strFileName : delFileList) {
			strFileName=dirPath+File.separator+strFileName;
			File file=new File(strFileName);
			if(file.exists()){
				file.delete();    
			}
		}
	}


	
}
