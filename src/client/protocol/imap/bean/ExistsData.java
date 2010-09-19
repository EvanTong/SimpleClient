package client.protocol.imap.bean;

public class ExistsData extends MailboxData {
	private int mNumber = -1;
	
	public int getNumber() {
		return mNumber;
	}
	
	/**
	 * @param number
	 */
	public void setNumber(int number) {
		mNumber = number;
	}
	public String toString() {
		return String.format("(ExistsData %d)", mNumber);
	}
}
