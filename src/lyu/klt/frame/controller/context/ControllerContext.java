package lyu.klt.frame.controller.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lyu.klt.frame.controller.transaction.TransactionSession;
import lyu.klt.frame.database.ConnectionAgent;

/**
 * Controller上下文，这个是一个线程安全的Context ControllerContext保存的内容如下：
 * 1、HttpServletRequest 2、HttpServletResponse
 * 
 * @author Jam 2016年3月29日 下午2:05:59
 * 
 */
public class ControllerContext {

	private static ThreadLocal<ControllerContext> instance = new ThreadLocal<ControllerContext>() {
		protected ControllerContext initialValue() {
			return new ControllerContext();
		}
	};

	/**
	 * 获得当前线程的上下文
	 * 
	 * @return
	 */
	public static ControllerContext getCurrentInstance() {
		return (instance.get());
	}

	private HttpServletRequest request;
	private HttpServletResponse response;
	private TransactionSession transactionSession;
	private String id;

	public ControllerContext() {
		super();
	}

	/**
	 * 
	 * @author Jam 2016年3月29日 下午2:13:32
	 * @param request
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 
	 * @author Jam 2016年3月29日 下午2:13:36
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:00:54
	 * @return
	 * @throws Exception
	 */
	public TransactionSession openTransactionSession() throws Exception {
		if (this.transactionSession == null) {
			this.transactionSession = new TransactionSession(ConnectionAgent
					.getInstance().getConnection());
		}
		return this.transactionSession;
	}

	public String getOperatorAddress() {
		return this.getIP4(this.getRequest());
	}

	public String getIP4(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 本方法很重要，不要改动，除非对Servlet的单例多线程的原理非常熟悉
	 */
	public void closeTransactionSession() {
		// this.transactionSession是个线程内共享的单例，在线程结束时必须置为空，然后线程进入线程池，下一次线程重新启用时，this.transactionSession才能被正确初始化
		this.transactionSession = null;
	}

}