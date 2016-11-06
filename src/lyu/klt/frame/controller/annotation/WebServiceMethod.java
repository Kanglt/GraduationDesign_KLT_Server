package lyu.klt.frame.controller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 在WebServiceController中，只有被@WebServiceMethod注解过的public方法，才可以被网页前端远程调用
 * 
 * @author Jam 2016年3月29日 下午2:16:21
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServiceMethod {
	boolean realtime() default false;
}