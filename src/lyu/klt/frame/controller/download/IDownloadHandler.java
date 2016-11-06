package lyu.klt.frame.controller.download;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDownloadHandler {

	/**
	 * 本接口方法主要用来给业务程序员验证下载权限，如果没有权限，则在方法中抛出BusinessException即可
	 * 
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	public void onBeforeDownloadFile(HttpServletRequest req,
			HttpServletResponse response) throws Exception;

	/**
	 * 业务程序员使用本方法为框架的下载文件服务提供文件名和实际文件
	 * 
	 * @param fileName
	 * @param file
	 * @return
	 */
	public DownloadFileInfo onPreparingFile(HttpServletRequest req,
			HttpServletResponse resp) throws Exception;
}
