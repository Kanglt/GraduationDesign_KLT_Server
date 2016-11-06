package lyu.klt.frame.module.uploadfile;

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

@WebServiceController("UploadFile")
public class UploadFileWebServiceController {

	private UploadFileDataService uploadFileDataService = new UploadFileDataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getUploadFileList(FormData formData, Integer page)
			throws Exception {

		formData.log();

		String queryOriginalFileName = formData.getString(
				"queryOriginalFileName", "原始文件名", true);
		String queryExt = formData.getString("queryExt", "文件扩展名", true);
		String queryImage = formData.getString("queryImage", "是否图片", true);
		String queryReferenceTableName = formData.getString(
				"queryReferenceTableName", "引用该记录的表名", true);
		String queryReferenceId = formData.getString("queryReferenceId",
				"引用该记录的表记录id", true);

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

		ProcedureResult pr = this.uploadFileDataService
				.getUploadFileList(queryId, queryOriginalFileName, queryExt,
						queryImage, queryReferenceTableName, queryReferenceId,
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
	public String getUploadFile(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.uploadFileDataService.getUploadFile(id,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String newUploadFile(FormData formData) throws Exception {

		formData.log();

		String originalFileName = formData.getString("originalFileName",
				"原始文件名", false);
		String ext = formData.getString("ext", "文件扩展名", false);
		Boolean image = Boolean.parseBoolean(formData.getString("image",
				"是否图片", false));
		String referenceTableName = formData.getString("referenceTableName",
				"引用该记录的表名", false);
		String referenceId = formData.getString("referenceId", "引用该记录的表记录id",
				false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.uploadFileDataService.newUploadFile(
				originalFileName, ext, image, referenceTableName, referenceId,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String deleteUploadFile(String id, Integer recordVersion)
			throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		this.uploadFileDataService.deleteUploadFile(id, recordVersion,
				operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String updateUploadFile(String id, Integer recordVersion,
			FormData formData) throws Exception {

		formData.log();

		String originalFileName = formData.getString("originalFileName",
				"原始文件名", false);
		String ext = formData.getString("ext", "文件扩展名", false);
		Boolean image = Boolean.parseBoolean(formData.getString("image",
				"是否图片", false));
		String referenceTableName = formData.getString("referenceTableName",
				"引用该记录的表名", false);
		String referenceId = formData.getString("referenceId", "引用该记录的表记录id",
				false);

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();

		ProcedureResult pr = this.uploadFileDataService.updateUploadFile(id,
				originalFileName, ext, image, referenceTableName, referenceId,
				operatorId, operatorAddress, recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}
}