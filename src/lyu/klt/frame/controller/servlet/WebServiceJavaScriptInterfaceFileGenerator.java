package lyu.klt.frame.controller.servlet;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Set;

import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.MethodDataModel;
import lyu.klt.frame.controller.handler.MethodInfo;
import lyu.klt.frame.utils.CharacterEncodingUtils;
import lyu.klt.frame.utils.FileUtils;

/**
 * @author Jam 2016年3月31日 上午9:04:49
 * 
 */
public class WebServiceJavaScriptInterfaceFileGenerator {

	final static private String BASE_PATH = "/js/webservice";
	final static private String MODULE_DEF_TEMPLATE = "if(typeof(this[\"{moduleName}\"])==\"undefined\")\n\tthis.{moduleName}={};\n";
	final static private String METHOD_DEF_TEMPLATE = "{moduleName}.{methodName}=function({params}successfulCallback,errorCallback){\n\tif(arguments.length!={paramsCount}){\n\t\talert(\"调用{moduleName}.{methodName}方法时提供的参数个数不正确，该方法需要下列参数{params}successCallback,errorCallback\");return;}\n\tvar params = {paramsData};\n\tWebService.service(\"{moduleName}.service?{methodName}\",params,successfulCallback,errorCallback);\n};\n";
	final static private String METHOD_REALTIME_DEF_TEMPLATE = "{moduleName}.{methodName}=function({params}successfulCallback,errorCallback){\n\tif(arguments.length!={paramsCount}){\n\t\talert(\"调用{moduleName}.{methodName}方法时提供的参数个数不正确，该方法需要下列参数{params}successCallback,errorCallback\");return;}\n\tvar params = {paramsData};\n\tWebServiceRealtime.service(\"{moduleName}.service?{methodName}\",params,successfulCallback,errorCallback);\n};\n";

	private Set<Class<?>> classes;

	/**
	 * @param classes
	 */
	public WebServiceJavaScriptInterfaceFileGenerator(Set<Class<?>> classes) {
		this.classes = classes;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:05:03
	 */
	public void process() throws Exception {
		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(WebServiceController.class)) {
				this.processOneClass(clazz);
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:11:10
	 * @param clazz
	 * @throws Exception
	 */
	private void processOneClass(Class<?> clazz) throws Exception {
		String moduleName = this.getModuleName(clazz);
		if (moduleName == null || moduleName.isEmpty()) {
			String msg = MultiLanguage
					.getResource(
							"000000",
							"WebServiceController的注解@WebServiceController需要一个值，该值是模块名，例如：@WebServiceController(\"Product\")，详情请见@WebServiceController");
			throw new Exception(msg);
		}
		MethodDataModel mdm = this.getMethodDataModel(clazz);
		String content = this.doGenerateJavaScriptFileText(mdm);
		this.saveJavaScriptFile(moduleName, content);
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:51:27
	 * @param mdm
	 * @return
	 * @throws Exception
	 */
	private String doGenerateJavaScriptFileText(MethodDataModel mdm)
			throws Exception {
		StringBuffer sb = new StringBuffer();

		String moduleDef = MODULE_DEF_TEMPLATE.replaceAll("\\{moduleName\\}",
				mdm.getModuleName());
		sb.append(moduleDef);

		for (MethodInfo mi : mdm.getMethodInfoList()) {

			String methodTemplate = METHOD_DEF_TEMPLATE;
			if (mi.isRealtime())
				methodTemplate = METHOD_REALTIME_DEF_TEMPLATE;

			String methodDef = methodTemplate.replaceAll("\\{moduleName\\}",
					mdm.getModuleName());
			methodDef = methodDef.replaceAll("\\{methodName\\}", mi.getName());
			methodDef = methodDef.replaceAll("\\{params\\}",
					this.getParameterList(mi.getParameterNames()));
			methodDef = methodDef.replaceAll("\\{paramsCount\\}",
					String.valueOf(mi.getParameterNames().length + 2));
			methodDef = methodDef.replaceAll("\\{paramsData\\}",
					this.getParameterDataJSONObject(mi.getParameterNames()));

			sb.append(methodDef);
		}

		return CharacterEncodingUtils.toUTF8String(sb.toString());
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午10:19:55
	 * @param parameterNames
	 * @return
	 */
	private String getParameterDataJSONObject(String[] parameterNames) {
		String result = "";
		for (String name : parameterNames) {
			result = result + name + ":" + name + ",";
		}
		if (result.length() > 0)
			result = result.substring(0, result.length() - 1);
		result = "{" + result + "}";
		return result;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午10:18:14
	 * @param parameterNames
	 * @return
	 */
	private String getParameterList(String[] parameterNames) {
		String result = "";
		for (String name : parameterNames) {
			result = result + name + ",";
		}
		return result;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:51:25
	 * @param text
	 */
	private void saveJavaScriptFile(String moduleName, String content)
			throws Exception {
		String directory = this.getClass().getResource("/").getPath() + "../.."
				+ BASE_PATH;
		directory = URLDecoder.decode(directory, "UTF-8");
		String fileName = String.format("%s.js", moduleName);
		FileUtils.saveFile(directory, fileName, content.getBytes());
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:21:14
	 * @return
	 */
	private String getModuleName(Class<?> clazz) {
		WebServiceController annotation = clazz
				.getAnnotation(WebServiceController.class);
		return annotation.value();
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:20:06
	 * @param clazz
	 * @return
	 */
	private MethodDataModel getMethodDataModel(Class<?> clazz) {
		MethodDataModel mdm = new MethodDataModel();

		String moduleName = this.getModuleName(clazz);
		mdm.setModuleName(moduleName);

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(WebServiceMethod.class)) {
				boolean realtime = method.getAnnotation(WebServiceMethod.class)
						.realtime();
				mdm.addMethod(method, realtime);
			}
		}
		return mdm;
	}
}