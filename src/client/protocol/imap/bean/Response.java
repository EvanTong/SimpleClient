package client.protocol.imap.bean;

import java.util.ArrayList;

import client.protocol.imap.ImapEvent;

public class Response extends ImapEvent {
	private ResponseDone             mResponseDone = null;
	private ArrayList<ResponseData>  mRespData     = new ArrayList<ResponseData>();
	public  String                   mType         = "";
	
	public void addResponseData(ResponseData respData) {
		mRespData.add(respData);
	}
	
	public void setResponseDone(ResponseDone respDone) {
		mResponseDone = respDone;
	}
	
	public ResponseDone getResponseDone() {
		return mResponseDone;
	}
	
	public ArrayList<ResponseData> getData() {
		return mRespData;
	}
	
	public boolean isOK() {
		return (mResponseDone instanceof ImapTaggedResponse && ((ImapTaggedResponse)mResponseDone).isOK());
	}
	
	public String toString() {
		return super.toString();
	}
}
