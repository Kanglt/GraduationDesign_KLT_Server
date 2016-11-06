package web.common.module.log;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import web.common.access.user.UserAccessHandler;
import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.UploadDownloadController;
import lyu.klt.frame.controller.dispatcher.UploadRequest;
import lyu.klt.frame.controller.download.DownloadFileInfo;
import lyu.klt.frame.controller.download.IDownloadHandler;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.upload.IUploadHandler;

@UploadDownloadController(id = "log")
public class LogFileDownloadController implements IUploadHandler,
		IDownloadHandler {

	@Access(handler = UserAccessHandler.class)
	@Override
	public void onBeforeDownloadFile(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
	}

	@Access(handler = UserAccessHandler.class)
	@Override
	public DownloadFileInfo onPreparingFile(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		// 这里一样要验证下载权限
		// String fileId = req.getParameter("fileId");// 前端此参数为null，后台不需要用
		String fileName = req.getParameter("fileName");

		String logFileDirectory = System.getProperty(Constants.LOG4J_LOG_DIR);

		String filePath = String.format("%s/WEB-INF/logs/%s", logFileDirectory,
				fileName);

		return new DownloadFileInfo(fileName, new File(filePath));
	}

	@Override
	public JSONObject onTempFileSaved(HttpServletRequest req,
			UploadRequest request, String fileName, File tempFile)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
