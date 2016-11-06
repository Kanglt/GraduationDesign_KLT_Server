package lyu.klt.frame.controller.security.config;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.utils.Utils;

/**
 * 本类负责解析config_security.prop，并且config_security.prop应保存在/WEB-INF/下
 * 
 * @author jam
 * 
 */
public class ConfigSecurity {

	private static Log log = LogFactory.getLog(ConfigSecurity.class);

	private static ConfigSecurity INSTANCE = null;

	private int cookieAliveTimeDays;
	private int loginValidTimeMinutes;

	private ConfigSecurity() {
		Properties config = new Properties();
		FileInputStream fis = null;
		try {
			String path = ConfigSecurity.class.getResource("/").getPath()
					+ "../config_security.prop";
			fis = new FileInputStream(URLDecoder.decode(path, "UTF-8"));
			config.load(fis);

			cookieAliveTimeDays = Integer.valueOf(config
					.getProperty("cookie.alive.time.days"));
			loginValidTimeMinutes = Integer.valueOf(config
					.getProperty("login.valid.time.minutes"));
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

	public static ConfigSecurity getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ConfigSecurity();
		return INSTANCE;
	}

	public int getCookieAliveTimeDays() {
		return cookieAliveTimeDays;
	}

	public void setCookieAliveTimeDays(int cookieAliveTimeDays) {
		this.cookieAliveTimeDays = cookieAliveTimeDays;
	}

	public int getLoginValidTimeMinutes() {
		return loginValidTimeMinutes;
	}

	public void setLoginValidTimeMinutes(int loginValidTimeMinutes) {
		this.loginValidTimeMinutes = loginValidTimeMinutes;
	}

}