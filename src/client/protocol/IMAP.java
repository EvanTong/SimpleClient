package client.protocol;

import java.io.InputStream;

import client.core.model.Notifiers;
import client.core.model.net.Protocol;
import client.core.model.net.Receiver;
import client.protocol.imap.ImapReceiver;

public class IMAP extends Protocol{
	public IMAP() {
		super("IMAP", "4");
	}
	
	public Receiver newReceiver(InputStream istream, Notifiers eventTo) {
		return new ImapReceiver(istream, eventTo);
	}
}
