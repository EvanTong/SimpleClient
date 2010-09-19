package client.core;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import client.core.model.Dispatcher;
import client.core.model.Event;
import client.core.model.EventListener;
import client.core.model.ListenerGroup;
import client.core.model.Notifiers;



/**
 * <p>Dispatch event to the target event listener group</p>
 * @author amas
 * DONE:
 * 1. ls : for list all listener group & info
 * 2. broadcast event to all listener groups
 * TODO:
 * 3. simplify hash map iter ...
 */
public class EventManager {
	private static EventManager  sInstance   = new EventManager();
	private BlockingQueue<Event> mEventQueue = new LinkedBlockingQueue<Event>();
	private EventDispather       mDispatcher = new EventDispather(mEventQueue);	
	private ConcurrentHashMap<String, ListenerGroup> mListenerGroups = new ConcurrentHashMap<String, ListenerGroup>();
	
	
	// event dispatcher thread
	protected class EventDispather extends Dispatcher<Event> {
		public EventDispather(BlockingQueue<Event> queue) {
			mQueue   = queue;
			mWorkers = Executors.newFixedThreadPool(1); // NOTICE:
		}
				
		@Override
		public Event onDispatch(Event event) {	
			if(event == null) {
				return null;
			}
			System.out.println("DISPACHER >>> "  + event);
			Notifiers eventTo = event.getNotifiers();
			// get notifiers and post the event
			if(eventTo.isNotifyAll()) {
				broadcastEvent(event);
			} else {
				for(String uri : eventTo.getNotifyUri()) {
					ListenerGroup lg = getListenerGroup(uri);
					if(lg != null) {
						lg.fireEvent(event);
					}
				}
			}
			return null;
		}		
	}
	
	/**
	 *  new instance and start assist thread
	 */
	private EventManager() {
		addListenerGroup(ListenerGroup.LGP_URI_default);
		mDispatcher.start();
	}
	
	public static EventManager I() {
		return sInstance;
	}
	
	/**
	 * <p>Push a event to the event queue, the event will be dispatched after while</p>
	 * @param event
	 */
	public void push(Event event) {
		if(event != null) {
			mEventQueue.add(event);
		}
	}	
	
	public void addListener(String uri, EventListener listener) {
		addListenerGroup(uri);
		mListenerGroups.get(uri).addListener(listener);
	}
	
	public void removeListener(String uri, EventListener listener) {
		ListenerGroup lg = getListenerGroup(uri);
		if(lg != null) {
			lg.removeListener(listener);
		}
	}
		
	//XXX: -> addDefaultListener
	public void addListener(EventListener listener) {
		getListenerGroup(ListenerGroup.LGP_URI_default).addListener(listener);
	}
	
	//XXX: -> removeDefaultListener
	public void removeListener(EventListener listener) {
		getListenerGroup(ListenerGroup.LGP_URI_default).removeListener(listener);
	}	

	
	private ListenerGroup getListenerGroup(String uri) {
		return mListenerGroups.get(uri);
	}
	
	/**
	 * Create a new listener group idendified by the uri, if
	 * the uri exsisted noting would happen
	 * @param uri
	 */
	public void addListenerGroup(String uri) {
		if(!mListenerGroups.containsKey(uri)) {
			mListenerGroups.put(uri, new ListenerGroup(uri));
		}
	}
	
	/**
	 * Remove the specify listener group 
	 * @param uri 
	 * @return null ? noting happened : the removed listener group
	 */
	public ListenerGroup removeListenerGroup(String uri) {
		 return mListenerGroups.remove(uri);
	}
	
	/**
	 * Broadcast the given event to all existed listener group
	 * @param event
	 */
	private void broadcastEvent(Event event) {
		Iterator<Map.Entry<String, ListenerGroup>> iter = mListenerGroups.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String, ListenerGroup> entry = (Map.Entry<String, ListenerGroup>) iter.next(); 
			//String         k = (String)entry.getKey();
			ListenerGroup  v = (ListenerGroup)entry.getValue();
			v.fireEvent(event);
		}	
	}
	
	/**
	 * List all listener group 
	 */
	public void ls() {
		Iterator<Map.Entry<String, ListenerGroup>> iter = mListenerGroups.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String, ListenerGroup> entry = (Map.Entry<String, ListenerGroup>) iter.next(); 
			String         k = (String)entry.getKey();
			ListenerGroup  v = (ListenerGroup)entry.getValue();
			System.err.println(String.format("|| %8s || %8s || %4d || %5s ||", k, v, v.size(), v.isDeaf()));
		}
	}
	/**
	 * Clear all listener group
	 */
	public void clearup() {
		Iterator<Map.Entry<String, ListenerGroup>> iter = mListenerGroups.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String, ListenerGroup> entry = (Map.Entry<String, ListenerGroup>) iter.next(); 
			//String         k = (String)entry.getKey();
			ListenerGroup  v = (ListenerGroup)entry.getValue();
			v.reset();
		}
	}
}
