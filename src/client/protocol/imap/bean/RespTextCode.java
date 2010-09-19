package client.protocol.imap.bean;

import java.util.HashMap;
import java.util.HashSet;


import client.protocol.imap.ImapEvent;

/**
 * <pre>
 * "ALERT" / "BADCHARSET" [SP "(" astring *(SP astring) ")" ] /
 * capability-data / "PARSE" /
 * "PERMANENTFLAGS" SP "("
 * [flag-perm *(SP flag-perm)] ")" /
 * "READ-ONLY" / "READ-WRITE" / "TRYCREATE" /
 * "UIDNEXT" SP nz-number / "UIDVALIDITY" SP nz-number /
 * "UNSEEN" SP nz-number /
 * atom [SP 1*<any TEXT-CHAR except "]">]
 * </pre>
 * @return
 * e.g:
 * UNSEEN 259
 * UIDVALIDITY 1
 */
public class RespTextCode extends ImapEvent {
	public enum Type {
		ALERT,
		BADCHARSET,
		CAPABILITY,
		PARSE,
		PERMANENTFLAGS,
		READ_ONLY,
		READ_WRITE,
		TRYCREATE,
		UIDNEXT,
		UIDVALIDITY,
		UNSEEN,
		OTHERS /// OTHERS means you should parse it by your need, the format follow -> atom [SP 1*<any TEXT-CHAR except "]">]
	};
	
	String mText  = "";
	
	/**
	 * Only valid when the Type is one of the following:
	 * <li>UIDNEXT</li>
	 * <li>UIDVALIDITY</li>
	 * <li>UNSEEN</li>
	 */
	int             mNzNum     = -1; 
	Type            mType      = Type.OTHERS;
	
	/**
	 * Only valid when Type is CAPABILITY
	 */
	CapabilityData  mCapb      = null;
	
	public RespTextCode(String text) {
		mText = text;
		//TODO: make it hasmap
		if(mText.startsWith("ALERT")) {
			mType  = Type.ALERT;
		} else if(mText.startsWith("BADCHARSET")) {
			mType  = Type.BADCHARSET;
		} else if(mText.startsWith("CAPABILITY")) {
			mType  = Type.CAPABILITY;
			//mCapb = new CapabilityData(text);
		} else if(mText.startsWith("PARSE")) {
			mType  = Type.PARSE;
		} else if(mText.startsWith("PERMANENTFLAGS")) {
			mType  = Type.PERMANENTFLAGS;
		} else if(mText.startsWith("READ_ONLY")) {
			mType  = Type.READ_ONLY;
		} else if(mText.startsWith("READ_WRITE")) {
			mType  = Type.READ_WRITE;
		} else if(mText.startsWith("TRYCREATE")) {
			mType  = Type.TRYCREATE;
		} else if(mText.startsWith("UIDNEXT")) {
			mType  = Type.UIDNEXT;
			mNzNum = Integer.parseInt(mText.substring("UIDNEXT".length()).trim());
		} else if(mText.startsWith("UIDVALIDITY")) {
			mType  = Type.UIDVALIDITY;
			mNzNum = Integer.parseInt(mText.substring("UIDVALIDITY".length()).trim());
		} else if(mText.startsWith("UNSEEN")) {
			mType  = Type.UNSEEN;
			mNzNum = Integer.parseInt(mText.substring("UNSEEN".length()).trim());
		} else {
		    /* mType = Type.OTHERS */
		}
	}
	
	public int getNumber() {
		return mNzNum;
	}
	
	public String toString() {
		return mText + " NzNumber="+mNzNum;
	}
	
	public CapabilityData getCapabilityData() {
		return mCapb;
	}
}