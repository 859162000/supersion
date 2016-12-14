package dbgssystem.actions.imps;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dbgssystem.actions.interfaces.IRemoteService;

public abstract class BaseRmiService extends UnicastRemoteObject implements IRemoteService{
	
	private static final long serialVersionUID = 1L;

	protected BaseRmiService() throws RemoteException {
		super();
	}

}
