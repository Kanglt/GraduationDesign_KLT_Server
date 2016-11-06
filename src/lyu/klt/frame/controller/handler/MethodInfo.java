/**
 * 
 */
package lyu.klt.frame.controller.handler;

/**
 * @author Jam 2016年3月31日 上午9:43:20
 * 
 */
public class MethodInfo {

	private String name;
	private boolean realtime;
	private String[] parameterNames;

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:43:54
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @author Jam 2016年3月31日 上午9:46:27
	 * @param names
	 */
	public void setParameterNames(String[] names) {
		this.parameterNames = names;
	}

	public String getName() {
		return name;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public boolean isRealtime() {
		return realtime;
	}

	public void setRealtime(boolean realtime) {
		this.realtime = realtime;
	}

}