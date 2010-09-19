package client.protocol.imap.task;

import java.io.IOException;

import client.core.EventManager;
import client.core.TaskManager;
import client.core.model.Event;
import client.core.model.EventListener;
import client.core.model.Task;
import client.protocol.imap.ImapConnection;
import client.protocol.imap.ImapEvent;
import client.protocol.imap.bean.CapabilityData;
import client.protocol.imap.bean.ImapTaggedResponse;
import client.protocol.imap.bean.MailboxData;
import client.protocol.imap.bean.Response;
import client.protocol.imap.bean.ResponseDone;
import client.protocol.imap.event.ImapConnectionClose;
import client.protocol.imap.event.ImapSendDone;
import client.protocol.imap.event.ImapSendError;

/**
 * <p>The {@link ImapCommand} is a kind of {@link Task} which can be processed by {@link TaskManager} </p>
 * @author amas
 */
public abstract class ImapCommand extends Task implements EventListener {
	protected static final String  PREFIX = "X";
	protected String         mTag         = PREFIX+sIncCounter.getAndDecrement();
	
	protected ImapConnection mConnection  = null;
	protected StringBuilder  mCmd         = new StringBuilder();
	protected Response       mResponse    = new Response();

	public ImapCommand() {
		
	}
	
	public void onEvent(Event event) {
		if(event == null) return;
		if(event instanceof ResponseDone) {
			onResponseDone((ResponseDone)event);
		} else if (event instanceof ImapConnectionClose) {
			EventManager.I().removeListener(mConnection.getUri(),this);
		} else if (event instanceof MailboxData) {
			onMailboxData((MailboxData) event);
		} else if (event instanceof CapabilityData) {
			onCapabilityData((CapabilityData)event);
		}
	}
	
	protected void onCapabilityData(CapabilityData capb) {
		
	}
	
	/**
	 * On receive {@link MailboxData}
	 * @param event
	 */
	protected void onMailboxData(MailboxData event) {
		
	}

	protected void onResponseDone(final ResponseDone resp) {		
		if(resp instanceof ImapTaggedResponse && ((ImapTaggedResponse)resp).getTag().equals(mTag)) {
			EventManager.I().removeListener(mConnection.getUri(), this);
			mResponse.setResponseDone(resp);
			EventManager.I().push(onPublishCommandResponse());	
		}		
	}
	
	protected ImapEvent onPublishCommandResponse() {
		mResponse.setFrom(""+getUid());
		mResponse.setNotifiers(getEventTo());
		return mResponse;
	}
	
	//TODO: be final ???
	@Override
	final protected Event process() {		
		EventManager.I().addListener(mConnection.getUri(),this);
		return onSendComand();
	}
	
	protected Event onSendComand() {
		try {
			mConnection.sendLine(getCommand());
		} catch (Exception e) {
			e.printStackTrace();
			return new ImapSendError(this, e.toString());
		}
		return new ImapSendDone(this);
	}
	
	@Override
	public String toString() {
		return String.format("(ImapCommand (%s))", getCommand());
	}
	
	protected String getCommand() {
		return mCmd.toString();
	}
}
