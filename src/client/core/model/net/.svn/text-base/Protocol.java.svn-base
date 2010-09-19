package client.core.model.net;

import java.io.InputStream;

import client.core.model.Notifiers;
import client.protocol.IMAP;
import client.protocol.POP;
import client.protocol.SMTP;


public class Protocol {
	/**
	 * Get protocol by uri
	 * TODO: simplify it
	 * @param uri
	 * @return
	 */
	public static Protocol getProtocolByUri(String uri) {
		String   scheme  = uri.split("://")[0];
		if(scheme.contains("smtp")) {
			return new SMTP();
		} else if(scheme.contains("imap")) {
			return new IMAP();
		} else if(scheme.contains("pop")) {
			return new POP();
		} 
		return null;
	}
	
	String            mName      = "";
	String            mVersion   = "";
	//ResponseParser    mParser    = null;
	
	public Protocol(String name, String version/*, ResponseParser parser*/) {
		mName    = name;
		mVersion = version;
		//mParser  = parser;
	}
	
	public String getName() {
		return mName;
	}

//	public ResponseParser getResponseParser() {
//		return mParser;
//	}	
	
	// uri is listener group
	public Receiver newReceiver(InputStream istream, Notifiers eventTo) {
		return null;
	}
}
