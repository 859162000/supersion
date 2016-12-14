package report.service.translate;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import report.dto.DownloadResource;
import report.helper.GetFilePath;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;


public class DeleteDownloadTranslate implements ITranslate{

	public void Translate() throws Exception {
		String[] idList=(String[])RequestManager.getIdList();
		for(int i=0;i<idList.length;i++){
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			DownloadResource DownloadResource = (DownloadResource)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{DownloadResource.class.getName(),idList[i],null});
			if(DownloadResource != null){
		        GetFilePath getFilePath = new GetFilePath();
				File file = new File(getFilePath.getDownloadResourcePath()+"/"+DownloadResource.getFileName());
				if(file.exists()){
					file.delete();
				}
			}
		}
	}
}
