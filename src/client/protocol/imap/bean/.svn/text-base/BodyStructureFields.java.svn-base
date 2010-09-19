package client.protocol.imap.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * BodyFields    = BodyFldParam SP BodyFldId SP BodyFldDesc SP BodyFldEnc SP BodyFldOctets;
 * BodyFldParam  = "(" ImapString SP ImapString *(SP ImapString SP ImapString) ")" / Nil;
 */
public class BodyStructureFields {
	
	public BodyStructureFields() {
		
	}
	
	/**
	 * @param params
	 */
	public void setParams(HashMap<String, String> params) {
		mParam = params;
	}
	
	/**
	 * Get param value by key
	 * @param key
	 * @return
	 */
	public String getParamValue(String key) {
		return mParam.get(key);
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void setParamValue(String key, String value) {
		mParam.put(key, value);
	}
	
	
	/**
	 * Get body length by octets
	 * @return
	 */
	public int getOctets() {
		return mOct;
	}
	
	public void setOctets(int length) {
		mOct = length;
	}
	
	/**
	 * Set encoding, please refer RFC3501:
	 * <p>
	 * (DQUOTE ("7BIT" / "8BIT" / "BINARY" / "BASE64"/"QUOTED-PRINTABLE") DQUOTE) / String;
	 * </p>
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		mEnc = encoding;
	}
	
	/**
	 * Get encoding
	 */
	public String getEncoding() {
		return mEnc;
	}
	
	public void setDesc(String desc) {
		mDesc = desc;
	}
	
	public String getDesc() {
		return mDesc;
	}
	
	public void setId(String id) {
		mId = id;
	}
	
	public String getId() {
		return mId;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("(");
		Iterator<Map.Entry<String, String>> iter = mParam.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>)iter.next();
			String key   = (String)entry.getKey();
			String value = (String)entry.getValue();
			sb.append(key).append(":").append(value).append(" ");
		}
		sb.append(")");
		sb.append(" ID:"+getId());
		sb.append(" DESC:"+getDesc());
		sb.append(" ENC:" + getEncoding());
		sb.append(" OCT:"+getOctets());
		sb.append(" )");
		return sb.toString();
	}
	
	HashMap<String, String> mParam = new HashMap<String, String>();
	String                  mId    = "";
	String                  mDesc  = "";
	String                  mEnc   = "";
	int                     mOct   = 0; 
}
