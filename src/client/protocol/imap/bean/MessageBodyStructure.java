package client.protocol.imap.bean;

public class MessageBodyStructure extends BodyStructure {
	private BodyStructure mBodyStructures = null;
	
	public MessageBodyStructure() {
		setMimeType("MESSAGE", "RFC822");
	}
	
	public void setBodyStructure(BodyStructure bs) {
		mBodyStructures = bs;
	}
	
	public BodyStructure getBodyStructure() {
		return mBodyStructures;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n").append(mBodyStructures);
		return sb.toString();
	}
}
