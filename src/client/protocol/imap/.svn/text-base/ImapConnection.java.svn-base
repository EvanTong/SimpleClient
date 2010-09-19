package client.protocol.imap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import client.core.TaskManager;
import client.core.model.Event;
import client.core.model.Task;
import client.core.model.TimeStamp;
import client.core.model.TimeStamp.Tag;
import client.core.model.net.Connection;
import client.core.model.net.Receiver;
import client.core.model.net.Sender;
import client.protocol.IMAP;
import client.protocol.imap.bean.CapabilityData;
import client.protocol.imap.bean.Greeting;
import client.protocol.imap.bean.ImapTaggedResponse;
import client.protocol.imap.bean.Response;
import client.protocol.imap.event.ImapConnectionClose;


/**
 * An IMAP4rev1 Connection object.
 * An IMAP4rev1 connection consists of :
 *  1. establishment of a client/server network connection
 *  2. initial greeting from the server
 *  3. client/server interactions
 *  	3.1. client command
 *      3.2. server data
 *      3.3. server completion result response.
 * @author amas
 */
public class ImapConnection extends Connection {
	static final int 	SOCKET_CONNECT_TIMEOUT = 60000;
	static final int 	SOCKET_READ_TIMEOUT    = 10*30000;
	
	public static enum State {
		UnConnected,       // stands for not connected
		NotAuthenticated,
		Authenticated,
		Selected,
		Logout
	};
	
	public static enum Security {
		Plain,
		SSL,
		TLS
	}
	TimeStamp        mTimeStamp   = new TimeStamp();
	private State    mStatus      = State.UnConnected;
	private Security mSecurity    = Security.Plain;
	private String   mAccount     = "";
	private String   mPasswd      = "";
	private String   mLoginCmd    = "";
	private String   mLoginCmdTag = "X00";

	
	public class LoggedIn extends Event {
		public LoggedIn() {
			setFrom(getUri());
		}
	}
	
	public ImapConnection(String uri) {
		super(uri);
	}
	
	/**
	 * @param host
	 * @param port
	 * @param security
	 * @param account
	 * @param passwd
	 */
	public ImapConnection (
			   String host, 
			   int    port, 
			   ImapConnection.Security security,
			   String account,
			   String passwd) {
		super(host,port, new IMAP(), "imap://"+account+"@"+host+":"+port+"/"+sIncCounter.getAndIncrement());
		mAccount  = account;
		mPasswd   = passwd;
		mSecurity = security;
		mLoginCmd = String.format("%s LOGIN %s %s", mLoginCmdTag, account, passwd);
	}
	
	
	@Override
	public Socket onCreateSocket(String host, int port) throws IOException {
		//TODO:
		// 1. support TLS
		// 2. SSL right???     
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(host, port), SOCKET_CONNECT_TIMEOUT);
		if(mSecurity == Security.SSL) { 
			System.err.println("UPGRADE SSL");
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket ss = (SSLSocket) factory.createSocket(socket, host,port, true);
			ss.setEnabledProtocols(new String[] { "TLSv1", "SSLv3" });
			ss.setUseClientMode(true);
			ss.startHandshake();
			socket = ss;
		}
		socket.setSoTimeout(SOCKET_READ_TIMEOUT);
		socket.setKeepAlive(true);
		return socket;
	}
	
	public Receiver onCreateReceiver(InputStream istream) {
		return new ImapReceiver(istream, mEventTo);
	}
	
	public Sender onCreateSender(OutputStream ostream) {
		return new Sender(ostream);
	}
	
	/**
	 * Get connection state
	 * @return
	 */
	public State getState() {
		return mStatus;
	}
	
	public void setState(State state) {
		mStatus = state;
	}
	
	/**
	 * Open connections and connect to the target.
	 * The Sender and Receiver will be created for comunication.
	 * @return true ? ok : error
	 * @throws IOException
	 */
	public boolean open() throws IOException {
		if(getState() != State.Authenticated) {
			mTimeStamp.touch(Tag.START_TIME);
			try{
				System.err.println("IMAP.CONN.OPEN         : " + mUri);
				mSock = onCreateSocket(mHost, mPort);
				System.err.println("IMAP.CONN.CREATE SOCKET: " + mSock);
				mRecv = onCreateReceiver(mSock.getInputStream());
				// we need create sender before start login
				mSend = onCreateSender(mSock.getOutputStream());
				// handle greeting
				onImapEventGreeting(((ImapReceiver)mRecv).getGreeting());
				System.err.println("IMAP.CONN.CREATE RECVER: " + mRecv);
				mRecv.start();			
				System.err.println("IMAP.CONN.CREATE SENDER: " + mSend);
				//mIsOn = true;
			} catch (ConnectException e) {
				e.printStackTrace();
				// TODO connection open failed, push event 
				// TODO clear staff
				//pushEvent();
			} catch (SocketException e) { 
				System.err.println("SOCKET EXCEPTION: " + e);
			}
		}
		return true;
	}
	
	/**
	 *  Close connection 
	 */
	public void close() {
		System.err.println(mUri+"    CLOSED");
		if(mSock != null) {
			try {
				mRecv.shutdown();
				mSock.close();
				mRecv = null;
				mSock = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			mIsOn = false;
			setState(State.UnConnected);
		}
	}
	
	@Override
	public void onEvent(Event event) {
		//TODO: make isFrom a interface	
		if(event.isFrom(mRecv.getName())) {
			onEventFromReceiver(event);
		}
	}
	
	/**
	 * Event from imap server pushed by receiver
	 * @param event
	 */
	public void onEventFromReceiver(Event event) {
		if(event instanceof ImapConnectionClose) {
			close();
			return;
		} else if (event instanceof ImapTaggedResponse) { 		
			ImapTaggedResponse tr = (ImapTaggedResponse)event;
			if(tr.getTag().equals(mLoginCmdTag)) {
				if(tr.isOK()) {
					setState(State.Authenticated);
					mTimeStamp.touch(Tag.END_TIME);
					pushEvent(new LoggedIn());
					System.err.println("IMAP.CONN.ON: " + mTimeStamp.getLifeTimeSec() + " sec");
				} else if (tr.isNo() || tr.isBad()) {
					setState(State.NotAuthenticated);
				}
			}
		} else if (event instanceof CapabilityData) {
			//TODO: save capability
		}
	}
	
	private void onImapEventGreeting(Greeting event) {
		if(event.isOK()) {
			setState(State.NotAuthenticated);
			try {
				sendLine(mLoginCmd);
			} catch (IOException e) {
				close();
				e.printStackTrace();
			}
		} else if(event.isBYE()) {
			System.out.println("SERVER SAY GOODBYE");
		}
	}
}
