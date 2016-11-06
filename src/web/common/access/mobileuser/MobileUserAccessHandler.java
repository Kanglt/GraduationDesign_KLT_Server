package web.common.access.mobileuser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import lyu.klt.frame.controller.annotation.IAccessHandler;
import lyu.klt.frame.controller.security.AESTool;
import web.common.module.user.Position;
import web.common.module.user.User;
import web.common.module.user.UserOperationSet;

public class MobileUserAccessHandler implements IAccessHandler {

	private static Map<String, MobileUserAccessInfo> MAP = new HashMap<String, MobileUserAccessInfo>();
	public static final String REQUEST_ATTRIBUTE_USER = "MobileUserAccessHandler.User";

	private MobileUserAccessInfo getUserAccessInfo(String token) {
		if (!MAP.containsKey(token))
			return null;
		return MAP.get(token);
	}

	public String addAccess(User user, Position position,
			UserOperationSet operationMap) {
		String token = UUID.randomUUID().toString();
		MAP.put(token, new MobileUserAccessInfo(token, user, position, operationMap,
				new Timestamp(new Date().getTime())));

		return token;
	}

	public void clearAccess(String token) {
		MAP.remove(token);
	}

	public User getAccessUser(String token) {
		MobileUserAccessInfo uai = this.getUserAccessInfo(token);
		if (uai == null)
			return null;
		else
			return uai.getUser();
	}

	public MobileUserAccessInfo getAccessInfo(String token) {
		return this.getUserAccessInfo(token);
	}



	@Override
	public boolean isValid(HttpServletRequest request) {
		String jsonDataStr = request.getParameter("jsonDataStr");
		try {
			//先解密
			String data = AESTool.getInstance().decrypt(jsonDataStr);
			JSONObject dataObject = new JSONObject(data);
			String token = dataObject.getString("token");
			MobileUserAccessInfo mobileUserAccessInfo = getUserAccessInfo(token);
			if(mobileUserAccessInfo != null){
				//这边只判断用户是否登录成功只要登录成功过，以后可以加上时间戳
				long requestTime = Long.parseLong(dataObject.getString("requestTime"));//请求包数据时间戳
				Date date = new Date(requestTime);
				if(date.getTime() > mobileUserAccessInfo.getRequestTime().getTime()){
					//数据包中的请求时间大于上次请求时间才认为是有效的请求
					mobileUserAccessInfo.setRequestTime(new Timestamp(requestTime));
					return true;
				}else{
					//用户用旧的数据包来请求，这边认定是伪造请求
					System.err.println("用户用旧的数据包来请求，这边认定是伪造请求");
					return false;
				}
				
			}else{
				System.err.println("用户没有登录");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage().toString());
			return false;
		}
		return false;
	}

	@Override
	public boolean userHasRequiredOperationCode(HttpServletRequest request,
			String requiredOperationCode) {
		//这边设置用户都有权限操作接口
		return true;
	}
}
