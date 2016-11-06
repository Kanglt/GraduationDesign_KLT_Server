package lyu.klt.frame.controller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Access {
	Class<? extends IAccessHandler> handler();

	String requiredOperationCode() default "";
}
