package client.core.model.net;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * A SSL connection based on plain connection
 * The simple idea is:
 *  1. create a plain socket
 *  2. upgrade the plain socket to SSL socket
 * @author amas
 */
public class SSLConnection extends PlainConnection {

	public SSLConnection(String uri) {
		super(uri);
	}
	
	@Override
	public Socket onCreateSocket(String host, int port) throws IOException {
		Socket socket = super.onCreateSocket(host, port);
		// upgrade to ssl socket
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket ss = (SSLSocket) factory.createSocket(socket, host,port, true);
		ss.setEnabledProtocols(new String[] { "TLSv1", "SSLv3" });
		ss.setUseClientMode(true);
		ss.startHandshake();
		socket = ss;
		return socket;
	}
}
