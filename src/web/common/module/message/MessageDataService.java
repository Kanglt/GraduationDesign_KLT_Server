package web.common.module.message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * @author Jam 2016年4月1日 下午5:07:53
 * 
 */
public class MessageDataService {

	private static List<String> MESSAGE_LIST;

	static {
		MESSAGE_LIST = new ArrayList<String>();
	}

	public void newMessage(String operatorId, String operatorAddress)
			throws Exception {
		MESSAGE_LIST.add(new Date().toString());
	}

	public String getMessage(String operatorId, String operatorAddress) {
		if (MESSAGE_LIST.size() <= 0)
			return null;
		String message = MESSAGE_LIST.get(0);
		MESSAGE_LIST.remove(0);
		return message;
	}

	/**
	 * @Title: getMessageListByReceive
	 * @Description: TODO(用户未发送消息列表)
	 * @CreateBy: 陈学博
	 * @CreateTime: 2016-7-20 下午4:43:41
	 * @UpdateBy: 陈学博
	 * @UpdateTime: 2016-7-20 下午4:43:41
	 * @param querySendUserId
	 * @param queryReceiveUserId
	 * @param queryMessageStatus
	 * @param querySendStatus
	 * @param departmentId
	 * @param operatorId
	 * @param operatorAddress
	 * @return
	 * @throws Exception
	 *             ProcedureResult
	 * @throws
	 */
	public ProcedureResult getMessageListByReceive(String querySendUserId,
			String queryReceiveUserId, String queryMessageStatus,
			String querySendStatus, String departmentId, String operatorId,
			String operatorAddress, Integer page, Integer pageSize)
			throws Exception {
		String queryId = null;
		String queryTitle = null;
		String queryMessageContent = null;
		String queryType = null;
		String queryMessageUrl = null;
		String queryUpdateBy = null;
		String queryUpdateByAddress = null;
		Timestamp queryUpdateTimeStart = null;
		Timestamp queryUpdateTimeEnd = null;
		String queryCreateBy = null;
		String queryCreateByAddress = null;
		Timestamp queryCreateTimeStart = null;
		Timestamp queryCreateTimeEnd = null;
		/*
		 * Integer page = 0; Integer pageSize = 0;
		 */
		/*
		 * String departmentId= null ; String operatorId= null ; String
		 * operatorAddress= null ;
		 */

		return this.sp_web_local_tax_message_query(queryId, queryTitle,
				queryMessageContent, queryType, querySendUserId,
				queryReceiveUserId, queryMessageStatus, querySendStatus,
				queryMessageUrl, queryUpdateBy, queryUpdateByAddress,
				queryUpdateTimeStart, queryUpdateTimeEnd, queryCreateBy,
				queryCreateByAddress, queryCreateTimeStart, queryCreateTimeEnd,
				page, pageSize, departmentId, operatorId, operatorAddress);
	}

