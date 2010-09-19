package client.core;

import java.util.concurrent.ConcurrentHashMap;

import client.core.model.net.Connection;
import client.core.model.net.PlainConnection;
import client.core.model.net.SSLConnection;
import client.protocol.imap.ImapConnection;


/**
 * Manager multi-connections, singleton pattern
 * @author amas
 */
public class ConnectionManager {
	public static ConnectionManager sInstance = new ConnectionManager();
	private ConcurrentHashMap<String, Connection> mConnections = new ConcurrentHashMap<String, Connection>(4);

	private ConnectionManager() {
		
	}
	
	public static ConnectionManager I() {
		return sInstance;
	}
	
	/**
	 * <p>
	 * e.g:
	 * <li>smtp://borqsmail@gmail.com/smtp.google.com:25</li>
	 * <li>imap4://borqsmail@gmail.com/imap.google.com:578/inbox</li>
	 * </p>
	 * @param uri
	 * @return
	 */
	public Connection createConnection(String uri) {
		Connection conn = getConnection(uri);
		if(conn == null) {
			String   scheme  = uri.split("://")[0];			
			if(scheme.contains("+ssl")) {
				conn = new SSLConnection(uri);
			} else {
				conn = new PlainConnection(uri);
			}			
			putConnection(uri, conn);
		}
		return conn;
	}
	
	/**
	 * <p> Create a imap connection </p>
	 * @param host
	 * @param port
	 * @param security
	 * @param account
	 * @param passwd
	 * @return
	 */
	public ImapConnection createImapConnection(String host, 
										   int    port, 
										   ImapConnection.Security security,
										   String account,
										   String passwd) {
		ImapConnection conn = new ImapConnection(host,port,security,account,passwd);
		putConnection(conn.getUri(), conn);
		return conn;	
	}
	
	/**
	 * <p>Get connection object by give uri</p>
	 * @param uri
	 * @return connection object
	 */
	public Connection getConnection(String uri) {
		return mConnections.get(uri);
	}
	
	public Connection putConnection(String uri, Connection conn) {
		return mConnections.put(uri, conn);
	}
	
	public boolean testConnection(String uri) {
		return false;
	}
	
	public void removeConnection(String uri) {
		Connection rm = mConnections.remove(uri);
		rm.clearup(); // remove related listener group
	}
}
