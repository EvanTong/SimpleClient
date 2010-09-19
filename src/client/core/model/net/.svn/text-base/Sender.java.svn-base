package client.core.model.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Sender {
	Writer       mWriter    = null;
	OutputStream mOstream   = null;
	
	public Sender(OutputStream ostream) {
		mWriter = new BufferedWriter(new OutputStreamWriter(ostream));
	}
	
	/** send line to server side 
	 * @throws IOException */
	public void sendLine(String line) throws IOException {
		if (mWriter != null) {
			System.out.println(">>>>>>>>" + line);
			if (mWriter instanceof BufferedWriter) {
				((BufferedWriter) mWriter).write(line+"\r\n"); // TODO: make it CRLF
				mWriter.flush();
			}
		}
	}
}
