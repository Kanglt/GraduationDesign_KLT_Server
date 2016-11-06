package lyu.klt.frame.database.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionFactory {

	public ConnectionFactory(String dbDriverClass) throws Exception {
		DriverManager.registerDriver((Driver) Class.forName(dbDriverClass)
				.newInstance());
	}

	public Connection getConnection(String dbUrl) throws Exception {
		Connection connection = DriverManager.getConnection(dbUrl);
		connection.setAutoCommit(false);
		connection
				.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return connection;
	}

}