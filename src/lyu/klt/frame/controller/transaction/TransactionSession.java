/**
 * 
 */
package lyu.klt.frame.controller.transaction;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.annotation.ControllerLogMessage;

/**
 * @author Jam 2016年3月30日 下午2:00:40
 * 
 */
public class TransactionSession {

	private static Log log = LogFactory.getLog(TransactionSession.class);

	private Connection connection;
	private boolean transactionStarted;

	/**
	 * @param connection
	 */
	public TransactionSession(Connection connection) {
		this.connection = connection;
		this.transactionStarted = false;
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:01:38
	 * @throws Exception
	 */
	synchronized public void commitTransaction() throws Exception {
		this.connection.commit();
		this.connection.close();
		this.transactionStarted = false;

		log.debug(new ControllerLogMessage("事务提交，connection:%s", connection));
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:02:24
	 * @throws Exception
	 */
	synchronized public void rollbackTransaction() throws Exception {
		this.connection.rollback();
		this.connection.close();
		this.transactionStarted = false;

		log.debug(new ControllerLogMessage("事务回滚，connection:%s", connection));
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:02:51
	 * @throws Exception
	 */
	synchronized public void startTransaction() throws Exception {
		this.connection.setSavepoint();
		this.transactionStarted = true;

		log.debug(new ControllerLogMessage("事务开始，connection:%s", connection));
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午2:06:40
	 * @return
	 */
	synchronized public boolean isStarted() {
		return this.transactionStarted;
	}

	/**
	 * 
	 * @author Jam 2016年4月1日 上午10:36:46
	 * @return
	 */
	synchronized public Connection getConnection() {
		return this.connection;
	}

}