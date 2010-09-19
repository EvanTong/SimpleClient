package client.protocol.imap.task;

import client.protocol.imap.ImapConnection;
import client.protocol.imap.bean.MailboxData;
import client.protocol.imap.bean.SearchData;


public class CmdSearch extends ImapCommand {	
	// TODO: enhance it
	public CmdSearch(ImapConnection conn, String cmd) {
		mConnection = conn;
		mCmd.append(mTag).append(" ").append(cmd);
	}
	
	/**
	 * On receive {@link MailboxData}
	 * @param event
	 */
	protected void onMailboxData(MailboxData data) {
		if(data instanceof SearchData) {
			mResponse.addResponseData(data);
		}
	}
}
