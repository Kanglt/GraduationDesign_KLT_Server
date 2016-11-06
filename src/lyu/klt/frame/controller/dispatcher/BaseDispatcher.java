package lyu.klt.frame.controller.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lyu.klt.frame.controller.context.ControllerContext;

/**
 * page请求和web service请求的抽象基类，该类主要完成以下事情： 1、为请求提供上下文context 2、 3、
 * 
 * @author Jam 2016年3月29日 上午11:28:52
 * 
 */
abstract public class BaseDispatcher {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public BaseDispatcher(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		this.request = request;
		this.response = response;

		this.init();
	}

	/**
	 * 初始化
	 * 
	 * @author Jam 2016年3月29日 上午11:34:09
	 * @throws Exception
	 */
	private void init() throws Exception {
		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(this.request);
		context.setResponse(this.response);
	}

	abstract public void process() throws Exception;
}