package web.common.module.person;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import web.common.access.user.UserAccessHandler;

@PageController("/personController")
public class PersonController {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String management() throws Exception {
		return "/page/webOperator/personManagement.jsp";
	}

}