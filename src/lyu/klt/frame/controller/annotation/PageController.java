package lyu.klt.frame.controller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 系统在启动时，会扫描所有的PageController，并将PageController注解过的requestMapping加入到映射表
 * 本注解的值就是requestMapping，例如：@PageController("/product/productController");
 * 
 * @author Jam 2016年3月29日 下午2:11:41
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PageController {
	String value();
}