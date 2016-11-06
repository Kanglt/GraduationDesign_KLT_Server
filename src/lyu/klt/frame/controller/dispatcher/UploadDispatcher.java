package lyu.klt.frame.controller.dispatcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.annotation.IAccessHandler;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.context.UploadDownloadControllerRequestMapping;
import lyu.klt.frame.controller.exception.AccessException;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.transaction.TransactionSession;
import lyu.klt.frame.controller.upload.IUploadHandler;
import lyu.klt.frame.controller.upload.UploadDownloadMessage;
import lyu.klt.frame.utils.IdGenerator;
import lyu.klt.frame.utils.Utils;

public class UploadDispatcher extends HttpServlet {

	private static final long serialVersionUID = -4553895998921494391L;

	private static Log log = LogFactory.getLog(UploadDispatcher.class);

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(request);
		context.setResponse(response);

		try {
			this.doService(request, response);
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

	private void doService(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		this.setResponseBehavior(res);

		UploadDownloadMessage msg = new UploadDownloadMessage();
		try {
			ControllerContext context = ControllerContext.getCurrentInstance();
			TransactionSession transactionSession = context
					.openTransactionSession();
			if (transactionSession != null)
				transactionSession.startTransaction();

			JSONObject data = this.doUpload(req, res);
			msg.setOk();
			msg.put("data", data);

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
			if (ex instanceof BusinessException
					|| ex instanceof AccessException)
				msg.setMessage(Utils.getOriginalMessageFromException(e));
			else
				msg.setMessage(MultiLanguage.getResource("000000",
						"额，服务器出了点小问题"));
		}

		PrintWriter out = null;
		try {
			out = res.getWriter();
			out.write(msg.toString());
		} catch (IOException e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				log.error(Utils.getOriginalMessageFromException(e), e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject doUpload(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		// uploadDownloadControllerId用来决定由哪个UploadDownloadController来处理
		String uploadDownloadControllerId = req
				.getParameter("uploadDownloadControllerId");
		IUploadHandler handler = (IUploadHandler) UploadDownloadControllerRequestMapping
				.getInstance().getUploadDownloadControllerInstance(
						uploadDownloadControllerId);

		// 如果UploadDownloadController的onTempFileSaved方法有指定登录检验方式，则检查登录
		Method method = handler.getClass().getMethod(
				"onTempFileSaved",
				new Class<?>[] { HttpServletRequest.class, UploadRequest.class,
						String.class, File.class });
		if (method == null) {
			String errorMessage = String.format(
					"UploadDownloadController: %s必须有: onTempFileSaved方法",
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

		InputStream fileInputStream = null;
		String tmpFile = null;
		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("utf-8");

			UploadRequest request = new UploadRequest();
			request.add("remoteAddr", req.getRemoteAddr());

			String tmpDir = System.getProperty("java.io.tmpdir");
			tmpFile = String.format("%s%s.tmp", tmpDir, IdGenerator.getNewId());

			String fileName = null;
			List<FileItem> fileList = null;
			fileList = upload.parseRequest(req);
			Iterator<FileItem> it = fileList.iterator();
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					try {
						fileInputStream = item.getInputStream();
						Utils.writeFile(tmpFile, fileInputStream);
					} catch (Exception e) {
						try {
							if (fileInputStream != null)
								fileInputStream.close();
						} catch (Exception e1) {
							log.error(
									Utils.getOriginalMessageFromException(e1),
									e1);
						}
					}
				} else {
					request.add(item.getFieldName(), item.getString());
				}
			}

			// 让业务程序员指定的UploadHandler获取已经保存好的临时文件
			return handler.onTempFileSaved(req, request, fileName, new File(
					tmpFile));

		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (Exception e) {
				log.error(Utils.getOriginalMessageFromException(e), e);
			}
			try {
				if (tmpFile != null)
					Utils.deleteFile(tmpFile);
			} catch (Exception e) {
				log.error(Utils.getOriginalMessageFromException(e), e);
			}
		}
	}

	private void setResponseBehavior(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html;charset=UTF-8");
	}

}
