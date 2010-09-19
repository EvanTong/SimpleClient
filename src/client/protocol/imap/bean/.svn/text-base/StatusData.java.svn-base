package client.protocol.imap.bean;
/**
 * Mailbox names are 7-bitC lient implementations MUST NOT attempt to create 8-bit mailbox names 
 * and SHOULD interpret any 8-bit mailbox names returned by LIST or LSUB as UTF-8
 * 
 *  The case-insensitive mailbox name INBOX is a special name reserved
 *  
 *  === Rules of create new mail box ===
 *  Any character which is one of the atom-specials (see the Formal Syntax) will require that the mailbox name be represented as a
 *  quoted string or literal.
 * 
 *  CTL and other non-graphic characters are difficult to represent n a user interface and are best avoided.
 *  
 *  Although the list-wildcard characters ("%" and "*") are valid in a mailbox name, it is difficult to use such mailbox names 
 *  with the LIST and LSUB commands due to the conflict with wildcard interpretation.
 *  
 *  Usually, a character (determined by the server implementation) is reserved to delimit levels of hierarchy.
 *  
 *  Two characters, "#" and "&", have meanings by convention, and should be avoided except when used in that convention.
 *  
 *  e.g: AOL server
 *  {{{
 *  Folder cannot contain any of the following letters: \ : * ? " < > | % /
 *  }}}
 */
public class StatusData extends MailboxData {
	StatusAttList     mStatusAttList  = null;
	String            mMailbox        = "";
}
