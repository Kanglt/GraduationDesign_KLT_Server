
import web.common.access.user.UserAccessHandler;
import cn.fjzxdz.frame.controller.annotation.Access;
import cn.fjzxdz.frame.controller.annotation.PageController;
import cn.fjzxdz.frame.controller.annotation.PageMethod;

@PageController("/{module_name_lower_case_first_character}Controller")
public class {module_name}Controller {

	@Access(handler = UserAccessHandler.class)
	@PageMethod
	public String management() throws Exception {
		return "/page/webOperator/{module_name_lower_case_first_character}Management.jsp"; 
	}

}