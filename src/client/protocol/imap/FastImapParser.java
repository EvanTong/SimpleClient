package client.protocol.imap;

import java.util.Vector;

import client.protocol.imap.bean.CapabilityData;
import client.protocol.imap.bean.ImapResponseFactory;
import client.protocol.imap.bean.RespCondState;

public class FastImapParser {
	public final static String REGEX_IMAP_TAG="^X[0-9][0-9]* .*$";
	public static ImapEvent parseLine(String line) {
		/**
		 *  ||00||01||02||03||04||??||
		 *  ||X ||..............||SP||
		 *  ||* ||SP||
		 *  ||+ ||SP||
		 */
		System.err.println(String.format("=============> FastImapParser{%4d} : '%s'",line.length(),line));
		if(line.matches(REGEX_IMAP_TAG)) {
			return ImapResponseFactory.createImapTaggedResponse(line);
		} else if(line.startsWith("* ")) {
			if (line.matches("^\\* OK|BAD|NO .*$")) {
				System.err.println("YOU RECV RespCondState");
			} else if (line.matches("\\* BYE .*")) {
				System.err.println("YOU RECV RespCondBye");
			} else if (line.matches("^\\* [0-9][0-9]* (FETCH|EXPUNGE) .*$")) {
				//TODO: performance tuning ...
				return ImapResponseFactory.createMessageData(line);			
			} else if (line.matches("^\\* (FLAGS|LIST|LSUB|SEARCH|STATUS) .*$")
					|| line.matches("^\\* ([0-9][0-9]*) (EXISTS|RECENT)$")) {
				return ImapResponseFactory.createMailboxData(line);
			} else if (line.matches("^\\* CAPABILITY .*$")) {				
				return ImapResponseFactory.createCapabilityData(line);
			}
		} else if(line.startsWith("+ ")) {
			System.err.println("YOU RECV PLUS");
		} else {
			System.err.println("YOU RECV I DON'T KNOW");
		}
		return null;
	}
}
