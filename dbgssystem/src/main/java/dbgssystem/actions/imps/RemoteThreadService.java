package dbgssystem.actions.imps;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RemoteThreadService implements Runnable {

	@Override
	public void run() {
		try{
			RemoteServiceImpl impl = new RemoteServiceImpl();
			LocateRegistry.createRegistry(6600);			
			Naming.rebind("rmi://localhost:6600/RemoteService", impl);
			System.out.println("rmi service  running!......");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
