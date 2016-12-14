package zxptsystem.service.procese;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.ReportModel_Table;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowList;
import framework.show.ShowOperation;
/**
 * 通过系统参数管理得到需要显示导入导出按钮的表
 * @author xiajieli
 * @since 20161110
 *
 */
public class IsShowImportAndExportDataButtonProcess  implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		// TODO Auto-generated method stub
		
		String tName=RequestManager.getTName();
		tName=tName.substring(tName.indexOf("AutoDTO_")+8, tName.length());
		String strTableName=null;
		String[]  strTableNames=null;
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object IsShowImpAndExpButton = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "IsShowImpAndExpButton" , null});
		 if(IsShowImpAndExpButton != null) {
			SystemParam systemParam = (SystemParam)IsShowImpAndExpButton;
			if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
				strTableName=systemParam.getStrParamValue().trim();
			}
		}
		
		if(!StringUtils.isBlank(strTableName) && strTableName.indexOf(",")>0){
			strTableNames=strTableName.split(",");
		}
		else{
			if(!StringUtils.isBlank(strTableName)){
				strTableNames=new String[1];
				strTableNames[0]=strTableName;
			}
		}
		
		ShowList showList=(ShowList)serviceResult;
		if(strTableNames!=null){
			
			List<ShowOperation> showOperationList =  showList.getOperationList();
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Table"));
			 detachedCriteria.add(Restrictions.eq("strTableName", tName));
			 List<ReportModel_Table> reportModel_Table = (List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			 if(reportModel_Table.size()>0 && !StringUtils.isBlank(reportModel_Table.get(0).getStrAddFields())){
				 if(reportModel_Table.get(0).getStrAddFields().equals("1")){//基础段
					 for (String str : strTableNames) {
						if(tName.equals(str)){
							
							ShowOperation showOperationImp = new ShowOperation();
							showOperationImp.setOperationName("导入");
							showOperationImp.setOperationType("Upload");
							showOperationImp.setOperationNamespace("framework");
							showOperationImp.setOperationAction("ForReportImportDataLevelAUTODTO-"+RequestManager.getTName());
							
							ShowOperation showOperationExp = new ShowOperation();
							showOperationExp.setOperationName("导出");
							showOperationExp.setOperationType("Post");
							showOperationExp.setOperationNamespace("framework");
							showOperationExp.setOperationAction("ExportAllDataLevelAUTODTO-"+RequestManager.getTName());
							
							showOperationList.add(showOperationImp);
							showOperationList.add(showOperationExp);
							break;
						}
					}
				 }
				 else{//明细段
					Field field=ReflectOperation.getFieldByName(RequestManager.getTName(), "FOREIGNID");
					for (String str : strTableNames) {
						if(field.getGenericType().equals(Class.forName("zxptsystem.dto.AutoDTO_"+str))){
							ShowOperation showOperationImp = new ShowOperation();
							showOperationImp.setOperationName("导入");
							showOperationImp.setOperationType("Upload");
							showOperationImp.setOperationNamespace("framework");
							showOperationImp.setOperationAction("ForReportImportDataLevelAutoDTOMX-"+RequestManager.getTName());
							
							ShowOperation showOperationExp = new ShowOperation();
							showOperationExp.setOperationName("导出");
							showOperationExp.setOperationType("Post");
							showOperationExp.setOperationNamespace("framework");
							showOperationExp.setOperationAction("MXExportAllDataLevelAutoDTOMX-"+RequestManager.getTName());
							
							showOperationList.add(showOperationImp);
							showOperationList.add(showOperationExp);
							break;
						}
					}
					
				 }
			 }
			
			showList.setOperationList(showOperationList);
		}
		return showList;
	}

}
