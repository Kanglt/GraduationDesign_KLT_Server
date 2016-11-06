package web.common.module.log;

import java.sql.Timestamp;

import web.common.access.user.UserAccessHandler;
import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.controller.method.parameter.datatype.FormData;
import lyu.klt.frame.database.core.ProcedureResult;

@WebServiceController("Log")
public class LogWebServiceController {

	private LogDataService logDataService = new LogDataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getLogList(FormData formData, Integer page) throws Exception {

		formData.log();

		Timestamp queryFileModifyTimeStart = formData.getTimestamp(
				"queryFileModifyTimeStart", "修改时间开始", true);
		Timestamp queryFileModifyTimeEnd = formData.getTimestamp(
				"queryFileModifyTimeEnd", "修改时间截止", true);

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

		String queryFileName = null;
		Integer queryFileSizeStart = null;
		Integer queryFileSizeEnd = null;
		String queryFileType = null;

		ProcedureResult pr = this.logDataService.getLogList(queryId,
				queryFileName, queryFileModifyTimeStart,
				queryFileModifyTimeEnd, queryFileSizeStart, queryFileSizeEnd,
				queryFileType, queryUpdateBy, queryUpdateByAddress,
				queryUpdateTimeStart, queryUpdateTimeEnd, queryCreateBy,
				queryCreateByAddress, queryCreateTimeStart, queryCreateTimeEnd,
				page, pageSize, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}
}