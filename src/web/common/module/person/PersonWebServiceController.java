package web.common.module.person;

import java.sql.Timestamp;

import web.common.access.user.UserAccessHandler;
import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.controller.method.parameter.datatype.FormData;
import lyu.klt.frame.database.core.ProcedureResult;

@WebServiceController("Person")
public class PersonWebServiceController {

	private PersonDataService personDataService = new PersonDataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getPersonList(FormData formData, Integer page)
			throws Exception {

		formData.log();

		String queryCustomerId = formData.getString("queryCustomerId", "客户编号",
				true);
		String queryCustomerType = formData.getString("queryCustomerType",
				"客户类型", true);
		String queryCustomerName = formData.getString("queryCustomerName",
				"客户名称", true);
		String queryCustomerPaymentType = formData.getString(
				"queryCustomerPaymentType", "客户付款类型", true);
		String queryVip = formData.getString("queryVip", "是否会员", true);
		Timestamp queryRegisterDateStart = formData.getTimestamp(
				"queryRegisterDateStart", "注册日期开始", true);
		Timestamp queryRegisterDateEnd = formData.getTimestamp(
				"queryRegisterDateEnd", "注册日期截止", true);
		Double queryAccountBalanceStart = formData.getDouble(
				"queryAccountBalanceStart", "账户余额", true);
		Double queryAccountBalanceEnd = formData.getDouble(
				"queryAccountBalanceEnd", "账户余额", true);
		Integer queryEmployeeCountStart = formData.getInteger(
				"queryEmployeeCountStart", "员工数", true);
		Integer queryEmployeeCountEnd = formData.getInteger(
				"queryEmployeeCountEnd", "员工数", true);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		Integer pageSize = Constants.COMMON_PAGE_SIZE;

		String queryId = null;
		String queryUpdateBy = null;
		String queryUpdateByAddress = null;
		Timestamp queryUpdateTimeStart = null;
		Timestamp queryUpdateTimeEnd = null;
		String queryCreateBy = null;
		String queryCreateByAddress = null;
		Timestamp queryCreateTimeStart = null;
		Timestamp queryCreateTimeEnd = null;

		ProcedureResult pr = this.personDataService.getPersonList(queryId,
				queryCustomerId, queryCustomerType, queryCustomerName,
				queryCustomerPaymentType, queryVip, queryRegisterDateStart,
				queryRegisterDateEnd, queryAccountBalanceStart,
				queryAccountBalanceEnd, queryEmployeeCountStart,
				queryEmployeeCountEnd, queryUpdateBy, queryUpdateByAddress,
				queryUpdateTimeStart, queryUpdateTimeEnd, queryCreateBy,
				queryCreateByAddress, queryCreateTimeStart, queryCreateTimeEnd,
				page, pageSize, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getPerson(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.personDataService.getPerson(id, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String newPerson(FormData formData) throws Exception {

		formData.log();

		String customerId = formData.getString("customerId", "客户编号", false);
		String customerType = formData.getString("customerType", "客户类型", false);
		String customerName = formData.getString("customerName", "客户名称", false);
		String customerPaymentType = formData.getString("customerPaymentType",
				"客户付款类型", false);
		Boolean vip = Boolean.parseBoolean(formData.getString("vip", "是否会员",
				false));
		Timestamp registerDate = formData.getTimestamp("registerDate", "注册日期",
				false);
		Double accountBalance = formData.getDouble("accountBalance", "账户余额",
				false);
		Integer employeeCount = formData.getInteger("employeeCount", "员工数",
				false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.personDataService.newPerson(customerId,
				customerType, customerName, customerPaymentType, vip,
				registerDate, accountBalance, employeeCount, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String deletePerson(String id, Integer recordVersion)
			throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		this.personDataService.deletePerson(id, recordVersion, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String updatePerson(String id, Integer recordVersion,
			FormData formData) throws Exception {

		formData.log();

		String customerId = formData.getString("customerId", "客户编号", false);
		String customerType = formData.getString("customerType", "客户类型", false);
		String customerName = formData.getString("customerName", "客户名称", false);
		String customerPaymentType = formData.getString("customerPaymentType",
				"客户付款类型", false);
		Boolean vip = Boolean.parseBoolean(formData.getString("vip", "是否会员",
				false));
		Timestamp registerDate = formData.getTimestamp("registerDate", "注册日期",
				false);
		Double accountBalance = formData.getDouble("accountBalance", "账户余额",
				false);
		Integer employeeCount = formData.getInteger("employeeCount", "员工数",
				false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.personDataService.updatePerson(id,
				customerId, customerType, customerName, customerPaymentType,
				vip, registerDate, accountBalance, employeeCount, operatorId,
				operatorAddress, recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}
}