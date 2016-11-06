/**
 * 
 */
package lyu.klt.frame.controller.exception;

/**
 * 详细说明请见BaseException
 * 
 * @author Jam 2016�?�?9�?下午3:42:15
 * 
 */
public class AccessException extends BaseException {

	private static final long serialVersionUID = 7051187325013090906L;

	public AccessException(String errorCode, String errorMessage,
			Object... errorMessageFormatArugments) {
		super(errorCode, errorMessage, errorMessageFormatArugments);
	}

}