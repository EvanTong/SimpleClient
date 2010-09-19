package client.core.model;

import java.util.ArrayList;


// manage listener group Uris 
public class Notifiers {
	private ArrayList<String> mListenerGroupUris = new ArrayList<String>();
	
	public Notifiers() {
		this(ListenerGroup.LGP_URI_default);
	}
	
	public Notifiers(String uri) {
		mListenerGroupUris.add(uri);
	}
	
	public void addNotifyUri(String listenerGroupUri) {
		// avoid duplicate && skip if notifyall
		if(!mListenerGroupUris.contains(listenerGroupUri) && !isNotifyAll()) {
			mListenerGroupUris.add(listenerGroupUri);
		}
	}
	
	public void removeNotifyUri(String listenerGroupUri) {
		mListenerGroupUris.remove(listenerGroupUri);
	}
	
	public boolean isNotifyAll() {
		return mListenerGroupUris.contains(ListenerGroup.LGP_URI_all);
	}
	
	// means not notify any  listener
	public boolean isSilent() {
		return mListenerGroupUris == null || mListenerGroupUris.isEmpty();
	}
	
	public ArrayList<String> getNotifyUri() {
		return mListenerGroupUris;
	}
	
	// null means do not notify any listeners
	public void setNotifyUri(ArrayList<String> listenerGroupUris) {
		mListenerGroupUris = listenerGroupUris;
		if(mListenerGroupUris == null) {
			mListenerGroupUris = new ArrayList<String>();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String s : getNotifyUri()) {
			sb.append(s).append(" ");
		}
		return String.format("(:TO %s}", sb.toString());
	}
}
