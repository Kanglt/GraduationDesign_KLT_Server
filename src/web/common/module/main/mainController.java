package web.common.module.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import web.common.access.user.UserAccessHandler;
import web.common.access.user.UserAccessInfo;
import web.common.module.menu.MenuDataService;
import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.ProcedureResult;

/**   
 * 系统主页
 * @Author   戴珍贵
 * @Version   1.0 
 * @CreateTime 2016年7月19日 上午11:26:36   
 */ 
@PageController("/mainController")
public class mainController {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String index() throws Exception {
		UserAccessInfo userAccessInfo = new UserAccessHandler().getAccessInfo();
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		request.setAttribute(Constants.LOGIN_USER, userAccessInfo.getUser());
		request.setAttribute(Constants.LOGIN_USER_POSITION,
				userAccessInfo.getPosition());

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		MenuDataService mds = new MenuDataService();
		ProcedureResult pr = mds.getUserMenuTreeList(operatorId, operatorAddress);
		List<Menu> list = pr.getListAsObject(Menu.class);

		Menu root = this.convertListToTree(list);
		request.setAttribute(Constants.MENU_TREE, root);

		return "/page/webOperator/main.jsp";
	}

	private Menu convertListToTree(List<Menu> list) {
		int depth = 0;
		Menu root = null;
		for (Menu menu : list) {
			if (menu.getParentId().isEmpty()) {
				root = menu;
				root.setParent(true);
				root.setDepth(depth);
				break;
			}
		}
		root.buildChildren(list);
		return root;
	}

}