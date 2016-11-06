package lyu.klt.frame.database.core;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.transaction.TransactionSession;

/**
 * 本类是DataBaseService的代管类，主要功能是取出ControllerContext上下文中的数据库连接，
 * 并委托给DataBaseService完成实际数据库操作
 * 
 * @author Jam 2016年4月2日 上午8:41:59
 * 
 */
public class DataBaseServiceAgent {

	private static Log log = LogFactory.getLog(DataBaseServiceAgent.class);

	public ProcedureResult execProcedure(Procedure procedure) throws Exception {
		Connection connection = ControllerContext.getCurrentInstance()
				.openTransactionSession().getConnection();
		DataBaseService em = new DataBaseService();
		ProcedureResult result = em.execProcedure(procedure, connection);
		return result;
	}

	public static void main(String[] args) throws Exception {
		ControllerContext context = ControllerContext.getCurrentInstance();
		TransactionSession transaction = context.openTransactionSession();

		transaction.startTransaction();

		Procedure procedure = new Procedure("fjzx_frame_sp_test");

		procedure.addParameterString("operatorId", "");
		procedure.addParameterString("operatorAddress", null);

		ProcedureResult pr = new DataBaseServiceAgent()
				.execProcedure(procedure);

		log.debug(pr.getRecordAsJSONObject().toString());

		transaction.rollbackTransaction();

		context.closeTransactionSession();
	}
}