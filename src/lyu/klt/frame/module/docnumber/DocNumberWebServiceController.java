package lyu.klt.frame.module.docnumber;

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

@WebServiceController("DocNumber")
public class DocNumberWebServiceController {

	private DocNumberDataService docNumberDataService = new DocNumberDataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getDocNumberList(FormData formData, Integer page)
			throws Exception {

		formData.log();

		String queryType = formData.getString("queryType", "类型", true);
		String queryDocNoRule = formData.getString("queryDocNoRule", "单号模板",
				true);
		String queryDocNoDate = formData.getString("queryDocNoDate", "流水日期",
				true);
		Integer queryDocNoSequenceStart = formData.getInteger(
				"queryDocNoSequenceStart", "流水序号", true);
		Integer queryDocNoSequenceEnd = formData.getInteger(
				"queryDocNoSequenceEnd", "流水序号", true);
		Integer queryDocNoLengthStart = formData.getInteger(
				"queryDocNoLengthStart", "流水序号长度", true);
		Integer queryDocNoLengthEnd = formData.getInteger(
				"queryDocNoLengthEnd", "流水序号长度", true);

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

		ProcedureResult pr = this.docNumberDataService
				.getDocNumberList(queryId, queryType, queryDocNoRule,
						queryDocNoDate, queryDocNoSequenceStart,
						queryDocNoSequenceEnd, queryDocNoLengthStart,
						queryDocNoLengthEnd, queryUpdateBy,
						queryUpdateByAddress, queryUpdateTimeStart,
						queryUpdateTimeEnd, queryCreateBy,
						queryCreateByAddress, queryCreateTimeStart,
						queryCreateTimeEnd, page, pageSize, operatorId,
						operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getDocNumber(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.docNumberDataService.getDocNumber(id,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String newDocNumber(FormData formData) throws Exception {

		formData.log();

		String type = formData.getString("type", "类型", false);
		String docNoRule = formData.getString("docNoRule", "单号模板", false);
		String docNoDate = formData.getString("docNoDate", "流水日期", false);
		Integer docNoSequence = formData.getInteger("docNoSequence", "流水序号",
				false);
		Integer docNoLength = formData.getInteger("docNoLength", "流水序号长度",
				false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.docNumberDataService.newDocNumber(type,
				docNoRule, docNoDate, docNoSequence, docNoLength, operatorId,
				operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String deleteDocNumber(String id, Integer recordVersion)
			throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		this.docNumberDataService.deleteDocNumber(id, recordVersion,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String updateDocNumber(String id, Integer recordVersion,
			FormData formData) throws Exception {

		formData.log();

		String type = formData.getString("type", "类型", false);
		String docNoRule = formData.getString("docNoRule", "单号模板", false);
		String docNoDate = formData.getString("docNoDate", "流水日期", false);
		Integer docNoSequence = formData.getInteger("docNoSequence", "流水序号",
				false);
		Integer docNoLength = formData.getInteger("docNoLength", "流水序号长度",
				false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.docNumberDataService.updateDocNumber(id,
				type, docNoRule, docNoDate, docNoSequence, docNoLength,
				operatorId, operatorAddress, recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}
}