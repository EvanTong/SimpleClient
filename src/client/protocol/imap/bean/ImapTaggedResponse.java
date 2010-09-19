package client.protocol.imap.bean;

import client.protocol.imap.bean.RespCondState;

public class ImapTaggedResponse extends ResponseDone {
	private String        mTag  = "";
	private RespCondState mRcs  = null;;
	
	public ImapTaggedResponse(String tag, RespCondState rcs) {
		mTag   = tag;
		mRcs   = rcs;
	}
	
	public String getTag() {
		return mTag;
	}
	
	public boolean isOK() {
		return mRcs.isOK();
	}
	
	public boolean isBad() {
		return mRcs.isBAD();
	}
	
	public boolean isNo() {
		return mRcs.isNO();
	}
}
