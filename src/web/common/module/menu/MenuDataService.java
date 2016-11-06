package web.common.module.menu;

import java.sql.Timestamp;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class MenuDataService {

	// Java数据服务 -begin-

	public ProcedureResult getUserMenuTreeList(String operatorId,
			String operatorAddress) throws Exception {
		return this.sp_web_imperius_menu_get_user_menu_list(operatorId,
				operatorAddress);

	}

	public ProcedureResult getMenuTreeList(String operatorId,
			String operatorAddress) throws Exception {

		String queryId = null;
		String queryParentId = null;
		String queryName = null;
		String queryTabId = null;
		String queryUrl = null;
		Integer querySeqNoStart = null;
		Integer querySeqNoEnd = null;
		String queryStatus = null;

		String queryUpdateBy = null;
		String queryUpdateByAddress = null;
		Timestamp queryUpdateTimeStart = null;
		Timestamp queryUpdateTimeEnd = null;
		String queryCreateBy = null;
		String queryCreateByAddress = null;
		Timestamp queryCreateTimeStart = null;
		Timestamp queryCreateTimeEnd = null;
		Integer page = 0;
		Integer pageSize = null;
		return this
				.sp_web_imperius_menu_query(queryId, queryParentId, queryName,
						queryTabId, queryUrl, querySeqNoStart, querySeqNoEnd,
						queryStatus, queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);

	}

	/**
	 * 通用列表查询
	 */
	public ProcedureResult getMenuList(String queryId, String queryParentId,
			String queryName, String queryTabId, String queryUrl,
			Integer querySeqNoStart, Integer querySeqNoEnd, String queryStatus,
			String queryUpdateBy, String queryUpdateByAddress,
			Timestamp queryUpdateTimeStart, Timestamp queryUpdateTimeEnd,
			String queryCreateBy, String queryCreateByAddress,
			Timestamp queryCreateTimeStart, Timestamp queryCreateTimeEnd,
			Integer page, Integer pageSize, String operatorId,
			String operatorAddress) throws Exception {

		return this
				.sp_web_imperius_menu_query(queryId, queryParentId, queryName,
						queryTabId, queryUrl, querySeqNoStart, querySeqNoEnd,
						queryStatus, queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newMenu(String parentId, String name, String iconClass, String tabId,
			String url, Integer seqNo, String status, String operatorId,
			String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_web_imperius_menu_management(Procedure.ACTION_INSERT,
				id, parentId, name, iconClass, tabId, url, seqNo, status, operatorId,
				operatorAddress, recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deleteMenu(String id, Integer recordVersion,
			String operatorId, String operatorAddress) throws Exception {

		String parentId = null;
		String name = null;
		String tabId = null;
		String iconClass = null;
		String url = null;
		Integer seqNo = null;
		String status = null;

		return this.sp_web_imperius_menu_management(Procedure.ACTION_DELETE,
				id, parentId, name, iconClass, tabId, url, seqNo, status, operatorId,
				operatorAddress, recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getMenu(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_web_imperius_menu_read(id, operatorId, operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updateMenu(String id, String parentId, String name,
			String iconClass, String tabId, String url, Integer seqNo, String status,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {

		return this.sp_web_imperius_menu_management(Procedure.ACTION_UPDATE,
				id, parentId, name, iconClass, tabId, url, seqNo, status, operatorId,
				operatorAddress, recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_web_imperius_menu_management(String action,
			String id, String parentId, String name, String iconClass, String tabId, String url,
			Integer seqNo, String status, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure("sp_web_imperius_menu_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("parentId", parentId);
		procedure.addParameterString("name", name);
		procedure.addParameterString("iconClass", iconClass);
		procedure.addParameterString("tabId", tabId);
		procedure.addParameterString("url", url);
		procedure.addParameterInteger("seqNo", seqNo);
		procedure.addParameterString("status", status);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_web_imperius_menu_query(String queryId,
			String queryParentId, String queryName, String queryTabId,
			String queryUrl, Integer querySeqNoStart, Integer querySeqNoEnd,
			String queryStatus, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_web_imperius_menu_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryParentId", queryParentId);
		procedure.addParameterString("queryName", queryName);
		procedure.addParameterString("queryTabId", queryTabId);
		procedure.addParameterString("queryUrl", queryUrl);
		procedure.addParameterInteger("querySeqNoStart", querySeqNoStart);
		procedure.addParameterInteger("querySeqNoEnd", querySeqNoEnd);
		procedure.addParameterString("queryStatus", queryStatus);
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
	private ProcedureResult sp_web_imperius_menu_read(String id,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_web_imperius_menu_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult sp_web_imperius_menu_get_user_menu_list(
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure(
				"sp_web_imperius_menu_get_user_menu_list");

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	// 存储过程数据服务 -end-
}
