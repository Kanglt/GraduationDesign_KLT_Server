package lyu.klt.frame.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

	private String dbUrl;

	public ConnectionProvider(String dbUrl) {
		super();
		this.dbUrl = dbUrl;
	}

	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(this.dbUrl);
		connection.setAutoCommit(false);
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return connection;
	}
}