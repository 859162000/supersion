package autoETLsystem.service.interfaces;

import java.util.List;

import autoETLsystem.dto.AutoETL_WorkflowParamMV;

public interface IActivityNodeForJavaExtend {
	String Execute(List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList,String strFixParameter) throws Exception;
}
