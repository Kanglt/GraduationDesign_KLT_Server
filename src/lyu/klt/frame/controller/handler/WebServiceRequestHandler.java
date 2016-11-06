package lyu.klt.frame.controller.handler;

import java.lang.annotation.Annotation;
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
import lyu.klt.frame.controller.context.WebServiceControllerRequestMapping;
import lyu.klt.frame.controller.exception.AccessException;
import lyu.klt.frame.controller.exception.RequireLoginException;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.method.parameter.ParameterConverter;
import lyu.klt.frame.controller.method.parameter.annotation.Decrypt;
import lyu.klt.frame.controller.method.parameter.annotation.IDecryptHandler;
import lyu.klt.frame.controller.method.parameter.annotation.Nullable;

/**
 * @author Jam 2016年3月30日 下午9:03:17
 * 
 */
public class WebServiceRequestHandler {

	private static Log log = LogFactory.getLog(WebServiceRequestHandler.class);

	private ControllerContext context;
	private Object controller;
	private Method method;

	/**
	 * @param context
	 */
	public WebServiceRequestHandler(ControllerContext context) {
		this.context = context;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:28:23
	 * @throws Exception
	 */
	public String process() throws Exception {
		String servletPath = this.context.getRequest().getServletPath();
		String methodName = this.getMethodName();

		this.controller = this.getWebServiceControllerInstance(servletPath);
		this.method = this.getWebServiceControllerMethod(methodName);

		if (this.method.isAnnotationPresent(Access.class)) {
			Access accessAnnotation = this.method.getAnnotation(Access.class);
			IAccessHandler iAccessHandler = accessAnnotation.handler()
					.newInstance();
			if (!iAccessHandler.isValid(context.getRequest())) {
				log.error(new ControllerLogMessage(
						"没有权限访问该资源，试图访问的WebServiceController:%s，试图访问的Method:%s",
						this.controller.getClass().getName(), methodName));
				throw new RequireLoginException("000000",
						"您没有权限进行本操作，请用有权限的帐号重新登录");
			}
			if (!accessAnnotation.requiredOperationCode().isEmpty()) {
				if (!iAccessHandler.userHasRequiredOperationCode(
						context.getRequest(),
						accessAnnotation.requiredOperationCode())) {
					log.error(new ControllerLogMessage(
							"没有权限访问该资源，试图访问的WebServiceController:%s，试图访问的Method:%s",
							this.controller.getClass().getName(), methodName));
					throw new AccessException("000000",
							"您没有权限进行本操作，请用有权限的帐号重新登录");
				}
			}

		}

		log.debug(new ControllerLogMessage("执行　　<-----WebServiceController----->　　: %s，方法: %s",
				this.controller.getClass().getName(), this.method.getName()));
		Object[] args = this.getParameters();
		String result = (String) this.method.invoke(this.controller, args);

		return result;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午9:10:00
	 * @param servletPath
	 * @return
	 * @throws Exception
	 */
	private Object getWebServiceControllerInstance(String servletPath)
			throws Exception {
		return WebServiceControllerRequestMapping.getInstance()
				.getWebServiceControllerInstance(servletPath);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:28:34
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	private Method getWebServiceControllerMethod(String methodName)
			throws Exception {
		return WebServiceControllerRequestMapping.getInstance()
				.getWebServiceControllerMethod(this.controller.getClass(),
						methodName);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:28:45
	 * @return
	 * @throws Exception
	 */
	private Object[] getParameters() throws Exception {
		List<Object> list = new ArrayList<Object>();
		String[] names = WebServiceControllerRequestMapping.getInstance()
				.getWebServiceParameterNames(method);
		Class<?>[] types = method.getParameterTypes();
		Annotation[][] annotationsList = method.getParameterAnnotations();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Class<?> type = types[i];
			Annotation[] annotations = annotationsList[i];
			Object value = this.getParameterValue(annotations, type, name);
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
	 * @author Jam 2016年3月31日 上午8:56:34
	 * @param type
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private Object getParameterValue(Annotation[] annotations, Class<?> type,
			String name) throws Exception {
		HttpServletRequest request = ControllerContext.getCurrentInstance()
				.getRequest();
		HttpServletResponse response = ControllerContext.getCurrentInstance()
				.getResponse();

		if (type.equals(HttpServletRequest.class))
			return request;

		if (type.equals(HttpServletResponse.class))
			return response;

		String valueStr = request.getParameter(name);
		if ((valueStr == null || valueStr.isEmpty())
				&& !this.existsAnnotationNullAble(annotations))
			throw new Exception(
					String.format(
							MultiLanguage
									.getResource("000000",
											"WebServiceController的方法需要参数值，WebServiceController：%s，Method：%s，需要参数：%s"),
							this.controller.getClass().getName(), this.method
									.getName(), name));
		Decrypt decryptAnnotation = (Decrypt) this
				.getAnnotationDecrypt(annotations);
		if (decryptAnnotation != null) {
			IDecryptHandler handler = (IDecryptHandler) decryptAnnotation
					.handler().newInstance();
			valueStr = handler.decrypt(valueStr);
		}

		return ParameterConverter.getParameterByType(type, valueStr);
	}

	private Annotation getAnnotationDecrypt(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Decrypt)
				return annotation;
		}
		return null;
	}

	private boolean existsAnnotationNullAble(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Nullable)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午9:06:50
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
						"访问WebServiceController的url必须有一个参数，这个参数是Controller的方法名，这个参数必须位于第一个位置，如http://www.sina.com.cn/userController.service?login，login是方法名");
		if (queryString == null || queryString.isEmpty())
			throw new Exception(msg + "（当前访问的URL是：" + requestUrl + "）");
		String[] params = queryString.split("&");
		if (params.length <= 0 || params[0].isEmpty())
			throw new Exception(msg);
		return params[0];
	}

}