package lyu.klt.frame.controller.dispatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.annotation.IAccessHandler;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.context.UploadDownloadControllerRequestMapping;
import lyu.klt.frame.controller.download.DownloadFileInfo;
import lyu.klt.frame.controller.download.IDownloadHandler;
import lyu.klt.frame.controller.exception.AccessException;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.transaction.TransactionSession;
import lyu.klt.frame.controller.upload.UploadDownloadMessage;
import lyu.klt.frame.utils.Utils;

public class DownloadDispatcher extends HttpServlet {

	private static Log log = LogFactory.getLog(DownloadDispatcher.class);

	private static final long serialVersionUID = 5211020644248088935L;

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(req);
		context.setResponse(resp);

		try {
			this.doServiceHead(req, resp);
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

	private void doServiceHead(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		this.setResponseBehavior(resp);

		UploadDownloadMessage msg = new UploadDownloadMessage();
		try {
			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transactionSession = context
					.openTransactionSession();
			if (transactionSession != null)
				transactionSession.startTransaction();

			this.doHeadService(req, resp);

			msg.setOk();

			if (transactionSession != null && transactionSession.isStarted())
				transactionSession.commitTransaction();
			context.closeTransactionSession();
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);

			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transaction = context.openTransactionSession();
			if (transaction != null && transaction.isStarted())
				transaction.rollbackTransaction();
			context.closeTransactionSession();

			msg.setFailed();
			Throwable ex = Utils.getOriginalException(e);
			if (ex instanceof BusinessException) {
				msg.put("type", "BusinessException");
				msg.setMessage(Utils.getOriginalMessageFromException(e));
			} else if (ex instanceof AccessException) {
				msg.put("type", "AccessException");
				msg.setMessage(Utils.getOriginalMessageFromException(e));
			} else {
				msg.put("type", "Exception");
				msg.setMessage(MultiLanguage.getResource("000000",
						"额，服务器出了点小问题"));
			}
		}

		resp.addHeader("UploadDownloadMessage",
				URLEncoder.encode(msg.toString(), "UTF-8"));
	}

	private void doHeadService(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		// uploadDownloadControllerId用来决定由哪个UploadDownloadController来处理
		String uploadDownloadControllerId = req
				.getParameter("uploadDownloadControllerId");
		IDownloadHandler handler = (IDownloadHandler) UploadDownloadControllerRequestMapping
				.getInstance().getUploadDownloadControllerInstance(
						uploadDownloadControllerId);

		// 如果UploadDownloadController的onBeforeDownloadFile有指定登录检验方式，则检查登录
		Method method = handler.getClass().getMethod(
				"onBeforeDownloadFile",
				new Class<?>[] { HttpServletRequest.class,
						HttpServletResponse.class });
		if (method == null) {
			String errorMessage = String.format(
					"UploadDownloadController: %s必须有: onBeforeDownloadFile方法",
					handler.getClass().getName());
			log.debug(new ControllerLogMessage(errorMessage));
			throw new Exception(errorMessage);
		}

		log.debug(new ControllerLogMessage(
				"执行UploadDownloadController: %s，方法: %s", handler.getClass()
						.getName(), method.getName()));

		if (method.isAnnotationPresent(Access.class)) {
			Access accessAnnotation = method.getAnnotation(Access.class);
			IAccessHandler iAccessHandler = accessAnnotation.handler()
					.newInstance();
			if (!iAccessHandler.isValid(req)) {
				String errorMessage = MultiLanguage
						.getResource(
								"000000",
								String.format(
										"没有权限访问该资源，试图访问的UploadDownloadController: %s，试图访问的方法: %s",
										handler.getClass().getName(),
										method.getName()));
				log.error(new ControllerLogMessage(errorMessage));

				throw new AccessException("000000", "没有登录或者登录已过期，请重新登录");
			}
		}

		handler.onBeforeDownloadFile(req, resp);
	}

	private void setResponseBehavior(HttpServletResponse resp) {
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("application/octet-stream");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(req);
		context.setResponse(resp);

		try {
			this.doPostService(req, resp);
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
			resp.flushBuffer();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(req);
		context.setResponse(resp);

		try {
			this.doPostService(req, resp);
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
			resp.flushBuffer();
		}
	}

	private void doPostService(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		this.setResponseBehavior(resp);

		try {
			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transactionSession = context
					.openTransactionSession();
			if (transactionSession != null)
				transactionSession.startTransaction();

			this.doDownloadService(req, resp);

			if (transactionSession != null && transactionSession.isStarted())
				transactionSession.commitTransaction();
			context.closeTransactionSession();
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);

			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transaction = context.openTransactionSession();
			if (transaction != null && transaction.isStarted())
				transaction.rollbackTransaction();
			context.closeTransactionSession();
		}
	}

	private void doDownloadService(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		// uploadDownloadControllerId用来决定由哪个UploadDownloadController来处理
		String uploadDownloadControllerId = req
				.getParameter("uploadDownloadControllerId");
		IDownloadHandler handler = (IDownloadHandler) UploadDownloadControllerRequestMapping
				.getInstance().getUploadDownloadControllerInstance(
						uploadDownloadControllerId);

		// 如果UploadDownloadController的onPreparingFile有指定登录检验方式，则检查登录
		Method method = handler.getClass().getMethod(
				"onPreparingFile",
				new Class<?>[] { HttpServletRequest.class,
						HttpServletResponse.class });
		if (method == null) {
			String errorMessage = String.format(
					"UploadDownloadController: %s必须有: onPreparingFile方法",
					handler.getClass().getName());
			log.debug(new ControllerLogMessage(errorMessage));
			throw new Exception(errorMessage);
		}

		log.debug(new ControllerLogMessage(
				"执行UploadDownloadController: %s，方法: %s", handler.getClass()
						.getName(), method.getName()));

		if (method.isAnnotationPresent(Access.class)) {
			Access accessAnnotation = method.getAnnotation(Access.class);
			IAccessHandler iAccessHandler = accessAnnotation.handler()
					.newInstance();
			if (!iAccessHandler.isValid(req)) {
				String errorMessage = MultiLanguage
						.getResource(
								"000000",
								String.format(
										"没有权限访问该资源，试图访问的UploadDownloadController: %s，试图访问的方法: %s",
										handler.getClass().getName(),
										method.getName()));
				log.error(new ControllerLogMessage(errorMessage));

				throw new AccessException("000000", "没有登录或者登录已过期，请重新登录");
			}
		}

		// 得到由业务程序员给的将要下载文件名和文件
		DownloadFileInfo downloadFileInfo = handler.onPreparingFile(req, resp);
		String desFileName = downloadFileInfo.getFileName();

		// 在response header中填写文件名
		resp.addHeader(
				"Content-Disposition",
				String.format("attachment; filename=%s",
						URLEncoder.encode(desFileName, "UTF-8")));
		// 在response header中填写文件长度
		resp.addHeader("Content-Length",
				String.valueOf(downloadFileInfo.getFile().length()));

		// 往response流中写文件
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(downloadFileInfo.getFile());
			int len = 0;
			byte[] buff = new byte[1024];
			while ((len = fis.read(buff, 0, buff.length)) > 0) {
				resp.getOutputStream().write(buff, 0, len);
			}
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				log.error(Utils.getOriginalMessageFromException(e), e);
			}
			try {
				resp.flushBuffer();
			} catch (Exception e) {
				log.error(Utils.getOriginalMessageFromException(e), e);
			}
		}
	}

}
