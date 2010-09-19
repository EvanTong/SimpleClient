package client.protocol.imap.bean;


/**
 * 
 * @author amas
 */
public class RespCondBye extends ImapUntaggedResponse {
	public String mRespText = "";
	public RespCondBye(String respText) {
		mRespText = respText;
	}
	
	@Override
	public String toString() {
		return "BYE "+mRespText;
	}
	
	/**
	 * Get BYE greeting reason
	 */
	public String getRespText() {
		return mRespText;
	}
}