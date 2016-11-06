package lyu.klt.frame.controller.method.parameter.datatype;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.method.parameter.datatype.handler.BooleanDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.DateDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.DoubleDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.DoubleNonnegativeDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.DoublePositiveDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.IntegerDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.IntegerNonnegativeDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.IntegerPositiveDataTypeHandler;
import lyu.klt.frame.controller.method.parameter.datatype.handler.StringDataTypeHandler;
import lyu.klt.frame.utils.Utils;

public class FormData extends JSONObject {

	private static Log log = LogFactory.getLog(FormData.class);

	private static Map<String, DataTypeHandler> MAP = new HashMap<String, DataTypeHandler>();

	static {
		MAP.put("fjzx-prog-string", new StringDataTypeHandler());
		MAP.put("fjzx-prog-boolean", new BooleanDataTypeHandler());
		MAP.put("fjzx-prog-integer", new IntegerDataTypeHandler());
		MAP.put("fjzx-prog-integer-nonnegative",
				new IntegerNonnegativeDataTypeHandler());
		MAP.put("fjzx-prog-integer-positive",
				new IntegerPositiveDataTypeHandler());
		MAP.put("fjzx-prog-double", new DoubleDataTypeHandler());
		MAP.put("fjzx-prog-double-nonnegative",
				new DoubleNonnegativeDataTypeHandler());
		MAP.put("fjzx-prog-double-positive",
				new DoublePositiveDataTypeHandler());
		MAP.put("fjzx-prog-date", new DateDataTypeHandler());
	}

	public FormData(String formDataStr) throws Exception {
		super(formDataStr);
	}

	public void log() throws Exception {

		log.debug(new ControllerLogMessage(
				"<====================FormData Info====================>"));

		@SuppressWarnings("unchecked")
		Iterator<String> it = this.keys();
		while (it.hasNext()) {
			String key = it.next();
			this.logValidateDataTypeJavaCodeOne(key);
		}

		log.debug(new ControllerLogMessage(
				"---------------------------------------------------------------------------------------"));
		log.debug(new ControllerLogMessage(this.toString()));
		log.debug(new ControllerLogMessage(
				"<====================FormData Info====================>\n"));
	}

	private void logValidateDataTypeJavaCodeOne(String key) throws Exception {
		JSONObject obj = this.getJSONObject(key);
		String inputType = obj.getString("inputType");
		if (inputType.equalsIgnoreCase("text")) {
			this.inspectText(key, obj);
		} else if (inputType.equalsIgnoreCase("radio")) {
			this.inspectRadio(key, obj);
		} else if (inputType.equalsIgnoreCase("checkbox")) {
			this.inspectCheckbox(key, obj);
		} else if (inputType.equalsIgnoreCase("select")) {
			this.inspectSelect(key, obj);
		} else if (inputType.equalsIgnoreCase("treeSelect")) {
			this.inspectTreeSelect(key, obj);
		} else
			throw new Exception(
					"不支持的输入类型，目前web只支持text(text、password、textarea)、radio、checkbox、componentSelect四种输入类型");
	}

	private void inspectTreeSelect(String key, JSONObject obj) throws Exception {
		String dataType = obj.getString("dataType");

		DataTypeHandler handler = MAP.get(dataType);
		if (handler == null)
			throw new Exception("不支持的数据类型");

		handler.inspect(key, obj);
	}

	private void inspectSelect(String key, JSONObject obj) throws Exception {
		String dataType = obj.getString("dataType");

		DataTypeHandler handler = MAP.get(dataType);
		if (handler == null)
			throw new Exception("不支持的数据类型");

		handler.inspect(key, obj);
	}

	private void inspectText(String key, JSONObject obj) throws Exception {
		String dataType = obj.getString("dataType");

		DataTypeHandler handler = MAP.get(dataType);
		if (handler == null)
			throw new Exception("不支持的数据类型");

		handler.inspect(key, obj);
	}

	private void inspectRadio(String key, JSONObject obj) throws Exception {
		String fieldName = key;
		String tipName = obj.getString("tipName");
		String nullable = obj.getString("nullable");
		String javaCode = String.format(
				"String %s = formData.getString(\"%s\",\"%s\",%s);", fieldName,
				fieldName, tipName, nullable);

		log.debug(new ControllerLogMessage(javaCode));
	}

	private void inspectCheckbox(String key, JSONObject obj) throws Exception {
		String fieldName = key;
		String tipName = obj.getString("tipName");
		String nullable = obj.getString("nullable");
		String javaCode = String.format(
				"String[] %s = formData.getStringArray(\"%s\",\"%s\",%s);",
				fieldName, fieldName, tipName, nullable);

		log.debug(new ControllerLogMessage(javaCode));
	}

	public String getString(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if (value.equals("[\"\"]")) {
			log.debug(new ControllerLogMessage(
					"系统检测到\"%s\"的组件是checkbox，但是系统需要的是radio或一个字符串值",
					fieldTipName));
			throw new BusinessException("000000", "额，服务器出了点小问题");
		}
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		return value;
	}

	public String[] getStringArray(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		JSONArray value = obj.getJSONArray("value");
		if ((value == null || value.length() <= 0) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String[] result = new String[value.length()];
		for (int i = 0; i < value.length(); i++) {
			result[i] = value.getString(i);
		}
		return result;
	}

	public Integer getInteger(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		Integer result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Integer.valueOf(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是整数", fieldTipName);
		}
		return result;
	}

	public Integer getIntegerPositive(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		Integer result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Integer.valueOf(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是正整数", fieldTipName);
		}
		if (result != null && result <= 0)
			throw new BusinessException("000000", "%s必须是正整数", fieldTipName);

		return result;
	}

	public Integer getIntegerNonnegative(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		Integer result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Integer.valueOf(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是非负整数", fieldTipName);
		}
		if (result != null && result < 0)
			throw new BusinessException("000000", "%s必须是非负整数", fieldTipName);

		return result;
	}

	public Double getDouble(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		Double result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Double.valueOf(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是浮点数", fieldTipName);
		}
		return result;
	}

	public Double getDoublePositive(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		Double result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Double.valueOf(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是正数", fieldTipName);
		}
		if (result != null && result <= 0)
			throw new BusinessException("000000", "%s必须是正数", fieldTipName);

		return result;
	}

	public Double getDoubleNonnegative(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		Double result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Double.valueOf(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是非负数", fieldTipName);
		}
		if (result != null && result < 0)
			throw new BusinessException("000000", "%s必须是非负数", fieldTipName);

		return result;
	}

	public Timestamp getTimestamp(String fieldName, String fieldTipName,
			boolean nullable) throws Exception {
		JSONObject obj = this.getJSONObject(fieldName);
		if (obj == null && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		String value = null;
		if (obj != null)
			value = obj.getString("value");
		if ((value == null || value.isEmpty()) && !nullable)
			throw new BusinessException("000000", "%s不可以为空", fieldTipName);

		if (value.isEmpty())
			return null;

		Date result = null;
		try {
			if (value.isEmpty())
				return null;
			result = Utils.parseDate(value);
		} catch (Exception e) {
			throw new BusinessException("000000", "%s必须是日期", fieldTipName);
		}
		return new Timestamp(result.getTime());
	}

}
