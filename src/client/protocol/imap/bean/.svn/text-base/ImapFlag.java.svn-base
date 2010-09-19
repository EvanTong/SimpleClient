package client.protocol.imap.bean;

/**
 * Flag          = "\Answered" 
 *                / "\Flagged" 
 *                / "\Deleted" 
 *                / "\Seen" 
 *                / "\Draft" 
 *                / FlagKeyword 
 *                / FlagExtension; # Does not include "\Recent"
 *                
 * FlagPerm      = Flag / "\*";
 * 
 * MbxListSflag  = "\Noselect" / "\Marked" / "\Unmarked"; 
 */
public class ImapFlag {
	
	public static final String ANSWERED             = "\\Answered";
	public static final String FLAGGED              = "\\Flagged";
	public static final String DELETED              = "\\Deleted";
	public static final String SEEN                 = "\\Seen";
	public static final String DRAFT                = "\\Draft";
	public static final String MBX_SFLAG_noselected = "\\Noselect";
	public static final String MBX_SFLAG_marked     = "\\Marked";
	public static final String MBX_SFLAG_unmarked   = "\\Unmarked";
	public static final String MBX_OFLAG_unmarked   = "\\Noinferiors";

	private String mValue = "";
	
	public ImapFlag(String value) {
		mValue = value;
	}
	
	public String getValue() {
		return mValue;
	}
	
	public String toString() {
		return mValue;
	}
}
