package lyu.klt.frame.controller.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.exception.AccessException;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.exception.RealtimeException;
import lyu.klt.frame.controller.exception.RequireLoginException;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.WebServiceRequestHandler;
import lyu.klt.frame.controller.transaction.TransactionSession;
import lyu.klt.frame.utils.ExceptionUtils;
import lyu.klt.frame.utils.IdGenerator;
import lyu.klt.frame.utils.Utils;

public class WebServiceDispatcher extends BaseDispatcher {

	private static Log log = LogFactory.getLog(WebServiceDispatcher.class);

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public WebServiceDispatcher(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super(request, response);

		ControllerContext.getCurrentInstance().setId(IdGenerator.getNewId());
	}

	@Override
	public void process() throws Exception {
		Timestamp startTime = Utils.getTimestamp();
		log.debug(new ControllerLogMessage("WebServiceDispatcher.process()开始"));

		String result = null;
		Exception exception = null;
		try {
			result = this.doProcess();
		} catch (Exception e) {
			if (!(e instanceof RealtimeException))
				log.error(
						new ControllerLogMessage(Utils
								.getOriginalMessageFromException(e)), e);
			exception = e;

			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transactionSession = context
					.openTransactionSession();
			if (transactionSession != null && transactionSession.isStarted())
				transactionSession.rollbackTransaction();
			context.closeTransactionSession();
		}

		this.processResult(result, exception);

		log.debug(new ControllerLogMessage(
				"WebServiceDispatcher.process()结束，共耗时：%s", Utils
						.getDurationTime(startTime)));
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午5:06:09
	 * @param result
	 * @param exception
	 * @throws Exception
	 */
	private void processResult(String result, Exception exception)
			throws Exception {
		if (exception == null)
			this.processResult(result);
		else
			this.processResultException(exception);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午5:09:33
	 * @param result
	 * @throws Exception
	 */
	private void processResult(String result) throws Exception {
		JSONObject resultObj = new JSONObject();
		resultObj.put("type", "Message");
		resultObj.put("data", result);

		this.writeResult(resultObj);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午5:09:50
	 * @param exception
	 * @throws Exception
	 */
	private void processResultException(Exception exception) throws Exception {
		Throwable throwable = ExceptionUtils.getOriginalException(exception);
		String data = ExceptionUtils.getOriginalMessageFromException(exception);

		String errorCode = "";
		String exceptionType = "Exception";
		if (throwable instanceof BusinessException) {
			exceptionType = "BusinessException";
			errorCode = ((BusinessException) throwable).getErrorCode();
		} else if (throwable instanceof RequireLoginException) {
			exceptionType = "RequireLoginException";
			errorCode = ((RequireLoginException) throwable).getErrorCode();
		} else if (throwable instanceof AccessException) {
			exceptionType = "AccessException";
			errorCode = ((AccessException) throwable).getErrorCode();
		} else if (throwable instanceof RealtimeException) {
			exceptionType = "RealtimeException";
			errorCode = ((RealtimeException) throwable).getErrorCode();
		} else {
			data = MultiLanguage.getResource("000000", "额，服务器出了点小问题");
		}

		JSONObject resultObj = new JSONObject();
		resultObj.put("errorCode", errorCode);
		resultObj.put("type", exceptionType);
		resultObj.put("data", data);

		this.writeResult(resultObj);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午5:12:46
	 * @param resultObj
	 */
	private void writeResult(JSONObject resultObj) {
		PrintWriter writer = null;
		try {
			writer = ControllerContext.getCurrentInstance().getResponse()
					.getWriter();
			writer.write(resultObj.toString());
		} catch (IOException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (Exception e) {
					log.error(
							new ControllerLogMessage(Utils
									.getOriginalMessageFromException(e)), e);
				}
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午9:02:13
	 * @throws Exception
	 */
	private String doProcess() throws Exception {
		ControllerContext context = ControllerContext.getCurrentInstance();
		TransactionSession transactionSession = context
				.openTransactionSession();

		if (transactionSession != null)
			transactionSession.startTransaction();

		String result = new WebServiceRequestHandler(context).process();

		if (transactionSession != null && transactionSession.isStarted())
			transactionSession.commitTransaction();

		context.closeTransactionSession();

		return result;
	}

}