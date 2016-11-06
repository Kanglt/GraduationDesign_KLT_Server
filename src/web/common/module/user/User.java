package web.common.module.user;

public class User {

	private String id;
	private String loginId;
	private String password;
	private String name;

	public User() {
		super();
	}

	public User(String id, String loginId, String password, String name) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
