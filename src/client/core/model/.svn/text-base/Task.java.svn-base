package client.core.model;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;


import client.core.model.TimeStamp.Tag;

/**
 * <p>
 * A task is atom which be processed by workers, the worker
 * process a task and finally get a process result which called
 * event.
 * Or the task described how to make event happen.</p>
 * @author amas
 * 1. ���Զ�ָ̬��task �����event�㲥���ĸ�listener��
 */
public class Task implements Callable<Event>, Comparable<Task> {
	//---------------------------------------------------------[ Members ]
	/**
	 *<p>A atomically incremented counters</p> 
	 * TODO: long ???
	 */
	public static AtomicInteger   sIncCounter = new AtomicInteger(0);
	
	/**
	 * <p> For trace task life time</p> 
	 */
	protected TimeStamp            mTimeStamp = new TimeStamp();
	
	/**
	 * <p>A integer value stands for priority of ready to run task</p>
	 */
	private int                       mWeight = sIncCounter.getAndIncrement();
	
	/**
	 * <p>A short description of task</p>
	 */
	private String                      mDesc = "";
	
	/**
	 * <p>Task Id</p>
	 */
	private String                       mId  = "";
	
	/**
	 * <p>Task object unique id<p>
	 */
	private int                          mUid = hashCode();
	
	private Notifiers                 mEventTo = new Notifiers();
	
	//---------------------------------------------------------[ Methods.Constructor ]
	public Task() {
		
	}
	
	public Task(String id) {
		mId  = id;
	}
	
	//---------------------------------------------------------[ Methods.public ]
	
	/**
	 * Get unique Id  
	 * @return
	 */
	protected int getUid() {
		return mUid;
	}
	
	public String getDesc() {
		return mDesc;
	}
	
	public void setDesc(String desc) {
		mDesc = desc;
	}
	
	public String getId() {
		return mId ;
	}
	
	public void setId(String id) {
		mId  = id;
	}
	
	public int getWeight() {
		return mWeight;
	}
	
	public void addEventTo(String lgUri) {
		mEventTo.addNotifyUri(lgUri);
	}
	
	/** There is no reason to set a task weight after the task new a instance */
/*	public void setWeight(int weight) {
		mWeight = weight;
	}*/
	
	final public Event call() throws java.io.IOException {
		mTimeStamp.touch(Tag.START_TIME);
		Event event = process(); 
		touch(event);
		mTimeStamp.touch(Tag.END_TIME);
		return event;
	}
	
	/**
	 * The result event to
	 * TODO: doc
	 * @param event
	 * @return
	 */
	protected boolean touch(Event event) {
		if(mEventTo == null || event == null) {
			return false;
		}
		
		//event.setFrom(getUri());
		event.setFrom(""+getUid());
		event.setNotifiers(mEventTo);
		return true;
	}
	
	/**
	 * </p>The task logic, you can override this method
	 * to process something you like, each task must 
	 * return a Event object to inform the 
	 * task process result</p>
	 * @return Event object
	 */
	protected Event process() {
		return null;
	}
		
	/**
	 * IMPORTANT: define the task schedule policy here
	 */
	public int compareTo(Task other) {
		return this.mWeight > other.mWeight ? 1: (this.mWeight == other.mWeight) ? 0 : -1;
	}
	
	public String toString() {
		return String.format("{%8s (:desc %8s) (:weight %5d) (:sec %f)}", mId , mDesc, mWeight, mTimeStamp.getLifeTimeSec());
	}
	
	public void setEventTo(Notifiers eventTo) {
		mEventTo = eventTo;
	}
	
	public Notifiers getEventTo() {
		return mEventTo;
	}
}
