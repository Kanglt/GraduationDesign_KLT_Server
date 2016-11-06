package lyu.klt.frame.module.systemcode;

import java.sql.Timestamp;

import lyu.klt.frame.controller.annotation.ISelectDataServiceHandler;
import lyu.klt.frame.controller.context.ComponentSelectMapping;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class SystemCodeDataService {

	// Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getSystemCodeList(String queryId, String queryType,
			String queryTypeName, Integer querySeqNoStart,
			Integer querySeqNoEnd, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		return this
				.sp_fjzx_frame_system_code_query(queryId, queryType,
						queryTypeName, querySeqNoStart, querySeqNoEnd,
						queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newSystemCode(String type, String typeName,
			Integer seqNo, String operatorId, String operatorAddress)
			throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_fjzx_frame_system_code_management(
				Procedure.ACTION_INSERT, id, type, typeName, seqNo, operatorId,
				operatorAddress, recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deleteSystemCode(String id, Integer recordVersion,
			String operatorId, String operatorAddress) throws Exception {

		String type = null;
		String typeName = null;
		Integer seqNo = null;

		return this.sp_fjzx_frame_system_code_management(
				Procedure.ACTION_DELETE, id, type, typeName, seqNo, operatorId,
				operatorAddress, recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getSystemCode(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_fjzx_frame_system_code_read(id, operatorId,
				operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updateSystemCode(String id, String type,
			String typeName, Integer seqNo, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		return this.sp_fjzx_frame_system_code_management(
				Procedure.ACTION_UPDATE, id, type, typeName, seqNo, operatorId,
				operatorAddress, recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_fjzx_frame_system_code_management(String action,
			String id, String type, String typeName, Integer seqNo,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_system_code_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("type", type);
		procedure.addParameterString("typeName", typeName);
		procedure.addParameterInteger("seqNo", seqNo);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_fjzx_frame_system_code_query(String queryId,
			String queryType, String queryTypeName, Integer querySeqNoStart,
			Integer querySeqNoEnd, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_fjzx_frame_system_code_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryType", queryType);
		procedure.addParameterString("queryTypeName", queryTypeName);
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
	private ProcedureResult sp_fjzx_frame_system_code_read(String id,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_fjzx_frame_system_code_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	// 存储过程数据服务 -end-

	public ProcedureResult getComponentSelectList(String selectType,
			String queryParentId, String queryValue, Integer page,
			int pageSize, String positionId, String operatorId,
			String operatorAddress) throws Exception {

		Class<? extends ISelectDataServiceHandler> clazz = ComponentSelectMapping
				.getInstance().getClazz();
		if (clazz != null) {
			SelectProcedure procedure = clazz.newInstance().getSelectProcedure(
					selectType);
			if (procedure != null) {
				if (procedure instanceof SelectProcedureWithParentId) {
					return this
							.getComponentSelectListByProcedureNameWithParentId(
									((SelectProcedureWithParentId) procedure)
											.getProcedureName(), queryParentId,
									queryValue, page, pageSize, operatorId,
									operatorAddress);
				} else {
					return this.getComponentSelectListByProcedureName(
							procedure.getProcedureName(), queryValue, page,
							pageSize, operatorId, operatorAddress);
				}
			} else {
				return this
						.getComponentSelectListSystemCode(selectType,
								queryValue, page, pageSize, operatorId,
								operatorAddress);
			}
		} else {
			return this.getComponentSelectListSystemCode(selectType,
					queryValue, page, pageSize, operatorId, operatorAddress);
		}
	}

	private ProcedureResult getComponentSelectListSystemCode(String selectType,
			String queryValue, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		return this.sp_fjzx_frame_component_select_system_code_query(
				selectType, queryValue, page, pageSize, operatorId,
				operatorAddress);
	}

	private ProcedureResult getComponentSelectListByProcedureName(
			String procedureName, String queryValue, Integer page,
			Integer pageSize, String operatorId, String operatorAddress)
			throws Exception {

		Procedure procedure = new Procedure(procedureName);

		procedure.addParameterString("queryValue", queryValue);

		procedure.addParameterInteger("page", page);
		procedure.addParameterInteger("pageSize", pageSize);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult getComponentSelectListByProcedureNameWithParentId(
			String procedureName, String queryParentId, String queryValue,
			Integer page, int pageSize, String operatorId,
			String operatorAddress) throws Exception {

		Procedure procedure = new Procedure(procedureName);

		procedure.addParameterString("queryParentId", queryParentId);
		procedure.addParameterString("queryValue", queryValue);

		procedure.addParameterInteger("page", page);
		procedure.addParameterInteger("pageSize", pageSize);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult sp_fjzx_frame_component_select_system_code_query(
			String selectType, String queryValue, Integer page,
			Integer pageSize, String operatorId, String operatorAddress)
			throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_component_select_system_code_query");

		procedure.addParameterString("selectType", selectType);
		procedure.addParameterString("queryValue", queryValue);

		procedure.addParameterInteger("page", page);
		procedure.addParameterInteger("pageSize", pageSize);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	public ProcedureResult getComponentTreeList(String selectType,
			String extraParams, String positionId, String operatorId,
			String operatorAddress) throws Exception {

		Class<? extends ISelectDataServiceHandler> clazz = ComponentSelectMapping
				.getInstance().getClazz();
		if (clazz != null) {
			TreeSelectProcedure procedure = clazz.newInstance()
					.getTreeSelectProcedure(selectType);
			if (procedure != null) {
				return this.getComponentTreeListByProcedureName(
						procedure.getProcedureName(), extraParams, positionId,
						operatorId, operatorAddress);
			}
		}

		throw new BusinessException("000000", "无效的树形选择类型");
	}

	private ProcedureResult getComponentTreeListByProcedureName(
			String procedureName, String extraParams, String positionId,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure(procedureName);

		procedure.addParameterString("extraParams", extraParams);

		procedure.addParameterString("positionId", positionId);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
}
