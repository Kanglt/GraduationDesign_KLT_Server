package lyu.klt.frame.controller.annotation;

import lyu.klt.frame.controller.context.ControllerContext;

public class ControllerLogMessage {

	private String message;

	public ControllerLogMessage(String message, Object... parameter) {
		super();
		this.message = String.format(message, parameter);
	}

	@Override
	public String toString() {
		String id = ControllerContext.getCurrentInstance().getId();
		return String.format("[ControllerContextId：%s] %s", id, this.message);
	}

}
