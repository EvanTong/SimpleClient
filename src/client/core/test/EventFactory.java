package client.core.test;

import java.util.ArrayList;

import client.core.model.Event;

public class EventFactory {
	private static EventFactory sInstance = new EventFactory();
	private EventFactory() {
		
	}
	public static EventFactory I() {
		return sInstance;
	}
	
	class TestEvent extends Event {
		TestEvent(String uri) {
			super(uri);
		}
	}
	
	/**
	 * <p>create specify number events for testing</p>
	 * @param number
	 * @return
	 */
	public ArrayList<Event> createTestEvent(int number) {
		ArrayList<Event> events = new ArrayList<Event>(number);
		for(int i=0; i<number; ++i) {
			events.add(new TestEvent("event://TestEvent/"+i));
		}
		return events;
	}
}
