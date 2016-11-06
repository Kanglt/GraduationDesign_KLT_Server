package lyu.klt.frame.database.core;

public class Parameter {

	private String name;

	private boolean nullValue;
	private int nullType;

	private Object value;
	private Class<? extends Object> type;

	public Parameter(String name) {
		this.name = name;
		this.value = null;
		this.type = null;
		this.nullValue = false;
		this.nullType = 0;
	}

	public Parameter(String name, int nullType) {
		this.name = name;
		this.value = null;
		this.type = null;
		this.nullValue = true;
		this.nullType = nullType;
	}

	public Parameter(Class<? extends Object> type, Object value) {
		super();
		this.value = value;
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<? extends Object> getType() {
		return type;
	}

	public void setType(Class<? extends Object> type) {
		this.type = type;
	}

	public boolean isNullValue() {
		return nullValue;
	}

	public void setNullValue(boolean nullValue) {
		this.nullValue = nullValue;
	}

	public int getNullType() {
		return nullType;
	}

	public void setNullType(int nullType) {
		this.nullType = nullType;
	}

	public String getName() {
		return name;
	}

}