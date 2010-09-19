package client.protocol.imap.bean;

import client.protocol.imap.ImapEvent;

public class ResponseFatal extends ResponseDone {
	RespCondBye mRespCondBye = null;
	public ResponseFatal(ImapEvent respCondBye) {
		mRespCondBye = (RespCondBye)respCondBye;
	}	
	
	public String toString() {
		return this.getClass().getSimpleName()+" : " + mRespCondBye;
	}
}
