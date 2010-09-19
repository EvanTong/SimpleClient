package client.protocol.imap.bean;

import client.protocol.imap.ImapEvent;

/**
 * A ResponseDone include two subclass:
 * 	<li>ResponseTagged<li>
 *	<li>ResponseFatal<li>
 * Both of them indicate finish of a c/s interact. 
 * @author amas
 *
 */
public class ResponseDone extends ImapEvent{	
	public ResponseDone() {
		
	}
}