	/**
	 * @Title: updateMessageSendStatus
	 * @Description: TODO(修改用户消息发送状态)
	 * @CreateBy: 陈学博
	 * @CreateTime: 2016-7-20 下午4:44:08
	 * @UpdateBy: 陈学博
	 * @UpdateTime: 2016-7-20 下午4:44:08
	 * @param id
	 * @param messageIds
	 * @param sendStatus
	 * @param departmentId
	 * @param operatorId
	 * @param operatorAddress
	 * @param recordVersion
	 * @return
	 * @throws Exception
	 *             ProcedureResult
	 * @throws
	 */
	public ProcedureResult updateMessageSendStatus(String id,
			String messageIds, String sendStatus, String departmentId,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {
		return this.sp_web_local_tax_message_send_status(id, messageIds,
				sendStatus, departmentId, operatorId, operatorAddress,
				recordVersion);
	}

	public ProcedureResult updateMessageReadtatus(String messageId,
			String messageStatus, String operatorId, String operatorAddress,
			Integer recordVersion) throws Exception {

		return this.sp_web_local_tax_message_read_status(messageId,
				messageStatus, operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newMessage(String title, String messageContent,
			String type, String sendUserId, String receiveUserId,
			Boolean messageStatus, Boolean sendStatus, String messageUrl,
			String operatorId, String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_web_local_tax_message_management(
				Procedure.ACTION_INSERT, id, title, messageContent, type,
				sendUserId, receiveUserId, messageStatus, sendStatus,
				messageUrl, operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_web_local_tax_message_query(String queryId,
			String queryTitle, String queryMessageContent, String queryType,
			String querySendUserId, String queryReceiveUserId,
			String queryMessageStatus, String querySendStatus,
			String queryMessageUrl, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String departmentId, String operatorId, String operatorAddress)
			throws Exception {

		Procedure procedure = new Procedure("sp_web_local_tax_message_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryTitle", queryTitle);
		procedure
				.addParameterString("queryMessageContent", queryMessageContent);
		procedure.addParameterString("queryType", queryType);
		procedure.addParameterString("querySendUserId", querySendUserId);
		procedure.addParameterString("queryReceiveUserId", queryReceiveUserId);
		procedure.addParameterString("queryMessageStatus", queryMessageStatus);
		procedure.addParameterString("querySendStatus", querySendStatus);
		procedure.addParameterString("queryMessageUrl", queryMessageUrl);
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

		procedure.addParameterString("departmentId", departmentId);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	private ProcedureResult sp_web_local_tax_message_send_status(String id,
			String messageIds, String sendStatus, String departmentId,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {
		Procedure procedure = new Procedure(
				"sp_web_local_tax_message_send_status");
		procedure.addParameterString("id", id);
		procedure.addParameterString("messageIds", messageIds);
		procedure.addParameterString("sendStatus", sendStatus);
		procedure.addParameterString("departmentId", departmentId);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	private ProcedureResult sp_web_local_tax_message_read_status(
			String messageId, String messageStatus, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {
		Procedure procedure = new Procedure(
				"sp_web_local_tax_message_read_status");
		procedure.addParameterString("messageId", messageId);
		procedure.addParameterString("messageStatus", messageStatus);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}
	
	/**
	 * 下面是Web端用到的
	 */

	// Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getLocalTaxMessageList(String queryId,
			String queryTitle, String queryMessageContent, String queryType,
			String querySendUserId, String queryReceiveUserId,
			String queryMessageStatus, String querySendStatus,
			String queryMessageUrl, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		return this.sp_web_local_tax_message_query(queryId, queryTitle,
				queryMessageContent, queryType, querySendUserId,
				queryReceiveUserId, queryMessageStatus, querySendStatus,
				queryMessageUrl, queryUpdateBy, queryUpdateByAddress,
				queryUpdateTimeStart, queryUpdateTimeEnd, queryCreateBy,
				queryCreateByAddress, queryCreateTimeStart, queryCreateTimeEnd,
				page, pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newLocalTaxMessage(String title,
			String messageContent, String type, String sendUserId,
			String receiveUserId, Boolean messageStatus, Boolean sendStatus,
			String messageUrl, String operatorId, String operatorAddress)
			throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_web_local_tax_message_management(
				Procedure.ACTION_INSERT, id, title, messageContent, type,
				sendUserId, receiveUserId, messageStatus, sendStatus,
				messageUrl, operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deleteLocalTaxMessage(String id,
			Integer recordVersion, String operatorId, String operatorAddress)
			throws Exception {

		String title = null;
		String messageContent = null;
		String type = null;
		String sendUserId = null;
		String receiveUserId = null;
		Boolean messageStatus = null;
		Boolean sendStatus = null;
		String messageUrl = null;

		return this.sp_web_local_tax_message_management(
				Procedure.ACTION_DELETE, id, title, messageContent, type,
				sendUserId, receiveUserId, messageStatus, sendStatus,
				messageUrl, operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getLocalTaxMessage(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_web_local_tax_message_read(id, operatorId,
				operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updateLocalTaxMessage(String id, String title,
			String messageContent, String type, String sendUserId,
			String receiveUserId, Boolean messageStatus, Boolean sendStatus,
			String messageUrl, String operatorId, String operatorAddress,
			Integer recordVersion) throws Exception {

		return this.sp_web_local_tax_message_management(
				Procedure.ACTION_UPDATE, id, title, messageContent, type,
				sendUserId, receiveUserId, messageStatus, sendStatus,
				messageUrl, operatorId, operatorAddress, recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_web_local_tax_message_management(String action,
			String id, String title, String messageContent, String type,
			String sendUserId, String receiveUserId, Boolean messageStatus,
			Boolean sendStatus, String messageUrl, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure(
				"sp_web_local_tax_message_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("title", title);
		procedure.addParameterString("messageContent", messageContent);
		procedure.addParameterString("type", type);
		procedure.addParameterString("sendUserId", sendUserId);
		procedure.addParameterString("receiveUserId", receiveUserId);
		procedure.addParameterBoolean("messageStatus", messageStatus);
		procedure.addParameterBoolean("sendStatus", sendStatus);
		procedure.addParameterString("messageUrl", messageUrl);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_web_local_tax_message_query(String queryId,
			String queryTitle, String queryMessageContent, String queryType,
			String querySendUserId, String queryReceiveUserId,
			String queryMessageStatus, String querySendStatus,
			String queryMessageUrl, String queryUpdateBy,
			String queryUpdateByAddress, Timestamp queryUpdateTimeStart,
			Timestamp queryUpdateTimeEnd, String queryCreateBy,
			String queryCreateByAddress, Timestamp queryCreateTimeStart,
			Timestamp queryCreateTimeEnd, Integer page, Integer pageSize,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_web_local_tax_message_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryTitle", queryTitle);
		procedure
				.addParameterString("queryMessageContent", queryMessageContent);
		procedure.addParameterString("queryType", queryType);
		procedure.addParameterString("querySendUserId", querySendUserId);
		procedure.addParameterString("queryReceiveUserId", queryReceiveUserId);
		procedure.addParameterString("queryMessageStatus", queryMessageStatus);
		procedure.addParameterString("querySendStatus", querySendStatus);
		procedure.addParameterString("queryMessageUrl", queryMessageUrl);
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

		procedure.addParameterString("departmentId", "");
		procedure.addParameterString("operatorId", operatorId);
		;
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	/**
	 * 单条记录查询存储过程
	 */
	private ProcedureResult sp_web_local_tax_message_read(String id,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_web_local_tax_message_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	// 存储过程数据服务 -end-

	public ProcedureResult updateLocalTaxMessageStatus(String id,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {

		return this.sp_web_local_tax_message_update_status(id, operatorId,
				operatorAddress, recordVersion);
	}

	private ProcedureResult sp_web_local_tax_message_update_status(String id,
			String operatorId, String operatorAddress, Integer recordVersion)
			throws Exception {
		Procedure procedure = new Procedure(
				"sp_web_local_tax_message_update_status");

		procedure.addParameterString("id", id);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}
}
