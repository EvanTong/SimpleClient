package client.protocol.imap.bean;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Imap server capability data
 * TODO: +comment
 * TODO: it handled at bean layer, am I right ???
 * TODO: look at formal syntax of CapabilityData:
 * <pre>
 * capability-data = "CAPABILITY" *(SP capability) SP "IMAP4rev1"
 *                    (SP capability)
 *                   ; Servers MUST implement the STARTTLS, AUTH=PLAIN,
 *                   ; and LOGINDISABLED capabilities
 *                   ; Servers which offer RFC 1730 compatibility MUST
 *                   ; list "IMAP4" as the first capability.
 * </pre>
 * seems it need to have two kind of capability, Now we have one
 * @author amas
 */
public class CapabilityData extends ImapUntaggedResponse {
	HashSet<String> mCapb      = new HashSet<String>();
	
	public CapabilityData() {
		
	}
	
	public void addCapability(String capability) {
		mCapb.add(capability);
	}
	
	public boolean isExisted(String capability) {
		return mCapb.contains(capability);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = mCapb.iterator();
		while(iter.hasNext()) {
			sb.append((String)iter.next()).append(":");
		}
		return String.format("CAPABILITY (%s)",sb.toString());
	}
}
