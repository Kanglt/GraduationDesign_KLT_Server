package lyu.klt.frame.database.connection;

import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionPoolFactory {

	public ConnectionPoolFactory(String dbDriverClass) throws Exception {
		this.addDriverClass(dbDriverClass);
	}

	public void addDriverClass(String dbDriverClass) throws Exception {
		DriverManager.registerDriver((Driver) Class.forName(dbDriverClass)
				.newInstance());
	}

	public ConnectionPool getConnectionPool(String dbUrl) throws Exception {
		return new ConnectionPool(new ConnectionProvider(dbUrl));
	}
}