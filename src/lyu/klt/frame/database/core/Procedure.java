package lyu.klt.frame.database.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Procedure {

	public static final String ACTION_INSERT = "insert";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_DELETE = "delete";
	public static final String USER_SYSTEM = "system";

	private static final int NULL_TYPE_STRING = java.sql.Types.VARCHAR;
	private static final int NULL_TYPE_BOOLEAN = java.sql.Types.BOOLEAN;
	private static final int NULL_TYPE_DECIMAL = java.sql.Types.DECIMAL;
	private static final int NULL_TYPE_DATETIME = java.sql.Types.TIMESTAMP;
	private static final int NULL_TYPE_INTEGER = java.sql.Types.INTEGER;

	private String procedureName;
	private List<Parameter> parameters;

	public Procedure(String procedureName) {
		super();
		this.procedureName = procedureName;
		this.parameters = new ArrayList<Parameter>();
	}

	public String getProcedureSql() {
		String sql = String.format("EXEC [dbo].[%s] \n%s\n",
				this.procedureName, this.getParametersSql());
		return sql;
	}

	/**
	 * 
	 * @author Jam 2016年4月2日 上午8:49:15
	 * @return
	 */
	private String getParametersSql() {
		String result = "";
		for (Parameter p : this.getParameters()) {
			result = result + String.format("?,--%s\n", p.getName());
		}
		return result;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void addParameterString(String name, String value) {
		this.addParameter(name, value, NULL_TYPE_STRING);
	}

	public void addParameterBoolean(String name, Boolean value) {
		this.addParameter(name, value, NULL_TYPE_BOOLEAN);
	}

	public void addParameterDouble(String name, Double value) {
		this.addParameter(name, value, NULL_TYPE_DECIMAL);
	}

	public void addParameterTimestamp(String name, Timestamp value) {
		this.addParameter(name, value, NULL_TYPE_DATETIME);
	}

	public void addParameterInteger(String name, Integer value) {
		this.addParameter(name, value, NULL_TYPE_INTEGER);
	}

	private void addParameter(String name, Object value, int nullType) {
		Parameter p = null;
		if (value != null) {
			p = new Parameter(name);
			p.setType(value.getClass());
			p.setValue(value);
		} else {
			p = new Parameter(name, nullType);
		}
		this.parameters.add(p);
	}

	/**
	 * 
	 * @author Jam 2016年4月2日 上午8:40:06
	 * @return
	 * @throws Exception
	 */
	public ProcedureResult exec() throws Exception {
		return new DataBaseServiceAgent().execProcedure(this);
	}

	public String getProcedureName() {
		return procedureName;
	}

	/**
	 * 
	 * @author Jam 2016年4月2日 下午12:31:44
	 * @return
	 */
	public String getDebugSql() {
		String sql = String
				.format("=======================================\nBEGIN TRAN\n\nDECLARE @msg VARCHAR(1000),@size int\n\nEXEC [dbo].[%s] \n%s\n\nSELECT @msg AS msg,@size AS recordSize\n\nROLLBACK TRAN\n=======================================\n",
						this.procedureName, this.getDebugParametersSql());
		return sql;
	}

	/**
	 * 
	 * @author Jam 2016年4月2日 下午12:33:32
	 * @return
	 */
	private String getDebugParametersSql() {
		return String.format("%s@msg OUTPUT,--msg\n@size OUTPUT--recordSize",
				this.getDebugParametersSqlCustom());
	}

	/**
	 * 
	 * @author Jam 2016年4月2日 下午12:37:51
	 * @return
	 */
	private Object getDebugParametersSqlCustom() {
		String result = "";
		for (Parameter p : this.getParameters()) {
			String line = "";
			if (p.getValue() == null)
				line = String.format("%s,--%s\n", "null", p.getName());
			else
				line = String
						.format("'%s',--%s\n", p.getValue(), p.getName());
			result = result + line;
		}
		return result;
	}
}