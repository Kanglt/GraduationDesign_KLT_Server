package web.common.module.message;

import org.json.JSONObject;

import web.common.access.mobileuser.MobileUserAccessHandler;
import web.common.handler.DecrptHandler;
import web.common.handler.WebServiceMobileMessage;
import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.method.parameter.annotation.Decrypt;
import lyu.klt.frame.database.core.ProcedureResult;

@WebServiceController("MessageMobile")
public class MessageWebServiceMobileController {

	private MessageDataService messageDataService = new MessageDataService();

	/**
	 * @Title: getMessageList
	 * @Description: TODO(查询接收用户消息列表)
	 * @CreateBy: 陈学博
	 * @CreateTime: 2016-7-25 下午4:58:21
	 * @UpdateBy: 陈学博
	 * @UpdateTime: 2016-7-25 下午4:58:21
	 * @param jsonDataStr
	 * @return
	 * @throws Exception
	 *             String
	 * @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getMessageList(
			@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		String departmentId = "";
		String operatorId = "";
		String operatorAddress = "";
		Integer page = 0;

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String querySendUserId = "";
		String queryReceiveUserId = jsonData.getString("queryReceiveUserId");
		if (jsonData.has("departmentId")) {
			departmentId = jsonData.getString("departmentId");
		}
		if (jsonData.has(operatorId)) {
			operatorId = jsonData.getString("operatorId");
		}
		if (jsonData.has("page"))
			page = Integer.valueOf(jsonData.getString("page"));
		String queryMessageStatus = "";
		String querySendStatus = "";

		Integer pageSize = Constants.COMMON_PAGE_SIZE;

		ProcedureResult pr = this.messageDataService.getMessageListByReceive(
				querySendUserId, queryReceiveUserId, queryMessageStatus,
				querySendStatus, departmentId, operatorId, operatorAddress,
				page, pageSize);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		msg.setMessage(MultiLanguage.getResource("100054", "获取消息列表成功"));
		return msg.toString();
	}

	/*
	 * @Access(handler = UserAccessHandler.class)
	 * 
	 * @WebServiceMethod public String getMessage(String id) throws Exception {
	 * 
	 * String operatorId = new UserAccessHandler().getAccessUser().getId();
	 * String operatorAddress = ControllerContext.getCurrentInstance()
	 * .getOperatorAddress();
	 * 
	 * ProcedureResult pr = this.messageDataService.getMessage(id, operatorId,
	 * operatorAddress);
	 * 
	 * WebServiceMessage msg = new WebServiceMessage();
	 * msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
	 * msg.setMessage(MultiLanguage.getResource("000000", "读取成功")); return
	 * msg.toString(); }
	 */

	/*
	 * @Access(handler = UserAccessHandler.class)
	 * 
	 * @WebServiceMethod public String newMessage(String jsonDataStr) throws
	 * Exception {
	 * 
	 * JSONObject jsonData = new JSONObject(jsonDataStr);
	 * 
	 * String title = jsonData.getString("title"); String messageContent =
	 * jsonData.getString("messageContent"); String type =
	 * jsonData.getString("type"); String sendUserId =
	 * jsonData.getString("sendUserId"); String receiveUserId =
	 * jsonData.getString("receiveUserId"); Boolean messageStatus =
	 * jsonData.getBoolean("messageStatus"); Boolean sendStatus =
	 * jsonData.getBoolean("sendStatus"); String messageUrl =
	 * jsonData.getString("messageUrl");
	 * 
	 * String operatorId = new UserAccessHandler().getAccessUser().getId();
	 * String operatorAddress = ControllerContext.getCurrentInstance()
	 * .getOperatorAddress();
	 * 
	 * ProcedureResult pr = this.messageDataService.newMessage(title,
	 * messageContent, type, sendUserId, receiveUserId, messageStatus,
	 * sendStatus, messageUrl, operatorId, operatorAddress);
	 * 
	 * WebServiceMessage msg = new WebServiceMessage();
	 * msg.put(Constants.RECORD, pr.getRecordAsJSONObject()); return
	 * msg.toString(); }
	 * 
	 * @Access(handler = UserAccessHandler.class)
	 * 
	 * @WebServiceMethod public String deleteMessage(String id, Integer
	 * recordVersion) throws Exception {
	 * 
	 * String operatorId = new UserAccessHandler().getAccessUser().getId();
	 * String operatorAddress = ControllerContext.getCurrentInstance()
	 * .getOperatorAddress();
	 * 
	 * this.messageDataService.deleteMessage(id, recordVersion, operatorId,
	 * operatorAddress);
	 * 
	 * WebServiceMessage msg = new WebServiceMessage();
	 * msg.setMessage(MultiLanguage.getResource("000000", "删除成功")); return
	 * msg.toString(); }
	 * 
	 * @Access(handler = UserAccessHandler.class)
	 * 
	 * @WebServiceMethod public String updateMessage(String id, Integer
	 * recordVersion, String jsonDataStr) throws Exception {
	 * 
	 * JSONObject jsonData = new JSONObject(jsonDataStr);
	 * 
	 * String title = jsonData.getString("title"); String messageContent =
	 * jsonData.getString("messageContent"); String type =
	 * jsonData.getString("type"); String sendUserId =
	 * jsonData.getString("sendUserId"); String receiveUserId =
	 * jsonData.getString("receiveUserId"); Boolean messageStatus =
	 * jsonData.getBoolean("messageStatus"); Boolean sendStatus =
	 * jsonData.getBoolean("sendStatus"); String messageUrl =
	 * jsonData.getString("messageUrl");
	 * 
	 * String operatorId = new UserAccessHandler().getAccessUser().getId();
	 * String operatorAddress = ControllerContext.getCurrentInstance()
	 * .getOperatorAddress();
	 * 
	 * ProcedureResult pr = this.messageDataService.updateMessage(id, title,
	 * messageContent, type, sendUserId, receiveUserId, messageStatus,
	 * sendStatus, messageUrl, operatorId, operatorAddress, recordVersion);
	 * 
	 * WebServiceMessage msg = new WebServiceMessage();
	 * msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
	 * msg.setMessage(MultiLanguage.getResource("000000", "修改成功")); return
	 * msg.toString(); }
	 */

	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String deleteMessageForMobile(
			@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {
		JSONObject jsonData = new JSONObject(jsonDataStr);

		String id = "";
		String operatorId = "";
		Integer recordVersion = 0;
		if (jsonData != null) {
			id = jsonData.getString("messageId");
			operatorId = jsonData.getString("operatorId");
			recordVersion = Integer
					.valueOf(jsonData.getString("recordVersion"));
		}

		String operatorAddress = "";

		this.messageDataService.deleteLocalTaxMessage(id,
				recordVersion, operatorId, operatorAddress);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.setMessage(MultiLanguage.getResource("100055", "消息删除成功"));
		return msg.toString();
	}
}