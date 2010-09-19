package client.core.model;

/**
 * For tracking task life 
 * @author amas
 */
public class TimeStamp {
	public enum Tag {
		START_TIME,
		END_TIME
	}
	private long mBornTime  = 0;
	private long mStartTime = 0;
	private long mEndTime   = 0;	
	
	public TimeStamp() {
		mBornTime = System.currentTimeMillis();
	}
	
	public void touch(Tag tag) {
		switch (tag) {
		case START_TIME:
			mStartTime = System.currentTimeMillis();
			break;
		case END_TIME:
			mEndTime   = System.currentTimeMillis();
			break;
		default:
			/* NOP */
		}
	}

	public long getLifeTime() {
		return mEndTime - mStartTime;
	}
	
	public double getLifeTimeSec() {
		return (mEndTime - mStartTime) / 1000.0;
	}
	
	public long getStartTimestamp() {
		return mStartTime;
	}
	
	public long getEndTimestamp() {
		return mEndTime;
	}
}
