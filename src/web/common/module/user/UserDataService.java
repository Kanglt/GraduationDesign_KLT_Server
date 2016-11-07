package web.common.module.user;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * @author Jam 2016年4月1日 下午5:07:53
 * 
 */
public class UserDataService {

	/**
	 * 
	 * @author Jam 2016年4月1日 下午8:19:29
	 * @param loginId
	 * @param password
	 * @param operatorAddress
	 * @param operatorId
	 * @return
	 */
	public ProcedureResult register(String loginId, String password,
			String operatorAddress) throws Exception {

		if (this.loginIdUsed(loginId, operatorAddress))
			throw new BusinessException("000000", "该用户名已存在，请使用其他的用户名吧");

		String id = null;
		String operatorId = Procedure.USER_SYSTEM;
		Integer recordVersion = null;

		return this.sp_demo_user_management(Procedure.ACTION_INSERT, id,
				loginId, password, operatorId, operatorAddress, recordVersion);
	}

	private boolean loginIdUsed(String queryLoginId, String operatorAddress)
			throws Exception {
		String operatorId = null;
		ProcedureResult pr = this.sp_demo_user_loginId_existence_query(
				queryLoginId, operatorId, operatorAddress);
		return pr.getRecordSize() > 0;
	}

	public User login(String loginId, String password, String operatorAddress)
			throws Exception {

		ProcedureResult pr = this.sp_demo_user_login(loginId, password,
				operatorAddress);
		List<Map<String, String>> list = pr.getList();
		if (list.size() > 0) {
			String id = list.get(0).get("id");
			String userLoginId = list.get(0).get("loginId");
			String userName = list.get(0).get("name");
			String userPassword = list.get(0).get("password");

			User user = new User(id, userLoginId, userPassword, userName);
			return user;
		} else
			return null;
	}

	private ProcedureResult sp_demo_user_management(String action, String id,
			String loginId, String password, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure("sp_demo_user_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);
		procedure.addParameterString("loginId", loginId);
		procedure.addParameterString("password", password);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	private ProcedureResult sp_demo_user_read(String id, String operatorId,
			String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_demo_user_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult sp_demo_user_query(String queryId,
			String queryLoginId, String queryPassword, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_demo_user_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryLoginId", queryLoginId);
		procedure.addParameterString("queryPassword", queryPassword);

		procedure.addParameterString("queryUpdateBy", queryUpdateBy);
		procedure.addParameterString("queryUpdateByAddress",
				queryUpdateByAddress);
		procedure.addParameterTimestamp("queryUpdateTimeStart",
				queryUpdateTimeStart);
		procedure.addParameterTimestamp("queryUpdateTimeEnd",
				queryUpdateTimeEnd);
		procedure.addParameterString("queryCreateBy", queryCreateBy);
		procedure.addParameterString("queryCreateByAddress",
				queryCreateByAddress);
		procedure.addParameterTimestamp("queryCreateTimeStart",
				queryCreateTimeStart);
		procedure.addParameterTimestamp("queryCreateTimeEnd",
				queryCreateTimeEnd);

		procedure.addParameterInteger("page", page);
		procedure.addParameterInteger("pageSize", pageSize);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult sp_demo_user_login(String loginId, String password,
			String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_demo_user_login");

		procedure.addParameterString("loginId", loginId);
		procedure.addParameterString("password", password);

		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult sp_demo_user_loginId_existence_query(
			String queryLoginId, String operatorId, String operatorAddress)
			throws Exception {

		Procedure procedure = new Procedure(
				"sp_demo_user_loginId_existence_query");

		procedure.addParameterString("queryLoginId", queryLoginId);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

}