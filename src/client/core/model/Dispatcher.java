package client.core.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;



/**
 * <p>Dispacher using thread pool to dispatch element typed "T", you can
 * implement your dispatch logic through override onDispatch()</p>
 * @author amas
 *
 * @param <T> dispatched element type
 */
public abstract class Dispatcher<T> extends Thread implements IDispatch<T> {
	protected BlockingQueue<T>    mQueue     = null;
	protected ExecutorService     mWorkers   = null;
	private volatile boolean      mStop      = false;
		
	public Dispatcher() {
		
	}
	
	protected ExecutorService getWorkers() {
		return mWorkers;
	}
	
	protected BlockingQueue<T> getQueue() { 
		return mQueue;
	}
	
	public Event onDispatch(T elem) {
		return null;
	}

	// start a dispatch loop;
	public void run() {
		while(!mStop) {
			try {
				dispatchLoop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/** we do not recomand you override this method except you have no way to do something */
	protected void dispatchLoop() throws InterruptedException {
		T elem = getQueue().take();
		getWorkers().submit(new DispatchTask<T>(elem, this));
	}
		
	/** may not start at once */
	public void assign(T elem) {
		getQueue().add(elem);
	}
	
	/** Stop dispatch loop */
	public void shutdown() {
		getWorkers().shutdown();
		mStop = true;
	}	
}
