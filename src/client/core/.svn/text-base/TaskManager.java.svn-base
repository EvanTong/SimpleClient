package client.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


import client.core.model.Dispatcher;
import client.core.model.Event;
import client.core.model.Task;


/**
 * <p> Task manager, singleton pattern </p>
 * @author amas
 */
public class TaskManager {
	private static TaskManager     sInstance                        = new TaskManager();
	
	private BlockingQueue<Task>    mTaskQueue                       = new PriorityBlockingQueue<Task>(32);
	private BlockingQueue<FutureTask<Event>> mPendingEventQueue     = new LinkedBlockingQueue<FutureTask<Event>>();
	private TaskDispather          mDispather                       = new TaskDispather(mTaskQueue);
	private PendingEventDispatcher mPendingEventDispatcher          = new PendingEventDispatcher(mPendingEventQueue);
	private ExecutorService        mTaskWorkers                     = Executors.newFixedThreadPool(1); // TODO: reuse dispatcher workers  
	
	
	class TaskDispather extends Dispatcher<Task> {
		public TaskDispather(BlockingQueue<Task> queue) {
			mQueue   = queue;
		    mWorkers = Executors.newFixedThreadPool(1); 	
		}
				
		@Override
		public Event onDispatch(Task elem) {
			FutureTask<Event> ftask = new FutureTask<Event>(elem); 
			watch(ftask);
			mTaskWorkers.submit(ftask);
			
/*			try {
				EventManager.I().push(ftask.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			return null; /* we do not need notify anything */
		}
	}
	
	class PendingEventDispatcher extends Dispatcher<FutureTask<Event>> {
		public PendingEventDispatcher(BlockingQueue<FutureTask<Event>> queue) {
			mQueue   = queue;
			mWorkers = Executors.newFixedThreadPool(1); 
		}
		
		@Override
		public Event onDispatch(FutureTask<Event> ftask) {
			try {
				Event event = ftask.get();
				System.out.println("GET : " + event);
				EventManager.I().push(event);
			} catch (InterruptedException e) {
				System.out.println("TaskManager.onDispatch");
				e.printStackTrace();
			} catch (ExecutionException e) {
				System.out.println("TaskManager.onDispatch");
				e.printStackTrace();
			} catch (Exception e){
				System.out.println("TaskManager.onDispatch");
				e.printStackTrace();
			}
			return null; /* we do not need notify anything */
		}
	}
		
	private TaskManager() {
		mDispather.start();
		mPendingEventDispatcher.start();
	}
	
	/** short for getInstance() */
	public static TaskManager I() {
		return  sInstance;
	}
	
	public void assign(Task task) {
		mDispather.assign(task);
	}
	
	public void watch(FutureTask<Event> ftask) {
		mPendingEventDispatcher.assign(ftask);
	}
	
	public synchronized void restart() {
		
	}
}
