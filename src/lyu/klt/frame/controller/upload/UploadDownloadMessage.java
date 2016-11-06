package lyu.klt.frame.controller.upload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import lyu.klt.frame.utils.Utils;

public class UploadDownloadMessage extends JSONObject {

	private static Log log = LogFactory.getLog(UploadDownloadMessage.class);

	public void setOk() {
		try {
			this.put("code", "ok");
		} catch (JSONException e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

	public void setFailed() {
		try {
			this.put("code", "failed");
		} catch (JSONException e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

	public void setMessage(String msg) {
		try {
			this.put("msg", msg);
		} catch (JSONException e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

}
