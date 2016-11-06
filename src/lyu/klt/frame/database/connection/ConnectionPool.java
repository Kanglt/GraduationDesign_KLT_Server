package lyu.klt.frame.database.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.database.config.ConfigConnection;
import lyu.klt.frame.utils.Utils;

public class ConnectionPool {

	private static Log log = LogFactory.getLog(ConnectionPool.class);

	private int minConnections = ConfigConnection.getMinConnections();
	private int maxConnections = ConfigConnection.getMaxConnections();

	private ConnectionProvider connectionProvider;
	private List<Connection> pooling;

	public ConnectionPool(ConnectionProvider connectionProvider)
			throws Exception {
		this.connectionProvider = connectionProvider;

		this.pooling = new ArrayList<Connection>();
		for (int i = 0; i < this.minConnections; i++) {
			this.pooling.add(this.connectionProvider.getConnection());
		}
	}

	private synchronized Connection poolingAction(int action,
			Connection usedConnection) throws Exception {
		if (action == ACTION_GET_CONNECTION) {
			if (this.pooling.isEmpty()) {
				Connection newConnection = this.connectionProvider
						.getConnection();
				return newConnection;
			}
			Connection newConnection = this.pooling.get(0);
			this.pooling.remove(0);
			if (!newConnection.isValid(1000)) {
				try {
					newConnection.close();
				} catch (Exception e) {
					log.error(Utils.getOriginalMessageFromException(e), e);
				}
				newConnection = this.connectionProvider.getConnection();
			}
			return getProxy(newConnection, this);
		} else if (action == ACTION_RELEASE_CONNECTION) {
			if (usedConnection == null || usedConnection.isClosed())
				return null;
			if (this.pooling.size() >= this.maxConnections) {
				usedConnection.close();
				return null;
			}
			this.pooling.add(usedConnection);
		}
		return null;
	}

	public Connection getConnection() throws Exception {
		Connection connection = poolingAction(ACTION_GET_CONNECTION, null);
		return connection;
	}

	private Connection getProxy(final Connection connection,
			final ConnectionPool connectionPool) {
		InvocationHandler handler = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object args[])
					throws Exception {
				if (method.getName().equals("close")) {
					connectionPool.releaseConnection(connection);
					return null;
				}
				return method.invoke(connection, args);
			}
		};
		return (Connection) Proxy.newProxyInstance(
				Connection.class.getClassLoader(),
				new Class[] { Connection.class }, handler);
	}

	public void releaseConnection(Connection connection) throws Exception {
		poolingAction(ACTION_RELEASE_CONNECTION, connection);
	}

	public void close() throws Exception {
		for (int i = 0; i < this.pooling.size(); i++) {
			this.pooling.get(i).close();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

	public static final int ACTION_GET_CONNECTION = 0;
	public static final int ACTION_RELEASE_CONNECTION = 1;
}