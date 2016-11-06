package lyu.klt.frame.controller.method.parameter.datatype.handler;

import org.json.JSONObject;

import lyu.klt.frame.controller.method.parameter.datatype.DataTypeHandler;

public class IntegerPositiveDataTypeHandler implements DataTypeHandler {

	@Override
	public void inspect(String key, JSONObject obj) throws Exception {
		String fieldName = key;
		String tipName = obj.getString("tipName");
		String nullable = obj.getString("nullable");
		String javaCode = String.format(
				"Integer %s = formData.getIntegerPositive(\"%s\",\"%s\",%s);",
				fieldName, fieldName, tipName, nullable);

		System.out.println(javaCode);
	}

}
