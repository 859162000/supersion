package ncr.service.procese;

import java.util.ArrayList;
import java.util.List;

import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

public class NcrNewCopySaveProcess implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		// TODO Auto-generated method stub
		ShowSaveOrUpdate saveOrUpdate=(ShowSaveOrUpdate)serviceResult;
		List<ShowOperation> operationList = saveOrUpdate.getOperationList();
		ArrayList<ShowOperation> list = new ArrayList<ShowOperation>();
		for (ShowOperation showOperation : operationList) {
			if(showOperation.getOperationName().equals("复制保存")){
				String name = showOperation.getOperationAction();
				showOperation.setOperationAction("NCR"+name);
			}
			list.add(showOperation);
		}
		saveOrUpdate.setOperationList(list);
		return saveOrUpdate;
	}

}
