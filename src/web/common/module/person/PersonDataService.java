package web.common.module.person;

import java.sql.Timestamp;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class PersonDataService {

	// Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getPersonList(String queryId,
			String queryCustomerId, String queryCustomerType,
			String queryCustomerName, String queryCustomerPaymentType,
			String queryVip, Timestamp queryRegisterDateStart,
			Timestamp queryRegisterDateEnd, Double queryAccountBalanceStart,
			Double queryAccountBalanceEnd, Integer queryEmployeeCountStart,
			Integer queryEmployeeCountEnd, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		return this
				.sp_demo_person_query(queryId, queryCustomerId,
						queryCustomerType, queryCustomerName,
						queryCustomerPaymentType, queryVip,
						queryRegisterDateStart, queryRegisterDateEnd,
						queryAccountBalanceStart, queryAccountBalanceEnd,
						queryEmployeeCountStart, queryEmployeeCountEnd,
						queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newPerson(String customerId, String customerType,
			String customerName, String customerPaymentType, Boolean vip,
			Timestamp registerDate, Double accountBalance,
			Integer employeeCount, String operatorId, String operatorAddress)
			throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_demo_person_management(Procedure.ACTION_INSERT, id,
				customerId, customerType, customerName, customerPaymentType,
				vip, registerDate, accountBalance, employeeCount, operatorId,
				operatorAddress, recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deletePerson(String id, Integer recordVersion,
			String operatorId, String operatorAddress) throws Exception {

		String customerId = null;
		String customerType = null;
		String customerName = null;
		String customerPaymentType = null;
		Boolean vip = null;
		Timestamp registerDate = null;
		Double accountBalance = null;
		Integer employeeCount = null;

		return this.sp_demo_person_management(Procedure.ACTION_DELETE, id,
				customerId, customerType, customerName, customerPaymentType,
				vip, registerDate, accountBalance, employeeCount, operatorId,
				operatorAddress, recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getPerson(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_demo_person_read(id, operatorId, operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updatePerson(String id, String customerId,
			String customerType, String customerName,
			String customerPaymentType, Boolean vip, Timestamp registerDate,
			Double accountBalance, Integer employeeCount, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		return this.sp_demo_person_management(Procedure.ACTION_UPDATE, id,
				customerId, customerType, customerName, customerPaymentType,
				vip, registerDate, accountBalance, employeeCount, operatorId,
				operatorAddress, recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_demo_person_management(String action, String id,
			String customerId, String customerType, String customerName,
			String customerPaymentType, Boolean vip, Timestamp registerDate,
			Double accountBalance, Integer employeeCount, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure("sp_demo_person_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("customerId", customerId);
		procedure.addParameterString("customerType", customerType);
		procedure.addParameterString("customerName", customerName);
		procedure
				.addParameterString("customerPaymentType", customerPaymentType);
		procedure.addParameterBoolean("vip", vip);
		procedure.addParameterTimestamp("registerDate", registerDate);
		procedure.addParameterDouble("accountBalance", accountBalance);
		procedure.addParameterInteger("employeeCount", employeeCount);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_demo_person_query(String queryId,
			String queryCustomerId, String queryCustomerType,
			String queryCustomerName, String queryCustomerPaymentType,
			String queryVip, Timestamp queryRegisterDateStart,
			Timestamp queryRegisterDateEnd, Double queryAccountBalanceStart,
			Double queryAccountBalanceEnd, Integer queryEmployeeCountStart,
			Integer queryEmployeeCountEnd, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_demo_person_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryCustomerId", queryCustomerId);
		procedure.addParameterString("queryCustomerType", queryCustomerType);
		procedure.addParameterString("queryCustomerName", queryCustomerName);
		procedure.addParameterString("queryCustomerPaymentType",
				queryCustomerPaymentType);
		procedure.addParameterString("queryVip", queryVip);
		procedure.addParameterTimestamp("queryRegisterDateStart",
				queryRegisterDateStart);
		procedure.addParameterTimestamp("queryRegisterDateEnd",
				queryRegisterDateEnd);
		procedure.addParameterDouble("queryAccountBalanceStart",
				queryAccountBalanceStart);
		procedure.addParameterDouble("queryAccountBalanceEnd",
				queryAccountBalanceEnd);
		procedure.addParameterInteger("queryEmployeeCountStart",
				queryEmployeeCountStart);
		procedure.addParameterInteger("queryEmployeeCountEnd",
				queryEmployeeCountEnd);
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

		procedure.addParameterInteger("page", page);// 0代表全页
		procedure.addParameterInteger("pageSize", Constants.COMMON_PAGE_SIZE);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	/**
	 * 单条记录查询存储过程
	 */
	private ProcedureResult sp_demo_person_read(String id, String operatorId,
			String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_demo_person_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
	// 存储过程数据服务 -end-

}
