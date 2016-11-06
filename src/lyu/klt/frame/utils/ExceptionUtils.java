/**
 * 
 */
package lyu.klt.frame.utils;

/**
 * @author Jam 2016年3月31日 下午5:01:28
 * 
 */
public class ExceptionUtils {

	public static String getOriginalMessageFromException(Exception ajaxException) {
		Throwable throwable = ajaxException.getCause();
		if (throwable == null)
			return ajaxException.getMessage();
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}
		return throwable.getMessage();
	}

	public static Throwable getOriginalException(Exception ajaxException) {
		Throwable throwable = ajaxException.getCause();
		if (throwable == null)
			return ajaxException;
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}
		return throwable;
	}
}