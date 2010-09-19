package client.protocol.imap.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

import client.protocol.imap.ImapEvent;
import client.protocol.imap.ListReader;


public class ImapResponseFactory {
	public static MailboxData createMailboxData(String respText) {
		System.err.println("YOU RECV MaiboxData: " + respText);

		String[] sa = respText.split(" ");
		//TODO: performance tuning
		if(false) {		
		} else if(sa[1].equals("FLAGS")) {

		} else if(sa[1].equals("LIST")) {
			ListData listData = new ListData();
			
			String mailbox          = null;
			String delimiter        = null;
		    HashSet<ImapFlag> flags = new  HashSet<ImapFlag>();		    
			ConCell result =  ListReader.parse("("+respText+")", null);
			if(result != null) {
				mailbox   = result.getList().get(4).getValue();
				delimiter = result.getList().get(3).getValue();
				for(ConCell c: result.getList().get(2).getList()) {
					flags.add(new ImapFlag(c.getValue()));
				}
			}
			listData.setMailboxList(new MailboxList(mailbox, delimiter , flags));
		} else if(sa[1].equals("LSUB")) {
			
		} else if(sa[1].equals("SEARCH")) {
			SearchData searchData = new SearchData();
			if(sa.length>2) { 
				Vector<Integer> result = new Vector<Integer>(sa.length);
				for(int i=2; i<sa.length; ++i) {
					result.add(Integer.parseInt(sa[i]));
				}
				searchData.setSearchResult(result);
			}		
			return searchData;
		} else if(sa[1].equals("STATUS")) {
			
		} else if(sa[1].matches("\\d\\d*")) {
			if(sa[2].equals("EXISTS")) {
				ExistsData existsData = new ExistsData();
				existsData.setNumber(Integer.parseInt(sa[1]));
				return existsData;
			} else if(sa[2].equals("RECENT")) {
				RecentData rescentData = new RecentData();
				rescentData.setNumber(Integer.parseInt(sa[1]));
				return rescentData;
			}
		}
		return null;
	}
	
	public static ImapTaggedResponse createImapTaggedResponse(String respText) {
		// Imap taged response stands for a imap response completed
		System.err.println("YOU RECV TAGGED RESP ");
		String[] sa  = respText.split(" ");
		String   ret = sa[1];
		String rspTxt= respText.substring(sa[0].length()+sa[1].length()+2);
		return new ImapTaggedResponse(sa[0], new RespCondState(ret, rspTxt));
	}
	
	public static CapabilityData createCapabilityData(String respText) {
		System.err.println("YOU RECV CapabilityData: " + respText);
		CapabilityData mData = new CapabilityData();	
		String[] items = respText.split(" ");
		for(String s: items) {
			// skip "CAPABILITY" & "IMAP4rev1"
			if(items.equals("CAPABILITY") || items.equals("IMAP4rev1") || items.equals("*"))
				continue;
			mData.addCapability(s);
		}		
		return mData;
	}
		
	public static ImapEvent createMessageData(String line) {
		// TODO Auto-generated method stub
		System.err.println("YOU RECV MessageData:" + line);
		MessageDataVisitor v = new MessageDataVisitor();
		ListReader.parse("("+line+")", v);
		return v.createBs();
	}	
}

class MessageDataVisitor implements IVisitor {
	private HashMap<String, ConCell> mData     = new HashMap<String, ConCell>();
	private boolean                  mSaveNext = false;
	private String                   mLastKey  = "";
	
	public MessageDataVisitor() {
		mData.put("BODY", null);
	}
	
	public void onNewConCell(ConCell cell) {
		if(mSaveNext) {
			mData.put(mLastKey, cell);
			mLastKey = "";
			mSaveNext = false;
		}
		
		String v = cell.getValue();
		if(v == null) {
			//System.err.println("Cell is list " );
		} else {
			//System.err.println("Cell has value: " + cell.getValue());
			if(mData.containsKey(v)) {
				mSaveNext = true;
				mLastKey  = v;
			}
		}
	}
	
	public HashMap<String, ConCell> getData() {
		return mData;
	}
	
