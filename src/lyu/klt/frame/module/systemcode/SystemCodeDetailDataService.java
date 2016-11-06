package lyu.klt.frame.module.systemcode;

import java.sql.Timestamp;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class SystemCodeDetailDataService {

	// Java数据服务 -begin-
	
	/**
	 * 查询所有字典明细 Synchronous
	 */
	public ProcedureResult getSystemCodeDetailSynchronousList() throws Exception{
		return this.sp_fjzx_frame_system_code_detail_synchronous_query();
	}
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getSystemCodeDetailList(String queryId,
			String queryCodeId, String queryCodeValue, String queryCodeName,
			Integer querySeqNoStart, Integer querySeqNoEnd,
			String queryUpdateBy, String queryUpdateByAddress,
			Timestamp queryUpdateTimeStart, Timestamp queryUpdateTimeEnd,
			String queryCreateBy, String queryCreateByAddress,
			Timestamp queryCreateTimeStart, Timestamp queryCreateTimeEnd,
			Integer page, Integer pageSize, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_fjzx_frame_system_code_detail_query(queryId,
				queryCodeId, queryCodeValue, queryCodeName, querySeqNoStart,
				querySeqNoEnd, queryUpdateBy, queryUpdateByAddress,
				queryUpdateTimeStart, queryUpdateTimeEnd, queryCreateBy,
				queryCreateByAddress, queryCreateTimeStart, queryCreateTimeEnd,
				page, pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newSystemCodeDetail(String codeId, String codeValue,
			String codeName, Integer seqNo, String operatorId,
			String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_fjzx_frame_system_code_detail_management(
				Procedure.ACTION_INSERT, id, codeId, codeValue, codeName,
				seqNo, operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deleteSystemCodeDetail(String id,
			Integer recordVersion, String operatorId, String operatorAddress)
			throws Exception {

		String codeId = null;
		String codeValue = null;
		String codeName = null;
		Integer seqNo = null;

		return this.sp_fjzx_frame_system_code_detail_management(
				Procedure.ACTION_DELETE, id, codeId, codeValue, codeName,
				seqNo, operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getSystemCodeDetail(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_fjzx_frame_system_code_detail_read(id, operatorId,
				operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updateSystemCodeDetail(String id, String codeId,
			String codeValue, String codeName, Integer seqNo,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {

		return this.sp_fjzx_frame_system_code_detail_management(
				Procedure.ACTION_UPDATE, id, codeId, codeValue, codeName,
				seqNo, operatorId, operatorAddress, recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
		private ProcedureResult sp_fjzx_frame_system_code_detail_synchronous_query() throws Exception {
			Procedure procedure = new Procedure(
					"sp_fjzx_frame_system_code_detail_synchronous_query");

			return procedure.exec();
		}
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_fjzx_frame_system_code_detail_management(
			String action, String id, String codeId, String codeValue,
			String codeName, Integer seqNo, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_code_detail_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("codeId", codeId);
		procedure.addParameterString("codeValue", codeValue);
		procedure.addParameterString("codeName", codeName);
		procedure.addParameterInteger("seqNo", seqNo);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_fjzx_frame_system_code_detail_query(
			String queryId, String queryCodeId, String queryCodeValue,
			String queryCodeName, Integer querySeqNoStart,
			Integer querySeqNoEnd, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_code_detail_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryCodeId", queryCodeId);
		procedure.addParameterString("queryCodeValue", queryCodeValue);
		procedure.addParameterString("queryCodeName", queryCodeName);
		procedure.addParameterInteger("querySeqNoStart", querySeqNoStart);
		procedure.addParameterInteger("querySeqNoEnd", querySeqNoEnd);
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
	private ProcedureResult sp_fjzx_frame_system_code_detail_read(String id,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_code_detail_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
	// 存储过程数据服务 -end-

}
