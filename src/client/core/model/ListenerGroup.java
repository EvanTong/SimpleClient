package client.core.model;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * We try to manage listeners by group to reduce unnecessary listener call. 
 * @author amas
 */
public class ListenerGroup {
	public static final String SCHEME          =  "lg://";
	public static final String LGP_URI_default = SCHEME + "default";
	public static final String LGP_URI_all     = SCHEME + "*";
	
	/**
	 * The listener group uri or id
	 * e.g.:
	 * <li>listener://oms.mail/ui/activity</li>
	 */
	private String  mUri  = ""; 
	
	private volatile boolean mIsDeaf = false;
	
	/**
	 * Event listener queue
	 */
	private ConcurrentLinkedQueue<EventListener> mListener = new ConcurrentLinkedQueue<EventListener>();

	public ListenerGroup(String uri) {
		mUri = uri;
	}
	
	/**
	 * Fire a event to the event listeners
	 * @param event event to be fired
	 */
	public void fireEvent(Event event) {
		if (!isDeaf() && !mListener.isEmpty()) {
			for (EventListener l : mListener) {
				try {
					l.onEvent(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isDeaf() {
		return mIsDeaf;
	}
	
	/**
	 * Neglect any received event
	 * @param beDeaf
	 */
	public void setDeaf(boolean beDeaf) {
		mIsDeaf = beDeaf;
	}
	
	/**
	 * Add {@link EventListener}, only if it not existed 
	 * @param listener {@link EventListener} object
	 */
	public void addListener(EventListener listener) {
		if(!mListener.contains(listener)) {
			mListener.add(listener);
			System.err.println(mUri + " ++LISTENER " + listener);
		} 
	}
	
	/**
	 * Remove {@link EventListener} from the listener group 
	 * @param listener
	 */
	public void removeListener(EventListener listener) {
		if(mListener.remove(listener)) {
			System.err.println(mUri+" ("+ mListener.size()+") " + " --LISTENER " + listener );
		}
	}

	
	public void reset() {
		setDeaf(true);
		mListener = new ConcurrentLinkedQueue<EventListener>();
		setDeaf(false);
	}
	
	public void deaf() {
		
	}
	
	public String getUri() {
		return mUri;
	}
	
	public int size() {
		return mListener.size();
	}
	
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("(").append(mUri).append(" ");
//		for(EventListener e : mListener) {
//			sb.append(e).append(" ");
//		}
//		sb.append(")");
//		return sb.toString();
//	}
}
