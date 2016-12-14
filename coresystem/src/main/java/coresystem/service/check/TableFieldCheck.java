package coresystem.service.check;

import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class TableFieldCheck implements ICheck{

	public MessageResult Check() throws Exception {
		
        MessageResult messageResult = new MessageResult();

        Object tObject = RequestManager.getTOject();
        if(RequestManager.getActionName().equals("Save-extend.dto.ReportModel_Table")
        		|| RequestManager.getActionName().equals("Update-extend.dto.ReportModel_Table")) {
        	ReportModel_Table table = (ReportModel_Table)tObject;
        	if(!table.getStrTableName().matches("[a-zA-Z_$][a-zA-Z_0-9$]*")) {
        		messageResult.setSuccess(false);
    			messageResult.getMessageSet().add("实体表名不正确");
        	}
        }
        
        if(RequestManager.getActionName().equals("SaveLevel1-extend.dto.ReportModel_Field")
        		|| RequestManager.getActionName().equals("UpdateLevel1-extend.dto.ReportModel_Field")) {
        	ReportModel_Field field = (ReportModel_Field)tObject;
        	//ReportModel_Table table2 = field.getReportModel_Table();
        	
        	/*Object oldID = RequestManager.getId();
        	String oldTName = RequestManager.getTName();
        	RequestManager.setId(field.getReportModel_Table().getAutoTableID());
        	RequestManager.setTName("extend.dto.ReportModel_Table");
        	IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
        	ReportModel_Table table2 = (ReportModel_Table)singleObjectFindByIdDao.paramObjectResultExecute(null);
        	RequestManager.setId(oldID);
        	RequestManager.setTName(oldTName);
        	for(ReportModel_Field fieldTmp: table2.getReportModel_Fields()) {
        		// 检测同名字段
        		if( fieldTmp.getStrFieldName().equals(field.getStrFieldName())
        			&& !RequestManager.getActionName().equals("UpdateLevel1-extend.dto.ReportModel_Field")) {
        			messageResult.setSuccess(false);
        			messageResult.getMessageSet().add("表中已存在同名字段！");
        			break;
        		}
        		
        		// 检测序号
        		if(fieldTmp.getNSeq() == field.getNLength() ) {
        			messageResult.setSuccess(false);
        			messageResult.getMessageSet().add("表中已存在同名显示顺序字段！");
        			break;
        		}
        	}*/

        	// 主键不允许空
        	if(field.getIsKey().equals("1") && field.getStrEmptyType().equals("1")) {
        		messageResult.setSuccess(false);
    			messageResult.getMessageSet().add("主键字段，不允许为空;");
        	}
        	
        	if(!field.getStrFieldName().matches("[a-zA-Z_$][a-zA-Z_0-9$]*")) {
        		messageResult.setSuccess(false);
    			messageResult.getMessageSet().add("字段名不正确");
        	}
        }

		return messageResult;
	}

}
