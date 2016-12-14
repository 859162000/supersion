package zxptsystem.service.imps;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;

import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;

public class EtlExportPackageService implements IActivityNodeForJavaExtend {

	public String Execute(
			List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,String strFixParameter)
			throws Exception {
			String filePath="";
			String fileName="";
			String dircrory="";
			String dtDate="";
			for(AutoETL_WorkflowParamMV paramw : activeNodeETLWorkflowParamMVList){
				if(!String.valueOf(paramw.getIntGroup()).equals("1")){
					continue;
				}
				if(paramw.getAutoETL_Param().getStrParamName().toUpperCase().equals("FILEPATH")){
					filePath=paramw.getStrValue();
					if(filePath.endsWith("/")){
						filePath=filePath.substring(0,filePath.length()-1);
					}
				}
				else if(paramw.getAutoETL_Param().getStrParamName().toUpperCase().equals("DTDATE")){
					fileName="XXXXX_"+paramw.getStrValue()+".tar";
					dtDate=paramw.getStrValue();
				}
				else if(paramw.getAutoETL_Param().getStrParamName().toUpperCase().equals("DIRCRORY")){
					dircrory=paramw.getStrValue();
					if(dircrory.endsWith("/")){
						dircrory=dircrory.substring(0,dircrory.length()-1);
					}
				}
			}
			if(StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)  || StringUtils.isBlank(dircrory)){
				return "缺少必要的参数";
			}
			OutputStream out = null;
			TarInputStream in = new TarInputStream(new FileInputStream(new File(filePath+"/"+fileName)));
			TarEntry entry = null;
			dircrory=dircrory+"/"+dtDate+"/";
			while ((entry = in.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}
				File outfile = new File(dircrory + entry.getName());
				new File(outfile.getParent()).mkdirs();
				out = new BufferedOutputStream(new FileOutputStream(outfile));
				int x = 0;
				while ((x = in.read()) != -1) {
					out.write(x);
				}
				out.close();
			}
			in.close();
			return "";
	}



}
