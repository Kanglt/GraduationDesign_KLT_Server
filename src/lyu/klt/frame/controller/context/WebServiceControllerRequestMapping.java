package lyu.klt.frame.controller.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.config.ConfigController;
import lyu.klt.frame.controller.global.MultiLanguage;

/**
 * @author Jam 2016年3月30日 下午8:15:23
 * 
 */
public class WebServiceControllerRequestMapping {

	private static WebServiceControllerRequestMapping INSTANCE = new WebServiceControllerRequestMapping();

	public static WebServiceControllerRequestMapping getInstance()
			throws Exception {
		return INSTANCE;
	}

	private Map<String, Class<?>> webServiceControllerServletPathMapping;
	private Map<String, Method> webServiceControllerMethodMapping;
	private Map<Method, String[]> webServiceControllerMethodParametersMapping;

	private WebServiceControllerRequestMapping() {
		this.webServiceControllerServletPathMapping = new HashMap<String, Class<?>>();
		this.webServiceControllerMethodMapping = new HashMap<String, Method>();
		this.webServiceControllerMethodParametersMapping = new HashMap<Method, String[]>();
	}

	public void init(Set<Class<?>> classes) throws Exception {
		this.initWebServiceControllerMapping(classes);
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午8:18:42
	 * @param classes
	 * @throws Exception
	 */
	private void initWebServiceControllerMapping(Set<Class<?>> classes)
			throws Exception {
		this.initWebServiceControllerServletPathMapping(classes);
		this.initWebServiceControllerMethodMapping();
		this.initWebServiceControllerMethodParameterMapping();
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午3:37:56
	 * @param classes
	 * @throws Exception
	 */
	private void initWebServiceControllerServletPathMapping(
			Set<Class<?>> classes) throws Exception {

		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(WebServiceController.class)) {
				WebServiceController annotation = clazz
						.getAnnotation(WebServiceController.class);
				String moduleName = annotation.value();
				if (moduleName == null || moduleName.isEmpty())
					throw new Exception(
							String.format(
									MultiLanguage
											.getResource("000000",
													"WebServiceController没有指定模块名，WebServiceController：%s"),
									clazz.getName()));

				String servletPath = String.format("/%s%s", moduleName,
						ConfigController.getWebServiceSuffix());

				if (this.webServiceControllerServletPathMapping
						.containsKey(servletPath)) {
					String existsPageControllerName = this.webServiceControllerServletPathMapping
							.get(servletPath).getName();
					String toAddPageControllerName = clazz.getName();
					String msg = String
							.format(MultiLanguage
									.getResource(
											"000000",
											"WebServiceController扫描发现重复模块名，已存在的模块名：%s，已存在的WebServiceController：%s，试图添加的模块名：%s，试图添加的WebServiceController：%s"),
									moduleName, existsPageControllerName,
									moduleName, toAddPageControllerName);
					throw new Exception(msg);
				}
				this.webServiceControllerServletPathMapping.put(servletPath,
						clazz);
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:23:35
	 * @throws Exception
	 */
	private void initWebServiceControllerMethodMapping() throws Exception {
		for (Class<?> clazz : this.webServiceControllerServletPathMapping
				.values()) {
			this.doInitWebServiceControllerMethodMappingByOneClass(clazz);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:23:44
	 * @param clazz
	 */
	private void doInitWebServiceControllerMethodMappingByOneClass(
			Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(WebServiceMethod.class)) {
				String key = this.getMethodMappingKeyByClassNameAndMethodName(
						clazz, method.getName());
				this.webServiceControllerMethodMapping.put(key, method);
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:26:11
	 * @throws Exception
	 */
	private void initWebServiceControllerMethodParameterMapping()
			throws Exception {
		for (Method method : this.webServiceControllerMethodMapping.values()) {
			ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
			String[] names = discoverer.getParameterNames(method);
			this.webServiceControllerMethodParametersMapping.put(method, names);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午8:54:04
	 * @param servletPath
	 * @return
	 * @throws Exception
	 */
	public Object getWebServiceControllerInstance(String servletPath)
			throws Exception {
		Class<?> clazz = this.webServiceControllerServletPathMapping
				.get(servletPath);
		if (clazz == null)
			throw new Exception(String.format(MultiLanguage.getResource(
					"000000", "url没有对应的WebServiceController，url:%s"),
					servletPath));
		return clazz.newInstance();
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午8:54:44
	 * @param clazz
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public Method getWebServiceControllerMethod(Class<? extends Object> clazz,
			String methodName) throws Exception {
		String key = this.getMethodMappingKeyByClassNameAndMethodName(clazz,
				methodName);
		Method method = this.webServiceControllerMethodMapping.get(key);
		if (method == null)
			throw new Exception(
					String.format(
							MultiLanguage
									.getResource(
											"000000",
											"WebServiceController的方法不存在、或者不是public方法、或者没有@WebServiceMethod注解，WebServiceController名称：%s，方法名称：%s"),
							clazz.getName(), methodName));
		return method;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午8:56:00
	 * @param class1
	 * @return
	 */
	public String[] getWebServiceParameterNames(Method method) {
		return this.webServiceControllerMethodParametersMapping.get(method);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 下午4:09:42
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	private String getMethodMappingKeyByClassNameAndMethodName(
			Class<? extends Object> clazz, String methodName) {
		return String.format("%s::%s", clazz, methodName);
	}

}