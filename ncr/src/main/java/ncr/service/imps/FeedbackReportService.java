package ncr.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandlerForText;
import framework.show.ShowContext;


public class FeedbackReportService extends BaseService{

	@Override
	public void initSuccessResult() throws Exception {

		super.initSuccessResult();

		String path = RequestManager.getUploadFile().getPath();
		List<Field> fieldList = new ArrayList<Field>();
		String tableName = RequestManager.getReportCheckParam().get("strRadio");
		
		/*String tableKey = RequestManager.getReportCheckParam().get("strRadio");
		Map<String, String> feedbackReportTableMap = ShowContext.getInstance().getShowEntityMap().get("feedbackReportTable");
		String tableName = feedbackReportTableMap.get(tableKey);*/
		
		int lines = 0;
		
		if(tableName != null){
			Class<?> type = Class.forName(tableName);
			Field[] fields = ReflectOperation.getReflectFields(type);
			
			for (int i = 0; i < fields.length; i++) {
				if(!fields[i].getName().toUpperCase().equals("AUTOID") && !fields[i].getName().toUpperCase().equals("SERIALVERSIONUID")){
					fieldList.add(fields[i]);
				}
			}
			try{
				IParamVoidResultExecute dao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
				dao.paramVoidResultExecute(new Object[]{detachedCriteria, null});
				
				lines = new FileHandlerForText().WriteFromPathToDatabase(null, tableName, fieldList, path, "\t", 10000, "UTF-8");
			}catch (Exception e) {
				e.printStackTrace();
				this.setServiceResult(new MessageResult(false,"导入文件失败"));
			}
		}
		this.setServiceResult(new MessageResult(true,"共导入数据:"+lines+"行"));
	}
}
