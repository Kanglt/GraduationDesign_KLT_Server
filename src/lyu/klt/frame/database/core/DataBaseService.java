package lyu.klt.frame.database.core;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.annotation.ControllerLogMessage;
import lyu.klt.frame.controller.exception.BusinessException;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.utils.Utils;

public class DataBaseService {

	private static Log log = LogFactory.getLog(DataBaseService.class);

	public DataBaseService() {
		super();
	}

	/**
	 * 
	 * 执行带@msg output、@size output两个输出参数的存储过程。<br />
	 * -@msg output值为存储过程返回的消息。<br />
	 * -@size output的值为本存储过程影响的记录数，如果为query类存储过程则为记录总数。<br />
	 * 执行后，这两个输出参数的结果值保存在ProcedureResult中；如果有结果集， 结果集也保存在ProcedureResult中。
	 * 
	 * @param pc
	 * @param connection
	 * @return
	 */
	public ProcedureResult execProcedure(Procedure procedure,
			Connection connection) throws Exception {

		ProcedureResult result = new ProcedureResult();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		String preparedSql = procedure.getProcedureSql();
		preparedSql = String.format("%s?,--msg\n?--recordSize", preparedSql);

		List<Parameter> parameters = procedure.getParameters();

		ResultSet rs = null;

		CallableStatement stmt = connection.prepareCall(preparedSql);
		this.setParameters(stmt, parameters);
		int outputParametersStartIndex = parameters.size() + 1;

		// @msg output
		stmt.registerOutParameter(outputParametersStartIndex,
				java.sql.Types.VARCHAR);
		// @size output
		stmt.registerOutParameter(outputParametersStartIndex + 1,
				java.sql.Types.INTEGER);

		try {
			log.debug(new ControllerLogMessage("开始执行存储过程，存储过程名：%s\n%s\n",
					procedure.getProcedureName(), procedure.getDebugSql()));

			rs = stmt.executeQuery();
			while (rs.next()) {
				Map<String, String> row = new HashMap<String, String>();
				for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
					String columnName = rs.getMetaData().getColumnName(i);
					String fieldValue = rs.getString(i);
					if (fieldValue == null)
						fieldValue = "";
					row.put(columnName, fieldValue);
				}
				resultList.add(row);
			}
			String msg = stmt.getString(outputParametersStartIndex);
			int recordSize = stmt.getInt(outputParametersStartIndex + 1);
			result.setMsg(msg);
			result.setRecordSize(recordSize);
			result.setList(resultList);
		} catch (SQLException e) {
			if (e.getErrorCode() == 50000) {
				// 此处打印的异常消息是给程序员看的，不需要多语言转换
				log.info(new ControllerLogMessage(
						"存储过程执行时抛出业务逻辑异常，存储过程名：%s 异常消息：%s\n%s\n", procedure
								.getProcedureName(), e.getMessage(), procedure
								.getDebugSql()));
				// 此处的errorCode和errorMessage已经是存储过程中进行过多语言转换的数据，errorCode长度固定为6位
				String errorCode = e.getMessage().substring(0, 6);
				String errorMessage = e.getMessage().substring(6);
				throw new BusinessException(errorCode, errorMessage);
			} else {
				// 此处Exception的errorMessage最终将在ControllerDispatcher中打印在控制台中，显示给用户看的是“额，服务器出了点小问题”
				String errorMessage = String.format(
						"存储过程执行时抛出系统异常，存储过程名：%s 异常消息：%s\n%s\n",
						procedure.getProcedureName(), e.getMessage(),
						procedure.getDebugSql());
				throw new Exception(errorMessage);
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				log.error(
						new ControllerLogMessage(Utils
								.getOriginalMessageFromException(e)), e);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				log.error(
						new ControllerLogMessage(Utils
								.getOriginalMessageFromException(e)), e);
			}
		}

		return result;
	}

	private void setParameters(PreparedStatement stmt,
			List<Parameter> parameters) throws Exception {
		for (int i = 0; i < parameters.size(); i++) {
			Parameter parameter = parameters.get(i);

			if (!parameter.isNullValue()) {
				Method method = PreparedStatementMethodMap.getInstance()
						.getMethod(parameter.getType());
				if (method == null)
					throw new Exception(String.format(MultiLanguage
							.getResource("000000", "存储过程参数类型还不支持%s"), parameter
							.getType().getName()));
				method.invoke(stmt, i + 1, parameter.getValue());
			} else {
				stmt.setNull(i + 1, parameter.getNullType());
			}
		}
	}
}