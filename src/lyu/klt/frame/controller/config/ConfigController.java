package lyu.klt.frame.controller.config;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.utils.Utils;

/**
 * 本类负责解析config_controller.prop，并且config_controller.prop应保存在/WEB-INF/下
 * 
 * @author jam
 * 
 */
public class ConfigController {

	private static Log log = LogFactory.getLog(ConfigController.class);

	private static ConfigController instance = null;

	private String basePackageToScan;
	private String webServiceSuffix;

	private ConfigController() {
		Properties config = new Properties();
		FileInputStream fis = null;
		try {
			String path = ConfigController.class.getResource("/").getPath()
					+ "../config_controller.prop";
			fis = new FileInputStream(URLDecoder.decode(path, "UTF-8"));
			config.load(fis);

			basePackageToScan = config.getProperty("basePackageToScan");
			webServiceSuffix = config.getProperty("webServiceSuffix");
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

	private static ConfigController getInstance() {
		if (instance == null)
			instance = new ConfigController();
		return instance;
	}

	public static String getBasePackageToScan() {
		return ConfigController.getInstance().basePackageToScan;
	}

	public static String getWebServiceSuffix() {
		return ConfigController.getInstance().webServiceSuffix;
	}

}