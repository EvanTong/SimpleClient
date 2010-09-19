package client.core.model;


public class DispatchTask<T> extends Task {
	protected T             mElem = null;
	protected IDispatch<T>  mCall = null;
	
	public DispatchTask(T elem, IDispatch<T> callback) {
		mElem = elem;
		mCall = callback;
	}
	
	@Override
	public Event process() {		
		return mCall.onDispatch(mElem); // TODO: return event if needed
	}
}
