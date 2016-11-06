package lyu.klt.frame.database.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProcedureResult {

	private String msg;
	private int recordSize;

	private List<Map<String, String>> list;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	public JSONArray getListAsJSONArray() throws Exception {
		JSONArray result = new JSONArray();

		List<Map<String, String>> list = this.getList();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> row = list.get(i);
			JSONObject obj = new JSONObject();
			for (Entry<String, String> col : row.entrySet()) {
				obj.put(col.getKey(), col.getValue());
			}
			result.put(obj);
		}
		return result;
	}

	public JSONObject getRecordAsJSONObject() throws Exception {
		if (this.list.size() <= 0)
			throw new Exception(this.msg);

		JSONObject result = new JSONObject();
		Map<String, String> record = this.list.get(0);
		for (Entry<String, String> item : record.entrySet()) {
			result.put(item.getKey(), item.getValue());
		}
		return result;
	}

	public String getFieldValue(Integer rowIndex, String fieldName) {
		if (this.list == null)
			return "";

		if (rowIndex < 0 || rowIndex > this.list.size() - 1)
			return "";

		Map<String, String> map = this.list.get(rowIndex);
		if (!map.containsKey(fieldName))
			return "";

		return map.get(fieldName);
	}

	/**
	 * 将存储过程中读取到的List<Map<String,String>>转换为List<Class<?>>
	 * 本例使用了泛型，该泛型类必须实现一个无参数的构造函数，以供newInstance方法使用
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getListAsObject(Class<T> clazz) throws Exception {
		List<T> resultList = new ArrayList<T>();

		List<Map<String, String>> list = this.getList();
		for (Map<String, String> map : list) {
			T entity = clazz.newInstance();
			BeanUtils.populate(entity, map);
			resultList.add(entity);
		}

		return resultList;
	}

	/**
	 * 读取单条记录Map<String,String>，并将其转化为Class<?>对象
	 * 本例使用了泛型，该泛型类必须实现一个无参数的构造函数，以供newInstance方法使用
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T getRecordAsObject(Class<T> clazz) throws Exception {
		if (this.list.size() <= 0)
			throw new Exception(this.msg);

		T entity = clazz.newInstance();
		BeanUtils.populate(entity, this.list.get(0));

		return entity;
	}

	@Override
	public String toString() {
		String result = String.format("Result Info:\n{msg:%s size:%d}\n",
				this.getMsg(), this.getRecordSize());
		String resultSet = "Result Set:\n";
		List<Map<String, String>> list = this.getList();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> row = list.get(i);
			String line = "";
			int j = 0;
			for (Entry<String, String> col : row.entrySet()) {
				j++;
				line = line
						+ String.format("[     |列%d|   %s:%s     ]", j,
								col.getKey(), col.getValue());
			}
			resultSet = resultSet + String.format("{—行%d—%s}\n", i, line);
		}
		return result + resultSet;
	}

}