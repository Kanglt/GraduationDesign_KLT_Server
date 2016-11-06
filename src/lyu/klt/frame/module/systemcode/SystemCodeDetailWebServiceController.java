package lyu.klt.frame.module.systemcode;

import java.sql.Timestamp;

import sun.text.resources.FormatData;

import web.common.access.user.UserAccessHandler;
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

@WebServiceController("SystemCodeDetail")
public class SystemCodeDetailWebServiceController {

	private SystemCodeDetailDataService systemCodeDetailDataService = new SystemCodeDetailDataService();

	@Access(handler = UserAccessHandler.class, requiredOperationCode = "systemCodeManagement_detail_submit")
	@WebServiceMethod
	public String getSystemCodeDetailList(@Nullable String queryCodeName,String queryCodeId, Integer page)
			throws Exception {

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

		String queryCodeValue = null;
		//String queryCodeName = formData.getString("queryCodeName","系统代码明细名称",true);
		Integer querySeqNoStart = null;
		Integer querySeqNoEnd = null;

		ProcedureResult pr = this.systemCodeDetailDataService
				.getSystemCodeDetailList(queryId, queryCodeId, queryCodeValue,
						queryCodeName, querySeqNoStart, querySeqNoEnd,
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

	@Access(handler = UserAccessHandler.class, requiredOperationCode = "systemCodeManagement_detail_view")
	@WebServiceMethod
	public String getSystemCodeDetail(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDetailDataService
				.getSystemCodeDetail(id, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class, requiredOperationCode = "systemCodeManagement_detail_add")
	@WebServiceMethod
	public String newSystemCodeDetail(String codeId, FormData formData)
			throws Exception {

		formData.log();

		String codeValue = formData.getString("codeValue", "系统代码明细", false);
		if (codeValue != null)
			codeValue = codeValue.toUpperCase();

		String codeName = formData.getString("codeName", "系统代码明细名称", false);
		Integer seqNo = formData.getInteger("seqNo", "序号", false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDetailDataService
				.newSystemCodeDetail(codeId, codeValue, codeName, seqNo,
						operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class, requiredOperationCode = "systemCodeManagement_detail_remove")
	@WebServiceMethod
	public String deleteSystemCodeDetail(String id, Integer recordVersion)
			throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		this.systemCodeDetailDataService.deleteSystemCodeDetail(id,
				recordVersion, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class, requiredOperationCode = "systemCodeManagement_detail_update")
	@WebServiceMethod
	public String updateSystemCodeDetail(String id, Integer recordVersion,
			FormData formData) throws Exception {

		formData.log();

		String codeId = null;// 不修改codeId，相应存储过程也已注释掉
		String codeValue = formData.getString("codeValue", "系统代码明细", false);
		if (codeValue != null)
			codeValue = codeValue.toUpperCase();

		String codeName = formData.getString("codeName", "系统代码明细名称", false);
		Integer seqNo = formData.getInteger("seqNo", "序号", false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.systemCodeDetailDataService
				.updateSystemCodeDetail(id, codeId, codeValue, codeName, seqNo,
						operatorId, operatorAddress, recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}
}