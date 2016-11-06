package lyu.klt.frame.controller.context;

import java.util.Set;

import lyu.klt.frame.controller.annotation.ISelectDataServiceHandler;
import lyu.klt.frame.controller.annotation.SelectDataService;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.utils.Utils;

/**
 * @author Jam 2016年3月30日 下午8:15:23
 * 
 */
public class ComponentSelectMapping {

	private static ComponentSelectMapping INSTANCE = new ComponentSelectMapping();

	public static ComponentSelectMapping getInstance() throws Exception {
		return INSTANCE;
	}

	private Class<? extends ISelectDataServiceHandler> clazz;

	private ComponentSelectMapping() {
		this.setClazz(null);
	}

	@SuppressWarnings("unchecked")
	public void init(Set<Class<?>> classes) throws Exception {

		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(SelectDataService.class)) {
				if (!Utils.existsInterface(clazz,
						ISelectDataServiceHandler.class))
					throw new Exception(
							String.format(
									MultiLanguage
											.getResource("000000",
													"@SelectDataService注解的类没有实现指定接口，Class: %s，必须实现的指定接口：%s"),
									clazz.getName(),
									ISelectDataServiceHandler.class.getName()));
				this.setClazz((Class<? extends ISelectDataServiceHandler>) clazz);
			}
		}
	}

	public Class<? extends ISelectDataServiceHandler> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends ISelectDataServiceHandler> clazz) {
		this.clazz = clazz;
	}
}