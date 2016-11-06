package web.common.module.user;

public class Position {

	private String id;
	private String departmentId;
	private String code;
	private String description;
	private String userId;

	public Position() {
		super();
	}

	public Position(String id, String departmentId, String code,
			String description, String userId) {
		this.id = id;
		this.departmentId = departmentId;
		this.code = code;
		this.description = description;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
