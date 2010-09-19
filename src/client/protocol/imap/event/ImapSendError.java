package client.protocol.imap.event;

import client.core.model.Event;
import client.protocol.imap.task.ImapCommand;

/**
 * Imap command send error
 * @author amas
 *
 */
public class ImapSendError extends Event{
	private ImapCommand mCmd = null;
	
	public ImapSendError(ImapCommand cmd, String desc) {
		mCmd = cmd;
		setDesc(desc);
	}
	
	public final ImapCommand getCommand() {
		return mCmd;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(mCmd);
		sb.append(super.toString()).append(")");
		return sb.toString();
	}
}
