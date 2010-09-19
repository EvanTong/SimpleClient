package client.protocol.imap.bean;

/**
 * BodyTypeText  = MediaText SP BodyFields SP BodyFldLines;
 * @author amas
 */
public class TextBodyStructure extends BodyStructure {
	private int mBodyFieldLines = 0;
	
	public TextBodyStructure(String mediaSubtype) {
		setMimeType("TEXT", mediaSubtype);
	}
	
	public void setBodyFiledLines(int line) {
		mBodyFieldLines = line;
	}
	
	public int getBodyFiledLines() {
		return mBodyFieldLines;
	}	
	
	public String toString() {
		return super.toString();
	}
}
