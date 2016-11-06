package lyu.klt.frame.controller.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import lyu.klt.frame.controller.annotation.PageController;
import lyu.klt.frame.controller.annotation.PageMethod;
import lyu.klt.frame.controller.global.MultiLanguage;

/**
 * @author Jam 2016年3月30日 下午2:38:09
 * 
 */
public class PageControllerRequestMapping {

	private static PageControllerRequestMapping INSTANCE = new PageControllerRequestMapping();

	public static PageControllerRequestMapping getInstance() throws Exception {
		return INSTANCE;
	}

	private Map<String, Class<?>> pageControllerServletPathMapping;
	private Map<String, Method> pageControllerMethodMapping;
	private Map<Method, String[]> pageControllerMethodParametersMapping;

	private PageControllerRequestMapping() {
		this.pageControllerServletPathMapping = new HashMap<String, Class<?>>();
		this.pageControllerMethodMapping = new HashMap<String, Method>();
		this.pageControllerMethodParametersMapping = new HashMap<Method, String[]>();
	}

	public void init(Set<Class<?>> classes) throws Exception {
		this.initPageControllerMapping(classes);
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午4:47:28
	 * @param result
	 */
	private void initPageControllerMapping(Set<Class<?>> classes)
			throws Exception {
		this.initPageControllerServletPathMapping(classes);
		this.initPageControllerMethodMapping();
		this.initPageControllerMethodParameterMapping();
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午4:52:53
	 * @param classSet
	 */
	private void initPageControllerServletPathMapping(Set<Class<?>> classSet)
			throws Exception {

		for (Class<?> clazz : classSet) {
			if (clazz.isAnnotationPresent(PageController.class)) {
				PageController annotation = clazz
						.getAnnotation(PageController.class);
				String servletPath = annotation.value();
				if (servletPath == null || servletPath.isEmpty())
					throw new Exception(
							String.format(
									MultiLanguage
											.getResource("000000",
													"PageController没有指定映射url，PageController：%s"),
									clazz.getName()));
				if (this.pageControllerServletPathMapping
						.containsKey(servletPath)) {
					String existsPageControllerName = this.pageControllerServletPathMapping
							.get(servletPath).getName();
					String toAddPageControllerName = clazz.getName();
					String msg = String
							.format(MultiLanguage
									.getResource(
											"000000",
											"PageController扫描发现重复的url映射，已存在的url：%s，已存在的PageController：%s，试图添加的url：%s，试图添加的PageController：%s"),
									existsPageControllerName, servletPath,
									toAddPageControllerName, servletPath);
					throw new Exception(msg);
				}
				this.pageControllerServletPathMapping.put(servletPath, clazz);
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午4:52:52
	 */
	private void initPageControllerMethodMapping() throws Exception {
		for (Class<?> clazz : this.pageControllerServletPathMapping.values()) {
			this.doInitPageControllerMethodMappingByOneClass(clazz);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午6:55:39
	 * @param clazz
	 */
	private void doInitPageControllerMethodMappingByOneClass(Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(PageMethod.class)) {
				String key = this.getMethodMappingKeyByClassNameAndMethodName(
						clazz, method.getName());
				this.pageControllerMethodMapping.put(key, method);
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午4:52:50
	 */
	private void initPageControllerMethodParameterMapping() throws Exception {
		for (Method method : this.pageControllerMethodMapping.values()) {
			ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
			String[] names = discoverer.getParameterNames(method);
			this.pageControllerMethodParametersMapping.put(method, names);
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:51:44
	 * @param servletPath
	 * @return
	 */
	public Object getPageControllerInstance(String servletPath)
			throws Exception {
		Class<?> clazz = this.pageControllerServletPathMapping.get(servletPath);
		if (clazz == null)
			throw new Exception(String.format(MultiLanguage.getResource(
					"000000", "url没有对应的PageController，url:%s"), servletPath));
		return clazz.newInstance();
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:53:41
	 * @param clazz
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public Method getPageControllerMethod(Class<? extends Object> clazz,
			String methodName) throws Exception {
		String key = this.getMethodMappingKeyByClassNameAndMethodName(clazz,
				methodName);
		Method method = this.pageControllerMethodMapping.get(key);
		if (method == null)
			throw new Exception(
					String.format(
							MultiLanguage
									.getResource(
											"000000",
											"PageController的方法不存在、或者不是public方法、或者没有@PageMethod注解，PageController名称：%s，方法名称：%s"),
							clazz.getName(), methodName));
		return method;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午3:03:23
	 * @param clazz
	 * @return
	 */
	public String[] getPageParameterNames(Method method) {
		return this.pageControllerMethodParametersMapping.get(method);
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午4:11:24
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	private String getMethodMappingKeyByClassNameAndMethodName(
			Class<? extends Object> clazz, String methodName) {
		return String.format("%s::%s", clazz, methodName);
	}

}