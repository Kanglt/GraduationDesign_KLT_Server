package lyu.klt.frame.module.realtime;

public interface MessageSubscriber {
	public void notifyNewMessage(String messageType);
}
