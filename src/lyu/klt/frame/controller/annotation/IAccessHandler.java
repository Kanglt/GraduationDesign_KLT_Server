package lyu.klt.frame.controller.annotation;

import javax.servlet.http.HttpServletRequest;

public interface IAccessHandler {

	public boolean isValid(HttpServletRequest request);

	public boolean userHasRequiredOperationCode(HttpServletRequest request,
			String requiredOperationCode);
}
