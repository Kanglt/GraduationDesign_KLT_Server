/**
 * 
 */
package lyu.klt.frame.controller.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.annotation.Initialization;
import lyu.klt.frame.controller.annotation.ResourceLanguage;
import lyu.klt.frame.controller.config.ConfigController;
import lyu.klt.frame.controller.context.ComponentSelectMapping;
import lyu.klt.frame.controller.context.PageControllerRequestMapping;
import lyu.klt.frame.controller.context.UploadDownloadControllerRequestMapping;
import lyu.klt.frame.controller.context.WebServiceControllerRequestMapping;
import lyu.klt.frame.controller.global.IResourceLanguage;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.utils.PackageScanner;
import lyu.klt.frame.utils.Utils;

/**
 * @author Jam 2016年3月30日 下午5:20:22
 * 
 */
public class Initializer extends HttpServlet {

	private static final long serialVersionUID = -7550287292471535257L;

	private static Log log = LogFactory.getLog(Initializer.class);

	@Override
	public void init() throws ServletException {
		super.init();

		this.doInit();

		try {
			String path = URLDecoder.decode(Initializer.class.getResource("/")
					.getPath(), "UTF-8");
			System.out.println(path);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author Jam 2016年3月30日 下午5:21:20
	 */
	private void doInit() {
		Timestamp startTime = Utils.getTimestamp();
		try {
			log.debug("初始化开始");
			// 初始化Controller
			this.doInitController();
			this.doInitResource();
			log.debug(String.format("初始化结束，共耗时：%s",
					Utils.getDurationTime(startTime)));

			this.doAutoTask();
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		}
	}

	private void doInitResource() {
		// 取出符合扫描条件的包下所有的类
		Set<Class<?>> classes = this.getBasePackageToScan();
		for (Class<?> clazz : classes) {
			try {
				if (clazz.isAnnotationPresent(ResourceLanguage.class)) {
					Object resource = clazz.newInstance();
				 	if (resource instanceof IResourceLanguage)
						log.error(String
								.format("具有注解ResourceLanguage的类%s必须实现接口IResourceLanguage",
										clazz.getName()));
					MultiLanguage.init((IResourceLanguage) resource);
				}
			} catch (Exception e) {
				log.error(Utils.getOriginalMessageFromException(e), e);
			}
		}
	}

	private void doAutoTask() {
		// 取出符合扫描条件的包下所有的类
		Set<Class<?>> classes = this.getBasePackageToScan();
		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(Initialization.class)) {
				try {
					clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void doInitController() throws Exception {
		// 取出符合扫描条件的包下所有的类
		Set<Class<?>> classes = this.getBasePackageToScan();
		// 初始化PageController映射表
		PageControllerRequestMapping.getInstance().init(classes);
		log.info("初始化PageController映射表完成(1/4)");

		// 初始化WebServiceController映射表
		WebServiceControllerRequestMapping.getInstance().init(classes);
		log.info("初始化WebServiceController映射表完成(2/4)");

		// 初始化UploadDownloadController映射表
		UploadDownloadControllerRequestMapping.getInstance().init(classes);
		log.info("初始化UploadDownloadController映射表完成(3/4)");

		// 初始化ComoponentSelect和TreeComponentSelect的存储过程映射表
		ComponentSelectMapping.getInstance().init(classes);

		// 为WebServiceController生成对应的JavaScript文件，并放在网站/js/webservice路径下
		new WebServiceJavaScriptInterfaceFileGenerator(classes).process();
		log.info("WebServiceController对应的JavaScript文件生成完成(4/4)");
	}

	private Set<Class<?>> getBasePackageToScan() {
		Set<Class<?>> result = new HashSet<Class<?>>();
		String packageConfig = ConfigController.getBasePackageToScan();
		if (packageConfig == null || packageConfig.isEmpty())
			return result;

		String[] packageList = packageConfig.split(",");
		for (String packageName : packageList) {
			Set<Class<?>> set = PackageScanner.getClasses(packageName);
			result.addAll(set);
		}
		return result;
	}
}