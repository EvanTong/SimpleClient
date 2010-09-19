package client.protocol.imap.bean;

import client.protocol.imap.ImapEvent;

public class RespCondState extends ImapEvent{
	public enum Result {
		OK,
		NO,
		BAD
	}
	
	Result mResult   = Result.BAD;
	String mRespText = "";
	
	public RespCondState(String result, String respText) {
		if(result.equals("OK")) {
			mResult = Result.OK;
		} else if(result.equals("NO")) {
			mResult = Result.NO;
		} else if(result.equals("BAD")) {
			mResult = Result.BAD;
		}
		mRespText = respText;
	}
	
	public String toString() {
		return mResult + " " + mRespText;
	}
	
	public Result getResult() {
		return mResult;
	}
	
	public String getRespText() {
		return mRespText;
	}
	
	public boolean isOK() {
		return mResult == Result.OK;
	}
	
	public boolean isBAD() {
		return mResult == Result.BAD;
	}
	
	public boolean isNO() {
		return mResult == Result.NO;
	}
}
