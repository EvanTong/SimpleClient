package client.protocol.imap.bean;

import java.util.HashSet;

public class FlagsData extends MailboxData {
	HashSet<ImapFlag> mFlags          = new HashSet<ImapFlag>();
}
