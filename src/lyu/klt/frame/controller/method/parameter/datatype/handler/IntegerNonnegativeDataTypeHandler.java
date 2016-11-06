package lyu.klt.frame.controller.method.parameter.datatype.handler;

import org.json.JSONObject;

import lyu.klt.frame.controller.method.parameter.datatype.DataTypeHandler;

public class IntegerNonnegativeDataTypeHandler implements DataTypeHandler {

	@Override
	public void inspect(String key, JSONObject obj) throws Exception {
		String fieldName = key;
		String tipName = obj.getString("tipName");
		String nullable = obj.getString("nullable");
		String javaCode = String
				.format("Integer %s = formData.getIntegerNonnegative(\"%s\",\"%s\",%s);",
						fieldName, fieldName, tipName, nullable);

		System.out.println(javaCode);
	}

}
