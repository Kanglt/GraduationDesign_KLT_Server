/**
 * 
 */
package lyu.klt.frame.controller.exception;

import lyu.klt.frame.controller.global.MultiLanguage;

/**
 * 以下五种异常中，<font
 * color="red">第1、5两种异常会引起Controller事务回滚</font>，其他3种通常没有进入事务，所以不会引起事务回滚<br />
 * <br />
 * 
 * 系统中的Exception总共分为五种：<br />
 * 1、BusinessException 由业务程序员手工抛出的异常，例如检查业务逻辑不合条件，然后抛出该异常<br />
 * 2、RequireLoginException
 * 由框架程序员手工抛出的异常，如果发现没有登录，则抛出该异常，该异常在page端表现为转向登录页面，在网页端则引起弹出登录框，以供用户重新登录<br />
 * 3、AccessException 由框架程序员手工抛出的异常，没有权限进入指定页面，则在page和网页端都是转向显示“您没有权限访问该资源“的出错页面<br />
 * 4、NetworkException 由框架程序员手工抛出的异常，该异常在手机端将引起手机端显示”网络不通，请检查网络状态“<br />
 * 5、Exception
 * 通用的异常，该异常将导致page端转向”服务器出了点小问题“页面，在网页端弹出提示框”服务器出了点小问题“，在手机端弹出提示框”服务器出了点小问题“<br />
 * 
 * @author Jam 2016年3月29日 下午3:20:57
 * 
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -8997458587986982082L;

	private String errorCode;
	private String errorMessage;

	public BaseException(String errorCode, String errorMessage,
			Object... errorMessageFormatArugments) {

		this.errorCode = errorCode;

		try {
			String template = MultiLanguage
					.getResource(errorCode, errorMessage);
			String resultErrorMessage = String.format(template,
					errorMessageFormatArugments);

			this.errorMessage = resultErrorMessage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return this.errorMessage;
	}

}