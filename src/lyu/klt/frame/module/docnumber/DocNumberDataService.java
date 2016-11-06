package lyu.klt.frame.module.docnumber;

import java.sql.Timestamp;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class DocNumberDataService {

	// Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getDocNumberList(String queryId, String queryType,
			String queryDocNoRule, String queryDocNoDate,
			Integer queryDocNoSequenceStart, Integer queryDocNoSequenceEnd,
			Integer queryDocNoLengthStart, Integer queryDocNoLengthEnd,
			String queryUpdateBy, String queryUpdateByAddress,
			Timestamp queryUpdateTimeStart, Timestamp queryUpdateTimeEnd,
			String queryCreateBy, String queryCreateByAddress,
			Timestamp queryCreateTimeStart, Timestamp queryCreateTimeEnd,
			Integer page, Integer pageSize, String operatorId,
			String operatorAddress) throws Exception {

		return this
				.sp_fjzx_frame_system_doc_number_rule_query(queryId, queryType,
						queryDocNoRule, queryDocNoDate,
						queryDocNoSequenceStart, queryDocNoSequenceEnd,
						queryDocNoLengthStart, queryDocNoLengthEnd,
						queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newDocNumber(String type, String docNoRule,
			String docNoDate, Integer docNoSequence, Integer docNoLength,
			String operatorId, String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_fjzx_frame_system_doc_number_rule_management(
				Procedure.ACTION_INSERT, id, type, docNoRule, docNoDate,
				docNoSequence, docNoLength, operatorId, operatorAddress,
				recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deleteDocNumber(String id, Integer recordVersion,
			String operatorId, String operatorAddress) throws Exception {

		String type = null;
		String docNoRule = null;
		String docNoDate = null;
		Integer docNoSequence = null;
		Integer docNoLength = null;

		return this.sp_fjzx_frame_system_doc_number_rule_management(
				Procedure.ACTION_DELETE, id, type, docNoRule, docNoDate,
				docNoSequence, docNoLength, operatorId, operatorAddress,
				recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getDocNumber(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_fjzx_frame_system_doc_number_rule_read(id, operatorId,
				operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updateDocNumber(String id, String type,
			String docNoRule, String docNoDate, Integer docNoSequence,
			Integer docNoLength, String operatorId, String operatorAddress,
			Integer recordVersion) throws Exception {

		return this.sp_fjzx_frame_system_doc_number_rule_management(
				Procedure.ACTION_UPDATE, id, type, docNoRule, docNoDate,
				docNoSequence, docNoLength, operatorId, operatorAddress,
				recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_fjzx_frame_system_doc_number_rule_management(
			String action, String id, String type, String docNoRule,
			String docNoDate, Integer docNoSequence, Integer docNoLength,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_doc_number_rule_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("type", type);
		procedure.addParameterString("docNoRule", docNoRule);
		procedure.addParameterString("docNoDate", docNoDate);
		procedure.addParameterInteger("docNoSequence", docNoSequence);
		procedure.addParameterInteger("docNoLength", docNoLength);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_fjzx_frame_system_doc_number_rule_query(
			String queryId, String queryType, String queryDocNoRule,
			String queryDocNoDate, Integer queryDocNoSequenceStart,
			Integer queryDocNoSequenceEnd, Integer queryDocNoLengthStart,
			Integer queryDocNoLengthEnd, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_doc_number_rule_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryType", queryType);
		procedure.addParameterString("queryDocNoRule", queryDocNoRule);
		procedure.addParameterString("queryDocNoDate", queryDocNoDate);
		procedure.addParameterInteger("queryDocNoSequenceStart",
				queryDocNoSequenceStart);
		procedure.addParameterInteger("queryDocNoSequenceEnd",
				queryDocNoSequenceEnd);
		procedure.addParameterInteger("queryDocNoLengthStart",
				queryDocNoLengthStart);
		procedure.addParameterInteger("queryDocNoLengthEnd",
				queryDocNoLengthEnd);
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
	private ProcedureResult sp_fjzx_frame_system_doc_number_rule_read(
			String id, String operatorId, String operatorAddress)
			throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_doc_number_rule_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
	// 存储过程数据服务 -end-

}
