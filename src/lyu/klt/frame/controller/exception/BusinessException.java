/**
 * 
 */
package lyu.klt.frame.controller.exception;

/**
 * 详细说明请见BaseException
 * 
 * @author Jam 2016年3月29日 下午3:40:49
 * 
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = -5592120414208628785L;

	public BusinessException(String errorCode, String errorMessage,
			Object... errorMessageFormatArugments) {
		super(errorCode, errorMessage, errorMessageFormatArugments);
	}

}