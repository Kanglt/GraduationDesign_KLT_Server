package lyu.klt.frame.controller.dispatcher;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UploadRequest {

	private Map<String, String> params;

	public UploadRequest() {
		super();
		this.params = new HashMap<String, String>();
	}

	public void add(String name, String value) throws UnsupportedEncodingException {
		this.params.put(name, new String(value.getBytes("8859_1"),"UTF-8"));
	}

	public String getParameter(String name) {
		return this.params.get(name);
	}

	public Map<String, String> getParameterMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.putAll(this.params);
		return map;
	}

	public Set<String> getParameterNames() {
		return this.params.keySet();
	}
}
