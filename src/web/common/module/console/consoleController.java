package web.common.module.console;

import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;

@PageController("/consoleController")
public class consoleController {

	@PageMethod
	public String index() {
		return "/page/webOperator/console.jsp";
	}

}