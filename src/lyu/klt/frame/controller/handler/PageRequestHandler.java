package lyu.klt.frame.controller.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.annotation.IAccessHandler;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.context.PageControllerRequestMapping;
import lyu.klt.frame.controller.exception.AccessException;
import lyu.klt.frame.controller.global.MultiLanguage;

/**
 * @author Jam 2016年3月30日 下午2:05:28
 * 
 */
public class PageRequestHandler {

	private static Log log = LogFactory.getLog(PageRequestHandler.class);

	private ControllerContext context;
	private Object controller;
	private Method method;

	/**
	 * @param context
	 */
	public PageRequestHandler(ControllerContext context) {
		this.context = context;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:05:39
	 */
	public void process() throws Exception {
		String servletPath = this.context.getRequest().getServletPath();
		String methodName = this.getMethodName();

		this.controller = this.getPageControllerInstance(servletPath);
		this.method = this.getPageControllerMethod(methodName);

		if (this.method.isAnnotationPresent(Access.class)) {
			Access accessAnnotation = this.method.getAnnotation(Access.class);
			IAccessHandler iAccessHandler = accessAnnotation.handler()
					.newInstance();
			if (!iAccessHandler.isValid(context.getRequest())) {
				throw new AccessException("000000",
						"没有权限访问该资源，试图访问的PageController:%s，试图访问的Method:%s",
						this.controller.getClass().getName(), methodName);
			}
		}

		log.debug(new ControllerLogMessage("执行　　[=====PageController=====]　　: %s，方法: %s",
				this.controller.getClass().getName(), this.method.getName()));
		Object[] args = this.getParameters();
		String url = (String) this.method.invoke(this.controller, args);

		HttpServletRequest request = this.context.getRequest();
		HttpServletResponse response = this.context.getResponse();

		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:45:53
	 * @param servletPath
	 * @return
	 * @throws Exception
	 */
	private Object getPageControllerInstance(String servletPath)
			throws Exception {
		return PageControllerRequestMapping.getInstance()
				.getPageControllerInstance(servletPath);
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:45:50
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	private Method getPageControllerMethod(String methodName) throws Exception {
		return PageControllerRequestMapping
				.getInstance()
				.getPageControllerMethod(this.controller.getClass(), methodName);
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:45:48
	 * @return
	 * @throws Exception
	 */
	private Object[] getParameters() throws Exception {
		List<Object> list = new ArrayList<Object>();
		String[] names = PageControllerRequestMapping.getInstance()
				.getPageParameterNames(method);
		Class<?>[] types = method.getParameterTypes();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Class<?> type = types[i];
			Object value = this.getParameterValue(type, name);
			list.add(value);
		}
		Object[] result = new Object[names.length];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午7:31:03
	 * @param i
	 * @param type
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private Object getParameterValue(Class<?> type, String name)
			throws Exception {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		if (type.equals(HttpServletRequest.class))
			return request;

		if (type.equals(HttpServletResponse.class))
			return response;

		String value = request.getParameter(name);
		if (value != null) {
			return value.trim();
		} else {
			throw new Exception(String.format(MultiLanguage.getResource(
					"000000",
					"Controller的方法需要参数值，Controller：%s，Method：%s，需要参数：%s"),
					this.controller.getClass().getName(),
					this.method.getName(), name));
		}
	}

	/**
	 * 从queryString中获取要调用的controller的方法名，规则为queryString中的第一个参数，<br />
	 * 如http://localhost:8080/fjzxFrameWeb/product/productController?index&
	 * menuId=Xe2TE41ze1D，<br />
	 * index就是java的方法名
	 * 
	 * @author Jam 2016年3月30日 下午2:09:46
	 * @return
	 * @throws Exception
	 */
	private String getMethodName() throws Exception {
		String queryString = this.context.getRequest().getQueryString();
		String requestUrl = this.context.getRequest().getRequestURL()
				.toString();
		String msg = MultiLanguage
				.getResource(
						"000000",
						"访问PageController的url必须有一个参数，这个参数是Controller的方法名，这个参数必须位于第一个位置，如http://www.sina.com.cn/userController?index，index是方法名");
		if (queryString == null || queryString.isEmpty())
			throw new Exception(
					msg
							+ "（当前访问的URL是："
							+ requestUrl
							+ "，如果URL是静态资源，请考虑修改web.xml中的<servlet-mapping><servlet-name>default</servlet-name></servlet-mapping>配置）");
		String[] params = queryString.split("&");
		if (params.length <= 0 || params[0].isEmpty())
			throw new Exception(msg);
		return params[0];
	}

}