package zdzsystem.service.procese;

import java.util.ArrayList;
import java.util.List;

import framework.services.interfaces.IProcese;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

/**
 * 总对总隐藏复制保存按钮
 * @author Administrator
 *
 */
public class ZDZHiddenCopySaveButtenProcese implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		// TODO Auto-generated method stub
		ShowSaveOrUpdate showSaveOrUpdate=(ShowSaveOrUpdate)serviceResult;
		List<ShowOperation> operationList = showSaveOrUpdate.getOperationList();
		List<ShowOperation> newOperationList=new ArrayList<ShowOperation>();
		for (ShowOperation showOperation : operationList) {
			String operationName = showOperation.getOperationName();
			if(!operationName.equals("复制保存")){
				newOperationList.add(showOperation);
			}
			showSaveOrUpdate.setOperationList(newOperationList);
		}
		return showSaveOrUpdate;
	}

}
