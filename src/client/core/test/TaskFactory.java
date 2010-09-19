package client.core.test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import client.core.model.Event;
import client.core.model.Task;
import client.core.model.TimeStamp.Tag;
import client.core.model.project.ITodo;
import client.core.model.project.Project;

public class TaskFactory {
	private static TaskFactory sInstance = new  TaskFactory();
	//private static Random      mRandom   = new Random(2);
	
	class TestTask extends Task  {
		int    mTime = 0;
		
		/**
		 * @param desc description
		 * @param time msecond
		 */
		public TestTask(String uri, int time, int weight) {
			mTime = time;
			setId(uri);
			setDesc(uri);
		}
		
		public Event process() {
			
			try {
				
				Thread.sleep(mTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.mTimeStamp.touch(Tag.END_TIME);
			System.out.println(this + "  OVER ...");
			return new Event(toString()+ " is over ");
		}
	}
	
	
	class TestProject extends Project {
		public TestProject() {
			super("TestProject");
			for(int i=0; i<10; ++i) {
				addTodo(new ITodo() {					
					public Task launchTask(Project project, Event event) {
						System.out.println(".......");
						return new TestTask("P.T", 1500, 0);
					}
				});
			}
		}
	}
	
	public static TaskFactory I() {
		return sInstance;
	}
	
	public Project createTestProject() {
		return new TestProject();
	}
	
	public ArrayList<Task> createRandomTestTask(int number, int minMsec, int maxMsec) {
		Random rg = new Random();
		
		
		ArrayList<Task> tasks = new ArrayList<Task>(number);
		for(int i=0; i < number; ++i) {
			int msec = rg.nextInt(maxMsec);
			if(msec == 0) {
				msec = minMsec;
			}
			tasks.add(new TestTask("TestTask " + i, msec, i));
		}	
		return tasks;
	}
}
