package com.borqs;

import client.core.model.Event;
import client.core.model.EventListener;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

/**
 * Base activity will register listener to "lgroup://ui" group 
 * @author amas
 */
public class BaseActivity extends Activity implements EventListener{
	
	/**
	 * <p>A default handler, subclass may overrides onMessage() to implement
	 * custom logic</p>
	 */
	protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(!onMessage(msg)) {
				super.handleMessage(msg);
			}
		}
	};
	
	/**
	 * <p>Invoked when a message is arrived</p>
	 * 
	 * @param msg Message object
	 * @return true if the message is processed, or false if you want
	 * 		deliver the message to super class
	 */
	protected boolean onMessage(Message msg) {
		return false;
	}
	
	/**
	 * Notice: 
     * Do not update UI in this method, since it not be called on UI thread!!!
	 */
	public final void onEvent(final Event event) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {		
			public void run() {
				onEventInUiThread(event);	
			}
		});
	}
	
	/**
	 * You can update UI in this method safely, since it will be called on UI thread only.
	 * @param event
	 */
	protected void onEventInUiThread(Event event) {
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// remove event listener 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// add event listener 
	}
}
