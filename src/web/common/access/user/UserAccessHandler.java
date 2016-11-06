package web.common.access.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.common.module.user.UserOperationSet;
import web.common.module.user.Position;
import web.common.module.user.User;
import lyu.klt.frame.controller.annotation.IAccessHandler;
import lyu.klt.frame.controller.context.ControllerContext;

public class UserAccessHandler implements IAccessHandler {

	private static final String COOKIE_TOKEN = "UserAccessHandlerDefault";
	private static final String COOKIE_USER = "UserAccessHandlerExtra";

	private static Map<String, UserAccessInfo> MAP = new HashMap<String, UserAccessInfo>();
	public static final String REQUEST_ATTRIBUTE_USER = "UserAccessHandler.User";

	@Override
	public boolean isValid(HttpServletRequest request) {
		UserAccessInfo uai = this.getUserAccessInfo(request);// MAP.get(token);
		if (uai == null)
			return false;

		String token = uai.getToken();
		Timestamp now = new Timestamp(new Date().getTime());

		// 距离上次"登录"超过2小时，必须重新登录
		if (now.getTime() >= uai.getLoginTime().getTime() + 1000 * 60 * 120) {
			MAP.remove(token);
			return false;
		}

		// 距离上次"操作"超过15分钟，必须重新登录
		if (now.getTime() >= uai.getRenewTime().getTime() + 1000 * 60 * 15) {
			MAP.remove(token);
			return false;
		}

		// 刷新操作时间
		uai.setRenewTime(new Timestamp(now.getTime()));

		ControllerContext.getCurrentInstance().getRequest()
				.setAttribute(REQUEST_ATTRIBUTE_USER, uai.getUser());

		return true;
	}

	private UserAccessInfo getUserAccessInfo(HttpServletRequest request) {
		String token = "";

		Cookie cookie = this.getTokenCookie(request);
		if (cookie != null) {
			token = cookie.getValue();
		} else {
			token = this.getTokenFromRequest(request);
		}

		if (!MAP.containsKey(token))
			return null;
		return MAP.get(token);
	}

	/**
	 * 用来从request parameters中获取cookie信息，上传时会在parameters中附带cookie字符串
	 * 
	 * @param request
	 * @return
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String cookie = request.getParameter("cookie");
		if (cookie == null || cookie.isEmpty())
			return null;

		String[] keyPairs = cookie.split(";");
		for (String keyPair : keyPairs) {
			String[] s = keyPair.split("=");
			if (s[0].equals(COOKIE_TOKEN))
				return s[1];
		}

		return null;
	}

	private Cookie getTokenCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		// 然后迭代之
		if (cookies != null && cookies.length > 0) { // 如果没有设置过Cookie会返回null
			for (Cookie cookie : cookies) {
				if (COOKIE_TOKEN.equals(cookie.getName()))
					return cookie;
			}
		}
		return null;
	}

	private Cookie getUserCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		// 然后迭代之
		if (cookies != null && cookies.length > 0) { // 如果没有设置过Cookie会返回null
			for (Cookie cookie : cookies) {
				if (COOKIE_USER.equals(cookie.getName()))
					return cookie;
			}
		}
		return null;
	}

	public String addAccess(User user, Position position,
			UserOperationSet operationMap) {
		String token = UUID.randomUUID().toString();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();
		Cookie cookie = new Cookie(COOKIE_TOKEN, token);
		response.addCookie(cookie);
		Cookie userCookie = new Cookie(COOKIE_USER, user.getId());
		response.addCookie(userCookie);

		MAP.put(token, new UserAccessInfo(token, user, position, operationMap,
				new Timestamp(new Date().getTime())));

		return token;
	}

	public void clearAccess() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		Cookie cookie = this.getTokenCookie(request);
		if (cookie != null) {
			String token = cookie.getValue();
			MAP.remove(token);
		}

		response.addCookie(new Cookie(COOKIE_TOKEN, ""));
	}

	public User getAccessUser() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		UserAccessInfo uai = this.getUserAccessInfo(request);
		if (uai == null)
			return null;
		else
			return uai.getUser();
	}

	public UserAccessInfo getAccessInfo() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		return this.getUserAccessInfo(request);
	}

	public String getLastLoginUserId(HttpServletRequest request) {
		Cookie cookie = this.getUserCookie(request);
		if (cookie != null) {
			String userId = cookie.getValue();
			return userId;
		} else
			return "";
	}

	@Override
	public boolean userHasRequiredOperationCode(HttpServletRequest request,
			String requiredOperationCode) {
		UserAccessInfo userAccessInfo = this.getAccessInfo();
		if (userAccessInfo.getOperationMap().exists(requiredOperationCode))
			return true;
		else
			return false;
	}
}
