package client.core.model.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PlainConnection extends Connection {
	
	public PlainConnection(String uri) {
		super(uri);
	}
	
	@Override
	public Socket onCreateSocket(String host, int port) throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(host, port));
		return socket;
	}
	
	@Override
	public Sender onCreateSender(OutputStream ostream) {
		return new Sender(ostream);
	}
	
	@Override
	public synchronized void sendLine(String line) {
		try {
			getSender().sendLine(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get Receiver (Do not start Receiver thread in this function!)
	 * @param istream
	 * @return
	 */
	public Receiver onCreateReceiver(InputStream istream) {
		return mProt.newReceiver(istream, mEventTo);
	}
}
