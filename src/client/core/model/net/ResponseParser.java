package client.core.model.net;

import client.core.model.Event;


// be a interface ??
public interface ResponseParser {

	//public void onReceiveLine(String line) {
	
	public Event parse(String response);
	
}
