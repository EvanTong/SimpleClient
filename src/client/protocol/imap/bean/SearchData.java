package client.protocol.imap.bean;

import java.util.Vector;

public class SearchData extends MailboxData {
	private Vector<Integer> mSearchResult = null;
	
	public Vector<Integer> getSearchResult() {
		return mSearchResult;
	}
	
	public void setSearchResult(Vector<Integer> result) {
		mSearchResult = result;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(mSearchResult == null) {
			return "(SearchData 0 ())";
		}
		
		for(int i : mSearchResult) {
			sb.append(i).append(" ");
		}
		return String.format("(SearchData %d (%s))", mSearchResult.size(), sb.toString());
	}
}
