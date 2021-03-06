package web.common.module.carouselfigure;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import web.common.access.user.UserAccessHandler;


@PageController("/carouselFigureController")
public class CarouselFigureController {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String management() throws Exception {
		return "/page/webOperator/carouselFigureManagement.jsp";
	}

}