package web.common.access.mobileuser;

import java.sql.Timestamp;

import web.common.module.user.UserOperationSet;
import web.common.module.user.Position;
import web.common.module.user.User;

public class MobileUserAccessInfo {

	private String token;
	private Timestamp loginTime;
	private Timestamp renewTime;
	private User user;
	private Position position;
	private UserOperationSet operationSet;
	private Timestamp requestTime;//请求包数据时间戳

	public MobileUserAccessInfo(String token, User user, Position position,
			UserOperationSet operationMap, Timestamp timestamp) {
		this.token = token;
		this.user = user;
		this.position = position;
		this.operationSet = operationMap;
		this.loginTime = new Timestamp(timestamp.getTime());
		this.renewTime = new Timestamp(timestamp.getTime());
		this.requestTime =  new Timestamp(timestamp.getTime());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public UserOperationSet getOperationMap() {
		return operationSet;
	}

	public void setOperationSet(UserOperationSet operationSet) {
		this.operationSet = operationSet;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public Timestamp getRenewTime() {
		return renewTime;
	}

	public void setRenewTime(Timestamp renewTime) {
		this.renewTime = renewTime;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

}
