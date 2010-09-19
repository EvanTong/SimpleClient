package client.protocol.imap.bean;

import client.protocol.imap.ImapEvent;
/**
 * resp-cond-auth  = ("OK" / "PREAUTH") SP resp-text
 * @author amas
 *
 */
public class RespCondAuth extends ImapEvent {
	public String mResult    = "";
	public String mRespText  = "";
	
	public RespCondAuth(String result, String respText) { 		
		mResult   = result;
		mRespText = respText;
	}
	
	public String toString() {
		return mResult + " " + mRespText;
	}
	
	public boolean isOK() {
		return mResult.startsWith("OK");
	}
	
	public boolean isPREAUTH() {
		return mResult.startsWith("PREAUTH");
	}
	
	/**
	 * Get detail description
	 */
	public String getRespText() {
		return mRespText;
	}
}