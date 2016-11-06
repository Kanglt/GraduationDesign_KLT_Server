package lyu.klt.frame.controller.method.parameter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.method.parameter.datatype.DoubleNonnegative;
import lyu.klt.frame.controller.method.parameter.datatype.DoublePositive;
import lyu.klt.frame.controller.method.parameter.datatype.FormData;
import lyu.klt.frame.controller.method.parameter.datatype.IntegerNonnegative;
import lyu.klt.frame.controller.method.parameter.datatype.IntegerPositive;
import lyu.klt.frame.utils.Utils;

public class ParameterConverter {

	private static Map<Class<?>, Converter> MAP = new HashMap<Class<?>, Converter>();

	static {
		// String
		MAP.put(String.class, new Converter() {
			@Override
			public Object toValue(String valueStr) {
				return valueStr;
			}
		});
		// Integer
		MAP.put(Integer.class, new Converter() {
			@Override
			public Object toValue(String valueStr) {
				if (valueStr == null)
					return null;

				return Integer.valueOf(valueStr);
			}
		});
		// Double
		MAP.put(Double.class, new Converter() {
			@Override
			public Object toValue(String valueStr) {
				if (valueStr == null)
					return null;

				return Double.valueOf(valueStr);
			}
		});
		// Date
		MAP.put(Date.class, new Converter() {
			@Override
			public Object toValue(String valueStr) throws Exception {
				if (valueStr == null)
					return null;

				return Utils.parseDate(valueStr);
			}
		});
		// IntegerPositive
		MAP.put(IntegerPositive.class, new Converter() {
			@Override
			public Object toValue(String valueStr) throws Exception {
				if (valueStr == null)
					return null;

				Integer value = Integer.valueOf(valueStr);
				if (value <= 0)
					throw new Exception("参数必须为正整数");
				return new IntegerPositive(value);
			}
		});
		// IntegerNonnegative
		MAP.put(IntegerNonnegative.class, new Converter() {
			@Override
			public Object toValue(String valueStr) throws Exception {
				if (valueStr == null)
					return null;

				Integer value = Integer.valueOf(valueStr);
				if (value < 0)
					throw new Exception("参数必须为非负整数");
				return new IntegerNonnegative(value);
			}
		});
		// DoublePositive
		MAP.put(DoublePositive.class, new Converter() {
			@Override
			public Object toValue(String valueStr) throws Exception {
				if (valueStr == null)
					return null;

				Double value = Double.valueOf(valueStr);
				if (value <= 0)
					throw new Exception("参数必须为正浮点数");
				return new DoublePositive(value);
			}
		});
		// DoubleNonnegative
		MAP.put(DoubleNonnegative.class, new Converter() {
			@Override
			public Object toValue(String valueStr) throws Exception {
				if (valueStr == null)
					return null;

				Double value = Double.valueOf(valueStr);
				if (value < 0)
					throw new Exception("参数必须为非负浮点数");
				return new DoubleNonnegative(value);
			}
		});
		// FormData
		MAP.put(FormData.class, new Converter() {
			@Override
			public Object toValue(String valueStr) throws Exception {
				if (valueStr == null)
					return null;
				else
					return new FormData(valueStr);
			}
		});
	}

	public static Object getParameterByType(Class<?> clazz, String valueStr)
			throws Exception {
		if (!MAP.containsKey(clazz))
			throw new Exception(String.format(MultiLanguage.getResource(
					"000000", "不支持的Controller方法参数类型，方法试图使用的参数类型：%s"), clazz
					.getName()));

		return MAP.get(clazz).toValue(valueStr);
	}
}
