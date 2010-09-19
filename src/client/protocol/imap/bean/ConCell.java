package client.protocol.imap.bean;

import java.util.LinkedList;


/**
 * A lisp like cell for handle imap list
 * TODO: value and list can be a single class memeber ???
 * @author amas
 */
public class ConCell {		
	String mValue                = null;
	LinkedList<ConCell>   mList  = null;
	ConCell               mParent= null;      
	
	public ConCell() {
		
	}
	
	public ConCell(String value) {
		mValue = value;
	}
	
	public void setParent(ConCell cell) {
		mParent = cell;
	}
	
	public ConCell getParent() {
		return mParent;
	}

	public void setValue(String value) {
		mValue = value;
	}
	
	public void setList(LinkedList<ConCell> list) {
		mList = list;
	}
	
	public String getValue() {
		return mValue;
	}
	
	public LinkedList<ConCell> getList() {
		return mList;
	}
	
	
	/**
	 * Print concell
	 * @param head
	 */
	public static void printConCell(ConCell head) {
		if(head.getValue() == null) {
			System.err.print("(");
			if(head.getList() != null) {
				for(ConCell c : head.getList()) {
					printConCell(c);
				}
			}
			System.err.print(") ");
		} else {
			System.err.print(head.getValue()+" ");
		}		
	}
}