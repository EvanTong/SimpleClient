package client.protocol.imap.bean;

public class RecentData extends MailboxData {
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
		return String.format("(RecentData %d)", mNumber);
	}
}
