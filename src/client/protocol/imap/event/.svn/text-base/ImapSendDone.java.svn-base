package client.protocol.imap.event;

import client.core.model.Event;
import client.protocol.imap.task.ImapCommand;

/**
 * 
 * @author amas
 */
public class ImapSendDone extends Event{
	private ImapCommand mCmd = null;
	public ImapSendDone(final ImapCommand cmd) {
		mCmd = cmd;
	}
	public final ImapCommand getCommand() {
		return mCmd;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(ImapSendDone ").append(mCmd).append(")");
		return sb.toString();
	}
}
