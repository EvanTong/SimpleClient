package client.protocol.imap.task;

import client.core.EventManager;
import client.core.model.Event;
import client.protocol.imap.ImapConnection;
import client.protocol.imap.ImapEvent;
import client.protocol.imap.bean.ImapTaggedResponse;
import client.protocol.imap.bean.MailboxData;
import client.protocol.imap.bean.ResponseDone;
/**
 * <h3>6.3.1.  SELECT Command</h3>
 * <pre>
 * Arguments:  mailbox name
 * Responses:  REQUIRED untagged responses: FLAGS, EXISTS, RECENT
 *             REQUIRED OK untagged responses:  UNSEEN,  PERMANENTFLAGS,
 *             UIDNEXT, UIDVALIDITY
 * Result:     OK  - select completed, now in selected state
 *             NO  - select failure, now in authenticated state: no
 *                   such mailbox, can't access mailbox
 *             BAD - command unknown or arguments invalid
 *             
 *             OK [UNSEEN <n>]    // Message SN of 1st unseen message, this may missing
 *             OK [PERMANENTFLAGS (<list of flags>)]
 *             OK [UIDNEXT <n>]   // X1 OK LOGIN completed
 *             OK [UIDVALIDITY <n>]  // 
 *  
 *  Only one mailbox can be selected at a time in a connection;
 *  simultaneous access to multiple mailboxes requires multiple connections. 
 *  The SELECT command automatically deselects any currently selected mailbox 
 *  before attempting the new selection. Consequently, 
 *  if a mailbox is selected and a SELECT command that fails is attempted, 
 *  no mailbox is selected.
 *  If the client is permitted to modify the mailbox, 
 *  the server SHOULD prefix the text of the tagged OK response with the "[READ-WRITE]" response code. 
 * </pre>
 */
public class CmdSelect extends ImapCommand {
	public CmdSelect(ImapConnection conn, String mailbox) {
		mConnection = conn;
		mCmd.append(String.format("%s SELECT %s", mTag, mailbox));
	}
	
	protected void onMailboxData(MailboxData event) {
		mResponse.addResponseData(event);
	}
}
