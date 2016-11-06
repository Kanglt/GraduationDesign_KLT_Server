package lyu.klt.frame.controller.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 系统在启动时，会扫描所有WebServiceController，并为每一个WebServiceController生成一个js文件，放到web路径/js
 * /webservice/目录下，<br />
 * 本注释的值是用来指定模块名，生成的js文件名为“模块名.js”，在网页端引用这个文件后，使用“模块名.functionName()”来远程调用服务端的方法
 * ，<br />
 * 例如：<br />
 * 服务端：@WebServiceController("ProductManagement");...public function
 * getNameById(String id){...}<br />
 * 网页端引用：&lt;script src="js/webservice/ProductManagement.js"&gt;&lt;/script&gt;<br />
 * 网页端调用：&lt;script&gt;var name =
 * ProductManagement.getNameById(id);&lt;/script&gt;
 * 
 * @author Jam 2016年3月29日 下午2:12:15
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServiceController {
	String value();
}