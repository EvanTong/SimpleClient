package client.protocol.imap.bean;

import java.util.HashSet;

/* Example:    
 * C: A101 LIST "" ""
 * S: * LIST (\Noselect) "/" ""
 * S: A101 OK LIST Completed
 * C: A102 LIST #news.comp.mail.misc ""
 * S: * LIST (\Noselect) "." #news.
 * S: A102 OK LIST Completed
 * C: A103 LIST /usr/staff/jones ""
 * S: * LIST (\Noselect) "/" /
 * S: A103 OK LIST Completed
 * C: A202 LIST ~/Mail/ %
 * S: * LIST (\Noselect) "/" ~/Mail/foo
 * S: * LIST () "/" ~/Mail/meetings
 * S: A202 OK LIST completed 
 */
public class ListData extends MailboxData {
	MailboxList       mMbxList        = null;
	
	public void setMailboxList(MailboxList mbxlist) {
		mMbxList = mbxlist;
	}
	
	@Override
	public String toString() {
		return String.format("(ListData %s)", mMbxList);
	}
}
