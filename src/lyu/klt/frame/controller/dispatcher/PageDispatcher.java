package lyu.klt.frame.controller.dispatcher;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.exception.AccessException;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.exception.RequireLoginException;
import lyu.klt.frame.controller.handler.PageRequestHandler;
import lyu.klt.frame.controller.transaction.TransactionSession;
import lyu.klt.frame.utils.IdGenerator;
import lyu.klt.frame.utils.Utils;

public class PageDispatcher extends BaseDispatcher {

	private static Log log = LogFactory.getLog(PageDispatcher.class);

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public PageDispatcher(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super(request, response);

		ControllerContext.getCurrentInstance().setId(IdGenerator.getNewId());
	}

	@Override
	public void process() throws Exception {
		Timestamp startTime = Utils.getTimestamp();
		log.debug(new ControllerLogMessage("PageDispatcher.process()开始"));

		try {
			this.doProcess();
		} catch (Exception e) {
//			log.error(
//					new ControllerLogMessage(Utils
//							.getOriginalMessageFromException(e)), e);

			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transaction = context.openTransactionSession();

			if (transaction != null && transaction.isStarted())
				transaction.rollbackTransaction();

			context.closeTransactionSession();

			Throwable originException = Utils.getOriginalException(e);
			if (originException instanceof BusinessException) {
				this.doProcessBusinessException();
			} else if (originException instanceof RequireLoginException) {
				this.doProcessRequireLoginException();
			} else if (originException instanceof AccessException) {
				this.doProcessAccessException();
			} else {
//				log.error(
//						new ControllerLogMessage(Utils
//								.getOriginalMessageFromException(e)), e);
				this.doProcessAsPageNotFoundException();
			}
		}

		log.debug(new ControllerLogMessage("PageDispatcher.process()结束，共耗时：%s",
				Utils.getDurationTime(startTime)));
	}

	/**
	 * 
	 * @author Jam 2016年3月29日 下午3:56:41
	 */
	private void doProcessBusinessException() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		try {
			request.getRequestDispatcher("/600.jsp").forward(request, response);
		} catch (ServletException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		} catch (IOException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月29日 下午3:56:39
	 */
	private void doProcessAccessException() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		try {
			request.getRequestDispatcher("/403.jsp").forward(request, response);
		} catch (ServletException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		} catch (IOException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月29日 下午3:56:37
	 */
	private void doProcessRequireLoginException() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		try {
			request.getRequestDispatcher("/403.jsp").forward(request, response);
		} catch (ServletException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		} catch (IOException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		}
	}

	private void doProcessAsPageNotFoundException() {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		try {
			request.getRequestDispatcher("/404.jsp").forward(request, response);
		} catch (ServletException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		} catch (IOException e) {
			log.error(
					new ControllerLogMessage(Utils
							.getOriginalMessageFromException(e)), e);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月29日 下午3:53:06
	 */
	private void doProcess() throws Exception {
		ControllerContext context = ControllerContext.getCurrentInstance();
		TransactionSession transactionSession = context
				.openTransactionSession();

		if (transactionSession != null)
			transactionSession.startTransaction();

		new PageRequestHandler(context).process();

		if (transactionSession != null && transactionSession.isStarted())
			transactionSession.commitTransaction();

		context.closeTransactionSession();
	}

}