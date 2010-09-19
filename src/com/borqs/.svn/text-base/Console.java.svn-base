package com.borqs;

import java.io.IOException;

import client.core.ConnectionManager;
import client.core.EventManager;
import client.core.TaskManager;
import client.core.model.Event;
import client.core.model.EventListener;
import client.core.model.Task;
import client.core.test.TaskFactory;
import client.protocol.imap.ImapConnection;
import client.protocol.imap.ImapConnection.Security;
import client.protocol.imap.event.ImapConnectionClose;
import client.protocol.imap.task._DownloadAll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

class EventPrinter implements EventListener {

	public void onEvent(Event event) {
		System.err.println("========================");
		System.err.println(event);
		System.err.println("------------------------");
	}
	
}

public class Console extends Activity implements EventListener {
	EditText    mOut = null;
	EditText    mIn  = null;
	Button      mBtnDone = null;
	Button      mBtnCls  = null;
	Button      mBtnConn = null;
    ImapConnection conn = null;
    private static int sIncCounter = 1;
	_DownloadAll da1 = new _DownloadAll("imap.aol.com", 143, Security.Plain, "patest1234", "chimei");
	_DownloadAll da2 = new _DownloadAll("imap.aol.com", 143, Security.Plain, "patest000", "chimei");
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        mOut = (EditText)findViewById(R.id.et_out);
        mOut.setCursorVisible(true);
        mIn  = (EditText)findViewById(R.id.et_in);
        mBtnDone = (Button)findViewById(R.id.btn_enter);
        
        mBtnDone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				sendCmd();
			}
		});
        
        mBtnCls = (Button)findViewById(R.id.btn_cls);
        mBtnCls.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				mOut.setText("");
			}
		});
        
        mBtnConn = (Button)findViewById(R.id.btn_conn);
        mBtnConn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				connect();
			}
		});
        setTitle(getString(R.string.disconnected));
    }
    
    public void download() {
    	//EventManager.I().addListener(da1.getListenerGroupUri(), this);
    	//EventManager.I().addListener(da2.getListenerGroupUri(), this);
		TaskManager.I().assign(da1);
		TaskManager.I().assign(da2);
    }

	public void connect() {
		if(conn == null) {
			//conn = ConnectionManager.I().createImapConnection("imap.aol.com", 143,
			//	ImapConnection.Security.Plain, "patest1234@aol.com", "chimei");
			conn = ConnectionManager.I().createImapConnection("imap.aol.com", 143, ImapConnection.Security.Plain, "patest1234", "chimei");
		}

		EventManager.I().removeListener(conn.getUri(), this);
		EventManager.I().addListener(conn.getUri(), this);
		//EventManager.I().addListener(conn.getUri(), new EventPrinter());
		try {
			conn.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}


		setTitle(getString(R.string.connected));
		mBtnConn.setVisibility(View.GONE);
	}
    
	public void onEvent(final Event event) {
		runOnUiThread(new Runnable() {
			public void run() {
				_onEvent(event);
			}
		});
	};
	
	public void _onEvent(Event event) {
		out("[EVENT]: "+ event.toString());
		if(event instanceof ImapConnectionClose) {
			setTitle(getString(R.string.disconnected));
			mBtnConn.setVisibility(View.VISIBLE);
		}
	}
    
    public void onEnter() {
    	String line = mIn.getText().toString();
    	D("[IN]:" + line);
    	mOut.append("[IN]:"+line+"\r\n");
    	exec(line);
    	mIn.setText("");
    	//mOut.setText(line);
    }
    
    public void exec(String cmd) {
    	cmd = cmd.trim();
		if("conn".equals(cmd)) {
			//cmd_conn();
		} else if("test".equalsIgnoreCase(cmd)) {
			//cmd_test();
		} else if("event".equalsIgnoreCase(cmd)){
			//cmd_event();
		} else if("listen".equalsIgnoreCase(cmd)) {
			cmd_listen();
		} else if("quit".equalsIgnoreCase(cmd)) {
			//return true;
		} else if("login".equalsIgnoreCase(cmd)) {
			//cmd_login();
		} else if("pro".equalsIgnoreCase(cmd)) {
			//cmd_pro();
		} else if("ppp".equalsIgnoreCase(cmd)) {
			//cmd_project();
		} else if("imap".equalsIgnoreCase(cmd)) {
			cmd_imap();
	    } else {		
			D("BAD COMMAND: " + cmd);
		}
    }
    

    public void sendCmd() {
    	String cmd = mIn.getText().toString();
    	String tag = "X"+sIncCounter++;
    	StringBuilder sb = new StringBuilder();
    	
    	String[] items = cmd.split(" ");
    	for(int i=0; i<items.length-1; ++i) {
    		sb.append(items[i]).append(" ");
    	}
    	sb.append(items[items.length-1]);
    	conn.safeSendLine(tag+" " + cmd.trim());
    	mIn.setText("");
    	EventManager.I().ls();
    }
    
    private void cmd_imap() {
    }
    
    private class TestListener implements EventListener {
    	String mName = "";
    	public TestListener(String name) {
    		mName = name;
    	}
		public void onEvent(Event event) {
			// TODO Auto-generated method stub
			D(mName+" : "+event);
		} 
    	
    }
    private void cmd_listen() {
		EventManager.I().addListener(this);
		
		
		TaskManager tm  = TaskManager.I();
		TaskFactory tf  = TaskFactory.I();

		for(Task t : tf.createRandomTestTask(10, 1000, 1500)) {
			tm.assign(t);
		}
		
		new Thread() {
			public void run() {
				for(Task t : TaskFactory.I().createRandomTestTask(12, 1000, 1500)) {
					TaskManager.I().assign(t);
				}
			};
		}.start();
		
		new Thread() {
			public void run() {
				for(Task t : TaskFactory.I().createRandomTestTask(15, 1000, 1500)) {
					TaskManager.I().assign(t);
				}
			};
		}.start();
		
		new Thread() {
			public void run() {
				for(Task t : TaskFactory.I().createRandomTestTask(21, 1000, 1500)) {
					TaskManager.I().assign(t);
				}
			};
		}.start();
		
		System.out.println("=====================================");
		System.out.println("= Listener Group Info               =");
		System.out.println("=====================================");
		//em.ls();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
		if (conn != null) {
			conn.close();
		}
    }
    public void out(String text) {
    	mOut.append("#---------------------------------------------------------[ OUT ]\n"+text+"\r\n");
    }
    private void D(String msg) {android.util.Log.e("xxx", msg);}

}
