package web.common.module.menu;

import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONObject;

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

@WebServiceController("Menu")
public class MenuWebServiceController {

	private MenuDataService menuDataService = new MenuDataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getMenuList(FormData formData, Integer page) throws Exception {

		formData.log();

		String queryParentId = formData.getString("queryParentId", "父级菜单ID",
				true);
		String queryName = formData.getString("queryName", "菜单名称", true);
		String queryTabId = formData.getString("queryTabId", "tab ID", true);
		String queryUrl = formData.getString("queryUrl", "菜单地址", true);
		Integer querySeqNoStart = formData.getInteger("querySeqNoStart", "序号",
				true);
		Integer querySeqNoEnd = formData
				.getInteger("querySeqNoEnd", "序号", true);
		String queryStatus = formData.getString("queryStatus", "状态", true);

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

		ProcedureResult pr = this.menuDataService
				.getMenuList(queryId, queryParentId, queryName, queryTabId,
						queryUrl, querySeqNoStart, querySeqNoEnd, queryStatus,
						queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getMenu(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.menuDataService.getMenu(id, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String newMenu(String parentId,FormData formData) throws Exception {

		formData.log();

		String name = formData.getString("name", "菜单名称", false);
		String tabId = formData.getString("tabId", "tab ID", false);
		String url = formData.getString("url", "菜单地址", false);
		Integer seqNo = formData.getInteger("seqNo", "序号", false);
		String status = formData.getString("status", "状态", false);
		String iconClass = formData.getString("iconClass", "图标类名", false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.menuDataService.newMenu(parentId, name, iconClass,
				tabId, url, seqNo, status, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String deleteMenu(String id, Integer recordVersion) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		this.menuDataService.deleteMenu(id, recordVersion, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String updateMenu(String id, Integer recordVersion, FormData formData)
			throws Exception {

		formData.log();

		String parentId = null;
		String name = formData.getString("name", "菜单名称", false);
		String tabId = formData.getString("tabId", "tab ID", false);
		String url = formData.getString("url", "菜单地址", false);
		Integer seqNo = formData.getInteger("seqNo", "序号", false);
		String status = formData.getString("status", "状态", false);
		String iconClass = formData.getString("iconClass", "图标类名", false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.menuDataService.updateMenu(id, parentId,
				name, iconClass, tabId, url, seqNo, status, operatorId, operatorAddress,
				recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}
	
	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getMenuTreeList() throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.menuDataService.getMenuTreeList(
				operatorId, operatorAddress);
		JSONArray result = this.getResultArrayAsTreeList(pr
				.getListAsJSONArray());

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, result);
		return msg.toString();
	}

	private JSONArray getResultArrayAsTreeList(JSONArray jsonArray)
			throws Exception {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			// zTree需要id、pId、name三个字段，id和name在表中已经存在，pId不存在，需要将parentId的值转存到pId
			// obj.put("id", obj.getString("id"));
			obj.put("pId", obj.getString("parentId"));
			// obj.put("name", obj.getString("name"));
		}
		return jsonArray;
	}
	
}