package lyu.klt.frame.database.config;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.security.RSATool;
import lyu.klt.frame.utils.Utils;

/**
 * 本类负责解析config_connection.prop，并且config_connection.prop应保存在/WEB-INF/下
 * 
 * @author jam
 * 
 */
public class ConfigConnection {

	private static Log log = LogFactory.getLog(ConfigConnection.class);

	private static ConfigConnection instance = null;

	private String dbDriverClass;
	private String dbUrl;
	private String user;
	private String password;
	private int minConnections;
	private int maxConnections;

	private ConfigConnection() {
		Properties config = new Properties();
		FileInputStream fis = null;
		try {
			String path = ConfigConnection.class.getResource("/").getPath()
					+ "../config_connection.prop";
			fis = new FileInputStream(URLDecoder.decode(path, "UTF-8"));
			config.load(fis);

			dbDriverClass = config.getProperty("dbDriverClass");
			dbUrl = config.getProperty("dbUrl");
			user = config.getProperty("user");
			password = config.getProperty("password");

			this.readConnections(config);
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					log.error(Utils.getOriginalMessageFromException(e), e);
				}
			}
		}
	}

	/**
	 * 
	 * @author Jam 2016年4月1日 下午2:43:49
	 * @param config
	 */
	private void readConnections(Properties config) {
		try {
			this.minConnections = Integer.valueOf(config
					.getProperty("minConnections"));
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
			this.minConnections = 10;
		}

		try {
			this.maxConnections = Integer.valueOf(config
					.getProperty("maxConnections"));
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
			this.maxConnections = 100;
		}
	}

	private static ConfigConnection getInstance() {
		if (instance == null)
			instance = new ConfigConnection();
		return instance;
	}

	public static String getDbDriverClass() {
		return ConfigConnection.getInstance().dbDriverClass;
	}

	public static String getDataBaseUrl() {
		try {
			String userDecrypted = RSATool.decrypt(ConfigConnection
					.getInstance().user.replaceAll("_", "=")).substring("user".length());
			String passwordDecrypted = RSATool.decrypt(ConfigConnection
					.getInstance().password.replaceAll("_", "=")).substring("password".length());

			return String.format("%s;user=%s;password=%s",
					ConfigConnection.getInstance().dbUrl, userDecrypted,
					passwordDecrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getMinConnections() {
		return ConfigConnection.getInstance().minConnections;
	}

	public static int getMaxConnections() {
		return ConfigConnection.getInstance().maxConnections;
	}

}