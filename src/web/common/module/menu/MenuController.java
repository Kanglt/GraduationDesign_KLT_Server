package web.common.module.menu;


import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import web.common.access.user.UserAccessHandler;

@PageController("/menuController")
public class MenuController {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String management() throws Exception {
		return "/page/webOperator/menuManagement.jsp"; 
	}

}