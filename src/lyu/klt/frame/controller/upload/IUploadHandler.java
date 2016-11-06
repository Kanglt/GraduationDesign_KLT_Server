package lyu.klt.frame.controller.upload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import lyu.klt.frame.controller.dispatcher.UploadRequest;

public interface IUploadHandler {

	/**
	 * 框架程序在获取到上传的文件后，会将原始文件名和临时文件传给业务程序员，由业务程序员进行处理
	 * 
	 * 重要：这个方法接口不可以做任何修改，因为UploadDispatcher.java对这个方法进行了反射，如果方法名和参数做了任何修改，
	 * 将直接导致UploadDispatcher.java失效
	 * 
	 * @param req
	 * @param request
	 * @param fileName
	 * @param tempFile
	 * @return
	 * @throws Exception
	 */
	public JSONObject onTempFileSaved(HttpServletRequest req,
			UploadRequest request, String fileName, File tempFile)
			throws Exception;
}
