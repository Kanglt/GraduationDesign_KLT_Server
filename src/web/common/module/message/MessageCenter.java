package web.common.module.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类可以考虑增加消息队列缓存
 * @author Administrator
 *
 */
public class MessageCenter {

	private static List<MessageSubscriber> LIST = new ArrayList<MessageSubscriber>();

	public static void register(MessageSubscriber messageSubscriber) {
		synchronized (LIST) {
			LIST.add(messageSubscriber);
		}
	}

	public static void unregister(MessageSubscriber messageSubscriber) {
		synchronized (LIST) {
			LIST.remove(messageSubscriber);
		}
	}

	public static void notifyAllSubscriber(String messageType) {
		synchronized (LIST) {
			for (MessageSubscriber subscriber : LIST) {
				subscriber.notifyNewMessage(messageType);
			}
		}
	}
}
