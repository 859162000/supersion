package dbgssystem.service.imps;

import framework.interfaces.IParamVoidResultExecute;
import framework.services.imps.BaseDTService;
import framework.services.interfaces.MessageResult;

public class SingleObjectReportDeleteByIdService extends BaseDTService{

	@Override
    public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		((IParamVoidResultExecute)this.getBaseDaoObject()).paramVoidResultExecute(null);
		this.setServiceResult(new MessageResult(this.getSuccessMessage()));
	}
}
