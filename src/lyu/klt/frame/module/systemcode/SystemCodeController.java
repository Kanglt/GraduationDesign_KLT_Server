package lyu.klt.frame.module.systemcode;

import web.common.access.user.UserAccessHandler;
import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;

@PageController("/systemCodeController")
public class SystemCodeController {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String management() throws Exception {
		return "/page/webAdmin/systemCodeManagement.jsp";
	}

}