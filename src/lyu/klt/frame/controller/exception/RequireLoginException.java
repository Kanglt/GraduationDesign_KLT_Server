/**
 * 
 */
package lyu.klt.frame.controller.exception;

/**
 * 详细说明请见BaseException
 * 
 * @author Jam 2016年3月29日 下午3:41:30
 * 
 */
public class RequireLoginException extends BaseException {

	private static final long serialVersionUID = 1280754950245881007L;

	public RequireLoginException(String errorCode, String errorMessage,
			Object... errorMessageFormatArugments) {
		super(errorCode, errorMessage, errorMessageFormatArugments);
	}

}