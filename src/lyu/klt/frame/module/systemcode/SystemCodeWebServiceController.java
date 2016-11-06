package lyu.klt.frame.module.systemcode;

import java.sql.Timestamp;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.controller.method.parameter.annotation.Nullable;
import lyu.klt.frame.controller.method.parameter.datatype.FormData;
import lyu.klt.frame.database.core.ProcedureResult;
import web.common.access.user.UserAccessHandler;


@WebServiceController("SystemCode")
public class SystemCodeWebServiceController {

	private SystemCodeDataService systemCodeDataService = new SystemCodeDataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getSystemCodeList(FormData formData, Integer page)
			throws Exception {

		formData.log();

		String queryType = formData.getString("queryType", "系统代码", true);
		if (queryType != null)
			queryType = queryType.toUpperCase();

		String queryTypeName = formData.getString("queryTypeName", "系统代码名称",
				true);
		Integer querySeqNoStart = formData.getInteger("querySeqNoStart", "序号",
				true);
		Integer querySeqNoEnd = formData
				.getInteger("querySeqNoEnd", "序号", true);

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

		ProcedureResult pr = this.systemCodeDataService.getSystemCodeList(
				queryId, queryType, queryTypeName, querySeqNoStart,
				querySeqNoEnd, queryUpdateBy, queryUpdateByAddress,
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
	public String newSystemCode(FormData formData) throws Exception {

		formData.log();

		String type = formData.getString("type", "系统代码", false);
		if (type != null)
			type = type.toUpperCase();

		String typeName = formData.getString("typeName", "系统代码名称", false);
		Integer seqNo = formData.getInteger("seqNo", "序号", false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDataService.newSystemCode(type,
				typeName, seqNo, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String deleteSystemCode(String id, Integer recordVersion)
			throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		this.systemCodeDataService.deleteSystemCode(id, recordVersion,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getSystemCode(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDataService.getSystemCode(id,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String updateSystemCode(String id, Integer recordVersion,
			FormData formData) throws Exception {

		formData.log();

		String type = formData.getString("type", "系统代码", false);
		if (type != null)
			type = type.toUpperCase();

		String typeName = formData.getString("typeName", "系统代码名称", false);
		Integer seqNo = formData.getInteger("seqNo", "序号", false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDataService.updateSystemCode(id,
				type, typeName, seqNo, operatorId, operatorAddress,
				recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getComponentSelectList(String selectType,
			@Nullable String queryParentId, @Nullable String queryValue,
			Integer page) throws Exception {

		UserAccessHandler handler = new UserAccessHandler();
		String operatorId = handler.getAccessInfo().getUser().getId();
		String positionId = handler.getAccessInfo().getPosition().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		return this.getComponentSelectList(selectType, queryParentId,
				queryValue, page, positionId, operatorId, operatorAddress);
	}

	private String getComponentSelectList(String selectType,
			String queryParentId, String queryValue, Integer page,
			String positionId, String operatorId, String operatorAddress)
			throws Exception {

		int pageSize = Constants.COMMON_PAGE_SIZE;

		ProcedureResult pr = this.systemCodeDataService.getComponentSelectList(
				selectType, queryParentId, queryValue, page, pageSize,
				positionId, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getComponentTreeList(String selectType,
			@Nullable String extraParams) throws Exception {

		UserAccessHandler handler = new UserAccessHandler();
		String operatorId = handler.getAccessInfo().getUser().getId();
		String positionId = handler.getAccessInfo().getPosition().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDataService.getComponentTreeList(
				selectType, extraParams, positionId, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}
}