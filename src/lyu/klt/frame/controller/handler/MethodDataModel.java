package lyu.klt.frame.controller.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * @author Jam 2016年3月31日 上午9:19:48
 * 
 */
public class MethodDataModel {

	private String moduleName;
	private List<MethodInfo> methodInfoList;

	public MethodDataModel() {
		this.methodInfoList = new ArrayList<MethodInfo>();
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:28:47
	 * @param method
	 */
	public void addMethod(Method method, boolean realtime) {
		MethodInfo mi = new MethodInfo();
		mi.setName(method.getName());
		mi.setRealtime(realtime);

		ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

		Class<?>[] types = method.getParameterTypes();
		String[] names = discoverer.getParameterNames(method);

		String[] fixedParameterNames = this
				.getFixedParameterNames(types, names);
		mi.setParameterNames(fixedParameterNames);

		this.methodInfoList.add(mi);
	}

	/**
	 * 排除request和response，这个两个类型的参数不需要放在JS接口中
	 * 
	 * @param types
	 * @param names
	 * @return
	 */
	private String[] getFixedParameterNames(Class<?>[] types, String[] names) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < types.length; i++) {
			if (!types[i].equals(HttpServletRequest.class)
					&& !types[i].equals(HttpServletResponse.class))
				list.add(names[i]);
		}
		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public List<MethodInfo> getMethodInfoList() {
		return methodInfoList;
	}

}