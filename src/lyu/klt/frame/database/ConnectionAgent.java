package lyu.klt.frame.database;

import java.sql.Connection;

import lyu.klt.frame.database.config.ConfigConnection;
import lyu.klt.frame.database.connection.ConnectionPool;
import lyu.klt.frame.database.connection.ConnectionPoolFactory;

public class ConnectionAgent {

	private static ConnectionAgent AGENT;

	private ConnectionPool connectionPool;

	private ConnectionAgent() throws Exception {
		super();
		ConnectionPoolFactory cpf = new ConnectionPoolFactory(
				ConfigConnection.getDbDriverClass());
		this.connectionPool = cpf.getConnectionPool(ConfigConnection
				.getDataBaseUrl());
	}

	public static ConnectionAgent getInstance() throws Exception {
		if (AGENT == null)
			AGENT = new ConnectionAgent();
		return AGENT;
	}

	public Connection getConnection() throws Exception {
		return this.connectionPool.getConnection();
	}
}