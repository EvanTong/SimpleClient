package client.core.model.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import client.core.EventManager;
import client.core.model.Event;
import client.core.model.EventListener;
import client.core.model.Notifiers;
import client.core.model.project.ISubscribable;

/**
 * A Connection is a kind of resource.
 * <li>Socket for data transfer</li>
 * <li>Receiver for receive data from server</li>
 * <li>Sender for send request to server</li>
 * <hr>
 * A Connection object has an unique URI.
 * A Connection will register a listener group with it's URI
 * If you care about the event of specify Connection, you have two choices:
 * <li>1. Register your listener group to the Connection listener group.</p>
 * <li>2. Subscribe it's event to your listener group, That's means you should have a listener group first.</li>
 * @author amas
 */
public abstract class Connection implements ISubscribable , EventListener {
	protected static AtomicInteger   sIncCounter = new AtomicInteger(0);
	
	protected String    mUri     = null;
	protected Protocol  mProt    = null;
	protected Socket    mSock    = null;
	protected Receiver  mRecv    = null;
	protected Sender    mSend    = null;
	protected String    mHost    = null;
	protected int       mPort    = -1;
	protected boolean   mIsOn    = false;
	protected Notifiers mEventTo = null;
	
	/** 
	 * "smtp://borqsmail@gmail.com@smtp.gmail.com:25" 
	 *  imap://imap.aol.com:143
	 *  pop3://pop.aol.com:110
	 *  smtp+auth+://smtp.aol.com:587
	 *  imap+ssl+://imap.gmail.com:993
	 *  pop3+ssl+://pop.gmail.com:995
	 */
	public Connection(String uri) {
		mUri = uri;
		//TODO: enhance it
		String[] items = uri.split(":");
		mHost = items[1].replace("/", "");
		mPort = Integer.parseInt(items[2]);	
		mProt = Protocol.getProtocolByUri(uri);
		System.out.println("get prot : " + mProt);
		mEventTo = new Notifiers(mUri);
		EventManager.I().addListenerGroup(mUri);
	}
	
	public Connection(String host, int port, Protocol protocol, String uri) {
		mHost = host;
		mPort = port;
		mProt = protocol;
		mUri  = uri;
		mEventTo = new Notifiers(mUri);
		EventManager.I().addListener(mUri, this);
	}
	
	protected void setUri(String uri) {
		mUri = uri; 
	}
	
	public String getUri() {
		return mUri;
	}
	
	public void setNotifiers(Notifiers eventTo) {
		mEventTo = eventTo;
	}
	
	public Notifiers getNotifiers() {
		return mEventTo;
	}
	
	/**
	 * Is connection available
	 * @return true ? ok : bad
	 */
	public boolean isAvailable() {
		return mIsOn; 
	}
		
	/**
	 * Open connections and connect to the target.
	 * The Sender and Receiver will be created for comunication.
	 * @return true ? ok : error
	 * @throws IOException
	 */
	public boolean open() throws IOException {
		if(!isAvailable()) {
			mSock = onCreateSocket(mHost, mPort);
			mRecv = onCreateReceiver(mSock.getInputStream());
			mRecv.start();
			mSend = onCreateSender(mSock.getOutputStream());	
			mIsOn = true;
		}
		return true;
	}
	
	public Socket onCreateSocket(String host, int port) throws IOException {
		return null;
	}
	
	public Sender onCreateSender(OutputStream ostream) {
		return null;
	}
	
	/**
	 * Get Receiver (Do not start Receiver thread in this function!)
	 * @param istream
	 * @return
	 */
	public Receiver onCreateReceiver(InputStream istream) {
		return mProt.newReceiver(istream, mEventTo);
	}
	
	/**
	 * Get Sender 
	 * @return
	 */
	public Sender getSender() { 
		return mSend;
	}
	
	/**
	 * TODO: async send ???
	 * Send line to the server
	 * @param line
	 * @throws IOException 
	 */
	public void sendLine(String line) throws IOException {
		mSend.sendLine(line);
	}
	
	/**
	 * @param line
	 * @return true or failed false
	 */
	public boolean safeSendLine(String line) {
		try {
			sendLine(line);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 *  Close connection 
	 */
	public void close() {
		if(mSock != null) {
			try {
				mSock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mSock = null;
			mIsOn = false;
		}
	}
	
	/**
	 * remove listener group
	 */
	public void clearup() {
		close();
		EventManager.I().removeListenerGroup(mUri);
	}
	
	public void subscribeTo(String uri) {
		mEventTo.addNotifyUri(uri);
	}
	
	public void onEvent(Event event) {
		
	}
	
	public void pushEvent(Event event) {
		event.setFrom(getUri());
		event.setNotifiers(mEventTo);
		EventManager.I().push(event);
	}
	
	
}
