package client.protocol.imap;

import client.core.model.Event;

public abstract class ImapEvent extends Event {
	public enum Result {
		OK,
		NO,
		BAD
	};
	
	public String mData = null;
	
	public ImapEvent() {
		
	}
	
	public ImapEvent(String text) {
		mData = text;
	}
}
