package web.common.module.message;

public interface MessageSubscriber {
	public void notifyNewMessage(String messageType);
}
