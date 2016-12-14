package coresystem.service.imps;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import coresystem.dto.RoleFunction;
import coresystem.dto.RoleInfo;
import framework.services.imps.SingleObjectExportDataService;

public class RoleFunctionExportDataService extends SingleObjectExportDataService{
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected  void  ExportData() throws Exception{

		Set<RoleInfo> roleInfo=new HashSet<RoleInfo>();
		List<RoleFunction> objRet =(List<RoleFunction>) getServiceResult(); // 根据条件过滤后的数据结果
		for(RoleFunction role : objRet){
			if(roleInfo.contains(role.getRoleInfo())){
				role.setRoleInfo(null);
			}
			else{
				roleInfo.add(role.getRoleInfo());
			}
		}
		super.ExportData();
	}
	

}
