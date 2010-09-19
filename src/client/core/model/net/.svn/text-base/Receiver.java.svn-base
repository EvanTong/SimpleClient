package client.core.model.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import client.core.EventManager;
import client.core.model.Event;
import client.core.model.Notifiers;

//TODO: using ExecutorService instead ...
public class Receiver extends Thread {
	protected volatile boolean    mStop                = false;
	protected InputStream         mInStream            = null;
	protected Reader              mReader              = null;
	protected ResponseParser      mParser              = null;
	protected Notifiers           mEventTo             = null;
	
	public  Receiver() {
		
	}
	
	public Receiver(ResponseParser parser, InputStream istream, Notifiers eventTo) {
		mInStream = istream;
		mReader   = new BufferedReader(new InputStreamReader(istream));
		mEventTo  = eventTo;
	}
	
	@Override
	public void run() {
		// receive logic
	}
	
	public void shutdown() {
		try {
			mInStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mStop = true;
	}
	
	/**
	 * <p>Push a event to {@link EventManager}</p>
	 * @param event
	 */
	public void pushEvent(Event event) {
		if (event != null) {
			// sign the event
			event.setFrom(this.getName());
			event.setNotifiers(mEventTo);
			EventManager.I().push(event);
		}
	}
}
