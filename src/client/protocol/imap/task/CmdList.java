package client.protocol.imap.task;

import client.protocol.imap.ImapConnection;

/**
 * <code>
 * list           = "LIST" SP mailbox SP list-mailbox
 * list-mailbox    = 1*list-char / string
 * </code>
 * Arguments: reference name
 * mailbox name with possible wildcards
 * Responses: untagged responses: LIST
 * Result:     OK - list completed
 *             NO - list failure: can't list that reference or name
 *             BAD - command unknown or arguments invalid
 * <p>
 * The LIST command returns a subset of names from the complete 
 * set of all names available to the client. Zero or more untagged 
 * LIST replies are returned, containing the 
 * <li>name attributes</li> 
 * <li>hierarchy delimiter</li> 
 * <li>name</li>
 * 
 * The LIST command SHOULD return its data quickly, without undue delay. 
 * For example, it SHOULD NOT go to excess trouble to calculate the 
 * \Marked or \Unmarked status or perform other processing; 
 * if each name requires 1 second of processing, 
 * then a list of 1200 names would take 20 minutes
 * </p>
 */
public class CmdList extends ImapCommand {
	/**
	 * @param conn {@link ImapConnection} instance
	 * @param mailbox Mailbox Name or ""
	 * @param listMailbox <p>
	 *  <li>""</li> 
	 *  <li>*</li>
	 *  <li>%</li>
	 * </p>
	 *                     
	 */
	public CmdList (ImapConnection conn, String mailbox, String listMailbox) {
		mCmd.append(mTag)
		    .append(" ")
		    .append(mailbox)
		    .append(" ")
		    .append(listMailbox);
	}
	
}
