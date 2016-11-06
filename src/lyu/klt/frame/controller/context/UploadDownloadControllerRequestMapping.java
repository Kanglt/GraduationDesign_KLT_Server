package lyu.klt.frame.controller.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lyu.klt.frame.controller.annotation.UploadDownloadController;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.upload.IUploadHandler;
import lyu.klt.frame.utils.Utils;

public class UploadDownloadControllerRequestMapping {

	private static UploadDownloadControllerRequestMapping INSTANCE = new UploadDownloadControllerRequestMapping();

	public static UploadDownloadControllerRequestMapping getInstance()
			throws Exception {
		return INSTANCE;
	}

	private Map<String, Class<? extends IUploadHandler>> uploadControllerTypeMapping;

	private UploadDownloadControllerRequestMapping() {
		this.uploadControllerTypeMapping = new HashMap<String, Class<? extends IUploadHandler>>();
	}

	public void init(Set<Class<?>> classes) throws Exception {
		this.initWebServiceControllerMapping(classes);
	}

	private void initWebServiceControllerMapping(Set<Class<?>> classes)
			throws Exception {
		this.initUploadControllerTypeMapping(classes);
	}

	@SuppressWarnings("unchecked")
	private void initUploadControllerTypeMapping(Set<Class<?>> classes)
			throws Exception {

		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(UploadDownloadController.class)) {
				UploadDownloadController annotation = clazz
						.getAnnotation(UploadDownloadController.class);
				String uploadDownloadControllerId = annotation.id();
				if (uploadDownloadControllerId == null
						|| uploadDownloadControllerId.isEmpty())
					throw new Exception(
							String.format(
									MultiLanguage
											.getResource("000000",
													"UploadController没有指定类型名，UploadController：%s"),
									clazz.getName()));

				if (this.uploadControllerTypeMapping
						.containsKey(uploadDownloadControllerId)) {
					String existsUploadControllerName = this.uploadControllerTypeMapping
							.get(uploadDownloadControllerId).getName();
					String toAddUploadControllerName = clazz.getName();
					String msg = String
							.format(MultiLanguage
									.getResource(
											"000000",
											"UploadController扫描发现重复类型名，已存在的类型名：%s，已存在的UploadController：%s，试图添加的类型名：%s，试图添加的UploadController：%s"),
									uploadDownloadControllerId,
									existsUploadControllerName,
									uploadDownloadControllerId,
									toAddUploadControllerName);
					throw new Exception(msg);
				}
				if (!Utils.existsInterface(clazz, IUploadHandler.class))
					throw new Exception(
							String.format(
									MultiLanguage
											.getResource("000000",
													"UploadController没有实现指定接口，UploadController: %s，必须实现的指定接口：%s"),
									clazz.getName(),
									IUploadHandler.class.getName()));
				this.uploadControllerTypeMapping.put(
						uploadDownloadControllerId,
						(Class<? extends IUploadHandler>) clazz);
			}
		}
	}

	public Object getUploadDownloadControllerInstance(
			String uploadDownloadControllerId) throws Exception {
		if (uploadDownloadControllerId == null
				|| uploadDownloadControllerId.isEmpty())
			throw new Exception(
					String.format(MultiLanguage
							.getResource(
									"000000",
									"用uploadify上传文件时必须在script属性中加上uploadDownloadControllerId参数，如script: '${basePath}do.upload?uploadDownloadControllerId=report&cookie='+escape(document.cookie)")));
		Class<?> clazz = this.uploadControllerTypeMapping
				.get(uploadDownloadControllerId);
		if (clazz == null)
			throw new Exception(
					String.format(
							MultiLanguage
									.getResource("000000",
											"uploadDownloadControllerId没有对应的UploadController，uploadDownloadControllerId:%s"),
							uploadDownloadControllerId));
		return clazz.newInstance();
	}

}
