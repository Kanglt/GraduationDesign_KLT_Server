package lyu.klt.frame.module.realtime;

import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;

@PageController("/realtimeController")
public class RealtimeController {

	@PageMethod
	public String index() {
		return "/page/public/realtime.jsp";
	}
}
