package lyu.klt.frame.controller.handler;

import org.json.JSONObject;

public class WebServiceMessage extends JSONObject {

	private static final String MESSAGE = "msg";

	public WebServiceMessage() throws Exception {
		super();
	}

	public void setMessage(String msg) throws Exception {
		this.put(MESSAGE, msg);
	}

	public void setSizeInfo(int page, int pageSize, int maxSize)
			throws Exception {
		JSONObject sizeInfo = new JSONObject();

		sizeInfo.put("page", page);
		int maxPage = maxSize / pageSize;
		if (maxSize % pageSize > 0)
			maxPage++;
		sizeInfo.put("pageSize", pageSize);
		sizeInfo.put("maxPage", maxPage);
		sizeInfo.put("maxSize", maxSize);

		this.put("sizeInfo", sizeInfo);
	}
}