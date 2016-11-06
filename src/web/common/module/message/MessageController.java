package web.common.module.message;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import web.common.access.user.UserAccessHandler;

@PageController("/messageController")
public class MessageController {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String management() throws Exception {
		return "/page/webOperator/localTaxMessageManagement.jsp";
	}

}