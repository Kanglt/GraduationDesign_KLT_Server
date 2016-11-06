package lyu.klt.frame.controller.listener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.servlet.Initializer;

public class Log4jListener implements ServletContextListener {

	// 这个位置不可以启用log4j，因为本文件时用来为log4j.properties设置${LOG4J_LOG_DIR}值的，在该值没有设置完成之前启用log4j会导致log4j文件路径没有正确配置
	// private static Log log = LogFactory.getLog(Log4jListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.getProperties().remove(Constants.LOG4J_LOG_DIR);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			String path = URLDecoder.decode(Initializer.class.getResource("/")
					.getPath() + "../..", "UTF-8");
			System.out.println(String.format("log file direcotry: %s", path));
			System.setProperty(Constants.LOG4J_LOG_DIR, path);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
