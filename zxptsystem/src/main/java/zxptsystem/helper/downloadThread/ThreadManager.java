package zxptsystem.helper.downloadThread;




import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


public class ThreadManager implements Runnable{
	
	private static int maxThread = 3;
	private static int interval = 1000;
	private long startTime ;
	private long queryTime ;
	private long endTime ;

    private static ThreadManager threadManager = null;
	
	synchronized public static ThreadManager getInstance() throws Exception{
	    if (threadManager == null)  {
			threadManager = new ThreadManager();  
	    }
	    return threadManager;  
	}
	
	private ThreadManager() throws Exception{
		waitQueue = new LinkedList<Object>();
		runQueue = new LinkedList<Object>();
	}
	
	private boolean running;
	
	public boolean isRunning() {
		return running;
	}

	private void threadLogic(){
		try {
			Set<Thread> threadSet = new HashSet<Thread>();
			while(true){
				Object object = runQueue.poll();
				if(object == null){
					break;
				}
				
				ThreadLogic threadLogic = new ThreadLogic();
				threadLogic.setObject(object);
				Thread thread = new Thread(threadLogic);
				thread.start();
				threadSet.add(thread);
			}
				
			for(Thread thread : threadSet){
				thread.join();
			}
			for(Thread thread : threadSet){
				thread.interrupt();
			}
		}
		catch(Exception ex){
			//记录异常日志
			System.out.print(ex);
		}
	}
	
	private void businessLogic(){
		while(waitQueue.peek() != null){
			runQueue.add(waitQueue.poll());
			if(runQueue.size() > maxThread - 1){
				break;
			}
			if(waitQueue.peek() != null){
				if(businessLogicBreak()){
					break;
				}
			}
		}
	}
	
	private boolean businessLogicBreak(){
//		if((Integer)runQueue.peek() < (Integer)waitQueue.peek()){
//			return true;
//		}
//		else{
//			return false;
//		}
		return false;
	}
		
	private Queue<Object> waitQueue;
	
	private Queue<Object> runQueue;
	
	public void addWaitQueue(Object object){
		waitQueue.add(object);	
	}

	
	public void run() {
		while(true){
			try {
				Thread.sleep(interval);
				if(waitQueue.size() > 0){
					running = true;
					while(waitQueue.peek() != null){
						
						businessLogic();
						System.out.println("==========================================================================");
						System.out.print("等待队列总数量：" + String.valueOf(waitQueue.size()) + "，分别为：");
						for(Iterator<Object> iterator = waitQueue.iterator(); iterator.hasNext();){
							System.out.print(iterator.next() + "，");
						}
						System.out.println();
						System.out.print("运行队列总数量：" + String.valueOf(runQueue.size())+ "，分别为：");
						for(Iterator<Object> iterator = runQueue.iterator(); iterator.hasNext();){
							System.out.print(iterator.next() + "，");
						}
						System.out.println();
						threadLogic();
					}
					running = false;
				}
			} 
			catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		}
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
