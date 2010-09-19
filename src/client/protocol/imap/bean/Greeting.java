package client.protocol.imap.bean;

import client.protocol.imap.ImapEvent;


/**
 * Imap server greeting include:
 * <li>OK     : Imap server ready      </li>
 * <li>PREAUTH:                        </li>
 * <li>Bye    : Imap server say goodbye</li>
 * <hr/>
 * e.g.:
 * TODO: +e.g.
 * @author amas
 */
public class Greeting extends ImapUntaggedResponse {
	ImapEvent mRespCond =  null;     
	
	public Greeting(ImapEvent respCond) {
		mRespCond = respCond;
	}
	
	public String toString() {
		// Original response text
		return "* "+mRespCond.toString();
	}
	
	public boolean isBYE() {
		return (mRespCond instanceof RespCondBye);
	}
	
	public boolean isOK() {
		return (mRespCond instanceof RespCondAuth) && (((RespCondAuth)mRespCond).isOK());
	}
	
	public boolean isPREAUTH() {
		return (mRespCond instanceof RespCondAuth) && (((RespCondAuth)mRespCond).isPREAUTH());
	}
}