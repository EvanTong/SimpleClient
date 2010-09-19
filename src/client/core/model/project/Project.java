package client.core.model.project;

import java.io.IOException;
import java.util.Vector;


import client.core.EventManager;
import client.core.TaskManager;
import client.core.model.Event;
import client.core.model.EventListener;
import client.core.model.Notifiers;
import client.core.model.Task;
import client.core.model.TimeStamp.Tag;
import client.core.model.event.FutureEvent;
import client.core.model.event.TaskDoneEvent;

/** 
 * A project contains some tasks,
 * it keeps these tasks be processed by workers as
 * defined orders. 
 * 
 * each task in project called a sub task
 * 1. each project will create a new listener group for push & listen events.
 * 2. once the project finished or canceled, the listener group will be removed.
 * @author amas
 * TODO: maybe we do not need remove task and exe one by one, how to avoid 
 * relocated the duplicate project ???
 */
public abstract class Project extends Task implements EventListener, ISubscribable {
	
	/**
	 * A project over
	 * @author amas
	 *
	 */
	public class StartEvent extends Event {
		public StartEvent() {
			setFrom(myself());
		}
	}
	
	/**
	 * A project started
	 * @author amas
	 *
	 */
	public class OverEvent  extends TaskDoneEvent {
		public OverEvent() {
			setFrom(myself());
		}
	}
		
	protected Vector<ITodo> mTodoList  = new Vector<ITodo>();
	
	// name as uri
	public Project(String name) {
		setId(name);
		// the event to
		setEventTo(new Notifiers(myself()));
		EventManager.I().addListener(myself(), this);
	}
	
	@Override
	protected Event process() {
		if(mTodoList.isEmpty()) {
			// no sub task, return
			EventManager.I().removeListenerGroup(myself());
			return null;
		}
		onStart();		
		mTimeStamp.touch(Tag.START_TIME);
		return new StartEvent();
	}
	
	protected void onStart() {
		
	}
		
	public void onEvent(Event event) {
		if(event.isFrom(myself())) {
		} 
		onLaunchTask(event);
	}
	
	public void onLaunchTask(Event triggerEvent) {
		if(mTodoList.isEmpty()) {
			mTimeStamp.touch(Tag.END_TIME);
			System.err.println(toString()+" OVER " + mTimeStamp.getLifeTimeSec() + " sec");
		} else {
			ITodo todo = mTodoList.get(0);
			if(todo != null) {
				Task task = todo.launchTask(this, triggerEvent);				
				if(task != null) {
					//task.setEventTo(getEventTo()); // 不能覆盖EventTo, 只能设置
					task.addEventTo(myself());
					TaskManager.I().assign(task);
					mTodoList.remove(0);
					//TODO: add to done list ???
				}
			}
		}
	}
	
	/**
	 * @param taskDesc
	 */
	public void addTodo(ITodo taskDesc) {
		mTodoList.add(taskDesc);
		System.out.println(getName() +" +TODO " +  mTodoList.size());
	}
	
	protected String myself() {
		return "lg://"+getUid();
	}
		
	public String getName() {
		return getId();
	}
	
	/** push event to the private listener group */
	protected void pushEvent(Event event) {
		if(event == null) {
			return;
		}
		touch(event);
		EventManager.I().push(event);
	}
	
	/**
	 * The result event to
	 * TODO: doc
	 * @param event
	 * @return
	 */
	protected boolean touch(Event event) {
		if(getEventTo() == null || event == null) {
			return false;
		}
		
		event.setNotifiers(getEventTo());
		return true;
	}
	
	/**
	 * Stop process the left sub tasks
	 */
	public void cancel() {
		
	}
	
	/* (non-Javadoc)
	 * @see client.core.model.project.ISubscribable#subscribeTo(java.lang.String)
	 */
	public void subscribeTo(String listenerGroupUri) {
		getEventTo().addNotifyUri(listenerGroupUri);
	}
}
