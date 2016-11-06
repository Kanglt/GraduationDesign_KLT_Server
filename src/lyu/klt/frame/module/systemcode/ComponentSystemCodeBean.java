package lyu.klt.frame.module.systemcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lyu.klt.frame.database.core.ProcedureResult;
import lyu.klt.frame.utils.IdGenerator;
import lyu.klt.frame.utils.Utils;

public class ComponentSystemCodeBean {

	private static Log log = LogFactory.getLog(ComponentSystemCodeBean.class);

	private String type;

	@SuppressWarnings("unused")
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private HttpServletResponse response;

	private List<Map<String, String>> list;
	private JSONArray extraItems;

	public ComponentSystemCodeBean() {
		this.type = "";
		this.list = new ArrayList<Map<String, String>>();
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) throws Exception {
		this.response = response;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setExtra(String extra) {
		if (extra != null && !extra.equals("")) {
			try {
				this.extraItems = new JSONArray(extra);
			} catch (JSONException e) {
				this.extraItems = null;
			}
		}
	}

	public void setExec(boolean exec) {
		try {
			if (this.extraItems != null) {
				for (int i = 0; i < this.extraItems.length(); i++) {
					JSONObject obj = (JSONObject) this.extraItems.get(i);
					@SuppressWarnings("unchecked")
					Iterator<String> it = obj.keys();
					while (it.hasNext()) {
						String codeName = it.next();
						String codeValue = obj.getString(codeName);
						Map<String, String> item = new HashMap<String, String>();
						item.put("id", String.valueOf(IdGenerator.getNewId()));
						item.put("codeName", codeName);
						item.put("codeValue", codeValue);
						this.list.add(item);
					}
				}
			}
		} catch (JSONException e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}

		int page = 0;// 全页
		int pageSize = 20;
		String queryValue = "";
		String queryParentId = "";
		String operatorId = "";
		String positionId = "";
		String operatorAddress = "";

		ProcedureResult pr;
		try {
			pr = new SystemCodeDataService().getComponentSelectList(this.type,
					queryParentId, queryValue, page, pageSize, positionId,
					operatorId, operatorAddress);
			this.list.addAll(this.fixId(pr.getList()));
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

	private Collection<? extends Map<String, String>> fixId(
			List<Map<String, String>> list) {
		for (Map<String, String> item : list) {
			item.put("id",
					item.get("id") + String.valueOf(new Random().nextInt()));
		}
		return list;
	}

	public List<Map<String, String>> getList() {
		return this.list;
	}
}