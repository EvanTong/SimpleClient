package client.protocol.imap.bean;

import java.util.Vector;

/**
 * <code>
* BodyTypeMpart = 1*Body SP MediaSubtype [SP BodyExtMPart];
* </code>
 */
public class BodyStructureMPart extends BodyStructure {
	private Vector<BodyStructure> mBodys = new Vector<BodyStructure>();
	
	public BodyStructureMPart() {
		setMediaType("muiltpart");
	}
	/**
	 * Add a new body structure
	 * @param bs
	 */
	public void addBodyStructure(BodyStructure bs) {
		mBodys.add(bs);
	}
	
	/**
	 * Get all body structure
	 * @return
	 */
	public Vector<BodyStructure> getAllBodyStructure() {
		return mBodys;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(fill(getLevel())+getMimeType()).append("\n");
		for(BodyStructure bs: mBodys) {
			sb.append(bs.toString()+"\n"); // TODO: the last element should not have "\n"
		}
		return sb.toString();
	}
}
