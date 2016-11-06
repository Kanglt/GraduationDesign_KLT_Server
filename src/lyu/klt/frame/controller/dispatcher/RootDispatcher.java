package lyu.klt.frame.controller.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lyu.klt.frame.controller.config.ConfigController;

/**
 * 本Servlet拦截所有/请求
 * 
 * @author Jam 2016年3月29日 上午11:12:58
 * 
 */
@WebServlet("/")
public class RootDispatcher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final int PAGE_REQUEST = 1;
	private static final int WEB_SERVICE_REQUEST = 2;

	/**
	 * 拦截get请求，并调用doService
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			this.doService(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 拦截post请求，调用doService
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			this.doService(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 系统启动时，会使用lyu.klt.frame.controller.servlet.Initializer进行初始化，如果初始化完成，
	 * 则可以正常访问controller，否则转向系统正在启动的页面，对静态资源没有这种限制
	 * 
	 * @author Jam 2016年3月29日 上午11:27:06
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 设置response特性
		this.doSetResponseBehavior(response);

		int type = this.getRequestType(request);
		switch (type) {
		case RootDispatcher.PAGE_REQUEST:
			// 如果是page请求，使用PageDispatcher进行处理
			new PageDispatcher(request, response).process();
			break;
		case RootDispatcher.WEB_SERVICE_REQUEST:
			// 如果是web service请求，使用WebServiceDispatcher
			new WebServiceDispatcher(request, response).process();
			break;
		}
	}

	/**
	 * 取得请求的类型，请求的类型只有两种 1、page请求 2、web service请求 URI以.service结尾
	 * 
	 * @author Jam 2016年3月29日 上午11:17:48
	 * @param request
	 * @return
	 */
	private int getRequestType(HttpServletRequest request) {
		String uri = request.getServletPath();
		if (uri.toLowerCase().endsWith(ConfigController.getWebServiceSuffix()))
			return RootDispatcher.WEB_SERVICE_REQUEST;
		else
			return RootDispatcher.PAGE_REQUEST;
	}

	/**
	 * 设置response的特性：不要缓存，返回数据为html文本，并使用UTF-8编码
	 * 
	 * @author Jam 2016年3月29日 上午11:20:15
	 * @param response
	 */
	private void doSetResponseBehavior(HttpServletResponse response) {
		// 不要缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 指定返回格式为html文本、UTF-8编码
		response.setContentType("text/html;charset=UTF-8");
	}

}

// URI解析说明：

// http://localhost:8080/fjzxFrameWeb/product/productManagementController?index

// String uri = request.getRequestURI();
// uri="/fjzxFrameWeb/product/productManagementController"

// String url = request.getRequestURL().toString();
// url="http://localhost:8080/fjzxFrameWeb/product/productManagementController"

// String contextPath = request.getContextPath();
// contextPath="/fjzxFrameWeb"

// String queryString = request.getQueryString();
// queryString="index"

// String servletPath = request.getServletPath();
// servletPath="/product/productManagementController"
