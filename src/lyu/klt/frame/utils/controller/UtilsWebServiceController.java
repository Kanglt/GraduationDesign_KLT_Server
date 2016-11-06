package lyu.klt.frame.utils.controller;

import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.utils.IdGenerator;

@WebServiceController("Utils")
public class UtilsWebServiceController {

	@WebServiceMethod
	public String getNewId() throws Exception {
		WebServiceMessage msg = new WebServiceMessage();
		msg.put("code", "ok");
		msg.put("id", IdGenerator.getNewId());
		msg.setMessage(MultiLanguage.getResource("000000", "登录成功"));
		return msg.toString();
	}
}