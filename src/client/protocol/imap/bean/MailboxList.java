package client.protocol.imap.bean;

import java.util.HashSet;
import java.util.Iterator;

/**
 * MailboxList   = "(" [MbxListFlags] ")" SP (DQUOTE QUOTED_CHAR DQUOTE / Nil) SP Mailbox;
 * MbxListFlags  = *(MbxListOFlag SP) MbxListSflag *(SP MbxListOFlag) / MbxListOFlag *(SP MbxListOFlag);
 * MbxListOFlag  = "\Noinferiors" / FlagExtension;
 * MbxListSflag  = "\Noselect" / "\Marked" / "\Unmarked";   
 * @author amas
 *
 */
public class MailboxList {
	HashSet<ImapFlag> mMbxListFlags = new HashSet<ImapFlag>();
	String            mMailbox      = "";
	String            mDelimiter    = "";

	
	public MailboxList(String mailbox, String delimiter,HashSet<ImapFlag> mbxListFlags) {
		mMailbox      = mailbox;
		mDelimiter    = delimiter;
		mMbxListFlags = mbxListFlags;
	}
	
	public HashSet<ImapFlag> getFlags() {
		return mMbxListFlags;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<ImapFlag> iter = mMbxListFlags.iterator();
		while(iter.hasNext()) {
			sb.append(iter.next()+" ");
		}
		return String.format("(MailboxList '%s' '%s' (:FLAGS %s))", mMailbox, mDelimiter, sb.toString());
	}
}
