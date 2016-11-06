package web.common.module.user;

import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import web.common.access.user.UserAccessHandler;

@PageController("/userController")
public class UserController {

	@PageMethod
	public String login() {
		new UserAccessHandler().clearAccess();
		return "/page/webOperator/login.jsp";
	}
}