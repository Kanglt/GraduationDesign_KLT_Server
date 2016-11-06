package lyu.klt.frame.module.realtime;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.exception.RealtimeException;
import lyu.klt.frame.controller.handler.WebServiceMessage;

/**
 * @author Jam 2016年3月30日 下午7:57:51
 * 
 */
@WebServiceController(value = "Realtime")
public class RealtimeWebServiceController implements MessageSubscriber {

	private RealtimeDataService realtimeDataService = new RealtimeDataService();
	private Object monitor = new Object();
	private boolean timeout = false;
	private String messageType = null;

	@WebServiceMethod
	public String newMessage(String messageType) throws Exception {

		String operatorId = "";
		String operatorAddress = "";

		realtimeDataService.newMessage(operatorId, operatorAddress);

		MessageCenter.notifyAllSubscriber(String.format("%s: %s",
				new Date().toString(), messageType));

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage("ok");

		return msg.toString();
	}

	@WebServiceMethod(realtime = true)
	public String getMessage() throws Exception {

		MessageCenter.register(this);

		try {
			String operatorId = "";
			String operatorAddress = "";

			if (this.messageType != null) {
				WebServiceMessage msg = new WebServiceMessage();
				msg.put("messageType", this.messageType);
				return msg.toString();
			}

			synchronized (this.monitor) {
				while (true) {
					// 先设置好超时处理，不能放在this.monitor.wait()语句后面
					this.startTimeoutThread();

					this.monitor.wait();

					if (this.timeout)
						throw new RealtimeException("000000", String.format(
								"%s: 实时通信long polling超时（正常）", this.getClass()
										.getName()));

					if (this.messageType != null) {
						WebServiceMessage msg = new WebServiceMessage();
						msg.put("messageType", this.messageType);
						return msg.toString();
					}
				}
			}
		} finally {
			MessageCenter.unregister(this);
		}
	}

	private void startTimeoutThread() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				RealtimeWebServiceController.this.timeout = true;
				synchronized (RealtimeWebServiceController.this.monitor) {
					RealtimeWebServiceController.this.monitor.notifyAll();
				}
			}
		}, 60 * 1000);
	}

	@Override
	public void notifyNewMessage(String messageType) {
		synchronized (this.monitor) {
			this.messageType = messageType;
			this.monitor.notifyAll();
		}
	}
}
