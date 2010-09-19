package client.core.model;

import java.util.ArrayList;


//TODO(zhoujb): be abstract class
public class Event {	
	private String    mFrom                  = "";
	private String    mDesc                  = "";
	private Notifiers mEventTo               = null;

	public Event() {
		
	}
	
	// event description
	public void setDesc(String desc) {
		mDesc = desc;
	}
	
	public String getDesc() {
		return mDesc;
	}
	
	public Event(String From) {
		setFrom(From);
	}
	
	public Event(String From, String to) {
		setFrom(From);
	}
	
	public void setFrom(String from) {
		mFrom = from;
	}
	
	public boolean isFrom(String from) {
		return mFrom.equals(from);
	}
	
	public void setNotifiers(Notifiers eventTo) {
		mEventTo = eventTo;
	}
	
	public Notifiers getNotifiers()  {
		return mEventTo;
	}
	
	public String getFrom() {
		return mFrom;
	}
		
	@Override
	public String toString() {	
		return String.format("(%s (:DESC %s)(:FROM %s)%s)", this.getClass().getSimpleName()+"@"+hashCode(), getDesc(), getFrom(), getNotifiers());
	}
}
