package client.protocol.imap.task;

import java.io.IOException;
import java.util.Vector;

import client.core.ConnectionManager;
import client.core.model.Event;
import client.core.model.Task;
import client.core.model.project.ITodo;
import client.core.model.project.Project;
import client.protocol.imap.ImapConnection;
import client.protocol.imap.ImapConnection.LoggedIn;
import client.protocol.imap.ImapConnection.Security;
import client.protocol.imap.bean.Response;
import client.protocol.imap.bean.ResponseData;
import client.protocol.imap.bean.SearchData;

public class _DownloadAll extends Project {
	ImapConnection  conn = null;
	Vector<Integer> msgs = null;
	
	@Override
	protected void onStart() {
		try {
			conn.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getListenerGroupUri() {
		return conn.getUri();
	}

	
	public _DownloadAll(String host, int port, Security sec, String user,
			String passwd) {		
		super(host + ":" + port + "/" + user);
		conn = ConnectionManager.I().createImapConnection(host,
				port, sec, user, passwd);
		conn.getNotifiers().addNotifyUri(myself());
		
		
		addTodo(new ITodo() {			
			public Task launchTask(Project project, Event triggerEvent) {
				if(triggerEvent instanceof LoggedIn) {
					return new CmdSelect(conn, "inbox");
				}
				return null;
			}
		});
		
		addTodo(new ITodo() {	
			public Task launchTask(Project project, Event triggerEvent) {
				if(triggerEvent instanceof Response) {
					Response resp = (Response)triggerEvent;
					if(resp.isOK()) {
						return new CmdSearch(conn, "SEARCH ALL");
					}
				}
				return null;
			}
		});
		
		addTodo(new ITodo() {	
			public Task launchTask(Project project, Event triggerEvent) {
				if(triggerEvent instanceof Response) {
					Response resp = (Response)triggerEvent;
					if(resp.isOK()) {
						if (msgs == null) {
							for (ResponseData d : resp.getData()) {
								if (d instanceof SearchData) {
									// save msg sn
									msgs = ((SearchData) d).getSearchResult();
									
									CmdFetch cmdFetch = null;
									if(!msgs.isEmpty()) {
										int next = msgs.remove(0);
										cmdFetch = new CmdFetch(conn, ""+next).withBodyStructure();
									}
									
									int max = 100;
									while(!msgs.isEmpty() && max > 0) {
										project.addTodo(new ITodo() {	
											public Task launchTask(Project project, Event triggerEvent) {
												if(triggerEvent instanceof Response) {
													Response resp = (Response)triggerEvent;
													if(resp.isOK()) {
														return new CmdFetch(conn, ""+msgs.remove(0)).withBodyStructure();
													}
												}
												return null;
											}
										});
										max--;
									}
									return cmdFetch;
								}
							}
						} 

					}
				}
				return null;
			}
		});
	}
}
