package client.protocol.imap.event;

import client.core.model.Event;

public class ImapConnectionClose extends Event {
	String mReason = "unknow";
	
	public ImapConnectionClose(String reason) {
		setReason(reason);
	}
	
	public void setReason(String reason) {
		mReason = reason;
	}
	
	public String getReason() {
		return mReason;
	}
	
	public String toString() {
		return "ImapConnectionClose : \n"+mReason;
	}
}
