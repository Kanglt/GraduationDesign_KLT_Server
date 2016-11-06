/**
 * 
 */
package lyu.klt.frame.controller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * PageController里，被@PageMethod注解过的方法才可以被作为Controller的方法执行，
 * 例如前端url: /product/productManagementController?index，后端注解Controller类 @PageController ProductManagementController{...}
 * 将会执行ProductManagementController类中的index方法
 * @author Jam 2016年3月29日 下午2:12:02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PageMethod {

}