	// peek BodyStructureFields from body structure, see RFC3501
	// BodyFields    = BodyFldParam SP BodyFldId SP BodyFldDesc SP BodyFldEnc SP BodyFldOctets;
	// BodyFldParam  = "(" ImapString SP ImapString *(SP ImapString SP ImapString) ")" / Nil;
	public BodyStructureFields createBodyStructureFields(LinkedList<ConCell> bsList) {
		if(bsList != null) {
			// 0. MediaType    skip
			// String mediaType    = bsList.getFirst().getValue();		
			// 1. MediaSubtype skip
			// String mediaSubtype = bsList.get(1).getValue();
			
			BodyStructureFields flds = new BodyStructureFields();
			// 2. BodyFldParam			
			if (bsList.get(2).getList() != null) {
				LinkedList<ConCell> paramsCells = bsList.get(2).getList();
				// TODO: parms value can be a list ???
				for (int i = 0; i < paramsCells.size(); ++i) {
					String key = paramsCells.get(i).getValue();
					// TODO: range check
					String value = paramsCells.get(++i).getValue();
					flds.setParamValue(key, value);
				}
			}
						
			// 3. BodyFldId
			String bodyFldId     = bsList.get(3).getValue();
			flds.setId(bodyFldId);
			// 4. BodyFldDesc
			String bodyFldDesc   = bsList.get(4).getValue();
			flds.setDesc(bodyFldDesc);
			// 5. BodyFldEnc
			String bodyFldEnc    = bsList.get(5).getValue();
			flds.setEncoding(bodyFldEnc);
			// 6. BodyFldOctets
			String bodyFldOctets = bsList.get(6).getValue();
			flds.setOctets(Integer.parseInt(bodyFldOctets));
			return flds;
		} else {
			return null;
		}
	}
	
	protected MessageBodyStructure createMessageBodyStructure(LinkedList<ConCell> bsList, int level) {
		MessageBodyStructure msgBs = new MessageBodyStructure();
		if(bsList != null) {
			// 0. MediaType    skip
			// String mediaType    = bsList.getFirst().getValue();		
			// 1. MediaSubtype skip
			// String mediaSubtype = bsList.get(1).getValue();
			// 2 - 6 BodyStructureFields
			// 7. Envelope
			// TODO: get Envelope if needed
			// 8. Message BodyStructure
			ConCell cell = bsList.get(8);
			BodyStructure subBs = createBodyStructure(cell,level+1);
			msgBs.setBodyStructure(subBs);
			// 9. BodyFldLines
			
		}
		return msgBs;
	}
	
	// TODO: a recursive function, need improvement
	public BodyStructure createBodyStructure(ConCell bs, int level) {
		if (bs != null) {
			LinkedList<ConCell> cellList = bs.getList();
			if(cellList.getFirst().getList() == null) {
				// The first cell has value, we know it's time to create body structure instance
				String mediaType    = cellList.getFirst().getValue();
				String mediaSubtype = cellList.get(1).getValue();
				
				//TODO: performance tuning
				BodyStructure _bs = null;
				if (mediaType.equals("TEXT")) {
					_bs = new TextBodyStructure(mediaSubtype);								
				} else if(mediaType.equals("MESSAGE") && mediaSubtype.equals("RFC822")) {
					// BodyTypeMsg   = MediaMessage SP BodyFields SP Envelope SP Body SP BodyFldLines;
					
					_bs = createMessageBodyStructure(cellList, level);
				} else {
					_bs = new BasicBodyStructure(mediaType,mediaSubtype);
				}
				
				// Create BodyStructureFields
				BodyStructureFields flds = createBodyStructureFields(cellList);
				_bs.setFields(flds);
				_bs.setLevel(level);
				return _bs;
			} else {
				BodyStructureMPart muiltBs = new BodyStructureMPart();
				muiltBs.setLevel(level);
				for (ConCell c : cellList) {
					if (c.getList() != null) {
						if (c.getList().getFirst().getList() != null) {
							// has sub part
							BodyStructure sub = createBodyStructure(c, level+1);
							muiltBs.addBodyStructure(sub);					
							continue;
						} else {	
							BodyStructure _bs = createBodyStructure(c, level+1);
							muiltBs.addBodyStructure(_bs);
						}
					} else {
						// muiltpart sub media type
						String mediaSubtype = c.getValue();
						muiltBs.setMediaSubtype(mediaSubtype);
					}
				}
				return muiltBs;
			}
		} else {
			System.out.println("shit");
		}
		return null;
	}
	
	public BodyStructure createBs() {
		ConCell cell = mData.get("BODY");
		if(cell != null) {
			return createBodyStructure(cell, 0);
		}
		return null;
	}
}