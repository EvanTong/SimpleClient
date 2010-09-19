package client.protocol;

import client.core.model.net.Protocol;
import client.core.model.net.ResponseParser;

public class SMTP extends Protocol{
	public SMTP() {
		super("SMTP", "1");
	}
}
