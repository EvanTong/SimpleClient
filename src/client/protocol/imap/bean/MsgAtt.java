package client.protocol.imap.bean;

import java.util.HashMap;
import java.util.Vector;

import client.protocol.imap.ImapEvent;

public class MsgAtt extends ImapEvent{
	//----------------------------------------------------------[MsgAttStatic]
	private Envelope                mEnvelope;
	private String                  mInternalDate; //INERNALDATE
	private String                  mRfc822Header;
	private String          	    mRfc822Text;
	private int            		    mRfc822Size;
	//private Vector<BodyStructure>   mBodyStructures = new Vector<BodyStructure>();
	private BodyStructure           mBs;
	//----------------------------------------------------------[MsgAttStatic]
	private HashMap<String, String> mMsgAttDynamic; // TODO: we don't use it now
	
	public void setMsgAttStatic() {
		
	}
	
	/**
	 * add a body BodyStructure instance
	 * @param bs
	 */
	public void setBodyStructure(BodyStructure bs) {
		mBs = bs;
	}
	
	/**
	 * Get all BodyStructure instances
	 * @return
	 */
	public BodyStructure getBodyStructures() {
		return mBs;
	}
}
