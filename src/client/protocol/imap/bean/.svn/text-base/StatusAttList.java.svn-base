package client.protocol.imap.bean;

import java.util.HashMap;

/**
 * StatusAttList = StatusAtt SP Number *(SP StatusAtt SP number);
 * StatusAtt     = "MESSAGES" / "RECENT" / "UIDNEXT" / "UIDVALIDITY" / "UNSEEN";
 * @author amas
 *
 */
public class StatusAttList {
	public static final String SA_messages    = "MESSAGES";
	public static final String SA_recent      = "RECENT";
	public static final String SA_uidnext     = "UIDNEXT";
	public static final String SA_uidvalidity = "UIDVALIDITY";
	public static final String SA_unseen      = "UNSEEN";
	HashMap<String, Integer>   mStatusAtts    = new HashMap<String, Integer>();
	
	public StatusAttList() {
		
	}
	
	public void addAttributes(String name, int value) {
		mStatusAtts.put(name, value);
	}
	
	public int getAttributeValue(String name) {
		//TODO: if name don't exist ???
		return mStatusAtts.get(name);
	}
}
